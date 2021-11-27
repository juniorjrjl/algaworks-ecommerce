package com.algaworks.ecommerce.jpql;

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
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(EntityManagerExtension.class)
public class ExpressoesCondicionaisTest {

    @Test
    public void usarExpressaoIN(@EManager final EntityManager entityManager) {
        var cliente1 = new Cliente(); // entityManager.find(Cliente.class, 1);
        cliente1.setId(1);

        var cliente2 = new Cliente(); // entityManager.find(Cliente.class, 2);
        cliente2.setId(2);

        var clientes = Arrays.asList(cliente1, cliente2);

        var jpql = "select p from Pedido p where p.cliente in (:clientes)";

        var typedQuery = entityManager.createQuery(jpql, Pedido.class);
        typedQuery.setParameter("clientes", clientes);

        var lista = typedQuery.getResultList();
        assertThat(lista).isNotEmpty();
    }

    @Test
    public void usarExpressaoCase(@EManager final EntityManager entityManager) {
        var jpql = "select p.id, " +
                " case type(p.pagamento) " +
                "       when PagamentoBoleto then 'Pago com boleto' " +
                "       when PagamentoCartao then 'Pago com cartão' " +
                "       else 'Não pago ainda.' " +
                " end " +
                " from Pedido p";

        var typedQuery = entityManager.createQuery(jpql, Object[].class);

        var lista = typedQuery.getResultList();
        assertThat(lista).isNotEmpty();

        lista.forEach(arr -> System.out.println(arr[0] + ", " + arr[1]));
    }

    @Test
    public void usarExpressaoCondicionalLike(@EManager final EntityManager entityManager){
        var jpql = "select c from Cliente c where c.nome like concat('%', :nome, '%')";
        var typedQuery = entityManager.createQuery(jpql, Object[].class);
        typedQuery.setParameter("nome", "a");

        var lista = typedQuery.getResultList();
        assertThat(lista).isNotEmpty();
    }

    @Test
    public void usarIsNull(@EManager final EntityManager entityManager){
        var jpql = "select p from Produto p where p.foto is null";

        var typedQuery = entityManager.createQuery(jpql, Object[].class);

        var lista = typedQuery.getResultList();
        assertThat(lista).isNotEmpty();
    }

    @Test
    public void usarIsEmpty(@EManager final EntityManager entityManager){
        var jpql = "select p from Produto p where p.categorias is empty";

        var typedQuery = entityManager.createQuery(jpql, Object[].class);

        var lista = typedQuery.getResultList();
        assertThat(lista).isNotEmpty();
    }

    @Test
    public void usarMaiorMenor(@EManager final EntityManager entityManager){
        var jpql = "select p from Produto p where p.preco >= :precoInicial and p.preco <= : precoFinal";

        var typedQuery = entityManager.createQuery(jpql, Object[].class);
        typedQuery.setParameter("precoInicial", new BigDecimal(400));
        typedQuery.setParameter("precoFinal", new BigDecimal(1500));

        var lista = typedQuery.getResultList();
        assertThat(lista).isNotEmpty();
    }

    @Test
    public void usarMaiorMenorData(@EManager final EntityManager entityManager){
        var jpql = "select p from Pedido p where p.dataCriacao > :dataInicial";

        var typedQuery = entityManager.createQuery(jpql, Object[].class);
        typedQuery.setParameter("dataInicial", LocalDateTime.now().minusDays(2));

        var lista = typedQuery.getResultList();
        assertThat(lista).isNotEmpty();
    }

    @Test
    public void usarBetween(@EManager final EntityManager entityManager){
        var jpql = "select p from Pedido p " +
                " where p.dataCriacao between :dataInicial and :dataFinal";

        var typedQuery = entityManager.createQuery(jpql, Pedido.class);
        typedQuery.setParameter("dataInicial", LocalDateTime.now().minusDays(10));
        typedQuery.setParameter("dataFinal", LocalDateTime.now());

        var lista = typedQuery.getResultList();
        assertThat(lista).isNotEmpty();
    }

    @Test
    public void usarExpressaoDiferente(@EManager final EntityManager entityManager) {
        var jpql = "select p from Produto p where p.preco <> 100";

        var typedQuery = entityManager.createQuery(jpql, Produto.class);

        var lista = typedQuery.getResultList();
        assertThat(lista).isNotEmpty();
    }

}
