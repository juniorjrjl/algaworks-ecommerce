package com.algaworks.ecommerce.criteria;

import com.algaworks.ecommerce.extension.EManager;
import com.algaworks.ecommerce.extension.EntityManagerExtension;
import com.algaworks.ecommerce.model.Pedido;
import com.algaworks.ecommerce.model.Pedido_;
import com.algaworks.ecommerce.model.StatusPedido;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.persistence.EntityManager;
import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(EntityManagerExtension.class)
public class OperadoresLogicosCriteriaTest {

    @Test
    public void usarOperadores(@EManager final EntityManager entityManager) {
        var criteriaBuilder = entityManager.getCriteriaBuilder();
        var criteriaQuery= criteriaBuilder.createQuery(Pedido.class);
        var root = criteriaQuery.from(Pedido.class);

        criteriaQuery.select(root);

        // select p from Pedido p where (status = 'PAGO' or status = 'AGUARDANDO') and total > 499

        criteriaQuery.where(
                criteriaBuilder.or(
                        criteriaBuilder.equal(
                                root.get(Pedido_.status), StatusPedido.AGUARDANDO),
                        criteriaBuilder.equal(
                                root.get(Pedido_.status), StatusPedido.PAGO)
                ),
                criteriaBuilder.greaterThan(
                        root.get(Pedido_.total), new BigDecimal(499))
        );

        var typedQuery = entityManager.createQuery(criteriaQuery);

        var lista = typedQuery.getResultList();
        assertThat(lista).isNotEmpty();

        lista.forEach(p -> System.out.println(
                "ID: " + p.getId() + ", Total: " + p.getTotal()));
    }
}
