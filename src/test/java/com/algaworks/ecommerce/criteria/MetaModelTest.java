package com.algaworks.ecommerce.criteria;

import com.algaworks.ecommerce.extension.EManager;
import com.algaworks.ecommerce.extension.EntityManagerExtension;
import com.algaworks.ecommerce.model.Produto;
import com.algaworks.ecommerce.model.Produto_;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(EntityManagerExtension.class)
public class MetaModelTest {

    @Test
    public void utilizarMetaModel(@EManager final EntityManager entityManager) {
        var criteriaBuilder = entityManager.getCriteriaBuilder();
        var criteriaQuery = criteriaBuilder.createQuery(Produto.class);
        var root = criteriaQuery.from(Produto.class);

        criteriaQuery.select(root);

        criteriaQuery.where(criteriaBuilder.or(
                criteriaBuilder.like(root.get(Produto_.nome), "%C%"),
                criteriaBuilder.like(root.get(Produto_.descricao), "%C%")
        ));

        var typedQuery = entityManager.createQuery(criteriaQuery);
        var lista = typedQuery.getResultList();
        assertThat(lista).isNotEmpty();
    }
}