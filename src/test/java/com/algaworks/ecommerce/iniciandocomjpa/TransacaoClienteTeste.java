package com.algaworks.ecommerce.iniciandocomjpa;

import com.algaworks.ecommerce.iniciandocomjpa.extension.EManager;
import com.algaworks.ecommerce.iniciandocomjpa.extension.EntityManagerExtension;
import com.algaworks.ecommerce.model.Cliente;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(EntityManagerExtension.class)
public class TransacaoClienteTeste {

    @Test
    public void removerObjeto(@EManager final EntityManager entityManager){
        var cliente = entityManager.find(Cliente.class, 1);
        entityManager.getTransaction().begin();
        entityManager.remove(cliente);
        entityManager.getTransaction().commit();
        var clienteVerificacao = entityManager.find(Cliente.class, 1);
        assertThat(clienteVerificacao).isNull();
    }

    @Test
    public void atualizarObjeto(@EManager final EntityManager entityManager){
        var cliente = entityManager.find(Cliente.class, 2);
        entityManager.getTransaction().begin();
        cliente.setNome("Jão da Silva");
        entityManager.getTransaction().commit();
        var clienteVerificacao = entityManager.find(Cliente.class, 2);
        assertThat(clienteVerificacao.getNome()).isEqualTo("Jão da Silva");
    }

    @Test
    public void inserirObjetoComMerge(@EManager final EntityManager entityManager){
        var cliente = new Cliente();
        cliente.setId(3);
        cliente.setNome("Juca Barros");
        entityManager.getTransaction().begin();
        entityManager.merge(cliente);
        entityManager.getTransaction().commit();
        entityManager.clear();
        var clienteVerificacao = entityManager.find(Cliente.class, 1);
        assertThat(clienteVerificacao).isNotNull();
    }

}
