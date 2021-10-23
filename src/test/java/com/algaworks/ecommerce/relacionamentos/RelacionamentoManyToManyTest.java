package com.algaworks.ecommerce.relacionamentos;

import com.algaworks.ecommerce.extension.EManager;
import com.algaworks.ecommerce.extension.EntityManagerExtension;
import com.algaworks.ecommerce.model.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(EntityManagerExtension.class)
public class RelacionamentoManyToManyTest {

    @Test
    public void verificarRelacionamento(@EManager final EntityManager entityManager){
        var produto = entityManager.find(Produto.class, 1);
        var categoria = entityManager.find(Categoria.class, 1);

        entityManager.getTransaction().begin();
        produto.setCategorias(List.of(categoria));
        entityManager.getTransaction().commit();

        entityManager.clear();

        var cateogiriaVerificacao = entityManager.find(Categoria.class, categoria.getId());
        assertThat(cateogiriaVerificacao.getProdutos()).isNotEmpty();
    }

}
