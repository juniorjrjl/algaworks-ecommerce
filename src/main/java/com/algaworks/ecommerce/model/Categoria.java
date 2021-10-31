package com.algaworks.ecommerce.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@ToString(callSuper = true)
@Table(name = "categoria")
public class Categoria extends EntidadeBaseInteger{

    private String nome;

    @ManyToOne
    @JoinColumn(name = "categoria_pai")
    private Categoria categoriaPai;

    @ToString.Exclude
    @OneToMany(mappedBy = "categoriaPai")
    private List<Categoria> categorias;

    @ManyToMany(mappedBy = "categorias")
    @ToString.Exclude
    private List<Produto> produtos;

}
