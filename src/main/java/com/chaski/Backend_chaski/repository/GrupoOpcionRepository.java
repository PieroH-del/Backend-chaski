package com.chaski.Backend_chaski.repository;

import com.chaski.Backend_chaski.entity.GrupoOpcion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GrupoOpcionRepository extends JpaRepository<GrupoOpcion, Integer> {
    List<GrupoOpcion> findByProductoId(Integer productoId);
}

