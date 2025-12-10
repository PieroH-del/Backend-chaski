package com.chaski.Backend_chaski.repository;

import com.chaski.Backend_chaski.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Integer> {
    List<Producto> findByRestauranteId(Integer restauranteId);
    List<Producto> findByRestauranteIdAndDisponible(Integer restauranteId, Boolean disponible);

    @Query("SELECT p FROM Producto p WHERE p.nombre LIKE %:keyword% OR p.descripcion LIKE %:keyword%")
    List<Producto> buscarPorNombreODescripcion(@Param("keyword") String keyword);
}

