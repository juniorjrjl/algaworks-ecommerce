package com.algaworks.ecommerce.model;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@ToString(callSuper = true)
@Entity
@Table(name = "nota_fiscla")
public class NotaFiscal  extends EntidadeBaseInteger{

    @MapsId
    @JoinColumn(name = "pedido_id")
    @OneToOne(optional = false)
    private Pedido pedido;

    @Lob
    private byte[] xml;

    @Column(name = "data_emissao")
    private Date dataEmissao;
}