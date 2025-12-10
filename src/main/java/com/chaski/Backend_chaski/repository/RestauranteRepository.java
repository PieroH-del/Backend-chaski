package com.chaski.Backend_chaski.repository;

import com.chaski.Backend_chaski.entity.Restaurante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface RestauranteRepository extends JpaRepository<Restaurante, Integer> {
    List<Restaurante> findByEstaAbierto(Boolean estaAbierto);

    @Query("SELECT DISTINCT r FROM Restaurante r JOIN r.restauranteCategorias rc WHERE rc.categoria.id = :categoriaId")
    List<Restaurante> findByCategoriaId(@Param("categoriaId") Integer categoriaId);

    @Query("SELECT r FROM Restaurante r WHERE r.nombre LIKE %:keyword%")
    List<Restaurante> buscarPorNombre(@Param("keyword") String keyword);

    @Query("SELECT r FROM Restaurante r WHERE r.calificacionPromedio >= :minCalificacion")
    List<Restaurante> findByCalificacionPromedioGreaterThanEqual(@Param("minCalificacion") BigDecimal minCalificacion);
}

