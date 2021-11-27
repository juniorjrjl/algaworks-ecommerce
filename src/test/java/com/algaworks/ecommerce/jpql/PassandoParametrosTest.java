package com.algaworks.ecommerce.jpql;

import com.algaworks.ecommerce.extension.EManager;
import com.algaworks.ecommerce.extension.EntityManagerExtension;
import com.algaworks.ecommerce.model.NotaFiscal;
import com.algaworks.ecommerce.model.Pedido;
import com.algaworks.ecommerce.model.StatusPagamento;
import org.assertj.core.api.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.persistence.EntityManager;
import javax.persistence.TemporalType;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(EntityManagerExtension.class)
public class PassandoParametrosTest {

    @Test
    public void passarParametro(@EManager final EntityManager entityManager){
        var jpql = "select p from Pedido p join p.pagamento pag where p.id = :pedidoId and pag.status = ?1";

        var typedquery = entityManager.createQuery(jpql, Pedido.class);
        typedquery.setParameter("pedidoId", 2);
        typedquery.setParameter(1, StatusPagamento.PROCESSANDO);

        var lista = typedquery.getResultList();
        assertThat(lista.size()).isOne();
    }

    @Test
    public void passarParametroDate(@EManager final EntityManager entityManager) {
        var jpql = "select nf from NotaFiscal nf where nf.dataEmissao <= ?1";

        var typedQuery = entityManager.createQuery(jpql, NotaFiscal.class);
        typedQuery.setParameter(1, new Date(), TemporalType.TIMESTAMP);

        var lista = typedQuery.getResultList();
        assertThat(lista.size()).isOne();
    }

}
