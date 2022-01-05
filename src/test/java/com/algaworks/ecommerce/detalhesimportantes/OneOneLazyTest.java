package com.algaworks.ecommerce.detalhesimportantes;

import com.algaworks.ecommerce.extension.EManager;
import com.algaworks.ecommerce.extension.EntityManagerExtension;
import com.algaworks.ecommerce.model.Pedido;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(EntityManagerExtension.class)
public class OneOneLazyTest {

    @Test
    public void mostrarProblema(@EManager final EntityManager entityManager) {
        System.out.println("BUSCANDO UM PEDIDO:");
        var pedido = entityManager.find(Pedido.class, 1);
        assertThat(pedido).isNotNull();

        System.out.println("----------------------------------------------------");

        System.out.println("BUSCANDO UMA LISTA DE PEDIDOS:");
        var lista = entityManager
                .createQuery("select p from Pedido p", Pedido.class)
                .getResultList();
        assertThat(lista).isNotEmpty();
    }
}