package com.algaworks.ecommerce.mapeamentoavancado;

import com.algaworks.ecommerce.extension.EManager;
import com.algaworks.ecommerce.extension.EntityManagerExtension;
import com.algaworks.ecommerce.model.NotaFiscal;
import com.algaworks.ecommerce.model.Pedido;
import com.algaworks.ecommerce.model.Produto;
import com.algaworks.ecommerce.util.ManipulaArquivo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.persistence.EntityManager;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(EntityManagerExtension.class)
public class SalvandoArquivosTest {

    @Test
    public void salvarXmlNota(@EManager final EntityManager entityManager) {
        var pedido = entityManager.find(Pedido.class, 1);

        var notaFiscal = new NotaFiscal();
        notaFiscal.setPedido(pedido);
        notaFiscal.setDataEmissao(new Date());
        notaFiscal.setXml(ManipulaArquivo.carregarArquivo("nota-fiscal.xml"));

        entityManager.getTransaction().begin();
        entityManager.persist(notaFiscal);
        entityManager.getTransaction().commit();

        entityManager.clear();

        var notaFiscalVerificacao = entityManager.find(NotaFiscal.class, notaFiscal.getId());
        assertThat(notaFiscalVerificacao.getXml()).isNotNull();
        assertThat(notaFiscalVerificacao.getXml().length).isGreaterThan(0);

        //ManipulaArquivo.salvarArquivo("nota-fiscal.xml", notaFiscalVerificacao.getXml());
    }

    @Test
    public void salvarFotoProduto(@EManager final EntityManager entityManager){
        var produto = entityManager.find(Produto.class, 1);

        entityManager.getTransaction().begin();
        produto.setFoto(ManipulaArquivo.carregarArquivo("kindle.jpg"));
        entityManager.getTransaction().commit();

        entityManager.clear();

        var produtoVerificacao = entityManager.find(Produto.class, produto.getId());
        assertThat(produtoVerificacao.getFoto()).isNotNull();
        assertThat(produtoVerificacao.getFoto().length).isGreaterThan(0);

        //ManipulaArquivo.salvarArquivo("kindle.jpg", produtoVerificacao.getFoto());

    }

}
