package com.algaworks.ecommerce.jpql.funcoes;

import com.algaworks.ecommerce.extension.EManager;
import com.algaworks.ecommerce.extension.EntityManagerExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(EntityManagerExtension.class)
public class FuncoesAgregacaoTest {

    @Test
    public void aplicarFuncaoAgregacaoAVG(@EManager final EntityManager entityManager) {
        var jpql = "select avg(p.total) from Pedido p";

        var typedQuery = entityManager.createQuery(jpql, Number.class);

        var lista = typedQuery.getResultList();
        assertThat(lista).isNotEmpty();

        lista.forEach(System.out::println);
    }

    @Test
    public void aplicarFuncaoAgregacaoCount(@EManager final EntityManager entityManager) {
        var jpql = "select count(p.total) from Pedido p";

        var typedQuery = entityManager.createQuery(jpql, Number.class);

        var lista = typedQuery.getResultList();
        assertThat(lista).isNotEmpty();

        lista.forEach(System.out::println);
    }

    @Test
    public void aplicarFuncaoAgregacaoMin(@EManager final EntityManager entityManager) {
        var jpql = "select min(p.total) from Pedido p";

        var typedQuery = entityManager.createQuery(jpql, Number.class);

        var lista = typedQuery.getResultList();
        assertThat(lista).isNotEmpty();

        lista.forEach(System.out::println);
    }

    @Test
    public void aplicarFuncaoAgregacaoMax(@EManager final EntityManager entityManager) {
        var jpql = "select max(p.total) from Pedido p";

        var typedQuery = entityManager.createQuery(jpql, Number.class);

        var lista = typedQuery.getResultList();
        assertThat(lista).isNotEmpty();

        lista.forEach(System.out::println);
    }

    @Test
    public void aplicarFuncaoAgregacaoSun(@EManager final EntityManager entityManager) {
        var jpql = "select sum(p.total) from Pedido p";

        var typedQuery = entityManager.createQuery(jpql, Number.class);

        var lista = typedQuery.getResultList();
        assertThat(lista).isNotEmpty();

        lista.forEach(System.out::println);
    }
}
