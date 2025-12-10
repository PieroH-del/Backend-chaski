package com.chaski.Backend_chaski.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OpcionDTO {
    private Integer id;
    private Integer grupoOpcionId;
    private String nombre;
    private BigDecimal precioExtra;
}

