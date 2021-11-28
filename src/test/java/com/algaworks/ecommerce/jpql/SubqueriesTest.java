package com.algaworks.ecommerce.jpql;

import com.algaworks.ecommerce.extension.EManager;
import com.algaworks.ecommerce.extension.EntityManagerExtension;
import com.algaworks.ecommerce.model.Cliente;
import com.algaworks.ecommerce.model.Pedido;
import com.algaworks.ecommerce.model.Produto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(EntityManagerExtension.class)
public class SubqueriesTest {

    @Test
    public void pesquisarProdutosSempreVendidosPeloPrecoAtual(@EManager final EntityManager entityManager){
        var jpql = "select distinct p from ItemPedido i join i.produto p " +
                   " where i.precoProduto = ALL (select precoProduto from ItemPedido where produto = p and id <> i.id)";

        var typedQuery = entityManager.createQuery(jpql, Produto.class);

        var lista = typedQuery.getResultList();
        assertThat(lista).isNotEmpty();

        lista.forEach(obj -> System.out.println("ID: " + obj.getId()));
    }

    @Test
    public void pesquisarProdutosJaVendidosPorUmPrecoDiferenteDoAtual(@EManager final EntityManager entityManager) {
        var jpql = "select p from Produto p " +
                " where p.preco <> ANY (select precoProduto from ItemPedido where produto = p)";

        var typedQuery = entityManager.createQuery(jpql, Produto.class);

        var lista = typedQuery.getResultList();
        assertThat(lista).isNotEmpty();

        lista.forEach(obj -> System.out.println("ID: " + obj.getId()));
    }

    @Test
    public void pesquisarProdutosQueForamVendidosPeloMenosUmavezPeloPrecoAtual(@EManager final EntityManager entityManager) {
        var jpql = "select p from Produto p " +
                " where p.preco = ANY (select precoProduto from ItemPedido where produto = p)";

        var typedQuery = entityManager.createQuery(jpql, Produto.class);

        var lista = typedQuery.getResultList();
        assertThat(lista).isNotEmpty();

        lista.forEach(obj -> System.out.println("ID: " + obj.getId()));
    }

    @Test
    public void pesquisarProdutosNaoVendidosDepoisDeEncarecer(@EManager final EntityManager entityManager) {
        var jpql = "select p from Produto p where " +
                " p.preco > ALL (select precoProduto from ItemPedido where produto = p)";

        var typedQuery = entityManager.createQuery(jpql, Produto.class);

        var lista = typedQuery.getResultList();
        assertThat(lista).isNotEmpty();

        lista.forEach(obj -> System.out.println("ID: " + obj.getId()));
    }

    @Test
    public void pesquisarProdutosSempreVendidosPrecoAtual(@EManager final EntityManager entityManager) {

        var jpql = "select p from Produto p where " +
                " p.preco = ALL (select precoProduto from ItemPedido where produto = p)";

        var typedQuery = entityManager.createQuery(jpql, Produto.class);

        var lista = typedQuery.getResultList();
        assertThat(lista).isNotEmpty();

        lista.forEach(obj -> System.out.println("ID: " + obj.getId()));
    }

    @Test
    public void buscarProdutosNaoVendidos(@EManager final EntityManager entityManager) {
        var jpql = "select p from Produto p " +
                " where exists " +
                " (select 1 from ItemPedido where id.produtoId = p and precoProduto <> p.preco)";

        var typedQuery = entityManager.createQuery(jpql, Produto.class);

        var lista = typedQuery.getResultList();
        assertThat(lista).isNotEmpty();
        lista.forEach(p -> System.out.println(p.getId()));
    }

    @Test
    public void buscarPedidosComProdutoDeUmaCategoria(@EManager final EntityManager entityManager) {
        var jpql = "select c from Cliente c where size(c.pedidos) >= 2";
        //ou
        /*var jpql = "select c from Cliente c where " +
                " (select count(cliente) from Pedido where cliente = c) >= 2";*/

        var typedQuery = entityManager.createQuery(jpql, Cliente.class);

        var lista = typedQuery.getResultList();
        assertThat(lista).isNotEmpty();
        lista.forEach(p -> System.out.println(p.getId()));
    }

