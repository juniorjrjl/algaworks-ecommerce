package com.algaworks.ecommerce.criteria;

import com.algaworks.ecommerce.extension.EManager;
import com.algaworks.ecommerce.extension.EntityManagerExtension;
import com.algaworks.ecommerce.model.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@ExtendWith(EntityManagerExtension.class)
public class SubqueriesCriteriaTest {

    @Test
    public void pesquisarSubqueries01(@EManager final EntityManager entityManager) {
//         O produto ou os produtos mais caros da base.
//        String jpql = "select p from Produto p where " +
//                " p.preco = (select max(preco) from Produto)";

        var criteriaBuilder = entityManager.getCriteriaBuilder();
        var criteriaQuery = criteriaBuilder.createQuery(Produto.class);
        var root = criteriaQuery.from(Produto.class);

        criteriaQuery.select(root);

        var subquery = criteriaQuery.subquery(BigDecimal.class);
        var subqueryRoot = subquery.from(Produto.class);
        subquery.select(criteriaBuilder.max(subqueryRoot.get(Produto_.preco)));

        criteriaQuery.where(criteriaBuilder.equal(root.get(Produto_.preco), subquery));

        var typedQuery = entityManager.createQuery(criteriaQuery);

        var lista = typedQuery.getResultList();
        assertThat(lista).isNotEmpty();

        lista.forEach(obj -> System.out.println(
                "ID: " + obj.getId() + ", Nome: " + obj.getNome() + ", Preço: " + obj.getPreco()));
    }

    @Test
    public void pesquisarSubqueries02(@EManager final EntityManager entityManager) {
//         Todos os pedidos acima da média de vendas
//        String jpql = "select p from Pedido p where " +
//                " p.total > (select avg(total) from Pedido)";

        var criteriaBuilder = entityManager.getCriteriaBuilder();
        var criteriaQuery = criteriaBuilder.createQuery(Pedido.class);
        var root = criteriaQuery.from(Pedido.class);

        criteriaQuery.select(root);

        var subquery = criteriaQuery.subquery(BigDecimal.class);
        var subqueryRoot = subquery.from(Pedido.class);
        subquery.select(criteriaBuilder.avg(subqueryRoot.get(Pedido_.total)).as(BigDecimal.class));

        criteriaQuery.where(criteriaBuilder.greaterThan(root.get(Pedido_.total), subquery));

        var typedQuery = entityManager.createQuery(criteriaQuery);

        var lista = typedQuery.getResultList();
        assertThat(lista).isNotEmpty();

        lista.forEach(obj -> System.out.println(
                "ID: " + obj.getId() + ", Total: " + obj.getTotal()));
    }

    @Test
    public void pesquisarSubqueries03(@EManager final EntityManager entityManager) {
//        Bons clientes.
//        String jpql = "select c from Cliente c where " +
//                " 500 < (select sum(p.total) from Pedido p where p.cliente = c)";

        var criteriaBuilder = entityManager.getCriteriaBuilder();
        var criteriaQuery = criteriaBuilder.createQuery(Cliente.class);
        var root = criteriaQuery.from(Cliente.class);

        criteriaQuery.select(root);

        var subquery = criteriaQuery.subquery(BigDecimal.class);
        var subqueryRoot = subquery.from(Pedido.class);
        subquery.select(criteriaBuilder.sum(subqueryRoot.get(Pedido_.total)));
        subquery.where(criteriaBuilder.equal(
                root, subqueryRoot.get(Pedido_.cliente)));

        criteriaQuery.where(criteriaBuilder.greaterThan(subquery, new BigDecimal(1300)));

        var typedQuery = entityManager.createQuery(criteriaQuery);

        var lista = typedQuery.getResultList();
        assertThat(lista).isNotEmpty();

        lista.forEach(obj -> System.out.println(
                "ID: " + obj.getId() + ", Nome: " + obj.getNome()));
    }

