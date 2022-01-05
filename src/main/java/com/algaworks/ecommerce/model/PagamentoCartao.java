package com.algaworks.ecommerce.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@ToString(callSuper = true)
@Entity
@DiscriminatorValue("cartao")
public class PagamentoCartao  extends Pagamento{

    @NotEmpty
    @Column(name = "numero_cartao", length = 50)
    private String numeroCartao;

}
