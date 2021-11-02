package com.algaworks.ecommerce.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Getter
@Setter
@ToString(callSuper = true)
@Entity
@DiscriminatorValue("cartao")
public class PagamentoCartao  extends Pagamento{

    @Column(name = "numero_cartao")
    private String numeroCartao;

}
