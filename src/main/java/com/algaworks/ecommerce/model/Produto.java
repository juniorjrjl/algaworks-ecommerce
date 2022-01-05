package com.algaworks.ecommerce.model;

import com.algaworks.ecommerce.dto.ProdutoDTO;
import com.algaworks.ecommerce.listener.GenericoListener;
import com.algaworks.ecommerce.model.converter.BooleanToSimNaoConverter;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@SqlResultSetMappings({
        @SqlResultSetMapping(name = "produto.Produto",
                entities = {@EntityResult(entityClass = Produto.class)}),
        @SqlResultSetMapping(name = "produtoFS.Produto",
                entities = {@EntityResult(entityClass = Produto.class,
                        fields = {
                                @FieldResult(name = "id", column = "id"),
                                @FieldResult(name = "nome", column = "nome"),
                                @FieldResult(name = "descricao", column = "id"),
                                @FieldResult(name = "preco", column = "preco"),
                                @FieldResult(name = "foto", column = "foto"),
                                @FieldResult(name = "dataCriacao", column = "data_criacao"),
                                @FieldResult(name = "dataUltimaAtualizacao", column = "data_ultima_atualizacao"),
                        })}),
        @SqlResultSetMapping(name = "produtoCR.ProdutoDTO",
                classes = {
                        @ConstructorResult(targetClass = ProdutoDTO.class, columns = {
                                @ColumnResult(name = "id", type = Integer.class),
                                @ColumnResult(name = "nome", type = String.class)
                        })})
})
@Getter
@Setter
@ToString(callSuper = true)
@NamedNativeQueries({
        @NamedNativeQuery(name = "produto.listar",
                query = "select id, nome, descricao, data_criacao, data_ultima_atualizacao, preco, foto from produto",
                resultClass = Produto.class),
        @NamedNativeQuery(name = "produtoFS.listar", query = "select * from produto", resultSetMapping = "produtoFS.Produto")
})
@NamedQueries({
        @NamedQuery(name = "Produto.listar", query = "select p from Produto p"),
        @NamedQuery(name = "Produto.listarPorCategoria", query = "select p " +
                                                                  "  from Produto p " +
                                                                  " where exists (select 1 " +
                                                                  "                 from Categoria c2 " +
                                                                  "                 join c2.produtos p2 " +
                                                                  "                where p2 = p " +
                                                                  "                  and c2.id = :categoria)")
})
@EntityListeners({GenericoListener.class})
@Entity
@Table(name = "produto", indexes = @Index(name = "idx_nome", columnList = "nome"))
public class Produto extends EntidadeBaseInteger{

    @NotBlank
    @Column(length = 100, nullable = false)
    private String nome;

    @Lob
    private String descricao;

    @Positive
    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal preco;

    @Convert(converter = BooleanToSimNaoConverter.class)
    @NotNull
    @Column(length = 3, nullable = false)
    private Boolean ativo = Boolean.FALSE;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToMany
    @JoinTable(name = "produto_categoria",
            joinColumns = @JoinColumn(name = "produto_id", foreignKey = @ForeignKey(name = "fk_produto_produto_categoria")),
            inverseJoinColumns = @JoinColumn(name = "categoria_id", foreignKey = @ForeignKey(name = "fk_categoria_produto_categoria")))
    private List<Categoria> categorias;

    @OneToOne(mappedBy = "produto")
    private Estoque estoque;

    @NotNull
    @PastOrPresent
    @Column(name = "data_criacao", updatable = false, nullable = false)
    private LocalDateTime dataCriacao;

    @PastOrPresent
    @Column(name = "data_ultima_atualizacao", insertable = false)
    private LocalDateTime dataUltimaAtualizacao ;

    @ElementCollection
    @CollectionTable(name = "produto_tag", joinColumns = @JoinColumn(name = "produto_id"), foreignKey = @ForeignKey(name = "fk_produto_tags"))
    @Column(name = "tag", nullable = false, length = 50)
    private List<String> tags;

    @ElementCollection
    @CollectionTable(name = "produto_atributo", joinColumns = @JoinColumn(name = "produto_id", foreignKey = @ForeignKey(name = "fk_produto_produto_atributo")))
    private List<Atributo> atributos;

    @Lob
    private byte[] foto;

}
