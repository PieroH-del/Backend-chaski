package com.chaski.Backend_chaski.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTO {
    private Integer id;
    private String nombre;
    private String email;
    private String telefono;
    private String firebaseUid;
    private String imagenPerfilUrl;
    private LocalDateTime fechaRegistro;
    private Boolean activo;
    private List<DireccionDTO> direcciones;
}

