package com.algaworks.ecommerce.jpql;

import com.algaworks.ecommerce.extension.EManager;
import com.algaworks.ecommerce.extension.EntityManagerExtension;
import com.algaworks.ecommerce.model.Pedido;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(EntityManagerExtension.class)
public class OperadoresLogicosTest {

    @Test
    public void usarOperadores(@EManager final EntityManager entityManager) {
        var jpql = "select p from Pedido p " +
                " where (p.status = 'AGUARDANDO' or p.status = 'PAGO') and p.total > 100";

        var typedQuery = entityManager.createQuery(jpql, Pedido.class);

        var lista = typedQuery.getResultList();
        assertThat(lista).isNotEmpty();
    }

}