    @Test
    public void buscarClientesCom2OuMaisPedidos(@EManager final EntityManager entityManager) {
        var jpql = "select p " +
                   "  from Pedido p " +
                   " where p.id in (select p2.id " +
                   "                  from ItemPedido i2 " +
                   "                  join i2.pedido p2" +
                   "                  join i2.produto pro2" +
                   "                  join pro2.categorias c2 " +
                   "                 where c2.id = 2 )";

        var typedQuery = entityManager.createQuery(jpql, Pedido.class);

        var lista = typedQuery.getResultList();
        assertThat(lista).isNotEmpty();
        lista.forEach(p -> System.out.println(p.getId()));
    }

    @Test
    public void pesquisarComExists(@EManager final EntityManager entityManager) {
        var jpql = "select p from Produto p where exists " +
                " (select 1 from ItemPedido ip2 join ip2.produto p2 where p2 = p)";

        var typedQuery = entityManager.createQuery(jpql, Produto.class);

        var lista = typedQuery.getResultList();
        assertThat(lista).isNotEmpty();

        lista.forEach(obj -> System.out.println("ID: " + obj.getId()));
    }

    @Test
    public void pesquisarComIN(@EManager final EntityManager entityManager) {
        var jpql = "select p from Pedido p where p.id in " +
                " (select p2.id from ItemPedido i2 " +
                "      join i2.pedido p2 join i2.produto pro2 where pro2.preco > 100)";

        var typedQuery = entityManager.createQuery(jpql, Pedido.class);

        var lista = typedQuery.getResultList();
        assertThat(lista).isNotEmpty();

        lista.forEach(obj -> System.out.println("ID: " + obj.getId()));
    }

    @Test
    public void pesquisarProdutoMaisCaro(@EManager final EntityManager entityManager) {
        var jpql = "select p from Produto p where " +
                " p.preco = (select max(preco) from Produto)";

        var typedQuery = entityManager.createQuery(jpql, Produto.class);

        var lista = typedQuery.getResultList();
        assertThat(lista).isNotEmpty();

        lista.forEach(obj -> System.out.println("ID: " + obj.getId() + ", Nome: " + obj.getNome()));
    }

    @Test
    public void pesquisarPedidosAcimaMediaVenda(@EManager final EntityManager entityManager) {
        var jpql = "select p from Pedido p where " +
                " p.total > (select avg(total) from Pedido)";

        var typedQuery = entityManager.createQuery(jpql, Pedido.class);

        var lista = typedQuery.getResultList();
        assertThat(lista).isNotEmpty();

        lista.forEach(obj -> System.out.println("ID: " + obj.getId() + ", Nome: " + obj.getTotal()));
    }

    @Test
    public void pesquisarClientesCompraMaiorQueQuinhetosV1(@EManager final EntityManager entityManager) {
        var jpql = "select c from Cliente c where " +
                " 500 < (select sum(p.total) from c.pedidos p)";

        var typedQuery = entityManager.createQuery(jpql, Cliente.class);

        var lista = typedQuery.getResultList();
        assertThat(lista).isNotEmpty();

        lista.forEach(obj -> System.out.println("ID: " + obj.getId() + ", Nome: " + obj.getNome()));
    }

    @Test
    public void pesquisarClientesCompraMaiorQueQuinhetosV2(@EManager final EntityManager entityManager) {
        var jpql = "select c from Cliente c where " +
                " 500 < (select sum(p.total) from Pedido p where p.cliente = c)";

        var typedQuery = entityManager.createQuery(jpql, Cliente.class);

        var lista = typedQuery.getResultList();
        assertThat(lista).isNotEmpty();

        lista.forEach(obj -> System.out.println("ID: " + obj.getId() + ", Nome: " + obj.getNome()));
    }
}
