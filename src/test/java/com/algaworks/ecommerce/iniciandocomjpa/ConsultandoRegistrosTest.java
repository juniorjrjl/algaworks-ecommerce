package com.algaworks.ecommerce.iniciandocomjpa;

import com.algaworks.ecommerce.iniciandocomjpa.extension.EManager;
import com.algaworks.ecommerce.iniciandocomjpa.extension.EntityManagerExtension;
import com.algaworks.ecommerce.model.Produto;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(EntityManagerExtension.class)
public class ConsultandoRegistrosTest {

    @Test
    void buscarPorIdentificador(@EManager final EntityManager entityManager){
        var produto = entityManager.find(Produto.class, 1);
        //var produto = entityManager.getReference(Produto.class, 1); //só busca quando chama uma das propriedades do objeto
        assertThat(produto).isNotNull();
        assertThat(produto.getNome()).isEqualTo("Kindle");
    }

    @Test
    void atualizarReferencia(@EManager final EntityManager entityManager){
        var produto = entityManager.find(Produto.class, 1);
        produto.setNome("Microfone Samson");
        entityManager.refresh(produto);
        assertThat(produto.getNome()).isEqualTo("Kindle");
    }


}
