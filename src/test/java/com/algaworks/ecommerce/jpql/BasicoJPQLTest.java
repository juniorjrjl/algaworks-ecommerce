package com.algaworks.ecommerce.jpql;

import com.algaworks.ecommerce.dto.ProdutoDTO;
import com.algaworks.ecommerce.extension.EManager;
import com.algaworks.ecommerce.extension.EntityManagerExtension;
import com.algaworks.ecommerce.model.Cliente;
import com.algaworks.ecommerce.model.Pedido;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(EntityManagerExtension.class)
public class BasicoJPQLTest {

    @Test
    public void usarDistinct(@EManager final EntityManager entityManager) {
        var jpql = "select distinct p from Pedido p " +
                " join p.itens i join i.produto pro " +
                " where pro.id in (1, 2, 3, 4) ";

        var typedQuery = entityManager.createQuery(jpql, Pedido.class);

        var lista = typedQuery.getResultList();
        assertThat(lista).isNotEmpty();

        System.out.println(lista.size());
    }

    @Test
    public void ordenarResultados(@EManager final EntityManager entityManager) {
        var jpql = "select c from Cliente c order by c.nome asc"; // desc

        var typedQuery = entityManager.createQuery(jpql, Cliente.class);

        var lista = typedQuery.getResultList();
        assertThat(lista).isNotEmpty();

        lista.forEach(c -> System.out.println(c.getId() + ", " + c.getNome()));
    }


    @Test
    public void projetarNoDTO(@EManager final EntityManager entityManager){
        var jpql = "select new com.algaworks.ecommerce.dto.ProdutoDTO(id, nome) from Produto";
        var typedQuery = entityManager.createQuery(jpql, ProdutoDTO.class);
        var lista = typedQuery.getResultList();
        assertThat(lista).isNotEmpty();
        lista.forEach(System.out::println);
    }

    @Test
    public void projetarOResultado(@EManager final EntityManager entityManager){
        var jpql = "select id, nome from Produto";
        var typedQuery = entityManager.createQuery(jpql, Object[].class);
        var lista = typedQuery.getResultList();
        assertThat(lista.get(0)).hasSize(2);
        lista.forEach(l -> System.out.println(l[0] + ", " + l[1]));
    }

    @Test
    public void selecionarUmAtributoParaRetorno(@EManager final EntityManager entityManager) {
        var jpql = "select p.nome from Produto p";

        var typedQuery = entityManager.createQuery(jpql, String.class);
        var lista = typedQuery.getResultList();
        assertThat(lista.get(0)).isInstanceOf(String.class);

        var jpqlCliente = "select p.cliente from Pedido p";
        var typedQueryCliente = entityManager.createQuery(jpqlCliente, Cliente.class);
        var listaClientes = typedQueryCliente.getResultList();
        assertThat(listaClientes.get(0)).isInstanceOf(Cliente.class);
    }

    @Test
    public void buscarPorIdentificador(@EManager final EntityManager entityManager) {
        var typedQuery = entityManager.createQuery("select p from Pedido p where p.id = 1", Pedido.class);

        var pedido = typedQuery.getSingleResult();
        assertThat(pedido).isNotNull();
    }

    @Test
    public void mostrarDiferencaQueries(@EManager final EntityManager entityManager) {
        var jpql = "select p from Pedido p where p.id = 1";

        var typedQuery = entityManager.createQuery(jpql, Pedido.class);
        var pedido1 = typedQuery.getSingleResult();
        assertThat(pedido1).isNotNull();

        var query = entityManager.createQuery(jpql);
        var pedido2 = (Pedido) query.getSingleResult();
        assertThat(pedido2).isNotNull();

    }
}