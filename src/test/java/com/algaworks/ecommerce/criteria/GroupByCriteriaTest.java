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

@ExtendWith(EntityManagerExtension.class)
public class GroupByCriteriaTest {

    @Test
    public void agruparResultado01(@EManager final EntityManager entityManager) {
//        Quantidade de produtos por categoria.
//        String jpql = "select c.nome, count(p.id) from Categoria c join c.produtos p group by c.id";

        var criteriaBuilder = entityManager.getCriteriaBuilder();
        var criteriaQuery = criteriaBuilder.createQuery(Object[].class);
        var root = criteriaQuery.from(Categoria.class);
        Join<Categoria, Produto> joinProduto = root.join(Categoria_.produtos, JoinType.LEFT);

        criteriaQuery.multiselect(
                root.get(Categoria_.nome),
                criteriaBuilder.count(joinProduto.get(Produto_.id))
        );

        criteriaQuery.groupBy(root.get(Categoria_.id));

        var typedQuery = entityManager.createQuery(criteriaQuery);
        var lista = typedQuery.getResultList();

        lista.forEach(arr -> System.out.println("Nome: " + arr[0] + ", Count: " + arr[1]));
    }

    @Test
    public void agruparResultado02(@EManager final EntityManager entityManager) {
//        Total de vendas por categoria.
//        String jpql = "select c.nome, sum(ip.precoProduto) from ItemPedido ip " +
//                " join ip.produto pro join pro.categorias c " +
//                " group by c.id";

        var criteriaBuilder = entityManager.getCriteriaBuilder();
        var criteriaQuery = criteriaBuilder.createQuery(Object[].class);
        Root<ItemPedido> root = criteriaQuery.from(ItemPedido.class);
        Join<ItemPedido, Produto> joinProduto = root.join(ItemPedido_.produto);
        Join<Produto, Categoria> joinProdutoCategoria = joinProduto.join(Produto_.categorias);

        criteriaQuery.multiselect(
                joinProdutoCategoria.get(Categoria_.nome),
                criteriaBuilder.sum(root.get(ItemPedido_.precoProduto))
        );

        criteriaQuery.groupBy(joinProdutoCategoria.get(Categoria_.id));

        var typedQuery = entityManager.createQuery(criteriaQuery);
        var lista = typedQuery.getResultList();

        lista.forEach(arr -> System.out.println("Nome categoria: " + arr[0] + ", Sum: " + arr[1]));

    }

    @Test
    public void agruparResultado03Exercicio(@EManager final EntityManager entityManager) {
        var criteriaBuilder = entityManager.getCriteriaBuilder();
        var criteriaQuery = criteriaBuilder.createQuery(Object[].class);
        var root = criteriaQuery.from(ItemPedido.class);
        Join<ItemPedido, Pedido> joinPedido = root.join(ItemPedido_.pedido);
        Join<Pedido, Cliente> joinCliente = joinPedido.join(Pedido_.cliente);

        criteriaQuery.multiselect(joinCliente.get(Cliente_.nome),
                criteriaBuilder.sum(root.get(ItemPedido_.precoProduto)));
        criteriaQuery.groupBy(joinCliente.get(Cliente_.id));
        var typedQuery = entityManager.createQuery(criteriaQuery);
        var lista = typedQuery.getResultList();
        lista.forEach(arr -> System.out.println("Cliente: " + arr[0] + ", total vendas: " + arr[1]));
    }

    @Test
    public void agruparResultadoComFuncoes(@EManager final EntityManager entityManager) {
//         Total de vendas por mês.
//        String jpql = "select concat(year(p.dataCriacao), '/', function('monthname', p.dataCriacao)), sum(p.total) " +
//                " from Pedido p " +
//                " group by year(p.dataCriacao), month(p.dataCriacao) ";

        var criteriaBuilder = entityManager.getCriteriaBuilder();
        var criteriaQuery = criteriaBuilder.createQuery(Object[].class);
        var root = criteriaQuery.from(Pedido.class);

        var anoCriacaoPedido = criteriaBuilder
                .function("year", Integer.class, root.get(Pedido_.dataCriacao));
        var mesCriacaoPedido = criteriaBuilder
                .function("month", Integer.class, root.get(Pedido_.dataCriacao));
        var nomeMesCriacaoPedido = criteriaBuilder
                .function("monthname", String.class, root.get(Pedido_.dataCriacao));

        var anoMesConcat = criteriaBuilder.concat(
                criteriaBuilder.concat(anoCriacaoPedido.as(String.class), "/"),
                nomeMesCriacaoPedido
        );

        criteriaQuery.multiselect(
                anoMesConcat,
                criteriaBuilder.sum(root.get(Pedido_.total))
        );

        criteriaQuery.groupBy(anoMesConcat, anoCriacaoPedido, mesCriacaoPedido);

        var typedQuery = entityManager.createQuery(criteriaQuery);
        var lista = typedQuery.getResultList();

        lista.forEach(arr -> System.out.println("Ano/Mês: " + arr[0] + ", Sum: " + arr[1]));
    }

    @Test
    public void condicionarAgrupamentoComHaving(@EManager final EntityManager entityManager) {
//         Total de vendas dentre as categorias que mais vendem.
//        String jpql = "select cat.nome, sum(ip.precoProduto) from ItemPedido ip " +
//                " join ip.produto pro join pro.categorias cat " +
//                " group by cat.id " +
//                " having sum(ip.precoProduto) > 100 ";

        var criteriaBuilder = entityManager.getCriteriaBuilder();
        var criteriaQuery = criteriaBuilder.createQuery(Object[].class);
        var root = criteriaQuery.from(ItemPedido.class);
        Join<ItemPedido, Produto> joinProduto = root.join(ItemPedido_.produto);
        Join<Produto, Categoria> joinProdutoCategoria = joinProduto.join(Produto_.categorias);

        criteriaQuery.multiselect(
                joinProdutoCategoria.get(Categoria_.nome),
                criteriaBuilder.sum(root.get(ItemPedido_.precoProduto)),
                criteriaBuilder.avg(root.get(ItemPedido_.precoProduto))
        );

        criteriaQuery.groupBy(joinProdutoCategoria.get(Categoria_.id));

        criteriaQuery.having(criteriaBuilder.greaterThan(
                criteriaBuilder.avg(
                        root.get(ItemPedido_.precoProduto)).as(BigDecimal.class),
                new BigDecimal(700)));

        var typedQuery = entityManager.createQuery(criteriaQuery);
        var lista = typedQuery.getResultList();

        lista.forEach(arr -> System.out.println(
                "Nome categoria: " + arr[0]
                        + ", SUM: " + arr[1]
                        + ", AVG: " + arr[2]));
    }

}
