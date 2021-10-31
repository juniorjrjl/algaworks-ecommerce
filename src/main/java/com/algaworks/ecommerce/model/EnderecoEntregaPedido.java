package com.algaworks.ecommerce.model;

import lombok.*;

import javax.persistence.Embeddable;

@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
@Embeddable
public class EnderecoEntregaPedido {

    private String cep;

    private String logradouro;

    private String numero;

    private String bairro;

    private String cidade;

    private String estado;

}
