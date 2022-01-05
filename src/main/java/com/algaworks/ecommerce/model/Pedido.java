package com.algaworks.ecommerce.model;

import com.algaworks.ecommerce.listener.GenericoListener;
import com.algaworks.ecommerce.listener.GerarNotaFicalListener;
import lombok.*;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString(callSuper = true)
@EntityListeners({GerarNotaFicalListener.class, GenericoListener.class})
@Entity
@Table(name = "pedido")
@NamedEntityGraphs({
        @NamedEntityGraph(name = "Pedido.dadosEssencias", attributeNodes = {
                @NamedAttributeNode("dataCriacao"),
                @NamedAttributeNode("status"),
                @NamedAttributeNode("total"),
                @NamedAttributeNode(value = "cliente", subgraph = "cli"),
        }, subgraphs = {
                @NamedSubgraph(name = "cli", attributeNodes = {
                        @NamedAttributeNode("nome"),
                        @NamedAttributeNode("cpf")
                })
        })
})
public class Pedido  extends EntidadeBaseInteger /*implements PersistentAttributeInterceptable*/ {

    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "cliente_id", nullable = false, foreignKey = @ForeignKey(name = "fk_pedido_cliente"))
    private Cliente cliente;

    @PastOrPresent
    @NotNull
    @Column(name = "data_criacao", updatable = false, nullable = false)
    private LocalDateTime dataCriacao;

    @PastOrPresent
    @Column(name = "data_ultima_atualizacao", insertable = false)
    private LocalDateTime dataUltimaAtualizacao ;

    @PastOrPresent
    @Column(name = "data_conclusao")
    private LocalDateTime dataConlusao;

    @Positive
    @Column(precision = 19, scale = 2, nullable = false)
    private BigDecimal total;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(length = 30, nullable = false)
    private StatusPedido status;

    @Embedded
    private EnderecoEntregaPedido enderecoEntrega;

    @NotEmpty
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "pedido", orphanRemoval = true, cascade = CascadeType.PERSIST)
    private List<ItemPedido> itens;

    @ToString.Exclude
    @LazyToOne(LazyToOneOption.NO_PROXY)
    @OneToOne(mappedBy = "pedido"/*, fetch = FetchType.LAZY*/)
    private Pagamento pagamento;

    @ToString.Exclude
    @LazyToOne(LazyToOneOption.NO_PROXY)
    @OneToOne(mappedBy = "pedido"/*, fetch = FetchType.LAZY*/)
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

    /*public Pagamento getPagamento() {
            return (Objects.nonNull(this.persistentAttributeInterceptor)) ?
                    (Pagamento) persistentAttributeInterceptor.readObject(this, "pagamento", this.pagamento):
                    pagamento;
    }

    public void setPagamento(Pagamento pagamento) {
            this.pagamento = (Objects.nonNull(this.persistentAttributeInterceptor)) ?
                    (Pagamento) persistentAttributeInterceptor.writeObject(this, "pagamento", this.pagamento, pagamento):
                    pagamento;
    }

    public NotaFiscal getNotaFiscal() {
            return (Objects.nonNull(this.persistentAttributeInterceptor)) ?
                    (NotaFiscal) persistentAttributeInterceptor.readObject(this, "notaFiscal", this.notaFiscal):
                    notaFiscal;
    }

    public void setNotaFiscal(NotaFiscal notaFiscal) {
            this.notaFiscal = (Objects.nonNull(this.persistentAttributeInterceptor)) ?
                    (NotaFiscal) persistentAttributeInterceptor.writeObject(this, "notaFiscal", this.notaFiscal, notaFiscal):
                    notaFiscal;
    }

    @Setter(AccessLevel.PRIVATE)
    @Getter(AccessLevel.PRIVATE)
    @Transient
    private PersistentAttributeInterceptor persistentAttributeInterceptor;

    @Override
    public PersistentAttributeInterceptor $$_hibernate_getInterceptor() {
        return this.persistentAttributeInterceptor;
    }

    @Override
    public void $$_hibernate_setInterceptor(final PersistentAttributeInterceptor interceptor) {
        this.persistentAttributeInterceptor = interceptor;
    }*/
}
