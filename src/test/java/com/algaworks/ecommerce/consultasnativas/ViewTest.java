package com.algaworks.ecommerce.consultasnativas;

import com.algaworks.ecommerce.extension.EManager;
import com.algaworks.ecommerce.extension.EntityManagerExtension;
import com.algaworks.ecommerce.model.Cliente;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.persistence.EntityManager;
import java.util.List;

@ExtendWith(EntityManagerExtension.class)
public class ViewTest {

    @Test
    public void executarView(@EManager final EntityManager entityManager) {
        var query = entityManager.createNativeQuery(
                "select cli.id, cli.nome, sum(ped.total) " +
                        " from pedido ped " +
                        " join view_clientes_acima_media cli on cli.id = ped.cliente_id " +
                        " group by ped.cliente_id");

        List<Object[]> lista = query.getResultList();

        lista.forEach(arr -> System.out.println(
                String.format("Cliente => ID: %s, Nome: %s, Total: %s", arr[0], arr[1], arr[2])));
    }

    @Test
    public void executarViewRetornandoCliente(@EManager final EntityManager entityManager) {
        var query = entityManager.createNativeQuery(
                "select * from view_clientes_acima_media", Cliente.class);

        List<Cliente> lista = query.getResultList();

        lista.forEach(obj -> System.out.println(
                String.format("Cliente => ID: %s, Nome: %s", obj.getId(), obj.getNome())));
    }
}