package com.chaski.Backend_chaski.controller;

import com.chaski.Backend_chaski.dto.LoginRequest;
import com.chaski.Backend_chaski.dto.UsuarioDTO;
import com.chaski.Backend_chaski.dto.UsuarioRegistroRequest;
import com.chaski.Backend_chaski.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> obtenerTodos() {
        return ResponseEntity.ok(usuarioService.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(usuarioService.obtenerPorId(id));
    }

    /**
     * Registro con Firebase
     */
    @PostMapping("/registro")
    public ResponseEntity<UsuarioDTO> registrarUsuario(@RequestBody UsuarioRegistroRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(usuarioService.registrarUsuarioConFirebase(request));
    }

    /**
     * Login con Firebase
     */
    @PostMapping("/login")
    public ResponseEntity<UsuarioDTO> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(usuarioService.loginConFirebase(request));
    }

    /**
     * DEPRECADO: Mantener solo para testing sin Firebase
     */
    @PostMapping("/login/email")
    @Deprecated
    public ResponseEntity<UsuarioDTO> loginPorEmail(@RequestParam String email) {
        return ResponseEntity.ok(usuarioService.iniciarSesionPorEmail(email));
    }

    /**
     * DEPRECADO: Mantener solo para testing sin Firebase
     */
    @PostMapping("/login/telefono")
    @Deprecated
    public ResponseEntity<UsuarioDTO> loginPorTelefono(@RequestParam String telefono) {
        return ResponseEntity.ok(usuarioService.iniciarSesionPorTelefono(telefono));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDTO> actualizar(@PathVariable Integer id, @RequestBody UsuarioDTO usuarioDTO) {
        return ResponseEntity.ok(usuarioService.actualizarUsuario(id, usuarioDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        usuarioService.eliminarUsuario(id);
        return ResponseEntity.noContent().build();
    }
}
