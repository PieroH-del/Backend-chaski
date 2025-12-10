package com.chaski.Backend_chaski.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OpcionDetallePedidoDTO {
    private Integer id;
    private Integer detallePedidoId;
    private Integer opcionId;
    private String opcionNombre;
    private BigDecimal precioExtraCobrado;
}

