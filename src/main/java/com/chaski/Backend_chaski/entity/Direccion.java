package com.chaski.Backend_chaski.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "direcciones")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Direccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Column(nullable = false)
    private String etiqueta;

    @Column(name = "direccion_completa", nullable = false)
    private String direccionCompleta;

    private String referencia;

    @Column(name = "es_predeterminada")
    private Boolean esPredeterminada = false;
}

