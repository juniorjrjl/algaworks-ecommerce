package com.algaworks.ecommerce.consultasnativas;

import com.algaworks.ecommerce.extension.EManager;
import com.algaworks.ecommerce.extension.EntityManagerExtension;
import com.algaworks.ecommerce.model.Produto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;

import java.math.BigDecimal;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;


@ExtendWith(EntityManagerExtension.class)
public class StoreProcedureTest {

    @Test
    public void chamarNamedStoredProcedure(@EManager final EntityManager entityManager) {
        var storedProcedureQuery = entityManager
                .createNamedStoredProcedureQuery("compraram_acima_media");

        storedProcedureQuery.setParameter("ano", 2020);

        var lista = storedProcedureQuery.getResultList();

        assertThat(lista).isEmpty();
    }

    @Test
    public void ajustarPrecoProduto(@EManager final EntityManager entityManager){
        var storeProcedureQuery = entityManager.createStoredProcedureQuery("ajustar_preco_produto");
        storeProcedureQuery.registerStoredProcedureParameter("produto_id", Integer.class, ParameterMode.IN);
        storeProcedureQuery.registerStoredProcedureParameter("percentual_ajuste", BigDecimal.class, ParameterMode.IN);
        storeProcedureQuery.registerStoredProcedureParameter("preco_ajustado", BigDecimal.class, ParameterMode.OUT);
        storeProcedureQuery.setParameter("produto_id", 1);
        var percentual = new BigDecimal(10);
        storeProcedureQuery.setParameter("percentual_ajuste", percentual);
        BigDecimal novoPreco = (BigDecimal) storeProcedureQuery.getOutputParameterValue("preco_ajustado");
        var produto = entityManager.find(Produto.class, 1);
        assertThat(novoPreco).isNotNull();
    }

    @Test
    public void receberListaDaProcedure(@EManager final EntityManager entityManager){
        var storeProcedureQuery = entityManager.createStoredProcedureQuery("compraram_acima_media");
        storeProcedureQuery.registerStoredProcedureParameter("ano", Integer.class, ParameterMode.IN);
        storeProcedureQuery.setParameter("ano", 2021);
        var lista = storeProcedureQuery.getResultList();
        assertThat(lista).isEmpty();
    }

    @Test
    public void usarParametrosInEOut(@EManager final EntityManager entityManager){
        var storeProcedureQuery = entityManager.createStoredProcedureQuery("buscar_nome_produto");
        storeProcedureQuery.registerStoredProcedureParameter("produto_id", Integer.class, ParameterMode.IN);
        storeProcedureQuery.registerStoredProcedureParameter("produto_nome", String.class, ParameterMode.OUT);
        storeProcedureQuery.setParameter("produto_id", 1);
        var nome = storeProcedureQuery.getOutputParameterValue("produto_nome");
        assertThat(nome).isEqualTo("Kindle");
    }

}
