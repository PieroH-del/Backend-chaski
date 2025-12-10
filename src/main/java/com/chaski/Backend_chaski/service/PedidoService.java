package com.chaski.Backend_chaski.service;

import com.chaski.Backend_chaski.dto.DetallePedidoDTO;
import com.chaski.Backend_chaski.dto.PedidoDTO;
import com.chaski.Backend_chaski.entity.*;
import com.chaski.Backend_chaski.exception.BadRequestException;
import com.chaski.Backend_chaski.exception.ResourceNotFoundException;
import com.chaski.Backend_chaski.mapper.PedidoMapper;
import com.chaski.Backend_chaski.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private DireccionRepository direccionRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private DetallePedidoRepository detallePedidoRepository;

    @Autowired
    private PedidoMapper pedidoMapper;

    public List<PedidoDTO> obtenerTodos() {
        return pedidoMapper.toDTOList(pedidoRepository.findAll());
    }

    public PedidoDTO obtenerPorId(Integer id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido no encontrado con id: " + id));
        return pedidoMapper.toDTO(pedido);
    }

    public List<PedidoDTO> obtenerPedidosPorUsuario(Integer usuarioId) {
        return pedidoMapper.toDTOList(pedidoRepository.findByUsuarioId(usuarioId));
    }

    public List<PedidoDTO> obtenerPedidosPorRestaurante(Integer restauranteId) {
        return pedidoMapper.toDTOList(pedidoRepository.findByRestauranteId(restauranteId));
    }

    public List<PedidoDTO> obtenerPedidosPorEstado(EstadoPedido estado) {
        return pedidoMapper.toDTOList(pedidoRepository.findByEstado(estado));
    }

    public List<PedidoDTO> obtenerPedidosRecientesRestaurante(Integer restauranteId, Integer horasAtras) {
        LocalDateTime fechaInicio = LocalDateTime.now().minusHours(horasAtras);
        return pedidoMapper.toDTOList(pedidoRepository.findPedidosRecientesRestaurante(restauranteId, fechaInicio));
    }

    public PedidoDTO crearPedido(PedidoDTO pedidoDTO) {
        // Validar usuario
        Usuario usuario = usuarioRepository.findById(pedidoDTO.getUsuarioId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        // Validar restaurante
        Restaurante restaurante = restauranteRepository.findById(pedidoDTO.getRestauranteId())
                .orElseThrow(() -> new ResourceNotFoundException("Restaurante no encontrado"));

        if (!restaurante.getEstaAbierto()) {
            throw new BadRequestException("El restaurante está cerrado en este momento");
        }

        // Validar dirección
        Direccion direccion = null;
        if (pedidoDTO.getDireccionEntregaId() != null) {
            direccion = direccionRepository.findById(pedidoDTO.getDireccionEntregaId())
                    .orElseThrow(() -> new ResourceNotFoundException("Dirección no encontrada"));
        }

        // Crear pedido
        Pedido pedido = new Pedido();
        pedido.setUsuario(usuario);
        pedido.setRestaurante(restaurante);
        pedido.setDireccionEntrega(direccion);
        pedido.setNotasInstrucciones(pedidoDTO.getNotasInstrucciones());
        pedido.setEstado(EstadoPedido.PENDIENTE_PAGO);

        // Calcular totales
        BigDecimal subtotal = BigDecimal.ZERO;
        List<DetallePedido> detalles = new ArrayList<>();

        for (DetallePedidoDTO detalleDTO : pedidoDTO.getDetallesPedido()) {
            Producto producto = productoRepository.findById(detalleDTO.getProductoId())
                    .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado"));

            if (!producto.getDisponible()) {
                throw new BadRequestException("El producto " + producto.getNombre() + " no está disponible");
            }

            DetallePedido detalle = new DetallePedido();
            detalle.setPedido(pedido);
            detalle.setProducto(producto);
            detalle.setCantidad(detalleDTO.getCantidad());
            detalle.setPrecioUnitario(producto.getPrecio());

            BigDecimal totalDetalle = producto.getPrecio().multiply(BigDecimal.valueOf(detalleDTO.getCantidad()));
            subtotal = subtotal.add(totalDetalle);

            detalles.add(detalle);
        }

        pedido.setDetallesPedido(detalles);
        pedido.setSubtotalProductos(subtotal);
        pedido.setCostoEnvio(restaurante.getCostoEnvioBase());

        // Calcular impuestos (18% en Perú)
        BigDecimal impuestos = subtotal.multiply(BigDecimal.valueOf(0.18));
        pedido.setImpuestos(impuestos);

        BigDecimal total = subtotal.add(restaurante.getCostoEnvioBase()).add(impuestos);
        pedido.setTotalFinal(total);

        Pedido pedidoGuardado = pedidoRepository.save(pedido);
        return pedidoMapper.toDTO(pedidoGuardado);
    }

    public PedidoDTO actualizarEstadoPedido(Integer id, EstadoPedido nuevoEstado) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido no encontrado con id: " + id));

        // Validar transiciones de estado permitidas
        validarTransicionEstado(pedido.getEstado(), nuevoEstado);

        pedido.setEstado(nuevoEstado);
        Pedido pedidoActualizado = pedidoRepository.save(pedido);
        return pedidoMapper.toDTO(pedidoActualizado);
    }

    private void validarTransicionEstado(EstadoPedido estadoActual, EstadoPedido nuevoEstado) {
        // Implementar lógica de validación de transiciones
        if (estadoActual == EstadoPedido.CANCELADO || estadoActual == EstadoPedido.ENTREGADO) {
            throw new BadRequestException("No se puede cambiar el estado de un pedido cancelado o entregado");
        }
    }

    public PedidoDTO confirmarPedido(Integer id) {
        return actualizarEstadoPedido(id, EstadoPedido.CONFIRMADO_TIENDA);
    }

    public PedidoDTO marcarEnPreparacion(Integer id) {
        return actualizarEstadoPedido(id, EstadoPedido.EN_PREPARACION);
    }

    public PedidoDTO marcarListoParaRecoger(Integer id) {
        return actualizarEstadoPedido(id, EstadoPedido.LISTO_PARA_RECOGER);
    }

    public PedidoDTO marcarEnCamino(Integer id) {
        return actualizarEstadoPedido(id, EstadoPedido.EN_CAMINO);
    }

    public PedidoDTO marcarEntregado(Integer id) {
        return actualizarEstadoPedido(id, EstadoPedido.ENTREGADO);
    }

    public PedidoDTO cancelarPedido(Integer id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido no encontrado con id: " + id));

        if (pedido.getEstado() == EstadoPedido.EN_CAMINO || pedido.getEstado() == EstadoPedido.ENTREGADO) {
            throw new BadRequestException("No se puede cancelar un pedido en camino o entregado");
        }

        return actualizarEstadoPedido(id, EstadoPedido.CANCELADO);
    }
}

