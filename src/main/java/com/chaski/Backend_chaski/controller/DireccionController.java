package com.chaski.Backend_chaski.controller;

import com.chaski.Backend_chaski.dto.DireccionDTO;
import com.chaski.Backend_chaski.service.DireccionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/direcciones")
@CrossOrigin(origins = "*")
public class DireccionController {

    @Autowired
    private DireccionService direccionService;

    ////////////sasassas

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<DireccionDTO>> obtenerPorUsuario(@PathVariable Integer usuarioId) {
        return ResponseEntity.ok(direccionService.obtenerDireccionesPorUsuario(usuarioId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DireccionDTO> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(direccionService.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<DireccionDTO> crear(@RequestBody DireccionDTO direccionDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(direccionService.crearDireccion(direccionDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DireccionDTO> actualizar(@PathVariable Integer id, @RequestBody DireccionDTO direccionDTO) {
        return ResponseEntity.ok(direccionService.actualizarDireccion(id, direccionDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        direccionService.eliminarDireccion(id);
        return ResponseEntity.noContent().build();
    }
}

