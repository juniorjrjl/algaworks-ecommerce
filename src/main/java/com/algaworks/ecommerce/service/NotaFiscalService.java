package com.algaworks.ecommerce.service;

import com.algaworks.ecommerce.model.Pedido;

public class NotaFiscalService {

    public void gerar(final Pedido pedido){
        System.out.println("Gerando nota para o pedido " + pedido.getId() + ".");
    }

}
