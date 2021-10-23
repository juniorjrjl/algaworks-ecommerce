package com.algaworks.ecommerce.relacionamentos;

import com.algaworks.ecommerce.extension.EManager;
import com.algaworks.ecommerce.extension.EntityManagerExtension;
import com.algaworks.ecommerce.model.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.persistence.EntityManager;
import java.util.Date;
import java.util.List;

import static com.algaworks.ecommerce.model.StatusPagamento.PROCESSANDO;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(EntityManagerExtension.class)
public class RelacionamentoOneToOneTest {

    @Test
    public void verificarRelacionamento(@EManager final EntityManager entityManager){
        var pedido = entityManager.find(Pedido.class, 1);
        var pagamentoCartao = new PagamentoCartao();
        pagamentoCartao.setNumero("123");
        pagamentoCartao.setStatus(PROCESSANDO);
        pagamentoCartao.setPedido(pedido);

        entityManager.getTransaction().begin();
        entityManager.persist(pagamentoCartao);
        entityManager.getTransaction().commit();

        entityManager.clear();
        var pedidoVerificacao = entityManager.find(Pedido.class, 1);
        assertThat(pedidoVerificacao.getPagamentoCartao()).isNotNull();
    }

    @Test
    public void verificarRelacionamentoPedidoNotaFiscal(@EManager final EntityManager entityManager){
        var pedido = entityManager.find(Pedido.class, 1);

        var notaFiscal = new NotaFiscal();
        notaFiscal.setXml("TESTE");
        notaFiscal.setDataEmissao(new Date());
        notaFiscal.setPedido(pedido);

        entityManager.getTransaction().begin();
        entityManager.persist(notaFiscal);
        entityManager.getTransaction().commit();

        entityManager.clear();
        var pedidoVerificacao = entityManager.find(Pedido.class, 1);
        assertThat(pedidoVerificacao.getNotaFiscal()).isNotNull();
    }

}
