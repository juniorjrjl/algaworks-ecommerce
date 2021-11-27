package com.algaworks.ecommerce.jpql.funcoes;

import com.algaworks.ecommerce.extension.EManager;
import com.algaworks.ecommerce.extension.EntityManagerExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(EntityManagerExtension.class)
public class FuncoesNativasTest {

    @Test
    public void aplicarFuncaoNativas(@EManager final EntityManager entityManager) {
        var jpql = "select function('dayname', p.dataCriacao) from Pedido p " +
                " where function('acima_media_faturamento', p.total) = 1";

        var typedQuery = entityManager.createQuery(jpql, String.class);

        var lista = typedQuery.getResultList();
        assertThat(lista).isNotEmpty();

        lista.forEach(System.out::println);
    }

}
