package com.algaworks.ecommerce.jpql.funcoes;

import com.algaworks.ecommerce.extension.EManager;
import com.algaworks.ecommerce.extension.EntityManagerExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(EntityManagerExtension.class)
public class FuncoesNumeroTest {

    @Test
    public void aplicarFuncaoNumero(@EManager final EntityManager entityManager) {
        var jpql = "select abs(p.total * -1), mod(p.id, 2), sqrt(p.total) from Pedido p " +
                " where abs(p.total) > 1000";

        var typedQuery = entityManager.createQuery(jpql, Object[].class);

        var lista = typedQuery.getResultList();
        assertThat(lista).isNotEmpty();

        lista.forEach(arr -> System.out.println(arr[0] + " | " + arr[1] + " | " + arr[2]));
    }

}
