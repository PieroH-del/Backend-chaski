package com.chaski.Backend_chaski.dto;

import com.chaski.Backend_chaski.entity.EstadoPedido;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoDTO {
    private Integer id;
    private Integer usuarioId;
    private String usuarioNombre;
    private Integer restauranteId;
    private String restauranteNombre;
    private Integer direccionEntregaId;
    private String direccionEntregaCompleta;
    private BigDecimal subtotalProductos;
    private BigDecimal costoEnvio;
    private BigDecimal impuestos;
    private BigDecimal totalFinal;
    private EstadoPedido estado;
    private String notasInstrucciones;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;
    private List<DetallePedidoDTO> detallesPedido;
    private PagoDTO pago;
}

