package com.algaworks.ecommerce.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

public class ManipulaArquivo {

    public static byte[] carregarArquivo(final String arquivo){
        try {
            return Objects.requireNonNull(ManipulaArquivo.class.getResourceAsStream("/" + arquivo)).readAllBytes();
        }catch (IOException ex){
            throw new RuntimeException();
        }
    }

    public static void salvarArquivo(final String nome, final byte[] bytesArquivo){
        try {
            var out = new FileOutputStream(Files.createFile(Paths.get(System.getProperty("user.home") + "/" + nome)).toFile());
            out.write(bytesArquivo);
        }catch (Exception ex){
            throw new RuntimeException();
        }
    }

}
