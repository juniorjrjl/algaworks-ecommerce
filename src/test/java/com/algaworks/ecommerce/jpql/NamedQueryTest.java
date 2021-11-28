package com.algaworks.ecommerce.jpql;

import com.algaworks.ecommerce.extension.EManager;
import com.algaworks.ecommerce.extension.EntityManagerExtension;
import com.algaworks.ecommerce.model.Pedido;
import com.algaworks.ecommerce.model.Produto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(EntityManagerExtension.class)
public class NamedQueryTest {

    @Test
    public void executarConsultaArquivoXMLEspecificoProduto(@EManager final EntityManager entityManager) {
        var typedQuery = entityManager
                .createNamedQuery("Produto.todos", Produto.class);

        var lista = typedQuery.getResultList();

        assertThat(lista).isNotEmpty();
    }

    @Test
    public void executarConsultaArquivoXMLEspecificoPedido(@EManager final EntityManager entityManager) {
        var typedQuery = entityManager
                .createNamedQuery("Pedido.todos", Pedido.class);

        var lista = typedQuery.getResultList();

        assertThat(lista).isNotEmpty();
    }

    @Test
    public void executarConsultaArquivoXML(@EManager final EntityManager entityManager) {
        var typedQuery = entityManager
                .createNamedQuery("Pedido.listar", Pedido.class);

        var lista = typedQuery.getResultList();

        assertThat(lista).isNotEmpty();
    }

    @Test
    public void executarConsulta(@EManager final EntityManager entityManager) {
        var typedQuery = entityManager
                .createNamedQuery("Produto.listarPorCategoria", Produto.class);
        typedQuery.setParameter("categoria", 2);

        var lista = typedQuery.getResultList();

        assertThat(lista).isNotEmpty();
    }
}