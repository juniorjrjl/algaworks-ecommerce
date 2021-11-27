package com.algaworks.ecommerce.jpql;

import com.algaworks.ecommerce.extension.EManager;
import com.algaworks.ecommerce.extension.EntityManagerExtension;
import com.algaworks.ecommerce.model.Pedido;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(EntityManagerExtension.class)
public class PathExpressionTest {

    @Test
    public void buscarPedidosComProdutoEspecifico(@EManager final EntityManager entityManager){
        var jpql = "select p from Pedido p join fetch p.itens i where i.id.produtoId = 1";
        var typedQuery = entityManager.createQuery(jpql, Pedido.class);

        var lista = typedQuery.getResultList();
        lista.forEach(l -> l.getItens().forEach(i -> assertThat(i.getId().getProdutoId()).isEqualTo(1)));

    }


    @Test
    public void usarPathExpressions(@EManager final EntityManager entityManager) {
        var jpql = "select p.cliente.nome from Pedido p";

        var typedQuery = entityManager.createQuery(jpql, Object[].class);

        List<Object[]> lista = typedQuery.getResultList();
        assertThat(lista).isNotEmpty();
    }

}
