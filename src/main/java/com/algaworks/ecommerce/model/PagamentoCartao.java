package com.algaworks.ecommerce.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@NoArgsConstructor
@Entity
public class PagamentoCartao {

    @Id
    private Integer id;

    private Integer pedidoId;

    private StatusPagamento status;

    private String numero;

}
