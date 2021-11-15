package com.algaworks.ecommerce.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@ToString(callSuper = true)
@Table(name = "categoria", uniqueConstraints = @UniqueConstraint(name = "unq_nome", columnNames = {"nome"}))
public class Categoria extends EntidadeBaseInteger{

    @Column(length = 100, nullable = false)
    private String nome;

    @ManyToOne
    @JoinColumn(name = "categoria_pai", foreignKey = @ForeignKey(name = "fk_categoria_categoria_filho"))
    private Categoria categoriaPai;

    @ToString.Exclude
    @OneToMany(mappedBy = "categoriaPai")
    private List<Categoria> categorias;

    @ManyToMany(mappedBy = "categorias")
    @ToString.Exclude
    private List<Produto> produtos;

}
