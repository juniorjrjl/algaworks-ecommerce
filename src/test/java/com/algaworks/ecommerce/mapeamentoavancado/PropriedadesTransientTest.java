package com.algaworks.ecommerce.mapeamentoavancado;

import com.algaworks.ecommerce.extension.EManager;
import com.algaworks.ecommerce.extension.EntityManagerExtension;
import com.algaworks.ecommerce.model.Cliente;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(EntityManagerExtension.class)
public class PropriedadesTransientTest {

    @Test
    public void validarPirmeiroNome(@EManager final EntityManager entityManager){
        var cliente = entityManager.find(Cliente.class, 1);
        assertThat(cliente.getPrimeiroNome()).isEqualTo("Fernando");
    }

}
