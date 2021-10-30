package com.algaworks.ecommerce.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

@Data
@NoArgsConstructor
@Entity
@Table(name = "nota_fiscla")
public class NotaFiscal {

    @Id
    @Column(name = "pedido_id")
    private Integer id;

    @MapsId
    @JoinColumn(name = "pedido_id")
    @OneToOne(optional = false)
    private Pedido pedido;

    @Lob
    private byte[] xml;

    @Column(name = "data_emissao")
    private Date dataEmissao;
}