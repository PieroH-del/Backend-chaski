package com.chaski.Backend_chaski.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioRegistroRequest {
    private String firebaseIdToken;
    private String nombre;
    private String telefono;
    private String imagenPerfilUrl;
}

