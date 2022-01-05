package com.algaworks.ecommerce.cache;


import com.algaworks.ecommerce.model.Pedido;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class CacheTest {

    protected static EntityManagerFactory entityManagerFactory;

    @BeforeAll
    public static void setUp(){
        entityManagerFactory = Persistence
                .createEntityManagerFactory("Ecommerce-PU");
    }

    @Test
    public void buscarDoCache() {
        var entityManager1 = entityManagerFactory.createEntityManager();
        var entityManager2 = entityManagerFactory.createEntityManager();

        System.out.println("Buscando a partir da instância 1:");
        entityManager1.find(Pedido.class, 1);

        System.out.println("Buscando a partir da instância 2:");
        entityManager2.find(Pedido.class, 1);
    }

    @Test
    public void adicionarPedidosNoCache(){
        var entityManager1 = entityManagerFactory.createEntityManager();
        var entityManager2 = entityManagerFactory.createEntityManager();
        System.out.println("Buscando a partir da instância 1:");
        entityManager1.createQuery("select p from Pedido p", Pedido.class).getResultList();
        System.out.println("Buscando a partir da instância 2:");
        entityManager2.find(Pedido.class, 1);
    }

    @AfterAll
    public static void tearDown(){
        entityManagerFactory.close();
    }
}
