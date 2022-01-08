package com.algaworks.ecommerce.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.Objects;

@SqlResultSetMappings({
        @SqlResultSetMapping(name = "item_pedido-produto.ItemPedido-Produto",
                entities = {@EntityResult(entityClass = ItemPedido.class)})
})
@Getter
@Setter
@ToString(callSuper = true)
@Entity
@Table(name = "item_pedido")
public class ItemPedido {

    @EmbeddedId
    private ItemPedidoId id;

    @NotNull
    @MapsId("pedidoId")
    @EqualsAndHashCode.Exclude
    @ManyToOne(optional = false)
    @JoinColumn(name = "pedido_id", nullable = false, foreignKey = @ForeignKey(name = "fk_item_pedido_pedido"))
    private Pedido pedido;

    @NotNull
    @MapsId("produtoId")
    @EqualsAndHashCode.Exclude
    @ManyToOne(optional = false)
    @JoinColumn(name = "produto_id", nullable = false, foreignKey = @ForeignKey(name = "fk_item_pedido_produto"))
    private Produto produto;

    @Positive
    @NotNull
    @Column(name = "preco_produto", precision = 19, scale = 2, nullable = false)
    private BigDecimal precoProduto;

    @Positive
    @NotNull
    @Column(nullable = false)
    private Integer quantidade;

    @Version
    private Integer versao;

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final ItemPedido that = (ItemPedido) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