    @Test
    public void pesquisarComIN(@EManager final EntityManager entityManager) {
//        String jpql = "select p from Pedido p where p.id in " +
//                " (select p2.id from ItemPedido i2 " +
//                "      join i2.pedido p2 join i2.produto pro2 where pro2.preco > 100)";

        var criteriaBuilder = entityManager.getCriteriaBuilder();
        var criteriaQuery = criteriaBuilder.createQuery(Pedido.class);
        var root = criteriaQuery.from(Pedido.class);

        criteriaQuery.select(root);

        var subquery = criteriaQuery.subquery(Integer.class);
        var subqueryRoot = subquery.from(ItemPedido.class);
        Join<ItemPedido, Pedido> subqueryJoinPedido = subqueryRoot.join(ItemPedido_.pedido);
        Join<ItemPedido, Produto> subqueryJoinProduto = subqueryRoot.join(ItemPedido_.produto);
        subquery.select(subqueryJoinPedido.get(Pedido_.id));
        subquery.where(criteriaBuilder.greaterThan(
                subqueryJoinProduto.get(Produto_.preco), new BigDecimal(100)));

        criteriaQuery.where(root.get(Pedido_.id).in(subquery));

        var typedQuery = entityManager.createQuery(criteriaQuery);

        var lista = typedQuery.getResultList();
        assertThat(lista).isNotEmpty();

        lista.forEach(obj -> System.out.println("ID: " + obj.getId()));
    }

    @Test
    public void pesquisarComExists(@EManager final EntityManager entityManager) {
//        Todos os produtos que já foram vendidos.
//        String jpql = "select p from Produto p where exists " +
//                " (select 1 from ItemPedido ip2 join ip2.produto p2 where p2 = p)";

        var criteriaBuilder = entityManager.getCriteriaBuilder();
        var criteriaQuery = criteriaBuilder.createQuery(Produto.class);
        var root = criteriaQuery.from(Produto.class);

        criteriaQuery.select(root);

        var subquery = criteriaQuery.subquery(Integer.class);
        var subqueryRoot = subquery.from(ItemPedido.class);
        subquery.select(criteriaBuilder.literal(1));
        subquery.where(criteriaBuilder.equal(subqueryRoot.get(ItemPedido_.produto), root));

        criteriaQuery.where(criteriaBuilder.exists(subquery));

        var typedQuery = entityManager.createQuery(criteriaQuery);

        var lista = typedQuery.getResultList();
        assertThat(lista).isNotEmpty();

        lista.forEach(obj -> System.out.println("ID: " + obj.getId()));
    }

    @Test
    public void perquisarComSubqueryExercicio(@EManager final EntityManager entityManager) {
        var criteriaBuilder = entityManager.getCriteriaBuilder();
        var criteriaQuery = criteriaBuilder.createQuery(Cliente.class);
        var root = criteriaQuery.from(Cliente.class);

        criteriaQuery.select(root);

        var subquery = criteriaQuery.subquery(Long.class);
        var subqueryRoot = subquery.from(Pedido.class);
        subquery.select(criteriaBuilder.count(criteriaBuilder.literal(1)));
        subquery.where(criteriaBuilder.equal(subqueryRoot.get(Pedido_.cliente), root));

        criteriaQuery.where(criteriaBuilder.greaterThan(subquery, 2L));

        var typedQuery = entityManager.createQuery(criteriaQuery);

        var lista = typedQuery.getResultList();
        assertThat(lista).isNotEmpty();

        lista.forEach(obj -> System.out.println("ID: " + obj.getId() + ", Nome: " + obj.getNome()));
    }

    @Test
    public void pesquisarComINExercicio(@EManager final EntityManager entityManager) {
        var criteriaBuilder = entityManager.getCriteriaBuilder();
        var criteriaQuery = criteriaBuilder.createQuery(Pedido.class);
        var root = criteriaQuery.from(Pedido.class);

        criteriaQuery.select(root);

        var subquery = criteriaQuery.subquery(Integer.class);
        var subqueryRoot = subquery.from(ItemPedido.class);
        subquery.select(subqueryRoot.get(ItemPedido_.pedido).get(Pedido_.id));
        subquery.distinct(true);
        Join<ItemPedido, Produto> subJoinProduto = subqueryRoot.join(ItemPedido_.produto);
        subquery.where(criteriaBuilder.equal(subJoinProduto.join(Produto_.categorias).get(Categoria_.id), 2));
        criteriaQuery.where(root.get(Pedido_.id).in(subquery));
        var typedQuery = entityManager.createQuery(criteriaQuery);
        var lista = typedQuery.getResultList();
        assertThat(lista).isNotEmpty();
    }

