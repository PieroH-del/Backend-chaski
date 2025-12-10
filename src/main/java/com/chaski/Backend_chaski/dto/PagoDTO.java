package com.chaski.Backend_chaski.dto;

import com.chaski.Backend_chaski.entity.EstadoPago;
import com.chaski.Backend_chaski.entity.MetodoPago;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PagoDTO {
    private Integer id;
    private Integer pedidoId;
    private BigDecimal monto;
    private MetodoPago metodo;
    private EstadoPago estado;
    private String referenciaPasarela;
    private LocalDateTime fechaPago;
}

