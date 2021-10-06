package com.algaworks.ecommerce.iniciandocomjpa;

import com.algaworks.ecommerce.model.Produto;
import org.junit.jupiter.api.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import static org.assertj.core.api.Assertions.assertThat;

public class ConsultandoRegistrosTest {

    private static EntityManagerFactory entityManagerFactory;

    private static EntityManager entityManager;

    @BeforeAll
    static void setUpBeforeClass(){
        entityManagerFactory = Persistence.createEntityManagerFactory("Ecommerce-PU");
    }

    @BeforeEach
    void setUp(){
        entityManager = entityManagerFactory.createEntityManager();
    }

    @Test
    void buscarPorIdentificador(){
        var produto = entityManager.find(Produto.class, 1);
        //var produto = entityManager.getReference(Produto.class, 1); //só busca quando chama uma das propriedades do objeto
        assertThat(produto).isNotNull();
        assertThat(produto.getNome()).isEqualTo("Kindle");
    }

    @Test
    void atualizarReferencia(){
        var produto = entityManager.find(Produto.class, 1);
        produto.setNome("Microfone Samson");
        entityManager.refresh(produto);
        assertThat(produto.getNome()).isEqualTo("Kindle");
    }

    @AfterEach
    void tearDown(){
        entityManager.close();
    }

    @AfterAll
    static void tearDownAfterClass(){
        entityManagerFactory.close();
    }

}
