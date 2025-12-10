package com.chaski.Backend_chaski.repository;

import com.chaski.Backend_chaski.entity.Pago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PagoRepository extends JpaRepository<Pago, Integer> {
    Optional<Pago> findByPedidoId(Integer pedidoId);
    Optional<Pago> findByReferenciaPasarela(String referenciaPasarela);
}

