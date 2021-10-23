package com.algaworks.ecommerce.relacionamentos;

import com.algaworks.ecommerce.extension.EManager;
import com.algaworks.ecommerce.extension.EntityManagerExtension;
import com.algaworks.ecommerce.model.Pedido;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(EntityManagerExtension.class)
public class RemovendoEntidadesReferenciadasTest {

    @Test
    public void removendoEntidadeRelacionada(@EManager final EntityManager entityManager){
        var pedido = entityManager.find(Pedido.class, 1);
        assertThat(pedido.getItensPedido()).isNotEmpty();

        entityManager.getTransaction().begin();
        pedido.getItensPedido().forEach(entityManager::remove);
        entityManager.remove(pedido);
        entityManager.getTransaction().commit();
        entityManager.clear();

        var pedidoVerificacao = entityManager.find(Pedido.class, 1);
        assertThat(pedidoVerificacao).isNull();
    }

}
