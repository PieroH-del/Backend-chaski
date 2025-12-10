package com.chaski.Backend_chaski.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "grupos_opciones")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GrupoOpcion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "producto_id", nullable = false)
    private Producto producto;

    @Column(nullable = false)
    private String nombre;

    @Column(name = "es_obligatorio")
    private Boolean esObligatorio = false;

    @Column(name = "seleccion_minima")
    private Integer seleccionMinima = 0;

    @Column(name = "seleccion_maxima")
    private Integer seleccionMaxima = 1;

    @OneToMany(mappedBy = "grupoOpcion", cascade = CascadeType.ALL)
    private List<Opcion> opciones;
}

