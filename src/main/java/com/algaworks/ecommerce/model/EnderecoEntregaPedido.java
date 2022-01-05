package com.algaworks.ecommerce.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
@Embeddable
public class EnderecoEntregaPedido {

    @NotNull
    @Column(length = 9)
    @Size(min = 9, max = 9)
    private String cep;

    @NotNull
    @Column(length = 100)
    private String logradouro;

    @NotNull
    @Column(length = 10)
    private String numero;

    @Column(length = 50)
    private String complemento;

    @NotNull
    @Column(length = 50)
    private String bairro;

    @NotNull
    @Column(length = 50)
    private String cidade;

    @NotNull
    @Size(min = 2, max = 2)
    @Column(length = 2)
    private String estado;

}
