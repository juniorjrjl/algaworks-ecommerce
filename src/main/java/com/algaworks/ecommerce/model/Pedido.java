package com.algaworks.ecommerce.model;

import com.algaworks.ecommerce.listener.GenericoListener;
import com.algaworks.ecommerce.listener.GerarNotaFicalListener;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString(callSuper = true)
@EntityListeners({GerarNotaFicalListener.class, GenericoListener.class})
@Entity
@Table(name = "pedido")
public class Pedido  extends EntidadeBaseInteger{

    @ManyToOne(optional = false)
    @JoinColumn(name = "cliente_id", nullable = false, foreignKey = @ForeignKey(name = "fk_pedido_cliente"))
    private Cliente cliente;

    @Column(name = "data_criacao", updatable = false, nullable = false)
    private LocalDateTime dataCriacao;

    @Column(name = "data_ultima_atualizacao", insertable = false)
    private LocalDateTime dataUltimaAtualizacao ;

    @Column(name = "data_conclusao")
    private LocalDateTime dataConlusao;

    @Column(precision = 19, scale = 2, nullable = false)
    private BigDecimal total;

    @Enumerated(EnumType.STRING)
    @Column(length = 30, nullable = false)
    private StatusPedido status;

    @Embedded
    private EnderecoEntregaPedido enderecoEntrega;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "pedido", orphanRemoval = true, cascade = CascadeType.PERSIST)
    private List<ItemPedido> itens;

    @ToString.Exclude
    @OneToOne(mappedBy = "pedido")
    private Pagamento pagamento;

    @OneToOne(mappedBy = "pedido")
    private NotaFiscal notaFiscal;

    public boolean isPago(){
        return status == StatusPedido.PAGO;
    }

    private void calcularTotal() {
        total = BigDecimal.ZERO;
        if (itens != null) {
            total = itens.stream().map(i -> new BigDecimal(i.getQuantidade()).multiply(i.getPrecoProduto()))
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
        }
    }

    @PrePersist
    public void aoPersitir(){
        dataCriacao = LocalDateTime.now();
        calcularTotal();
    }

    @PreUpdate
    public void aoAtualizar(){
        dataUltimaAtualizacao = LocalDateTime.now();
        calcularTotal();
    }

    @PostPersist
    public void aposPersistir() {
        System.out.println("Após persistir Pedido.");
    }

    @PostUpdate
    public void aposAtualizar() {
        System.out.println("Após atualizar Pedido.");
    }

    @PreRemove
    public void aoRemover() {
        System.out.println("Antes de remover Pedido.");
    }

    @PostRemove
    public void aposRemover() {
        System.out.println("Após remover Pedido.");
    }

    @PostLoad
    public void aoCarregar() {
        System.out.println("Após carregar o Pedido.");
    }
}
