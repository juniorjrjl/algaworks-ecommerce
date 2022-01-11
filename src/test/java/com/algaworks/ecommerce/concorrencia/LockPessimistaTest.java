package com.algaworks.ecommerce.concorrencia;

import com.algaworks.ecommerce.extension.EMFactory;
import com.algaworks.ecommerce.extension.EntityManagerFactoryExtension;
import com.algaworks.ecommerce.model.Produto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.LockModeType;

import static com.algaworks.ecommerce.util.ConcorrenciaUtil.esperar;
import static com.algaworks.ecommerce.util.ConcorrenciaUtil.log;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(EntityManagerFactoryExtension.class)
public class LockPessimistaTest {

    @Test
    public void usarLockPessimistaLockModeTypePessimisticRead(@EMFactory EntityManagerFactory entityManagerFactory) {
        Runnable runnable1 = () -> {
            log("Iniciando Runnable 01.");

            var novaDescricao = "Descrição detalhada. CTM: " + System.currentTimeMillis();

            var entityManager1 = entityManagerFactory.createEntityManager();
            entityManager1.getTransaction().begin();

            log("Runnable 01 vai carregar o produto 1.");
            var produto = entityManager1.find(
                    Produto.class, 1, LockModeType.PESSIMISTIC_READ);

            log("Runnable 01 vai alterar o produto.");
            produto.setDescricao(novaDescricao);

            log("Runnable 01 vai esperar por 3 segundo(s).");
            esperar(3);

            log("Runnable 01 vai confirmar a transação.");
            entityManager1.getTransaction().commit();
            entityManager1.close();

            log("Encerrando Runnable 01.");
        };

        Runnable runnable2 = () -> {
            log("Iniciando Runnable 02.");

            var novaDescricao = "Descrição massa! CTM: " + System.currentTimeMillis();

            var entityManager2 = entityManagerFactory.createEntityManager();
            entityManager2.getTransaction().begin();

            log("Runnable 02 vai carregar o produto 2.");
            var produto = entityManager2.find(
                    Produto.class, 1, LockModeType.PESSIMISTIC_READ);

            log("Runnable 02 vai alterar o produto.");
            produto.setDescricao(novaDescricao);

            log("Runnable 02 vai esperar por 1 segundo(s).");
            esperar(1);

            log("Runnable 02 vai confirmar a transação.");
            entityManager2.getTransaction().commit();
            entityManager2.close();

            log("Encerrando Runnable 02.");
        };

        var thread1 = new Thread(runnable1);
        var thread2 = new Thread(runnable2);

        thread1.start();

        esperar(1);
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        var entityManager3 = entityManagerFactory.createEntityManager();
        var produto = entityManager3.find(Produto.class, 1);
        entityManager3.close();

        assertThat(produto.getDescricao()).startsWith("Descrição massa!");

        log("Encerrando método de teste.");
    }

    @Test
    public void usarLockPessimistaLockModeTypePessimisticWrite(@EMFactory EntityManagerFactory entityManagerFactory) {
        Runnable runnable1 = () -> {
            log("Iniciando Runnable 01.");

            var novaDescricao = "Descrição detalhada. CTM: " + System.currentTimeMillis();

            var entityManager1 = entityManagerFactory.createEntityManager();
            entityManager1.getTransaction().begin();

            log("Runnable 01 vai carregar o produto 1.");
            var produto = entityManager1.find(
                    Produto.class, 1, LockModeType.PESSIMISTIC_WRITE);

            log("Runnable 01 vai alterar o produto.");
            produto.setDescricao(novaDescricao);

            log("Runnable 01 vai esperar por 3 segundo(s).");
            esperar(3);

            log("Runnable 01 vai confirmar a transação.");
            entityManager1.getTransaction().commit();
            entityManager1.close();

            log("Encerrando Runnable 01.");
        };

        Runnable runnable2 = () -> {
            log("Iniciando Runnable 02.");

            var novaDescricao = "Descrição massa! CTM: " + System.currentTimeMillis();

            var entityManager2 = entityManagerFactory.createEntityManager();
            entityManager2.getTransaction().begin();

            log("Runnable 02 vai carregar o produto 2.");
            var produto = entityManager2.find(
                    Produto.class, 1, LockModeType.PESSIMISTIC_WRITE);

            log("Runnable 02 vai alterar o produto.");
            produto.setDescricao(novaDescricao);

            log("Runnable 02 vai esperar por 1 segundo(s).");
            esperar(1);

            log("Runnable 02 vai confirmar a transação.");
            entityManager2.getTransaction().commit();
            entityManager2.close();

            log("Encerrando Runnable 02.");
        };

        var thread1 = new Thread(runnable1);
        var thread2 = new Thread(runnable2);

        thread1.start();

        esperar(1);
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        var entityManager3 = entityManagerFactory.createEntityManager();
        var produto = entityManager3.find(Produto.class, 1);
        entityManager3.close();

        assertThat(produto.getDescricao()).startsWith("Descrição massa!");

        log("Encerrando método de teste.");
    }

