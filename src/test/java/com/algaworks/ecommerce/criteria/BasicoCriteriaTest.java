package com.algaworks.ecommerce.criteria;

import com.algaworks.ecommerce.dto.ProdutoDTO;
import com.algaworks.ecommerce.extension.EManager;
import com.algaworks.ecommerce.extension.EntityManagerExtension;
import com.algaworks.ecommerce.model.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.persistence.EntityManager;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(EntityManagerExtension.class)
public class BasicoCriteriaTest {

    @Test
    public void buscarPorIdentificador(@EManager final EntityManager entityManager) {
        var criteriaBuilder = entityManager.getCriteriaBuilder();
        var criteriaQuery = criteriaBuilder.createQuery(Pedido.class);
        var root = criteriaQuery.from(Pedido.class);
        criteriaQuery.select(root);
        criteriaQuery.where(criteriaBuilder.equal(root.get("id"), 1));
        var typedQuery = entityManager.createQuery(criteriaQuery);
        var pedido = typedQuery.getSingleResult();
        assertThat(pedido).isNotNull();
    }

    @Test
    public void selecionarUmAtributoParaRetorno(@EManager final EntityManager entityManager) {
        var criteriaBuilder = entityManager.getCriteriaBuilder();
        var criteriaQuery = criteriaBuilder.createQuery(BigDecimal.class);
        var root = criteriaQuery.from(Pedido.class);

        criteriaQuery.select(root.get("total"));

        criteriaQuery.where(criteriaBuilder.equal(root.get("id"), 1));

        var typedQuery = entityManager.createQuery(criteriaQuery);
        BigDecimal total = typedQuery.getSingleResult();
        assertThat(new BigDecimal("2398.00")).isEqualTo(total);
    }

    @Test
    public void retornarTodosOsProdutosExercicio(@EManager final EntityManager entityManager) {
        var criteriaBuilder = entityManager.getCriteriaBuilder();
        var criteriaQuery = criteriaBuilder.createQuery(Produto.class);
        var root = criteriaQuery.from(Produto.class);
        criteriaQuery.select(root);
        var typedQuery = entityManager.createQuery(criteriaQuery);
        var lista = typedQuery.getResultList();
        assertThat(lista).isNotEmpty();
    }

    @Test
    public void projetarOResultado(@EManager final EntityManager entityManager) {
        var criteriaBuilder = entityManager.getCriteriaBuilder();
        var criteriaQuery = criteriaBuilder.createQuery(Object[].class);
        var root = criteriaQuery.from(Produto.class);

        criteriaQuery.multiselect(root.get("id"), root.get("nome"));

        var typedQuery = entityManager.createQuery(criteriaQuery);
        var lista = typedQuery.getResultList();
        assertThat(lista).isNotEmpty();

        lista.forEach(arr -> System.out.println("ID: " + arr[0] + ", Nome: " + arr[1]));
    }

    @Test
    public void projetarOResultadoTuple(@EManager final EntityManager entityManager) {
        var criteriaBuilder = entityManager.getCriteriaBuilder();
        var criteriaQuery = criteriaBuilder.createTupleQuery();
        var root = criteriaQuery.from(Produto.class);

        criteriaQuery.select(criteriaBuilder
                .tuple(root.get("id").alias("id"), root.get("nome").alias("nome")));

        var typedQuery = entityManager.createQuery(criteriaQuery);
        var lista = typedQuery.getResultList();
        assertThat(lista).isNotEmpty();

        lista.forEach(t -> System.out.println("ID: " + t.get("id") + ", Nome: " + t.get("nome")));
    }

    @Test
    public void projetarOResultadoDTO(@EManager final EntityManager entityManager) {
        var criteriaBuilder = entityManager.getCriteriaBuilder();
        var criteriaQuery = criteriaBuilder.createQuery(ProdutoDTO.class);
        var root = criteriaQuery.from(Produto.class);

        criteriaQuery.select(criteriaBuilder
                .construct(ProdutoDTO.class, root.get("id"), root.get("nome")));

        var typedQuery = entityManager.createQuery(criteriaQuery);
        var lista = typedQuery.getResultList();
        assertThat(lista).isNotEmpty();

        lista.forEach(dto -> System.out.println("ID: " + dto.getId() + ", Nome: " + dto.getNome()));
    }

    @Test
    public void ordenarResultados(@EManager final EntityManager entityManager) {
        var criteriaBuilder = entityManager.getCriteriaBuilder();
        var criteriaQuery = criteriaBuilder.createQuery(Cliente.class);
        Root<Cliente> root = criteriaQuery.from(Cliente.class);

        criteriaQuery.orderBy(criteriaBuilder.desc(root.get(Cliente_.nome)));

        var typedQuery = entityManager.createQuery(criteriaQuery);

        var lista = typedQuery.getResultList();
        assertThat(lista).isNotEmpty();

        lista.forEach(c -> System.out.println(c.getId() + ", " + c.getNome()));
    }

    @Test
    public void usarDistinct(@EManager final EntityManager entityManager) {
        var criteriaBuilder = entityManager.getCriteriaBuilder();
        var criteriaQuery = criteriaBuilder.createQuery(Pedido.class);
        var root = criteriaQuery.from(Pedido.class);
        root.join(Pedido_.itens);

        criteriaQuery.select(root);
        criteriaQuery.distinct(true);

        var typedQuery = entityManager.createQuery(criteriaQuery);
        var lista = typedQuery.getResultList();

        lista.forEach(p -> System.out.println("ID: " + p.getId()));
    }

}
