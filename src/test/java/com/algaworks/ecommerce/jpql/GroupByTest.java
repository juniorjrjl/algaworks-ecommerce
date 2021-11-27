package com.algaworks.ecommerce.jpql;

import com.algaworks.ecommerce.extension.EManager;
import com.algaworks.ecommerce.extension.EntityManagerExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(EntityManagerExtension.class)
public class GroupByTest {

    @Test
    public void condicionarAgrupamentoComHaving(@EManager final EntityManager entityManager) {
//         Total de vendas dentre as categorias que mais vendem.
        var jpql = "select cat.nome, sum(ip.precoProduto) from ItemPedido ip " +
                " join ip.produto pro join pro.categorias cat " +
                " group by cat.id " +
                " having sum(ip.precoProduto) > 100 ";

        var typedQuery = entityManager.createQuery(jpql, Object[].class);

        var lista = typedQuery.getResultList();

        assertThat(lista).isNotEmpty();

        lista.forEach(arr -> System.out.println(arr[0] + ", " + arr[1]));
    }

    @Test
    public void agruparEFiltrarResultado(@EManager final EntityManager entityManager) {
//         Total de vendas por mês.
        /*var jpql = "select concat(year(p.dataCriacao), '/', function('monthname', p.dataCriacao)), sum(p.total) " +
                " from Pedido p " +
                " where year(p.dataCriacao) = year(current_date) " +
                " group by concat(year(p.dataCriacao), '/', function('monthname', p.dataCriacao)) ";*/

//         Total de vendas por categoria.
        /*var jpql = "select c.nome, sum(ip.precoProduto) from ItemPedido ip " +
                " join ip.produto pro join pro.categorias c join ip.pedido p " +
                " where year(p.dataCriacao) = year(current_date) and month(p.dataCriacao) = month(current_date) " +
                " group by c.id";*/

//        Total de vendas por cliente
        var jpql = "select c.nome, sum(ip.precoProduto) from ItemPedido ip " +
                " join ip.pedido p join p.cliente c join ip.pedido p " +
                " where year(p.dataCriacao) = year(current_date) and month(p.dataCriacao) >= (month(current_date) - 3) " +
                " group by c.id";

        var typedQuery = entityManager.createQuery(jpql, Object[].class);

        var lista = typedQuery.getResultList();

        assertThat(lista).isNotEmpty();

        lista.forEach(arr -> System.out.println(arr[0] + ", " + arr[1]));
    }

    @Test
    public void totalVendasCliente(@EManager final EntityManager entityManager) {
        var jpql = "select c.nome, sum(p.total) from Pedido p join p.cliente c group by c.id";


        var typedQuery = entityManager.createQuery(jpql, Object[].class);

        var lista = typedQuery.getResultList();

        assertThat(lista).isNotEmpty();

        lista.forEach(arr -> System.out.println(arr[0] + ", " + arr[1]));
    }

    @Test
    public void totalVendasCategoria(@EManager final EntityManager entityManager) {
        var jpql = "select c.nome, sum(p.total) from Pedido p join p.itens i join i.produto pro join pro.categorias c group by c.id";


        var typedQuery = entityManager.createQuery(jpql, Object[].class);

        var lista = typedQuery.getResultList();

        assertThat(lista).isNotEmpty();

        lista.forEach(arr -> System.out.println(arr[0] + ", " + arr[1]));
    }

    @Test
    public void agruparResultado(@EManager final EntityManager entityManager) {
        //Quantidade de produtos por categoria.
        //var jpql = "select c.nome, count(p.id) from Categoria c join c.produtos p group by c.id";

        //Total de vendas por mês.
        /*var jpql = "select concat(year(p.dataCriacao), '/', function('monthname', p.dataCriacao)), sum(p.total) " +
                " from Pedido p " +
                " group by concat(year(p.dataCriacao), '/', function('monthname', p.dataCriacao)) ";*/

        //Total de vendas por categoria.
        /*var jpql = "select c.nome, sum(ip.precoProduto) from ItemPedido ip " +
                " join ip.produto pro join pro.categorias c " +
                " group by c.id";*/

        //Total de vendas por cliente
        /*var jpql = "select c.nome, sum(ip.precoProduto) from ItemPedido ip " +
                " join ip.pedido p join p.cliente c " +
                " group by c.id";*/

//        Total de vendas por dia e por categoria
        var jpql = "select " +
                " concat(year(p.dataCriacao), '/', month(p.dataCriacao), '/', day(p.dataCriacao)), " +
                " concat(c.nome, ': ', sum(ip.precoProduto)) " +
                " from ItemPedido ip join ip.pedido p join ip.produto pro join pro.categorias c " +
                " group by concat(year(p.dataCriacao), '/', month(p.dataCriacao), '/', day(p.dataCriacao)), p.dataCriacao, c.nome " +
                " order by p.dataCriacao, c.nome ";


        var typedQuery = entityManager.createQuery(jpql, Object[].class);

        var lista = typedQuery.getResultList();

        assertThat(lista).isNotEmpty();

        lista.forEach(arr -> System.out.println(arr[0] + ", " + arr[1]));
    }

}
