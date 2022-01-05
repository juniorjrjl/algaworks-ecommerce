package com.algaworks.ecommerce.detalhesimportantes;

import com.algaworks.ecommerce.extension.EManager;
import com.algaworks.ecommerce.extension.EntityManagerExtension;
import com.algaworks.ecommerce.model.Cliente;
import com.algaworks.ecommerce.model.Cliente_;
import com.algaworks.ecommerce.model.Pedido;
import com.algaworks.ecommerce.model.Pedido_;
import org.assertj.core.api.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(EntityManagerExtension.class)
public class EntityGraphTest {

    @Test
    public void buscarAtributosEssenciaisDePedido(@EManager final EntityManager entityManager) {
        var entityGraph = entityManager.createEntityGraph(Pedido.class);
        entityGraph.addAttributeNodes("dataCriacao", "status", "total", "notaFiscal");

        Map<String, Object> properties = new HashMap<>();
        //properties.put("javax.persistence.fetchgraph", entityGraph);
        properties.put("javax.persistence.loadgraph", entityGraph);
        var pedido = entityManager.find(Pedido.class, 1, properties);
        assertThat(pedido).isNotNull();


        TypedQuery<Pedido> typedQuery = entityManager
                .createQuery("select p from Pedido p", Pedido.class);
        typedQuery.setHint("javax.persistence.fetchgraph", entityGraph);
        List<Pedido> lista = typedQuery.getResultList();
        assertThat(lista).isNotEmpty();
    }

    @Test
    public void buscarAtributosEssenciaisDePedido02(@EManager final EntityManager entityManager) {
        var entityGraph = entityManager.createEntityGraph(Pedido.class);
        entityGraph.addAttributeNodes("dataCriacao", "status", "total");

        var subgraphCliente = entityGraph.addSubgraph("cliente", Cliente.class);
        subgraphCliente.addAttributeNodes("nome", "cpf");

        var typedQuery = entityManager
                .createQuery("select p from Pedido p", Pedido.class);
        typedQuery.setHint("javax.persistence.fetchgraph", entityGraph);
        var lista = typedQuery.getResultList();
        assertThat(lista).isNotEmpty();
    }

    @Test
    public void buscarAtributosEssenciaisDePedido03(@EManager final EntityManager entityManager) {
        var entityGraph = entityManager.createEntityGraph(Pedido.class);
        entityGraph.addAttributeNodes(Pedido_.dataCriacao, Pedido_.status, Pedido_.total);

        var subgraphCliente = entityGraph.addSubgraph(Pedido_.cliente);
        subgraphCliente.addAttributeNodes(Cliente_.nome, Cliente_.cpf);

        var typedQuery = entityManager
                .createQuery("select p from Pedido p", Pedido.class);
        typedQuery.setHint("javax.persistence.fetchgraph", entityGraph);
        var lista = typedQuery.getResultList();
        assertThat(lista).isNotEmpty();
    }

    @Test
    public void buscarAtributosEssenciaisComNamedEntityGraph(@EManager final EntityManager entityManager) {
        var entityGraph = entityManager.createEntityGraph("Pedido.dadosEssencias");
        entityGraph.addAttributeNodes("pagamento");

        var typedQuery = entityManager.createQuery("select p from Pedido p", Pedido.class);
        typedQuery.setHint("javax.persistence.fetchgraph", entityGraph);
        var lista = typedQuery.getResultList();
        assertThat(lista).isNotEmpty();
    }

}