    @Test
    public void misturarTiposDeLocks(@EMFactory EntityManagerFactory entityManagerFactory) {
        Runnable runnable1 = () -> {
            log("Iniciando Runnable 01.");

            var novaDescricao = "Descrição detalhada. CTM: " + System.currentTimeMillis();

            var entityManager1 = entityManagerFactory.createEntityManager();
            entityManager1.getTransaction().begin();

            log("Runnable 01 vai carregar o produto 1.");
            var produto = entityManager1.find(
                    Produto.class, 1, LockModeType.PESSIMISTIC_READ);

            log("Runnable 01 vai alterar o produto.");
            produto.setDescricao(novaDescricao);

            log("Runnable 01 vai esperar por 3 segundo(s).");
            esperar(3);

            log("Runnable 01 vai confirmar a transação.");
            entityManager1.getTransaction().commit();
            entityManager1.close();

            log("Encerrando Runnable 01.");
        };

        Runnable runnable2 = () -> {
            log("Iniciando Runnable 02.");

            var novaDescricao = "Descrição massa! CTM: " + System.currentTimeMillis();

            var entityManager2 = entityManagerFactory.createEntityManager();
            entityManager2.getTransaction().begin();

            log("Runnable 02 vai carregar o produto 2.");
            var produto = entityManager2.find(
                    Produto.class, 1, LockModeType.PESSIMISTIC_WRITE);

            log("Runnable 02 vai alterar o produto.");
            produto.setDescricao(novaDescricao);

            log("Runnable 02 vai esperar por 1 segundo(s).");
            esperar(1);

            log("Runnable 02 vai confirmar a transação.");
            entityManager2.getTransaction().commit();
            entityManager2.close();

            log("Encerrando Runnable 02.");
        };

        var thread1 = new Thread(runnable1);
        var thread2 = new Thread(runnable2);

        thread1.start();

        esperar(1);
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        EntityManager entityManager3 = entityManagerFactory.createEntityManager();
        Produto produto = entityManager3.find(Produto.class, 1);
        entityManager3.close();

        assertThat(produto.getDescricao()).startsWith("Descrição detalhada.");

        log("Encerrando método de teste.");
    }

    @Test
    public void usarLockNaTypedQuery(@EMFactory EntityManagerFactory entityManagerFactory) {
        Runnable runnable1 = () -> {
            log("Iniciando Runnable 01.");

            var novaDescricao = "Descrição detalhada. CTM: " + System.currentTimeMillis();

            var entityManager1 = entityManagerFactory.createEntityManager();
            entityManager1.getTransaction().begin();

            log("Runnable 01 vai carregar todos os produtos.");
            var lista = entityManager1
                    .createQuery("select p from Produto p where p.id in (3,4,5)", Produto.class)
                    .setLockMode(LockModeType.PESSIMISTIC_READ)
                    .getResultList();

            var produto = lista
                    .stream()
                    .filter(p -> p.getId().equals(3))
                    .findFirst()
                    .get();

            log("Runnable 01 vai alterar o produto de ID igual a 1.");
            produto.setDescricao(novaDescricao);

            log("Runnable 01 vai esperar por 3 segundo(s).");
            esperar(3);

            log("Runnable 01 vai confirmar a transação.");
            entityManager1.getTransaction().commit();
            entityManager1.close();

            log("Encerrando Runnable 01.");
        };

        Runnable runnable2 = () -> {
            log("Iniciando Runnable 02.");

            var novaDescricao = "Descrição massa! CTM: " + System.currentTimeMillis();

            var entityManager2 = entityManagerFactory.createEntityManager();
            entityManager2.getTransaction().begin();

            log("Runnable 02 vai carregar o produto 2.");
            var produto = entityManager2.find(
                    Produto.class, 1, LockModeType.PESSIMISTIC_WRITE);

            log("Runnable 02 vai alterar o produto.");
            produto.setDescricao(novaDescricao);

            log("Runnable 02 vai esperar por 1 segundo(s).");
            esperar(1);

            log("Runnable 02 vai confirmar a transação.");
            entityManager2.getTransaction().commit();
            entityManager2.close();

            log("Encerrando Runnable 02.");
        };

        var thread1 = new Thread(runnable1);
        var thread2 = new Thread(runnable2);

        thread1.start();

        esperar(1);
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        var entityManager3 = entityManagerFactory.createEntityManager();
        Produto produto = entityManager3.find(Produto.class, 1);
        entityManager3.close();

        assertThat(produto.getDescricao()).startsWith("Descrição massa!");

        log("Encerrando método de teste.");
    }

}
