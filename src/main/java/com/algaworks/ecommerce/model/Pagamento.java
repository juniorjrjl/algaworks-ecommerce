package com.algaworks.ecommerce.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@ToString(callSuper = true)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_pagamento")
@Entity
@Table(name = "pagamento")
public abstract class Pagamento extends EntidadeBaseInteger{

    @MapsId
    @JoinColumn(name = "pedido_id")
    @OneToOne(optional = false)
    private Pedido pedido;

    @Enumerated(EnumType.STRING)
    private StatusPagamento status;


}