    @Test
    public void perquisarComExistsExercicio(@EManager final EntityManager entityManager) {
        var criteriaBuilder = entityManager.getCriteriaBuilder();
        var criteriaQuery = criteriaBuilder.createQuery(Produto.class);
        var root = criteriaQuery.from(Produto.class);

        criteriaQuery.select(root);

        var subquery = criteriaQuery.subquery(Integer.class);
        var subqueryRoot = subquery.from(ItemPedido.class);
        subquery.select(criteriaBuilder.literal(1));
        subquery.where(
                criteriaBuilder.equal(subqueryRoot.get(ItemPedido_.produto), root),
                criteriaBuilder.notEqual(
                        subqueryRoot.get(ItemPedido_.precoProduto), root.get(Produto_.preco))
        );

        criteriaQuery.where(criteriaBuilder.exists(subquery));

        var typedQuery = entityManager.createQuery(criteriaQuery);

        var lista = typedQuery.getResultList();
        assertThat(lista).isNotEmpty();

        lista.forEach(obj -> System.out.println("ID: " + obj.getId()));
    }

    @Test
    public void pesquisarComAll01(@EManager final EntityManager entityManager) {
//        Todos os produtos que SEMPRE foram vendidos pelo preco atual.
//        String jpql = "select p from Produto p where " +
//                " p.preco = ALL (select precoProduto from ItemPedido where produto = p)";

        var criteriaBuilder = entityManager.getCriteriaBuilder();
        var criteriaQuery = criteriaBuilder.createQuery(Produto.class);
        var root = criteriaQuery.from(Produto.class);

        criteriaQuery.select(root);

        var subquery = criteriaQuery.subquery(BigDecimal.class);
        var subqueryRoot = subquery.from(ItemPedido.class);
        subquery.select(subqueryRoot.get(ItemPedido_.precoProduto));
        subquery.where(criteriaBuilder.equal(subqueryRoot.get(ItemPedido_.produto), root));

        criteriaQuery.where(criteriaBuilder.equal(
                root.get(Produto_.preco), criteriaBuilder.all(subquery)));

        var typedQuery = entityManager.createQuery(criteriaQuery);

        var lista = typedQuery.getResultList();
        assertThat(lista).isNotEmpty();

        lista.forEach(obj -> System.out.println("ID: " + obj.getId()));
    }

    @Test
    public void pesquisarComAll02(@EManager final EntityManager entityManager) {
//        Todos os produtos não foram vendidos mais depois que encareceram
//        String jpql = "select p from Produto p where " +
//                " p.preco > ALL (select precoProduto from ItemPedido where produto = p)";
//                " and exists (select 1 from ItemPedido where produto = p)";

        var criteriaBuilder = entityManager.getCriteriaBuilder();
        var criteriaQuery = criteriaBuilder.createQuery(Produto.class);
        var root = criteriaQuery.from(Produto.class);

        criteriaQuery.select(root);

        var subquery = criteriaQuery.subquery(BigDecimal.class);
        var subqueryRoot = subquery.from(ItemPedido.class);
        subquery.select(subqueryRoot.get(ItemPedido_.precoProduto));
        subquery.where(criteriaBuilder.equal(subqueryRoot.get(ItemPedido_.produto), root));

        criteriaQuery.where(
                criteriaBuilder.greaterThan(
                        root.get(Produto_.preco), criteriaBuilder.all(subquery)),
                criteriaBuilder.exists(subquery)
        );

        var typedQuery = entityManager.createQuery(criteriaQuery);

        var lista = typedQuery.getResultList();
        assertThat(lista).isNotEmpty();

        lista.forEach(obj -> System.out.println("ID: " + obj.getId()));
    }

