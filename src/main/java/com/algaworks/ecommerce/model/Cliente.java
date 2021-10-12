package com.algaworks.ecommerce.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@NoArgsConstructor
@Entity
public class Cliente {

    @Id
    private Integer id;
    private String nome;

}
