package com.algaworks.ecommerce.jpql;

import com.algaworks.ecommerce.extension.EManager;
import com.algaworks.ecommerce.extension.EntityManagerExtension;
import com.algaworks.ecommerce.model.Categoria;
import org.assertj.core.api.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(EntityManagerExtension.class)
public class PaginacaoJPQLTest {

    @Test
    public void paginarResultados(@EManager final EntityManager entityManager) {
        var jpql = "select c from Categoria c order by c.nome";

        var typedQuery = entityManager.createQuery(jpql, Categoria.class);

        // FIRST_RESULT = MAX_RESULTS * (pagina - 1)
        typedQuery.setFirstResult(6);
        typedQuery.setMaxResults(2);

        var lista = typedQuery.getResultList();
        assertThat(lista).isNotEmpty();

        lista.forEach(c -> System.out.println(c.getId() + ", " + c.getNome()));
    }
}