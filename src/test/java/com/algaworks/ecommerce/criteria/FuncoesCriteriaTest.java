package com.algaworks.ecommerce.criteria;

import com.algaworks.ecommerce.extension.EManager;
import com.algaworks.ecommerce.extension.EntityManagerExtension;
import com.algaworks.ecommerce.model.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.persistence.EntityManager;
import javax.persistence.criteria.Join;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(EntityManagerExtension.class)
public class FuncoesCriteriaTest {

    @Test
    public void aplicarFuncaoString(@EManager final EntityManager entityManager) {
        var criteriaBuilder = entityManager.getCriteriaBuilder();
        var criteriaQuery = criteriaBuilder.createQuery(Object[].class);
        var root = criteriaQuery.from(Cliente.class);

        criteriaQuery.multiselect(
                root.get(Cliente_.nome),
                criteriaBuilder.concat("Nome do cliente: ", root.get(Cliente_.nome)),
                criteriaBuilder.length(root.get(Cliente_.nome)),
                criteriaBuilder.locate(root.get(Cliente_.nome), "a"),
                criteriaBuilder.substring(root.get(Cliente_.nome), 1, 2),
                criteriaBuilder.lower(root.get(Cliente_.nome)),
                criteriaBuilder.upper(root.get(Cliente_.nome)),
                criteriaBuilder.trim(root.get(Cliente_.nome))
        );

        criteriaQuery.where(criteriaBuilder.equal(
                criteriaBuilder.substring(root.get(Cliente_.nome), 1, 1), "M"));

        var typedQuery = entityManager.createQuery(criteriaQuery);

        var lista = typedQuery.getResultList();
        assertThat(lista).isNotNull();

        lista.forEach(arr -> System.out.println(
                arr[0]
                        + ", concat: " + arr[1]
                        + ", length: " + arr[2]
                        + ", locate: " + arr[3]
                        + ", substring: " + arr[4]
                        + ", lower: " + arr[5]
                        + ", upper: " + arr[6]
                        + ", trim: |" + arr[7] + "|"));
    }

    @Test
    public void aplicarFuncaoData(@EManager final EntityManager entityManager) {
        var criteriaBuilder = entityManager.getCriteriaBuilder();
        var criteriaQuery = criteriaBuilder.createQuery(Object[].class);
        var root = criteriaQuery.from(Pedido.class);
        Join<Pedido, Pagamento> joinPagamento = root.join(Pedido_.pagamento);
        Join<Pedido, PagamentoBoleto> joinPagamentoBoleto = criteriaBuilder
                .treat(joinPagamento, PagamentoBoleto.class);

        criteriaQuery.multiselect(
                root.get(Pedido_.id),
                criteriaBuilder.currentDate(),
                criteriaBuilder.currentTime(),
                criteriaBuilder.currentTimestamp()
        );

        criteriaQuery.where(
                criteriaBuilder.between(criteriaBuilder.currentDate(),
                        root.get(Pedido_.dataCriacao).as(java.sql.Date.class),
                        joinPagamentoBoleto.get(PagamentoBoleto_.dataVencimento).as(java.sql.Date.class)),
                criteriaBuilder.equal(root.get(Pedido_.status), StatusPedido.AGUARDANDO)
        );

        var typedQuery = entityManager.createQuery(criteriaQuery);

        var lista = typedQuery.getResultList();
        assertThat(lista).isNotNull();

        lista.forEach(arr -> System.out.println(
                arr[0]
                        + ", current_date: " + arr[1]
                        + ", current_time: " + arr[2]
                        + ", current_timestamp: " + arr[3]));
    }

    @Test
    public void aplicarFuncaoNumero(@EManager final EntityManager entityManager) {
        var criteriaBuilder = entityManager.getCriteriaBuilder();
        var criteriaQuery = criteriaBuilder.createQuery(Object[].class);
        var root = criteriaQuery.from(Pedido.class);

        criteriaQuery.multiselect(
                root.get(Pedido_.id),
                criteriaBuilder.abs(criteriaBuilder.prod(root.get(Pedido_.id), -1)),
                criteriaBuilder.mod(root.get(Pedido_.id), 2),
                criteriaBuilder.sqrt(root.get(Pedido_.total))
        );

        criteriaQuery.where(criteriaBuilder.greaterThan(
                criteriaBuilder.sqrt(root.get(Pedido_.total)), 10.0));

        var typedQuery = entityManager.createQuery(criteriaQuery);

        var lista = typedQuery.getResultList();
        assertThat(lista).isNotNull();

        lista.forEach(arr -> System.out.println(
                arr[0]
                        + ", abs: " + arr[1]
                        + ", mod: " + arr[2]
                        + ", sqrt: " + arr[3]));
    }

    @Test
    public void aplicarFuncaoColecao(@EManager final EntityManager entityManager) {
        var criteriaBuilder = entityManager.getCriteriaBuilder();
        var criteriaQuery = criteriaBuilder.createQuery(Object[].class);
        var root = criteriaQuery.from(Pedido.class);

        criteriaQuery.multiselect(
                root.get(Pedido_.id),
                criteriaBuilder.size(root.get(Pedido_.itens))
        );

        criteriaQuery.where(criteriaBuilder.greaterThan(
                criteriaBuilder.size(root.get(Pedido_.itens)), 1));

        var typedQuery = entityManager.createQuery(criteriaQuery);

        var lista = typedQuery.getResultList();
        assertThat(lista).isNotNull();

        lista.forEach(arr -> System.out.println(
                arr[0]
                        + ", size: " + arr[1]));
    }

    @Test
    public void aplicarFuncaoNativas(@EManager final EntityManager entityManager) {
        var criteriaBuilder = entityManager.getCriteriaBuilder();
        var criteriaQuery = criteriaBuilder.createQuery(Object[].class);
        var root = criteriaQuery.from(Pedido.class);

        criteriaQuery.multiselect(
                root.get(Pedido_.id),
                criteriaBuilder.function("dayname", String.class, root.get(Pedido_.dataCriacao))
        );

        criteriaQuery.where(criteriaBuilder.isTrue(
                criteriaBuilder.function(
                        "acima_media_faturamento", Boolean.class, root.get(Pedido_.total))));

        var typedQuery = entityManager.createQuery(criteriaQuery);

        var lista = typedQuery.getResultList();
        assertThat(lista).isNotNull();

        lista.forEach(arr -> System.out.println(
                arr[0] + ", dayname: " + arr[1]));
    }

    @Test
    public void aplicarFuncaoAgregacao(@EManager final EntityManager entityManager) {
        var criteriaBuilder = entityManager.getCriteriaBuilder();
        var criteriaQuery = criteriaBuilder.createQuery(Object[].class);
        var root = criteriaQuery.from(Pedido.class);

        criteriaQuery.multiselect(
                criteriaBuilder.count(root.get(Pedido_.id)),
                criteriaBuilder.avg(root.get(Pedido_.total)),
                criteriaBuilder.sum(root.get(Pedido_.total)),
                criteriaBuilder.min(root.get(Pedido_.total)),
                criteriaBuilder.max(root.get(Pedido_.total))
        );

        var typedQuery = entityManager.createQuery(criteriaQuery);

        var lista = typedQuery.getResultList();
        assertThat(lista).isNotNull();

        lista.forEach(arr -> System.out.println(
                "count: " + arr[0]
                        + ", avg: " + arr[1]
                        + ", sum: " + arr[2]
                        + ", min: " + arr[3]
                        + ", max: " + arr[4]));
    }

}
