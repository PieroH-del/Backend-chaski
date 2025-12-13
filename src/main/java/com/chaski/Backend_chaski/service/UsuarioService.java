package com.chaski.Backend_chaski.service;

import com.chaski.Backend_chaski.dto.LoginRequest;
import com.chaski.Backend_chaski.dto.UsuarioDTO;
import com.chaski.Backend_chaski.dto.UsuarioRegistroRequest;
import com.chaski.Backend_chaski.entity.Usuario;
import com.chaski.Backend_chaski.exception.BadRequestException;
import com.chaski.Backend_chaski.exception.ResourceNotFoundException;
import com.chaski.Backend_chaski.mapper.UsuarioMapper;
import com.chaski.Backend_chaski.repository.UsuarioRepository;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioMapper usuarioMapper;

    @Autowired
    private FirebaseAuthService firebaseAuthService;

    public List<UsuarioDTO> obtenerTodos() {
        return usuarioMapper.toDTOList(usuarioRepository.findAll());
    }

    public UsuarioDTO obtenerPorId(Integer id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con id: " + id));
        return usuarioMapper.toDTO(usuario);
    }

    /**
     * Registro con Firebase Token
     */
    public UsuarioDTO registrarUsuarioConFirebase(UsuarioRegistroRequest request) {
        try {
            // 1. Verificar token de Firebase
            FirebaseToken decodedToken = firebaseAuthService.verifyIdToken(request.getFirebaseIdToken());
            String firebaseUid = decodedToken.getUid();
            String email = decodedToken.getEmail();

            // 2. Validar que no exista usuario con ese Firebase UID
            if (usuarioRepository.existsByFirebaseUid(firebaseUid)) {
                throw new BadRequestException("Usuario ya registrado");
            }

            // 3. Validar que no exista email (normalizado)
            if (usuarioRepository.existsByEmailIgnoreCase(email)) {
                throw new BadRequestException("El email ya está registrado");
            }

            // 4. Validar teléfono si se proporciona
            if (request.getTelefono() != null &&
                usuarioRepository.existsByTelefono(request.getTelefono())) {
                throw new BadRequestException("El teléfono ya está registrado");
            }

            // 5. Crear usuario
            Usuario usuario = new Usuario();
            usuario.setFirebaseUid(firebaseUid);
            usuario.setEmail(email.toLowerCase());
            usuario.setNombre(request.getNombre());
            usuario.setTelefono(request.getTelefono());
            usuario.setImagenPerfilUrl(request.getImagenPerfilUrl());
            usuario.setActivo(true);

            Usuario usuarioGuardado = usuarioRepository.save(usuario);
            return usuarioMapper.toDTO(usuarioGuardado);

        } catch (FirebaseAuthException e) {
            throw new BadRequestException("Token de Firebase inválido: " + e.getMessage());
        }
    }

    /**
     * Login con Firebase Token
     */
    public UsuarioDTO loginConFirebase(LoginRequest request) {
        try {
            // 1. Verificar token de Firebase
            FirebaseToken decodedToken = firebaseAuthService.verifyIdToken(request.getFirebaseIdToken());
            String firebaseUid = decodedToken.getUid();

            // 2. Buscar usuario por Firebase UID
            Usuario usuario = usuarioRepository.findByFirebaseUid(firebaseUid)
                    .orElseThrow(() -> new ResourceNotFoundException(
                        "Usuario no encontrado. Por favor regístrate primero."
                    ));

            // 3. Verificar que esté activo
            if (!usuario.getActivo()) {
                throw new BadRequestException("Usuario desactivado");
            }

            return usuarioMapper.toDTO(usuario);

        } catch (FirebaseAuthException e) {
            throw new BadRequestException("Token de Firebase inválido: " + e.getMessage());
        }
    }

    /**
     * DEPRECADO: Solo para desarrollo/testing
     */
    @Deprecated
    public UsuarioDTO iniciarSesionPorEmail(String email) {
        Usuario usuario = usuarioRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
        return usuarioMapper.toDTO(usuario);
    }

    /**
     * DEPRECADO: Solo para desarrollo/testing
     */
    @Deprecated
    public UsuarioDTO iniciarSesionPorTelefono(String telefono) {
        Usuario usuario = usuarioRepository.findByTelefono(telefono)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
        return usuarioMapper.toDTO(usuario);
    }

    public UsuarioDTO actualizarUsuario(Integer id, UsuarioDTO usuarioDTO) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con id: " + id));

        usuario.setNombre(usuarioDTO.getNombre());
        usuario.setImagenPerfilUrl(usuarioDTO.getImagenPerfilUrl());

        Usuario usuarioActualizado = usuarioRepository.save(usuario);
        return usuarioMapper.toDTO(usuarioActualizado);
    }

    public void eliminarUsuario(Integer id) {
        if (!usuarioRepository.existsById(id)) {
            throw new ResourceNotFoundException("Usuario no encontrado con id: " + id);
        }
        usuarioRepository.deleteById(id);
    }
}

