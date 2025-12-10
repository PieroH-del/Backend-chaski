package com.chaski.Backend_chaski.controller;

import com.chaski.Backend_chaski.dto.PedidoDTO;
import com.chaski.Backend_chaski.entity.EstadoPedido;
import com.chaski.Backend_chaski.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
@CrossOrigin(origins = "*")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @GetMapping
    public ResponseEntity<List<PedidoDTO>> obtenerTodos() {
        return ResponseEntity.ok(pedidoService.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoDTO> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(pedidoService.obtenerPorId(id));
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<PedidoDTO>> obtenerPorUsuario(@PathVariable Integer usuarioId) {
        return ResponseEntity.ok(pedidoService.obtenerPedidosPorUsuario(usuarioId));
    }

    @GetMapping("/restaurante/{restauranteId}")
    public ResponseEntity<List<PedidoDTO>> obtenerPorRestaurante(@PathVariable Integer restauranteId) {
        return ResponseEntity.ok(pedidoService.obtenerPedidosPorRestaurante(restauranteId));
    }

    @GetMapping("/restaurante/{restauranteId}/recientes")
    public ResponseEntity<List<PedidoDTO>> obtenerRecientesRestaurante(
            @PathVariable Integer restauranteId,
            @RequestParam(defaultValue = "24") Integer horasAtras) {
        return ResponseEntity.ok(pedidoService.obtenerPedidosRecientesRestaurante(restauranteId, horasAtras));
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<PedidoDTO>> obtenerPorEstado(@PathVariable EstadoPedido estado) {
        return ResponseEntity.ok(pedidoService.obtenerPedidosPorEstado(estado));
    }

    @PostMapping
    public ResponseEntity<PedidoDTO> crear(@RequestBody PedidoDTO pedidoDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(pedidoService.crearPedido(pedidoDTO));
    }

    @PatchMapping("/{id}/confirmar")
    public ResponseEntity<PedidoDTO> confirmar(@PathVariable Integer id) {
        return ResponseEntity.ok(pedidoService.confirmarPedido(id));
    }

    @PatchMapping("/{id}/preparacion")
    public ResponseEntity<PedidoDTO> marcarEnPreparacion(@PathVariable Integer id) {
        return ResponseEntity.ok(pedidoService.marcarEnPreparacion(id));
    }

    @PatchMapping("/{id}/listo")
    public ResponseEntity<PedidoDTO> marcarListo(@PathVariable Integer id) {
        return ResponseEntity.ok(pedidoService.marcarListoParaRecoger(id));
    }

    @PatchMapping("/{id}/en-camino")
    public ResponseEntity<PedidoDTO> marcarEnCamino(@PathVariable Integer id) {
        return ResponseEntity.ok(pedidoService.marcarEnCamino(id));
    }

    @PatchMapping("/{id}/entregado")
    public ResponseEntity<PedidoDTO> marcarEntregado(@PathVariable Integer id) {
        return ResponseEntity.ok(pedidoService.marcarEntregado(id));
    }

    @PatchMapping("/{id}/cancelar")
    public ResponseEntity<PedidoDTO> cancelar(@PathVariable Integer id) {
        return ResponseEntity.ok(pedidoService.cancelarPedido(id));
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<PedidoDTO> cambiarEstado(@PathVariable Integer id, @RequestParam EstadoPedido estado) {
        return ResponseEntity.ok(pedidoService.actualizarEstadoPedido(id, estado));
    }
}

