package com.algaworks.ecommerce.listener;

import javax.persistence.PostLoad;

public class GenericoListener {

    @PostLoad
    public void logCarregamento(final Object object){
        System.out.println("Entidade " + object.getClass().getSimpleName() + " foi carregada.");
    }

}
