package com.chaski.Backend_chaski.controller;

import com.chaski.Backend_chaski.dto.RestauranteDTO;
import com.chaski.Backend_chaski.service.RestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/restaurantes")
@CrossOrigin(origins = "*")
public class RestauranteController {

    @Autowired
    private RestauranteService restauranteService;

    @GetMapping
    public ResponseEntity<List<RestauranteDTO>> obtenerTodos() {
        return ResponseEntity.ok(restauranteService.obtenerTodos());
    }

    @GetMapping("/abiertos")
    public ResponseEntity<List<RestauranteDTO>> obtenerAbiertos() {
        return ResponseEntity.ok(restauranteService.obtenerRestaurantesAbiertos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestauranteDTO> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(restauranteService.obtenerPorId(id));
    }

    @GetMapping("/categoria/{categoriaId}")
    public ResponseEntity<List<RestauranteDTO>> filtrarPorCategoria(@PathVariable Integer categoriaId) {
        return ResponseEntity.ok(restauranteService.filtrarPorCategoria(categoriaId));
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<RestauranteDTO>> buscar(@RequestParam String keyword) {
        return ResponseEntity.ok(restauranteService.buscarPorNombre(keyword));
    }

    @GetMapping("/calificacion")
    public ResponseEntity<List<RestauranteDTO>> filtrarPorCalificacion(@RequestParam BigDecimal minCalificacion) {
        return ResponseEntity.ok(restauranteService.filtrarPorCalificacion(minCalificacion));
    }

    @PostMapping
    public ResponseEntity<RestauranteDTO> crear(@RequestBody RestauranteDTO restauranteDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(restauranteService.crearRestaurante(restauranteDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RestauranteDTO> actualizar(@PathVariable Integer id, @RequestBody RestauranteDTO restauranteDTO) {
        return ResponseEntity.ok(restauranteService.actualizarRestaurante(id, restauranteDTO));
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<RestauranteDTO> cambiarEstado(@PathVariable Integer id, @RequestParam Boolean estaAbierto) {
        return ResponseEntity.ok(restauranteService.cambiarEstadoApertura(id, estaAbierto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        restauranteService.eliminarRestaurante(id);
        return ResponseEntity.noContent().build();
    }
}

