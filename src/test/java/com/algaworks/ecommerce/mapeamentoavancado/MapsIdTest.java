package com.algaworks.ecommerce.mapeamentoavancado;

import com.algaworks.ecommerce.extension.EManager;
import com.algaworks.ecommerce.extension.EntityManagerExtension;
import com.algaworks.ecommerce.model.*;
import com.algaworks.ecommerce.util.ManipulaArquivo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(EntityManagerExtension.class)
public class MapsIdTest {

    @Test
    public void inserirPagamento(@EManager final EntityManager entityManager){
        var pedido = entityManager.find(Pedido.class, 2);

        var notaFiscal = new NotaFiscal();
        notaFiscal.setPedido(pedido);
        notaFiscal.setDataEmissao(new Date());
        notaFiscal.setXml(ManipulaArquivo.carregarArquivo("nota-fiscal.xml"));

        entityManager.getTransaction().begin();
        entityManager.persist(notaFiscal);
        entityManager.getTransaction().commit();

        entityManager.clear();

        var notaFiscalVerificacao = entityManager.find(NotaFiscal.class, notaFiscal.getId());
        assertThat(notaFiscalVerificacao).isNotNull();
        assertThat(pedido.getId()).isEqualTo(notaFiscal.getId());
    }

    @Test
    public void inserirPagamentoCartao(@EManager final EntityManager entityManager){
        var pedido = entityManager.find(Pedido.class, 1);

        var pagamentoCartao = new PagamentoCartao();
        pagamentoCartao.setNumeroCartao("123");
        pagamentoCartao.setPedido(pedido);
        pagamentoCartao.setStatus(StatusPagamento.PROCESSANDO);

        entityManager.getTransaction().begin();
        entityManager.persist(pagamentoCartao);
        entityManager.getTransaction().commit();

        entityManager.clear();

        var pagamentoCartaoVerificacao = entityManager.find(PagamentoCartao.class, pagamentoCartao.getId());
        assertThat(pagamentoCartaoVerificacao).isNotNull();
        assertThat(pedido.getId()).isEqualTo(pagamentoCartaoVerificacao.getId());
    }

    @Test
    public void inserirItemPedido(@EManager final EntityManager entityManager) {
        Cliente cliente = entityManager.find(Cliente.class, 1);
        Produto produto = entityManager.find(Produto.class, 1);

        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);
        pedido.setDataCriacao(LocalDateTime.now());
        pedido.setStatus(StatusPedido.AGUARDANDO);
        pedido.setTotal(produto.getPreco());

        ItemPedido itemPedido = new ItemPedido();
        itemPedido.setId(new ItemPedidoId());
        itemPedido.setPedido(pedido);
        itemPedido.setProduto(produto);
        itemPedido.setPrecoProduto(produto.getPreco());
        itemPedido.setQuantidade(1);

        entityManager.getTransaction().begin();
        entityManager.persist(pedido);
        entityManager.persist(itemPedido);
        entityManager.getTransaction().commit();

        entityManager.clear();

        ItemPedido itemPedidoVerificacao = entityManager.find(ItemPedido.class, new ItemPedidoId(pedido.getId(), produto.getId()));
        assertThat(itemPedidoVerificacao).isNotNull();
    }

}
