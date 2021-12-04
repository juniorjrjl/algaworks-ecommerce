package com.algaworks.ecommerce.criteria;

import com.algaworks.ecommerce.extension.EManager;
import com.algaworks.ecommerce.extension.EntityManagerExtension;
import com.algaworks.ecommerce.model.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.persistence.EntityManager;
import javax.persistence.criteria.Join;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;


@ExtendWith(EntityManagerExtension.class)
public class PathExpressionsTest {

    @Test
    public void usarPathExpression(@EManager final EntityManager entityManager) {
        var criteriaBuilder = entityManager.getCriteriaBuilder();
        var criteriaQuery = criteriaBuilder.createQuery(Pedido.class);
        var root = criteriaQuery.from(Pedido.class);

        criteriaQuery.select(root);

        criteriaQuery.where(
                criteriaBuilder.like(root.get(Pedido_.cliente).get(Cliente_.nome), "M%"));

        var typedQuery = entityManager.createQuery(criteriaQuery);
        var lista = typedQuery.getResultList();

        assertThat(lista).isNotEmpty();
    }

    @Test
    public void buscarPedidosComProdutoDeIDIgual1Exercicio(@EManager final EntityManager entityManager) {
        var criteriaBuilder = entityManager.getCriteriaBuilder();
        var criteriaQuery = criteriaBuilder.createQuery(Pedido.class);
        var root = criteriaQuery.from(Pedido.class);
        Join<Pedido, ItemPedido> joinItem = root.join(Pedido_.itens);

        criteriaQuery.select(root);

        criteriaQuery.where(criteriaBuilder.equal(joinItem.get(ItemPedido_.id).get(ItemPedidoId_.produtoId), 1));
        var typedQuery = entityManager.createQuery(criteriaQuery);
        var lista = typedQuery.getResultList();
        assertThat(lista).isNotEmpty();
    }



}
