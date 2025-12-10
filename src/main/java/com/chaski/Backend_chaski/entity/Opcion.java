package com.chaski.Backend_chaski.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "opciones")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Opcion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "grupo_opcion_id", nullable = false)
    private GrupoOpcion grupoOpcion;

    @Column(nullable = false)
    private String nombre;

    @Column(name = "precio_extra", precision = 10, scale = 2)
    private BigDecimal precioExtra = BigDecimal.ZERO;

    @OneToMany(mappedBy = "opcion")
    private List<OpcionDetallePedido> opcionesDetallePedido;
}

