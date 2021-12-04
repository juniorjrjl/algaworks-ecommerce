package com.algaworks.ecommerce.criteria;

import com.algaworks.ecommerce.extension.EManager;
import com.algaworks.ecommerce.extension.EntityManagerExtension;
import com.algaworks.ecommerce.model.NotaFiscal;
import com.algaworks.ecommerce.model.Pedido;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.persistence.EntityManager;
import javax.persistence.TemporalType;
import java.util.Calendar;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(EntityManagerExtension.class)
public class PassandoParametrosCriteriaTest {

    @Test
    public void passarParametroDate(@EManager final EntityManager entityManager) {
        var criteriaBuilder = entityManager.getCriteriaBuilder();
        var criteriaQuery = criteriaBuilder.createQuery(NotaFiscal.class);
        var root = criteriaQuery.from(NotaFiscal.class);

        criteriaQuery.select(root);

        var parameterExpressionData = criteriaBuilder.parameter(Date.class, "dataInicial");

        criteriaQuery.where(criteriaBuilder.greaterThan(root.get("dataEmissao"), parameterExpressionData));

        var typedQuery = entityManager.createQuery(criteriaQuery);

        var dataInicial = Calendar.getInstance();
        dataInicial.add(Calendar.DATE, -30);

        typedQuery.setParameter("dataInicial", dataInicial.getTime(), TemporalType.TIMESTAMP);

        var lista = typedQuery.getResultList();
        assertThat(lista).isNotEmpty();
    }

    @Test
    public void passarParametro(@EManager final EntityManager entityManager) {
        var criteriaBuilder = entityManager.getCriteriaBuilder();
        var criteriaQuery = criteriaBuilder.createQuery(Pedido.class);
        var root = criteriaQuery.from(Pedido.class);

        criteriaQuery.select(root);

        var parameterExpressionId = criteriaBuilder.parameter(Integer.class, "id");

        criteriaQuery.where(criteriaBuilder.equal(root.get("id"), parameterExpressionId));

        var typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setParameter("id", 1);

        var pedido = typedQuery.getSingleResult();
        assertThat(pedido).isNotNull();
    }

}
