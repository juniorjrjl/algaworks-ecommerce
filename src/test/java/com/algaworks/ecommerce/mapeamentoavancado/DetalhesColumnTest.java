package com.algaworks.ecommerce.mapeamentoavancado;

import com.algaworks.ecommerce.extension.EManager;
import com.algaworks.ecommerce.extension.EntityManagerExtension;
import com.algaworks.ecommerce.model.Produto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(EntityManagerExtension.class)
public class DetalhesColumnTest {


    @Test
    public void impedirInsercaoDaColunaAtualizacao(@EManager final EntityManager entityManager){
        var produto = new Produto();
        produto.setNome("Teclado para smartphone");
        produto.setDescricao("O mais confortavel");
        produto.setPreco(BigDecimal.ONE);
        produto.setDataCriacao(LocalDateTime.now());
        produto.setDataUltimaAtualizacao(LocalDateTime.now());

        entityManager.getTransaction().begin();
        entityManager.persist(produto);
        entityManager.getTransaction().commit();
        entityManager.clear();

        var produtoVerificacao = entityManager.find(Produto.class, produto.getId());
        assertThat(produtoVerificacao.getDataCriacao()).isNotNull();
        assertThat(produtoVerificacao.getDataUltimaAtualizacao()).isNull();
    }

    @Test
    public void impedirAtualizacaoDaColunaCriacao(@EManager final EntityManager entityManager){
        entityManager.getTransaction().begin();
        var produto = entityManager.find(Produto.class, 1);
        produto.setPreco(BigDecimal.TEN);
        produto.setDataCriacao(LocalDateTime.now());
        produto.setDataUltimaAtualizacao(LocalDateTime.now());
        entityManager.getTransaction().commit();
        entityManager.clear();

        var produtoVerificacao = entityManager.find(Produto.class, produto.getId());
        assertThat(produto.getDataCriacao().truncatedTo(ChronoUnit.SECONDS))
                .isNotEqualTo(produtoVerificacao.getDataCriacao().truncatedTo(ChronoUnit.SECONDS));
        assertThat(produto.getDataUltimaAtualizacao().truncatedTo(ChronoUnit.SECONDS))
                .isEqualTo(produtoVerificacao.getDataUltimaAtualizacao().truncatedTo(ChronoUnit.SECONDS));
    }


}