    @Test
    public void pesquisarComAny01(@EManager final EntityManager entityManager) {
//        Todos os produtos que já foram vendidos, pelo menos, uma vez pelo preço atual.
//        String jpql = "select p from Produto p " +
//                " where p.preco = ANY (select precoProduto from ItemPedido where produto = p)";

        var criteriaBuilder = entityManager.getCriteriaBuilder();
        var criteriaQuery = criteriaBuilder.createQuery(Produto.class);
        var root = criteriaQuery.from(Produto.class);

        criteriaQuery.select(root);

        var subquery = criteriaQuery.subquery(BigDecimal.class);
        var subqueryRoot = subquery.from(ItemPedido.class);
        subquery.select(subqueryRoot.get(ItemPedido_.precoProduto));
        subquery.where(criteriaBuilder.equal(subqueryRoot.get(ItemPedido_.produto), root));

        criteriaQuery.where(
                criteriaBuilder.equal(
                        root.get(Produto_.preco), criteriaBuilder.any(subquery))
        );

        var typedQuery = entityManager.createQuery(criteriaQuery);

        List<Produto> lista = typedQuery.getResultList();
        assertThat(lista).isNotEmpty();

        lista.forEach(obj -> System.out.println("ID: " + obj.getId()));

    }

    @Test
    public void pesquisarComAny02(@EManager final EntityManager entityManager) {
//        Todos os produtos que já foram vendidos por um preco diferente do atual
//        String jpql = "select p from Produto p " +
//                " where p.preco <> ANY (select precoProduto from ItemPedido where produto = p)";

        var criteriaBuilder = entityManager.getCriteriaBuilder();
        var criteriaQuery = criteriaBuilder.createQuery(Produto.class);
        var root = criteriaQuery.from(Produto.class);

        criteriaQuery.select(root);

        var subquery = criteriaQuery.subquery(BigDecimal.class);
        var subqueryRoot = subquery.from(ItemPedido.class);
        subquery.select(subqueryRoot.get(ItemPedido_.precoProduto));
        subquery.where(criteriaBuilder.equal(subqueryRoot.get(ItemPedido_.produto), root));

        criteriaQuery.where(
                criteriaBuilder.notEqual(
                        root.get(Produto_.preco), criteriaBuilder.any(subquery))
        );

        var typedQuery = entityManager.createQuery(criteriaQuery);

        var lista = typedQuery.getResultList();
        assertThat(lista).isNotEmpty();

        lista.forEach(obj -> System.out.println("ID: " + obj.getId()));

    }

    @Test
    public void pesquisarComAllExercicio(@EManager final EntityManager entityManager) {
//        Todos os produtos que sempre foram vendidos pelo mesmo preço.
//        String jpql = "select distinct p from ItemPedido ip join ip.produto p where " +
//                " ip.precoProduto = ALL " +
//                " (select precoProduto from ItemPedido where produto = p and id <> ip.id)";

        var criteriaBuilder = entityManager.getCriteriaBuilder();
        var criteriaQuery = criteriaBuilder.createQuery(Produto.class);
        var root = criteriaQuery.from(ItemPedido.class);

        criteriaQuery.select(root.get(ItemPedido_.produto));
        criteriaQuery.distinct(true);

        var subquery = criteriaQuery.subquery(BigDecimal.class);
        var subqueryRoot = subquery.from(ItemPedido.class);
        subquery.select(subqueryRoot.get(ItemPedido_.precoProduto));
        subquery.where(
                criteriaBuilder.equal(subqueryRoot.get(ItemPedido_.produto), root.get(ItemPedido_.produto)),
                criteriaBuilder.notEqual(subqueryRoot, root)
        );

        criteriaQuery.where(
                criteriaBuilder.equal(
                        root.get(ItemPedido_.precoProduto), criteriaBuilder.all(subquery))
        );

        var typedQuery = entityManager.createQuery(criteriaQuery);

        var lista = typedQuery.getResultList();
        assertThat(lista).isNotEmpty();

        lista.forEach(obj -> System.out.println("ID: " + obj.getId()));
    }
}
