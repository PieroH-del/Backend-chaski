package com.chaski.Backend_chaski.controller;

import com.chaski.Backend_chaski.dto.ProductoDTO;
import com.chaski.Backend_chaski.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
@CrossOrigin(origins = "*")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @GetMapping
    public ResponseEntity<List<ProductoDTO>> obtenerTodos() {
        return ResponseEntity.ok(productoService.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoDTO> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(productoService.obtenerPorId(id));
    }

    @GetMapping("/restaurante/{restauranteId}")
    public ResponseEntity<List<ProductoDTO>> obtenerPorRestaurante(@PathVariable Integer restauranteId) {
        return ResponseEntity.ok(productoService.obtenerPorRestaurante(restauranteId));
    }

    @GetMapping("/restaurante/{restauranteId}/disponibles")
    public ResponseEntity<List<ProductoDTO>> obtenerDisponiblesPorRestaurante(@PathVariable Integer restauranteId) {
        return ResponseEntity.ok(productoService.obtenerDisponiblesPorRestaurante(restauranteId));
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<ProductoDTO>> buscar(@RequestParam String keyword) {
        return ResponseEntity.ok(productoService.buscarProductos(keyword));
    }

    @PostMapping
    public ResponseEntity<ProductoDTO> crear(@RequestBody ProductoDTO productoDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productoService.crearProducto(productoDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductoDTO> actualizar(@PathVariable Integer id, @RequestBody ProductoDTO productoDTO) {
        return ResponseEntity.ok(productoService.actualizarProducto(id, productoDTO));
    }

    @PatchMapping("/{id}/disponibilidad")
    public ResponseEntity<ProductoDTO> cambiarDisponibilidad(@PathVariable Integer id, @RequestParam Boolean disponible) {
        return ResponseEntity.ok(productoService.cambiarDisponibilidad(id, disponible));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        productoService.eliminarProducto(id);
        return ResponseEntity.noContent().build();
    }
}

