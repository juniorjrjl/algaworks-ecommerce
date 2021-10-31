package com.algaworks.ecommerce.model;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@ToString(callSuper = true)
@Entity
@Table(name = "pagamento_cartao")
public class PagamentoCartao  extends EntidadeBaseInteger{

    @MapsId
    @JoinColumn(name = "pedido_id")
    @OneToOne(optional = false)
    private Pedido pedido;

    @Enumerated(EnumType.STRING)
    private StatusPagamento status;

    private String numero;

}
