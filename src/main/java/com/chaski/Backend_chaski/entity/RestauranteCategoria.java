package com.chaski.Backend_chaski.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "restaurante_categorias")
@IdClass(RestauranteCategoria.RestauranteCategoriaId.class)
public class RestauranteCategoria {

    @Id
    @ManyToOne
    @JoinColumn(name = "restaurante_id")
    private Restaurante restaurante;

    @Id
    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RestauranteCategoriaId implements Serializable {
        private Integer restaurante;
        private Integer categoria;
    }
}

