package com.algaworks.ecommerce.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@NoArgsConstructor
@Entity
public class Estoque {

    @Id
    private Integer id;
    private Integer produtoId;
    private Integer quantidade;

}
