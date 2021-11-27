package com.algaworks.ecommerce.operacoesemcascata;

import com.algaworks.ecommerce.extension.EManager;
import com.algaworks.ecommerce.extension.EntityManagerExtension;
import com.algaworks.ecommerce.model.ItemPedido;
import com.algaworks.ecommerce.model.ItemPedidoId;
import com.algaworks.ecommerce.model.Pedido;
import com.algaworks.ecommerce.model.Produto;
import org.assertj.core.api.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(EntityManagerExtension.class)
public class CascadeTypeRemoveTest{

    @Test
    public void removerItensOrfaos(@EManager final EntityManager entityManager) {
        var pedido = entityManager.find(Pedido.class, 1);

        assertThat(pedido.getItens()).isNotEmpty();

        entityManager.getTransaction().begin();
        pedido.getItens().clear();
        entityManager.getTransaction().commit();

        entityManager.clear();

        var pedidoVerificacao = entityManager.find(Pedido.class, pedido.getId());
        assertThat(pedidoVerificacao.getItens()).isEmpty();
    }

    @Test
    public void removerRelacaoProdutoCategoria(@EManager final EntityManager entityManager) {
        var produto = entityManager.find(Produto.class, 1);

        assertThat(produto.getCategorias()).isNotEmpty();

        entityManager.getTransaction().begin();
        produto.getCategorias().clear();
        entityManager.getTransaction().commit();

        entityManager.clear();

        var produtoVerificacao = entityManager.find(Produto.class, produto.getId());
        assertThat(produtoVerificacao.getCategorias()).isEmpty();
    }

    //@Test
    public void removerPedidoEItens(@EManager final EntityManager entityManager) {
        var pedido = entityManager.find(Pedido.class, 1);

        entityManager.getTransaction().begin();
        entityManager.remove(pedido); // Necessário CascadeType.REMOVE no atributo "itens".
        entityManager.getTransaction().commit();

        entityManager.clear();

        var pedidoVerificacao = entityManager.find(Pedido.class, pedido.getId());
        assertThat(pedidoVerificacao).isNull();
    }

    //@Test
    public void removerItemPedidoEPedido(@EManager final EntityManager entityManager) {
        var itemPedido = entityManager.find(ItemPedido.class, new ItemPedidoId(1, 1));

        entityManager.getTransaction().begin();
        entityManager.remove(itemPedido); // Necessário CascadeType.REMOVE no atributo "pedido".
        entityManager.getTransaction().commit();

        entityManager.clear();

        var pedidoVerificacao = entityManager.find(Pedido.class, itemPedido.getPedido().getId());
        assertThat(pedidoVerificacao).isNull();
    }
}