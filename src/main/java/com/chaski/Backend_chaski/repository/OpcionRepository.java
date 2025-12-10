package com.chaski.Backend_chaski.repository;

import com.chaski.Backend_chaski.entity.Opcion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OpcionRepository extends JpaRepository<Opcion, Integer> {
    List<Opcion> findByGrupoOpcionId(Integer grupoOpcionId);
}

