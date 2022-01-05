package com.algaworks.ecommerce.detalhesimportantes;

import com.algaworks.ecommerce.extension.EManager;
import com.algaworks.ecommerce.extension.EntityManagerExtension;
import com.algaworks.ecommerce.model.Pedido;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(EntityManagerExtension.class)
public class ProblemaN1Test {

    @Test
    public void resolverComEntityGraph(@EManager final EntityManager entityManager) {
        var entityGraph = entityManager.createEntityGraph(Pedido.class);
        entityGraph.addAttributeNodes("cliente", "notaFiscal", "pagamento");

        var lista = entityManager
                .createQuery("select p from Pedido p ", Pedido.class)
                .setHint("javax.persistence.loadgraph", entityGraph)
                .getResultList();
        assertThat(lista).isNotEmpty();
    }

    @Test
    public void resolverComFetch(@EManager final EntityManager entityManager) {
        var lista = entityManager
                .createQuery("select p from Pedido p " +
                        " join fetch p.cliente c " +
                        " join fetch p.pagamento pag " +
                        " join fetch p.notaFiscal nf", Pedido.class)
                .getResultList();
        assertThat(lista).isNotEmpty();
    }
}