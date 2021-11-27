package com.algaworks.ecommerce.jpql.funcoes;

import com.algaworks.ecommerce.extension.EManager;
import com.algaworks.ecommerce.extension.EntityManagerExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(EntityManagerExtension.class)
public class FuncoesDataTest {

    @Test
    public void aplicarFuncaoDataPedacosHora(@EManager final EntityManager entityManager) {
        var jpql = "select hour(p.dataCriacao), minute(p.dataCriacao), second(p.dataCriacao) from Pedido p";

        var typedQuery = entityManager.createQuery(jpql, Object[].class);

        var lista = typedQuery.getResultList();
        assertThat(lista).isNotEmpty();

        lista.forEach(arr -> System.out.println(arr[0] + " | " + arr[1] + " | " + arr[2]));
    }

    @Test
    public void aplicarFuncaoDataPedacosDia(@EManager final EntityManager entityManager) {
        var jpql = "select year(p.dataCriacao), month(p.dataCriacao), day(p.dataCriacao) from Pedido p";

        var typedQuery = entityManager.createQuery(jpql, Object[].class);

        var lista = typedQuery.getResultList();
        assertThat(lista).isNotEmpty();

        lista.forEach(arr -> System.out.println(arr[0] + " | " + arr[1] + " | " + arr[2]));
    }

    @Test
    public void aplicarFuncaoDataObterDataEOuHoraAtual(@EManager final EntityManager entityManager) {
        // TimeZone.setDefault(TimeZone.getTimeZone("UTC")); ajuste timezone java
        var jpql = "select current_date, current_time, current_timestamp from Pedido p";

        var typedQuery = entityManager.createQuery(jpql, Object[].class);

        var lista = typedQuery.getResultList();
        assertThat(lista).isNotEmpty();

        lista.forEach(arr -> System.out.println(arr[0] + " | " + arr[1] + " | " + arr[2]));
    }

}
