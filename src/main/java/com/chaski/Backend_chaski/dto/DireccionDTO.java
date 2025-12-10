package com.chaski.Backend_chaski.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DireccionDTO {
    private Integer id;
    private Integer usuarioId;
    private String etiqueta;
    private String direccionCompleta;
    private String referencia;
    private Boolean esPredeterminada;
}

