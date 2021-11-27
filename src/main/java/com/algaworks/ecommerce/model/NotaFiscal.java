package com.algaworks.ecommerce.model;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@ToString(callSuper = true)
@Entity
@Table(name = "nota_fiscal")
public class NotaFiscal  extends EntidadeBaseInteger{

    @MapsId
    @JoinColumn(name = "pedido_id", nullable = false, foreignKey = @ForeignKey(name = "fk_nota_fiscal_pedido"))
    @OneToOne(optional = false)
    private Pedido pedido;

    @Lob
    @Column(nullable = false)
    private byte[] xml;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "data_emissao", nullable = false)
    private Date dataEmissao;
}