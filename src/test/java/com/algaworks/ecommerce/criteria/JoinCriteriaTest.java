package com.algaworks.ecommerce.criteria;

import com.algaworks.ecommerce.extension.EManager;
import com.algaworks.ecommerce.extension.EntityManagerExtension;
import com.algaworks.ecommerce.model.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(EntityManagerExtension.class)
public class JoinCriteriaTest {

    @Test
    public void fazerJoin(@EManager final EntityManager entityManager) {
        var criteriaBuilder = entityManager.getCriteriaBuilder();
        var criteriaQuery = criteriaBuilder.createQuery(Pedido.class);
        var root = criteriaQuery.from(Pedido.class);
        Join<Pedido, Pagamento> joinPagamento = root.join("pagamento");
        Join<Pedido, ItemPedido> joinItens = root.join("itens");
        Join<ItemPedido, Produto> joinItemProduto = joinItens.join("produto");

        criteriaQuery.select(root);
        criteriaQuery.where(criteriaBuilder
                .equal(joinPagamento.get("status"), StatusPagamento.PROCESSANDO));

        var typedQuery = entityManager.createQuery(criteriaQuery);
        var lista = typedQuery.getResultList();
        assertThat(lista).isNotEmpty();
    }

    @Test
    public void fazerJoinComOn(@EManager final EntityManager entityManager) {
        var criteriaBuilder = entityManager.getCriteriaBuilder();
        var criteriaQuery = criteriaBuilder.createQuery(Pedido.class);
        var root = criteriaQuery.from(Pedido.class);
        Join<Pedido, Pagamento> joinPagamento = root.join("pagamento");
        joinPagamento.on(criteriaBuilder.equal(joinPagamento.get("status"), StatusPagamento.PROCESSANDO));

        criteriaQuery.select(root);

        var typedQuery = entityManager.createQuery(criteriaQuery);
        var lista = typedQuery.getResultList();
        assertThat(lista).isNotEmpty();
    }

    @Test
    public void fazerLeftOuterJoin(@EManager final EntityManager entityManager) {
        var criteriaBuilder = entityManager.getCriteriaBuilder();
        var criteriaQuery = criteriaBuilder.createQuery(Pedido.class);
        var root = criteriaQuery.from(Pedido.class);
        Join<Pedido, Pagamento> joinPagamento = root.join("pagamento", JoinType.LEFT);

        criteriaQuery.select(root);

        var typedQuery = entityManager.createQuery(criteriaQuery);
        var lista = typedQuery.getResultList();
        assertThat(lista).isNotEmpty();
    }

    @Test
    public void usarJoinFetch(@EManager final EntityManager entityManager) {
        var criteriaBuilder = entityManager.getCriteriaBuilder();
        var criteriaQuery = criteriaBuilder.createQuery(Pedido.class);
        var root = criteriaQuery.from(Pedido.class);

        root.fetch("notaFiscal", JoinType.LEFT);
        root.fetch("pagamento", JoinType.LEFT);
        root.fetch("cliente");
        //Join<Pedido, Cliente> joinCliente = (Join<Pedido, Cliente>) root.<Pedido, Cliente>fetch("cliente");

        criteriaQuery.select(root);

        criteriaQuery.where(criteriaBuilder.equal(root.get("id"), 1));

        var typedQuery = entityManager.createQuery(criteriaQuery);

        var pedido = typedQuery.getSingleResult();
        assertThat(pedido).isNotNull();
    }

    @Test
    public void buscarPedidosComProdutoEspecifico(@EManager final EntityManager entityManager) {
        var criteriaBuilder = entityManager.getCriteriaBuilder();
        var criteriaQuery = criteriaBuilder.createQuery(Pedido.class);
        var root = criteriaQuery.from(Pedido.class);
        Join<ItemPedido, Produto> joinItemPedidoProduto = root
                .join("itens")
                .join("produto");

        criteriaQuery.select(root);

        criteriaQuery.where(criteriaBuilder.equal(
                joinItemPedidoProduto.get("id"), 1));

        var typedQuery = entityManager.createQuery(criteriaQuery);

        var lista = typedQuery.getResultList();
        assertThat(lista).isNotEmpty();
    }

}
