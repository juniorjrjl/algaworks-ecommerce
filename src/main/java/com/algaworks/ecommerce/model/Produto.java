package com.algaworks.ecommerce.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@Entity
public class Produto {

    @Id
    private Integer id;
    private String nome;
    private String descricao;
    private BigDecimal preco;

}
