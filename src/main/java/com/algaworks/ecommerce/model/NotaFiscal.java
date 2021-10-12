package com.algaworks.ecommerce.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Data
@NoArgsConstructor
@Entity
public class NotaFiscal {

    @Id
    private Integer id;

    private Integer pedidoId;

    private String xml;

    private Date dataEmissao;
}