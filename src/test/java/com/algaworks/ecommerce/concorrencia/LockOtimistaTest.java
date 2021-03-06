package com.algaworks.ecommerce.concorrencia;

import com.algaworks.ecommerce.extension.EMFactory;
import com.algaworks.ecommerce.extension.EntityManagerFactoryExtension;
import com.algaworks.ecommerce.model.Produto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import static com.algaworks.ecommerce.util.ConcorrenciaUtil.esperar;
import static com.algaworks.ecommerce.util.ConcorrenciaUtil.log;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(EntityManagerFactoryExtension.class)
public class LockOtimistaTest {

    @Test
    public void usarLockOtimista(@EMFactory EntityManagerFactory entityManagerFactory){
        Runnable runnable1 = () -> {
            var entityManager1 = entityManagerFactory.createEntityManager();
            entityManager1.getTransaction().begin();

            log("Runnable 01 vai carregar o produto 1.");
            var produto = entityManager1.find(Produto.class, 1);

            log("Runnable 01 vai esperar por 3 segundos.");
            esperar(3);

            log("Runnable 01 vai alterar o produto.");
            produto.setDescricao("Descrição detalhada.");

            log("Runnable 01 vai confirmar a transação.");
            entityManager1.getTransaction().commit();
            entityManager1.close();
        };

        Runnable runnable2 = () -> {
            EntityManager entityManager2 = entityManagerFactory.createEntityManager();
            entityManager2.getTransaction().begin();

            log("Runnable 02 vai carregar o produto 1.");
            var produto = entityManager2.find(Produto.class, 1);

            log("Runnable 02 vai esperar por 1 segundo.");
            esperar(1);

            log("Runnable 02 vai alterar o produto.");
            produto.setDescricao("Descrição massa!");

            log("Runnable 02 vai confirmar a transação.");
            entityManager2.getTransaction().commit();
            entityManager2.close();
        };

        var thread1 = new Thread(runnable1);
        var thread2 = new Thread(runnable2);

        thread1.start();
        thread2.start();

        try{
            thread1.join();
            thread2.join();
        }catch (InterruptedException ex){
            throw new RuntimeException();
        }
        var entityManager3 = entityManagerFactory.createEntityManager();
        var produto = entityManager3.find(Produto.class, 1);
        entityManager3.close();

        assertThat(produto.getDescricao()).isEqualTo("Descrição massa!");

        log("Encerrando método de teste.");
    }

}
