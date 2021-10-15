package com.algaworks.ecommerce.mapeamentobasico;

import com.algaworks.ecommerce.extension.EManager;
import com.algaworks.ecommerce.extension.EntityManagerExtension;
import com.algaworks.ecommerce.model.EnderecoEntregaPedido;
import com.algaworks.ecommerce.model.Pedido;
import com.algaworks.ecommerce.model.StatusPedido;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(EntityManagerExtension.class)
public class MapeamentoObjetoEmbutidoTest {

    @Test
    public void analisarMapeamentoObjetoEmbutido(@EManager final EntityManager entityManager){
        var enderecoEntregaPedido = new EnderecoEntregaPedido();
        enderecoEntregaPedido.setCep("0000-00");
        enderecoEntregaPedido.setLogradouro("Rua das Laranjeiras");
        enderecoEntregaPedido.setNumero("123");
        enderecoEntregaPedido.setBairro("Centro");
        enderecoEntregaPedido.setCidade("Uberlandia");
        enderecoEntregaPedido.setEstado("MG");
        var pedido = new Pedido();
        pedido.setId(1);
        pedido.setDataPedido(LocalDateTime.now());
        pedido.setStatus(StatusPedido.AGUARDANDO);
        pedido.setTotal(new BigDecimal(1000));
        pedido.setEnderecoEntrega(enderecoEntregaPedido);

        entityManager.getTransaction().begin();
        entityManager.persist(pedido);
        entityManager.getTransaction().commit();
        entityManager.clear();

        var pedidoVerificacao =  entityManager.find(Pedido.class, pedido.getId());
        assertThat(pedidoVerificacao).isNotNull();
        assertThat(pedidoVerificacao.getEnderecoEntrega()).isNotNull();

    }

}
