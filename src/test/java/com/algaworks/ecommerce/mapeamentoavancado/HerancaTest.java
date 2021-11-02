package com.algaworks.ecommerce.mapeamentoavancado;

import com.algaworks.ecommerce.extension.EManager;
import com.algaworks.ecommerce.extension.EntityManagerExtension;
import com.algaworks.ecommerce.model.*;
import org.assertj.core.api.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.persistence.EntityManager;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(EntityManagerExtension.class)
public class HerancaTest {

    @Test
    public void salvarCliente(@EManager final EntityManager entityManager){
        var cliente = new Cliente();
        cliente.setNome("Fernanda Morais");

        entityManager.getTransaction().begin();
        entityManager.persist(cliente);
        entityManager.getTransaction().commit();

        entityManager.clear();

        var clienteVerificacao = entityManager.find(Cliente.class, cliente.getId());
        assertThat(clienteVerificacao.getId()).isNotNull();
    }

    @Test
    public void buscarPagamentos(@EManager final EntityManager entityManager) {
        var pagamentos = entityManager
                .createQuery("select p from Pagamento p", Pagamento.class)
                .getResultList();

        assertThat(pagamentos).isNotEmpty();
    }

    @Test
    public void incluirPagamentoPedido(@EManager final EntityManager entityManager) {
        var pedido = entityManager.find(Pedido.class, 1);

        PagamentoCartao pagamentoCartao = new PagamentoCartao();
        pagamentoCartao.setPedido(pedido);
        pagamentoCartao.setStatus(StatusPagamento.PROCESSANDO);
        pagamentoCartao.setNumeroCartao("123");

        entityManager.getTransaction().begin();
        entityManager.persist(pagamentoCartao);
        entityManager.getTransaction().commit();

        entityManager.clear();

        var pedidoVerificacao = entityManager.find(Pedido.class, pedido.getId());
        assertThat(pedidoVerificacao.getPagamento()).isNotNull();
    }

}
