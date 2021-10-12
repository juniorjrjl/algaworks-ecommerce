package com.algaworks.ecommerce.iniciandocomjpa;

import com.algaworks.ecommerce.iniciandocomjpa.extension.EManager;
import com.algaworks.ecommerce.iniciandocomjpa.extension.EntityManagerExtension;
import com.algaworks.ecommerce.model.Produto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.persistence.EntityManager;
import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(EntityManagerExtension.class)
public class OperacaoComTransacaoTest {

    @Test
    public void removerObjeto(@EManager final EntityManager entityManager){
        var produto = entityManager.find(Produto.class, 3);
        entityManager.getTransaction().begin();
        entityManager.remove(produto);
        entityManager.getTransaction().commit();
        var produtoVerificacao = entityManager.find(Produto.class, 3);
        assertThat(produtoVerificacao).isNull();
    }

    @Test
    public void atualizarObjeto(@EManager final EntityManager entityManager){
        var produto = entityManager.find(Produto.class, 1);

        entityManager.getTransaction().begin();
        produto.setNome("Kindle Paperwithe");
        entityManager.getTransaction().commit();

        entityManager.clear();
        var produtoVerificacao = entityManager.find(Produto.class, produto.getId());
        assertThat(produtoVerificacao.getNome()).isEqualTo("Kindle Paperwithe");
    }

    @Test
    public void inserirObjeto(@EManager final EntityManager entityManager){
        var produto = new Produto();
        produto.setId(2);
        produto.setNome("Câmera Canon");
        produto.setDescricao("A melhor definição para suas fotos");
        produto.setPreco(new BigDecimal(5000));

        entityManager.getTransaction().begin();
        entityManager.persist(produto);
        entityManager.getTransaction().commit();
        entityManager.clear();
        var produtoVerificacao = entityManager.find(Produto.class, produto.getId());
        assertThat(produtoVerificacao).isNotNull();
    }

    @Test
    public void inserirObjetoComMerge(@EManager final EntityManager entityManager){
        var produto = new Produto();
        produto.setId(4);
        produto.setNome("Micofone Rode Videmic");
        produto.setDescricao("A melhor qualidade de som");
        produto.setPreco(new BigDecimal(1000));

        entityManager.getTransaction().begin();
        entityManager.merge(produto);
        entityManager.getTransaction().commit();
        entityManager.clear();
        var produtoVerificacao = entityManager.find(Produto.class, produto.getId());
        assertThat(produtoVerificacao).isNotNull();
    }

    @Test
    public void mostrarDiferencaPersistMerge(@EManager final EntityManager entityManager){
        var produtoPersist = new Produto();
        produtoPersist.setId(5);
        produtoPersist.setNome("Smartphone One Plus");
        produtoPersist.setDescricao("O processador mais rápido");
        produtoPersist.setPreco(new BigDecimal(2000));

        entityManager.getTransaction().begin();
        entityManager.persist(produtoPersist);// o persist inclui a instancia no entityManager
        produtoPersist.setNome("Smartphone Two Plus");
        entityManager.getTransaction().commit();
        entityManager.clear();
        var produtoVerificacaoPersist = entityManager.find(Produto.class, produtoPersist.getId());
        assertThat(produtoVerificacaoPersist).isNotNull();

        var produtoMerge = new Produto();
        produtoMerge.setId(6);
        produtoMerge.setNome("Notebbok Dell");
        produtoMerge.setDescricao("O melhor da categoria");
        produtoMerge.setPreco(new BigDecimal(2000));

        entityManager.getTransaction().begin();
        produtoMerge = entityManager.merge(produtoMerge);// o merge faz uma cópia da instancia e retorna essa copia
        produtoMerge.setNome("Notebbok Dell 2");
        entityManager.getTransaction().commit();
        entityManager.clear();
        var produtoVerificacaoMerge = entityManager.find(Produto.class, produtoMerge.getId());
        assertThat(produtoVerificacaoMerge).isNotNull();
    }

}
