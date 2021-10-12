package com.algaworks.ecommerce.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
public class Pedido {

    @Id
    private Integer id;
    private LocalDateTime dataPedido;
    private LocalDateTime dataConlusao;
    private Integer notaFiscalId;
    private BigDecimal total;
    private StatusPedido status;

}
