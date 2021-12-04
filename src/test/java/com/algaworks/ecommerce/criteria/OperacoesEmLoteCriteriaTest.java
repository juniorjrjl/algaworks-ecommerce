package com.algaworks.ecommerce.criteria;

import com.algaworks.ecommerce.extension.EManager;
import com.algaworks.ecommerce.extension.EntityManagerExtension;
import com.algaworks.ecommerce.model.Categoria;
import com.algaworks.ecommerce.model.Categoria_;
import com.algaworks.ecommerce.model.Produto;
import com.algaworks.ecommerce.model.Produto_;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.*;
import java.math.BigDecimal;

@ExtendWith(EntityManagerExtension.class)
public class OperacoesEmLoteCriteriaTest {

    @Test
    public void atualizarEmLote(@EManager final EntityManager entityManager) {
        entityManager.getTransaction().begin();

        var criteriaBuilder = entityManager.getCriteriaBuilder();
        var criteriaUpdate = criteriaBuilder.createCriteriaUpdate(Produto.class);
        var root = criteriaUpdate.from(Produto.class);

        criteriaUpdate.set(root.get(Produto_.preco),
                criteriaBuilder.prod(root.get(Produto_.preco), new BigDecimal("1.1")));

        var subquery = criteriaUpdate.subquery(Integer.class);
        var subqueryRoot = subquery.correlate(root);
        Join<Produto, Categoria> joinCategoria = subqueryRoot.join(Produto_.categorias);
        subquery.select(criteriaBuilder.literal(1));
        subquery.where(criteriaBuilder.equal(joinCategoria.get(Categoria_.id), 2));

        criteriaUpdate.where(criteriaBuilder.exists(subquery));

        var query = entityManager.createQuery(criteriaUpdate);
        query.executeUpdate();

        entityManager.getTransaction().commit();
    }

    @Test
    public void removerEmLoteExercicio(@EManager final EntityManager entityManager) {
        entityManager.getTransaction().begin();

//        String jpql = "delete from Produto p where p.id between 5 and 12";

        var criteriaBuilder = entityManager.getCriteriaBuilder();
        var criteriaDelete = criteriaBuilder.createCriteriaDelete(Produto.class);
        var root = criteriaDelete.from(Produto.class);

        criteriaDelete.where(criteriaBuilder.between(root.get(Produto_.id), 5, 12));

        var query = entityManager.createQuery(criteriaDelete);
        query.executeUpdate();

        entityManager.getTransaction().commit();
    }

}
