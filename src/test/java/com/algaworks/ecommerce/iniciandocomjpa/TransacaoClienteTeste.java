package com.algaworks.ecommerce.iniciandocomjpa;

import com.algaworks.ecommerce.extension.EManager;
import com.algaworks.ecommerce.extension.EntityManagerExtension;
import com.algaworks.ecommerce.model.Cliente;
import com.algaworks.ecommerce.model.SexoCliente;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(EntityManagerExtension.class)
public class TransacaoClienteTeste {

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
        cliente.setNome("Juca Barros");
        cliente.setCpf("973.841.010-00");
        cliente.setSexo(SexoCliente.MASCULINO);
        entityManager.getTransaction().begin();
        cliente = entityManager.merge(cliente);
        entityManager.getTransaction().commit();
        entityManager.clear();
        var clienteVerificacao = entityManager.find(Cliente.class, cliente.getId());
        assertThat(clienteVerificacao).isNotNull();
    }

}
