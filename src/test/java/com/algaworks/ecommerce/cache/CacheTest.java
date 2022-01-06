package com.algaworks.ecommerce.cache;


import com.algaworks.ecommerce.model.Pedido;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.persistence.*;

import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

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
        entityManager1.close();
        entityManager2.close();
    }

    @Test
    public void adicionarPedidosNoCache(){
        var entityManager1 = entityManagerFactory.createEntityManager();
        var entityManager2 = entityManagerFactory.createEntityManager();
        System.out.println("Buscando a partir da instância 1:");
        entityManager1.createQuery("select p from Pedido p", Pedido.class).getResultList();
        System.out.println("Buscando a partir da instância 2:");
        entityManager2.find(Pedido.class, 1);
        entityManager1.close();
        entityManager2.close();
    }

    @Test
    public void removerDoCache(){
        var cache = entityManagerFactory.getCache();
        var entityManager1 = entityManagerFactory.createEntityManager();
        var entityManager2 = entityManagerFactory.createEntityManager();
        System.out.println("Buscando a partir da instância 1:");
        entityManager1.createQuery("select p from Pedido p", Pedido.class).getResultList();
        System.out.println("Removendo pedido 1 do cache");
        cache.evictAll();
        //cache.evict(Pedido.class);
        //cache.evict(Pedido.class, 1);
        entityManager2.find(Pedido.class, 1);
        entityManager2.find(Pedido.class, 2);
        entityManager1.close();
        entityManager2.close();
    }

    @Test
    public void verificarSeEstaNoCache(){
        var cache = entityManagerFactory.getCache();
        var entityManager1 = entityManagerFactory.createEntityManager();
        System.out.println("Buscando a partir da instância 1:");
        entityManager1.createQuery("select p from Pedido p", Pedido.class).getResultList();
        assertThat(cache.contains(Pedido.class, 1)).isTrue();
        assertThat(cache.contains(Pedido.class, 2)).isTrue();
        entityManager1.close();
    }

    @Test
    public void analisarOpcoesCache() {
        var cache = entityManagerFactory.getCache();
        var entityManager1 = entityManagerFactory.createEntityManager();
        System.out.println("Buscando a partir da instância 1:");
        entityManager1.createQuery("select p from Pedido p", Pedido.class).getResultList();
        assertThat(cache.contains(Pedido.class, 1)).isTrue();
        entityManager1.close();
    }

    @Test
    public void controlarCacheDinamicamente(){
        var cache = entityManagerFactory.getCache();

        System.out.println("Buscando todos os pedidos..............................");
        var entityManager1 = entityManagerFactory.createEntityManager();
        entityManager1.createQuery("select p from Pedido p", Pedido.class)
                .setHint("javax.persistence.cache.storeMode", CacheStoreMode.BYPASS)
                .getResultList();

        System.out.println("Buscando o pedido de ID igual a 2..............................");
        var entityManager2 = entityManagerFactory.createEntityManager();
        entityManager2.find(Pedido.class, 2);
        entityManager1.close();
        entityManager2.close();
    }

    private static void esperar(int segundos) {
        try {
            TimeUnit.SECONDS.sleep(segundos);
        } catch (InterruptedException e) {
            System.out.println("erro");
            e.printStackTrace();
        }
    }

    private static void log(Object obj) {
        System.out.println("[LOG " + System.currentTimeMillis() + "] " + obj);
    }

    @Test
    public void ehcache() {
        var cache = entityManagerFactory.getCache();

        var entityManager1 = entityManagerFactory.createEntityManager();
        var entityManager2 = entityManagerFactory.createEntityManager();

        log("Buscando e incluindo no cache...");
        entityManager1.createQuery("select p from Pedido p", Pedido.class).getResultList();
        log("---");

        esperar(1);
        assertThat(cache.contains(Pedido.class, 2)).isTrue();
        entityManager2.find(Pedido.class, 2);

        esperar(3);
        assertThat(cache.contains(Pedido.class, 2)).isFalse();
        entityManager1.close();
        entityManager2.close();
    }

    @AfterAll
    public static void tearDown(){
        entityManagerFactory.close();
    }
}
