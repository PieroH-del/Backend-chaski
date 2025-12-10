package com.chaski.Backend_chaski.repository;

import com.chaski.Backend_chaski.entity.EstadoPedido;
import com.chaski.Backend_chaski.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Integer> {
    List<Pedido> findByUsuarioId(Integer usuarioId);
    List<Pedido> findByRestauranteId(Integer restauranteId);
    List<Pedido> findByEstado(EstadoPedido estado);
    List<Pedido> findByRestauranteIdAndEstado(Integer restauranteId, EstadoPedido estado);

    @Query("SELECT p FROM Pedido p WHERE p.restaurante.id = :restauranteId AND p.fechaCreacion >= :fechaInicio")
    List<Pedido> findPedidosRecientesRestaurante(@Param("restauranteId") Integer restauranteId, @Param("fechaInicio") LocalDateTime fechaInicio);
}

