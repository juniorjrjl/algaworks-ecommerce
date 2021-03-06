package com.algaworks.ecommerce.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.util.Date;

@Getter
@Setter
@ToString(callSuper = true)
@Entity
@Table(name = "nota_fiscal")
public class NotaFiscal  /*extends EntidadeBaseInteger*/{

    @Id
    private Integer id;

    @Version
    private Integer versao;

    @NotNull
    @MapsId
    @JoinColumn(name = "pedido_id", nullable = false, foreignKey = @ForeignKey(name = "fk_nota_fiscal_pedido"))
    @OneToOne(optional = false)
    private Pedido pedido;

    @NotEmpty
    @Lob
    @Column(nullable = false)
    //@Type(type = "org.hibernate.type.BinaryType")
    private byte[] xml;

    @PastOrPresent
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "data_emissao", nullable = false)
    private Date dataEmissao;
}