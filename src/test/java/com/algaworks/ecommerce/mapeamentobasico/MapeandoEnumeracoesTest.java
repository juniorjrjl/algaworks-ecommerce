package com.algaworks.ecommerce.mapeamentobasico;

import com.algaworks.ecommerce.extension.EManager;
import com.algaworks.ecommerce.extension.EntityManagerExtension;
import com.algaworks.ecommerce.model.Cliente;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.persistence.EntityManager;

import static com.algaworks.ecommerce.model.SexoCliente.MASCULINO;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(EntityManagerExtension.class)
public class MapeandoEnumeracoesTest {

    @Test
    public void testarEnum(@EManager final EntityManager entityManager){
        var cliente = new Cliente();
        cliente.setNome("José Mineiro");
        cliente.setSexo(MASCULINO);
        cliente.setCpf("159");

        entityManager.getTransaction().begin();
        entityManager.persist(cliente);
        entityManager.getTransaction().commit();
        entityManager.clear();

        var clienteVerificacao = entityManager.find(Cliente.class, cliente.getId());
        assertThat(clienteVerificacao).isNotNull();
    }

}
