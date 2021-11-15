package com.algaworks.ecommerce.conhecendoentitymanager;

import com.algaworks.ecommerce.extension.EManager;
import com.algaworks.ecommerce.extension.EntityManagerExtension;
import com.algaworks.ecommerce.model.Cliente;
import com.algaworks.ecommerce.model.Pedido;
import com.algaworks.ecommerce.model.Produto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.persistence.EntityManager;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static com.algaworks.ecommerce.model.StatusPedido.AGUARDANDO;
import static com.algaworks.ecommerce.model.StatusPedido.PAGO;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(EntityManagerExtension.class)
public class ListenersTest {

    @Test
    public void carregarEntidades(@EManager final EntityManager entityManager){
        entityManager.find(Produto.class, 1);
        entityManager.find(Pedido.class, 1);
    }

    @Test
    public void acionarListeners(@EManager final EntityManager entityManager){
        var cliente = entityManager.find(Cliente.class, 1);
        var pedido = new Pedido();

        pedido.setCliente(cliente);
        pedido.setStatus(AGUARDANDO);
        pedido.setDataCriacao(LocalDateTime.now());
        pedido.setTotal(BigDecimal.TEN);

        entityManager.getTransaction().begin();
        entityManager.persist(pedido);
        entityManager.flush();
        pedido.setStatus(PAGO);
        entityManager.getTransaction().commit();

        entityManager.clear();
        var pedidoVerificacao = entityManager.find(Pedido.class, pedido.getId());
        assertThat(pedidoVerificacao.getDataCriacao()).isNotNull();
        assertThat(pedidoVerificacao.getDataUltimaAtualizacao()).isNotNull();
    }

}
