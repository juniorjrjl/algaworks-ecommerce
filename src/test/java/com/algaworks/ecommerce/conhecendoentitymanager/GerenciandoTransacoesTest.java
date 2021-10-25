package com.algaworks.ecommerce.conhecendoentitymanager;

import com.algaworks.ecommerce.extension.EManager;
import com.algaworks.ecommerce.extension.EntityManagerExtension;
import com.algaworks.ecommerce.model.Pedido;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.persistence.EntityManager;

import static com.algaworks.ecommerce.model.StatusPedido.PAGO;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(EntityManagerExtension.class)
public class GerenciandoTransacoesTest {

    @Test
    public void abrirFecharCancelarTransacao(@EManager final EntityManager entityManager){
        assertThatThrownBy(() ->{
            try{
                entityManager.getTransaction().begin();
                metodoDeNegocio(entityManager);
                entityManager.getTransaction().commit();
            }catch (Exception ex){
                entityManager.getTransaction().rollback();
                throw  ex;
            }
        });
    }

    private void metodoDeNegocio(final EntityManager entityManager) {
        var pedido = entityManager.find(Pedido.class, 1);
        pedido.setStatus(PAGO);
        if (pedido.getPagamentoCartao() == null){
            throw new RuntimeException("O pedido ainda não foi pago");
        }
    }

}
