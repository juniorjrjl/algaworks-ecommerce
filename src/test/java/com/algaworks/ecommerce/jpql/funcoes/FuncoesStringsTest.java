package com.algaworks.ecommerce.jpql.funcoes;

import com.algaworks.ecommerce.extension.EManager;
import com.algaworks.ecommerce.extension.EntityManagerExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(EntityManagerExtension.class)
public class FuncoesStringsTest {

    @Test
    public void aplicarFuncaoConcat(@EManager final EntityManager entityManager) {
        var jpql = "select c.nome, concat(c.id, ' - ',c.nome) from Categoria c " +
                " where substring(c.nome, 1, 1) = 'N'";

        var typedQuery = entityManager.createQuery(jpql, Object[].class);

        var lista = typedQuery.getResultList();
        assertThat(lista).isNotEmpty();

        lista.forEach(arr -> System.out.println(arr[0] + " - " + arr[1]));
    }

    @Test
    public void aplicarFuncaoLength(@EManager final EntityManager entityManager) {
        var jpql = "select c.nome, length(c.nome) from Categoria c " +
                " where substring(c.nome, 1, 1) = 'N'";

        var typedQuery = entityManager.createQuery(jpql, Object[].class);

        var lista = typedQuery.getResultList();
        assertThat(lista).isNotEmpty();

        lista.forEach(arr -> System.out.println(arr[0] + " - " + arr[1]));
    }

    @Test
    public void aplicarFuncaoLocate(@EManager final EntityManager entityManager) {
        var jpql = "select c.nome, locate('a', c.nome) from Categoria c " +
                " where substring(c.nome, 1, 1) = 'N'";

        var typedQuery = entityManager.createQuery(jpql, Object[].class);

        var lista = typedQuery.getResultList();
        assertThat(lista).isNotEmpty();

        lista.forEach(arr -> System.out.println(arr[0] + " - " + arr[1]));
    }

    @Test
    public void aplicarFuncaoLower(@EManager final EntityManager entityManager) {
        var jpql = "select c.nome, lower(c.nome) from Categoria c " +
                " where substring(c.nome, 1, 1) = 'N'";

        var typedQuery = entityManager.createQuery(jpql, Object[].class);

        var lista = typedQuery.getResultList();
        assertThat(lista).isNotEmpty();

        lista.forEach(arr -> System.out.println(arr[0] + " - " + arr[1]));
    }

    @Test
    public void aplicarFuncaoUpper(@EManager final EntityManager entityManager) {
        var jpql = "select c.nome, upper(c.nome) from Categoria c " +
                " where substring(c.nome, 1, 1) = 'N'";

        var typedQuery = entityManager.createQuery(jpql, Object[].class);

        var lista = typedQuery.getResultList();
        assertThat(lista).isNotEmpty();

        lista.forEach(arr -> System.out.println(arr[0] + " - " + arr[1]));
    }

    @Test
    public void aplicarFuncaoTrim(@EManager final EntityManager entityManager) {
        var jpql = "select c.nome, trim(c.nome) from Categoria c " +
                " where substring(c.nome, 1, 1) = 'N'";

        var typedQuery = entityManager.createQuery(jpql, Object[].class);

        var lista = typedQuery.getResultList();
        assertThat(lista).isNotEmpty();

        lista.forEach(arr -> System.out.println(arr[0] + " - " + arr[1]));
    }

}