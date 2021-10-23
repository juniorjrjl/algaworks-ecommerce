package com.algaworks.ecommerce.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

@Data
@NoArgsConstructor
@Entity
@Table(name = "pedido")
public class Pedido {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Integer id;
    @ManyToOne(optional = false)
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;
    @Column(name = "data_pedido")
    private LocalDateTime dataPedido;
    @Column(name = "data_conclusão")
    private LocalDateTime dataConlusao;
    private BigDecimal total;
    @Enumerated(EnumType.STRING)
    private StatusPedido status;
    @Embedded
    private EnderecoEntregaPedido enderecoEntrega;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "pedido")
    private List<ItemPedido> itensPedido;
    @OneToOne(mappedBy = "pedido")
    private PagamentoCartao pagamentoCartao;
    @OneToOne(mappedBy = "pedido")
    private NotaFiscal notaFiscal;

}
