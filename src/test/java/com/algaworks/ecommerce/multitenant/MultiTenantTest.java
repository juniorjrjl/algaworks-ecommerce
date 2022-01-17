package com.algaworks.ecommerce.multitenant;

import com.algaworks.ecommerce.extension.EMFactory;
import com.algaworks.ecommerce.extension.EntityManagerFactoryExtension;
import com.algaworks.ecommerce.hibernate.EcmCurrentTenantIdentifierResolver;
import com.algaworks.ecommerce.model.Produto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.persistence.EntityManagerFactory;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(EntityManagerFactoryExtension.class)
public class MultiTenantTest {

    @Test
    public void usarAbordagemPorSchema(@EMFactory final EntityManagerFactory entityManagerFactory){
        //EcmCurrentTenantIdentifierResolver.setTenantIdentifier("algaworks_ecommerce");
        var entityManager1 = entityManagerFactory.createEntityManager();
        var produto1 = entityManager1.find(Produto.class, 1);
        assertThat(produto1.getNome()).isEqualTo("Kindle");
        entityManager1.close();

        //EcmCurrentTenantIdentifierResolver.setTenantIdentifier("loja_ecommerce");
        var entityManager2 = entityManagerFactory.createEntityManager();
        var produto2 = entityManager2.find(Produto.class, 1);
        assertThat(produto2.getNome()).isEqualTo("Kindle Paperwhite");
        entityManager1.close();
    }

    @Test
    public void usarAbordagemPorMaquina(@EMFactory final EntityManagerFactory entityManagerFactory){
        //EcmCurrentTenantIdentifierResolver.setTenantIdentifier("algaworks_ecommerce");
        var entityManager1 = entityManagerFactory.createEntityManager();
        var produto1 = entityManager1.find(Produto.class, 1);
        assertThat(produto1.getNome()).isEqualTo("Kindle");
        entityManager1.close();

        //EcmCurrentTenantIdentifierResolver.setTenantIdentifier("loja_ecommerce");
        var entityManager2 = entityManagerFactory.createEntityManager();
        var produto2 = entityManager2.find(Produto.class, 1);
        assertThat(produto2.getNome()).isEqualTo("Kindle Paperwhite");
        entityManager1.close();
    }

}
