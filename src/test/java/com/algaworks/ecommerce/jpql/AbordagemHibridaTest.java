package com.algaworks.ecommerce.jpql;

import com.algaworks.ecommerce.extension.EMFactory;
import com.algaworks.ecommerce.extension.EManager;
import com.algaworks.ecommerce.extension.EntityManagerExtension;
import com.algaworks.ecommerce.model.Categoria;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(EntityManagerExtension.class)
public class AbordagemHibridaTest {

    @BeforeEach
    public void setUpBeforeClass(@EMFactory final EntityManagerFactory entityManagerFactory,
                                 @EManager final EntityManager entityManager) {
        var jpql = "select c from Categoria c";
        var typedQuery = entityManager.createQuery(jpql, Categoria.class);
        entityManagerFactory.addNamedQuery("Categoria.listar", typedQuery);
    }

    @Test
    public void usarAbordagemHibrida(@EManager final EntityManager entityManager){
        var typedQuery = entityManager
                .createNamedQuery("Categoria.listar", Categoria.class);

        var lista = typedQuery.getResultList();
        assertThat(lista).isNotEmpty();
    }
}