package com.algaworks.ecommerce.model;

import lombok.*;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@NamedStoredProcedureQuery(name = "compraram_acima_media", procedureName = "compraram_acima_media",
        resultClasses = Cliente.class, parameters = {
        @StoredProcedureParameter(name = "ano", type = Integer.class, mode = ParameterMode.IN)
        })
@Getter
@Setter
@Entity
@ToString(callSuper = true)
@SecondaryTable(name = "cliente_detalhe", pkJoinColumns = @PrimaryKeyJoinColumn(name = "cliente_id"), foreignKey = @ForeignKey(name = "fk_cliente_cliente_detalhe"))
@Table(name = "cliente",
        uniqueConstraints = {@UniqueConstraint(name = "unq_cliente_cpf", columnNames = {"cpf"})},
        indexes = {@Index(name = "idx_cliente_nome", columnList = "nome")})
public class Cliente extends EntidadeBaseInteger{

    @NotBlank
    @Column(length = 100, nullable = false)
    private String nome;

    @CPF
    @NotBlank
    @Column(length = 100, nullable = false)
    private String cpf;

    @Transient
    private String primeiroNome;

    @NotNull
    @Column(table = "cliente_detalhe", length = 30, nullable = false)
    @Enumerated(EnumType.STRING)
    private SexoCliente sexo;

    @Column(table = "cliente_detalhe", name = "data_nascimento")
    private LocalDate dataNascimento;

    @ToString.Exclude
    @OneToMany(mappedBy = "cliente")
    private List<Pedido> pedidos;

    @ElementCollection
    @CollectionTable(name = "cliente_contato", joinColumns = @JoinColumn(name = "cliente_id", foreignKey = @ForeignKey(name = "fk_cliente_cliente_contato")))
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
