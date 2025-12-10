package com.chaski.Backend_chaski.repository;

import com.chaski.Backend_chaski.entity.Direccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DireccionRepository extends JpaRepository<Direccion, Integer> {
    List<Direccion> findByUsuarioId(Integer usuarioId);
    List<Direccion> findByUsuarioIdAndEsPredeterminada(Integer usuarioId, Boolean esPredeterminada);
}

