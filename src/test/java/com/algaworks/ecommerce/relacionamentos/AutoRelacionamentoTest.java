package com.algaworks.ecommerce.relacionamentos;

import com.algaworks.ecommerce.extension.EManager;
import com.algaworks.ecommerce.extension.EntityManagerExtension;
import com.algaworks.ecommerce.model.Categoria;
import com.algaworks.ecommerce.model.Cliente;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(EntityManagerExtension.class)
public class AutoRelacionamentoTest {

    @Test
    public void verificarRelacionamento(@EManager final EntityManager entityManager){

        var categoriaPai = new Categoria();
        categoriaPai.setNome("Eletônicos");

        var categoria = new Categoria();
        categoria.setNome("Celulares");
        categoria.setCategoriaPai(categoriaPai);

        entityManager.getTransaction().begin();
        entityManager.persist(categoriaPai);
        entityManager.persist(categoria);
        entityManager.getTransaction().commit();

        entityManager.clear();

        var categoriaVerificacao = entityManager.find(Categoria.class, categoria.getId());
        assertThat(categoriaVerificacao.getCategoriaPai()).isNotNull();
        var categoriaPaiVerificacao = entityManager.find(Categoria.class, categoriaPai.getId());
        assertThat(categoriaPaiVerificacao.getCategorias()).isNotEmpty();
    }

}
