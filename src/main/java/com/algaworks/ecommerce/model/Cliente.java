package com.algaworks.ecommerce.model;

import lombok.*;

import javax.persistence.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@Entity
@ToString(callSuper = true)
@SecondaryTable(name = "cliente_detalhe", pkJoinColumns = @PrimaryKeyJoinColumn(name = "cliente_id"))
@Table(name = "cliente")
public class Cliente extends EntidadeBaseInteger{

    private String nome;

    @Transient
    private String primeiroNome;

    @Column(table = "cliente_detalhe")
    @Enumerated(EnumType.STRING)
    private SexoCliente sexo;

    @Column(table = "cliente_detalhe", name = "data_nascimento")
    private LocalDate dataNascimento;

    @ToString.Exclude
    @OneToMany(mappedBy = "cliente")
    private List<Pedido> pedidos;

    @ElementCollection
    @CollectionTable(name = "cliente_contato", joinColumns = @JoinColumn(name = "cliente_id"))
    @MapKeyColumn(name = "tipo")
    @Column(name = "descricao")
    private Map<String, String> contatos;

    @PostLoad
    private void configurarPrimeiroNome(){
        if (nome != null && !nome.isBlank()){
            var index = nome.indexOf(" ");
            if (index > -1){
                primeiroNome = nome.substring(0, index);
            }
        }
    }

}
