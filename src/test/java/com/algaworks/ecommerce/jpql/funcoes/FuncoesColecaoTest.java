package com.algaworks.ecommerce.jpql.funcoes;

import com.algaworks.ecommerce.extension.EManager;
import com.algaworks.ecommerce.extension.EntityManagerExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(EntityManagerExtension.class)
public class FuncoesColecaoTest {

    @Test
    public void aplicarFuncaoColecao(@EManager final EntityManager entityManager) {
        var jpql = "select size(p.itens) from Pedido p where size(p.itens) > 1";

        var typedQuery = entityManager.createQuery(jpql, Integer.class);

        var lista = typedQuery.getResultList();
        assertThat(lista).isNotEmpty();

        lista.forEach(System.out::println);
    }

}
