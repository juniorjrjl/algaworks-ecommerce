package com.algaworks.ecommerce.util;

import java.util.concurrent.TimeUnit;

public class ConcorrenciaUtil {

    public static void log(Object obj) {
        System.out.printf("[LOG %s ] %s %n", System.currentTimeMillis(), obj);
    }

    public static void esperar(long segundos) {
        try {
            TimeUnit.SECONDS.sleep(segundos);
        } catch (InterruptedException e) {
            log("erro na espera");
        }
    }

}
