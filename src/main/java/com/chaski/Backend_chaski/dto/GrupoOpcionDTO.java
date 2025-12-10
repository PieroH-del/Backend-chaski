package com.chaski.Backend_chaski.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GrupoOpcionDTO {
    private Integer id;
    private Integer productoId;
    private String nombre;
    private Boolean esObligatorio;
    private Integer seleccionMinima;
    private Integer seleccionMaxima;
    private List<OpcionDTO> opciones;
}

