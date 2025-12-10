package com.chaski.Backend_chaski.service;

import com.chaski.Backend_chaski.dto.UsuarioDTO;
import com.chaski.Backend_chaski.entity.Usuario;
import com.chaski.Backend_chaski.exception.BadRequestException;
import com.chaski.Backend_chaski.exception.ResourceNotFoundException;
import com.chaski.Backend_chaski.mapper.UsuarioMapper;
import com.chaski.Backend_chaski.repository.UsuarioRepository;
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

    public List<UsuarioDTO> obtenerTodos() {
        return usuarioMapper.toDTOList(usuarioRepository.findAll());
    }

    public UsuarioDTO obtenerPorId(Integer id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con id: " + id));
        return usuarioMapper.toDTO(usuario);
    }

    public UsuarioDTO registrarUsuario(UsuarioDTO usuarioDTO) {
        // Validar que el email no exista
        if (usuarioRepository.existsByEmail(usuarioDTO.getEmail())) {
            throw new BadRequestException("El email ya está registrado");
        }

        // Validar que el teléfono no exista si se proporciona
        if (usuarioDTO.getTelefono() != null && usuarioRepository.existsByTelefono(usuarioDTO.getTelefono())) {
            throw new BadRequestException("El teléfono ya está registrado");
        }

        Usuario usuario = usuarioMapper.toEntity(usuarioDTO);
        Usuario usuarioGuardado = usuarioRepository.save(usuario);
        return usuarioMapper.toDTO(usuarioGuardado);
    }

    public UsuarioDTO iniciarSesionPorEmail(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con email: " + email));
        return usuarioMapper.toDTO(usuario);
    }

    public UsuarioDTO iniciarSesionPorTelefono(String telefono) {
        Usuario usuario = usuarioRepository.findByTelefono(telefono)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con teléfono: " + telefono));
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

