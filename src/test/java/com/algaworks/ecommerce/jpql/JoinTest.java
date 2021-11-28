package com.algaworks.ecommerce.jpql;

import com.algaworks.ecommerce.extension.EManager;
import com.algaworks.ecommerce.extension.EntityManagerExtension;
import com.algaworks.ecommerce.model.Pedido;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(EntityManagerExtension.class)
public class JoinTest {

    @Test
    public void fazerJoin(@EManager final EntityManager entityManager){
        var jpql = "select p from Pedido p inner join p.pagamento pag";

        var typedQuery = entityManager.createQuery(jpql, Pedido.class);

        var lista = typedQuery.getResultList();
        assertThat(lista).isNotEmpty();
    }

    @Test
    public void fazerLeftJoin(@EManager final EntityManager entityManager){
        var jpql = "select p from Pedido p left join p.pagamento pag on pag.status = 'PROCESSANDO'";

        var typedQuery = entityManager.createQuery(jpql, Pedido.class);

        var lista = typedQuery.getResultList();
        assertThat(lista).isNotEmpty();
    }

    @Test
    public void usarJoinFetch(@EManager final EntityManager entityManager){
        var jpql = "select p from Pedido p "
                + " left join fetch p.pagamento "
                + " join fetch p.cliente "
                + " left join fetch p.notaFiscal "
                ;

        var typedQuery = entityManager.createQuery(jpql, Pedido.class);

        var lista = typedQuery.getResultList();
        assertThat(lista).isNotEmpty();
    }

}
