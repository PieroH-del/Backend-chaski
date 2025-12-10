package com.chaski.Backend_chaski.service;

import com.chaski.Backend_chaski.dto.DireccionDTO;
import com.chaski.Backend_chaski.entity.Direccion;
import com.chaski.Backend_chaski.entity.Usuario;
import com.chaski.Backend_chaski.exception.ResourceNotFoundException;
import com.chaski.Backend_chaski.mapper.DireccionMapper;
import com.chaski.Backend_chaski.repository.DireccionRepository;
import com.chaski.Backend_chaski.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DireccionService {

    @Autowired
    private DireccionRepository direccionRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private DireccionMapper direccionMapper;

    public List<DireccionDTO> obtenerDireccionesPorUsuario(Integer usuarioId) {
        return direccionMapper.toDTOList(direccionRepository.findByUsuarioId(usuarioId));
    }

    public DireccionDTO obtenerPorId(Integer id) {
        Direccion direccion = direccionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Dirección no encontrada con id: " + id));
        return direccionMapper.toDTO(direccion);
    }

    public DireccionDTO crearDireccion(DireccionDTO direccionDTO) {
        Usuario usuario = usuarioRepository.findById(direccionDTO.getUsuarioId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        // Si es predeterminada, quitar predeterminado a las demás
        if (Boolean.TRUE.equals(direccionDTO.getEsPredeterminada())) {
            List<Direccion> direccionesPredeterminadas = direccionRepository
                    .findByUsuarioIdAndEsPredeterminada(usuario.getId(), true);
            direccionesPredeterminadas.forEach(d -> d.setEsPredeterminada(false));
            direccionRepository.saveAll(direccionesPredeterminadas);
        }

        Direccion direccion = direccionMapper.toEntity(direccionDTO);
        direccion.setUsuario(usuario);
        Direccion direccionGuardada = direccionRepository.save(direccion);
        return direccionMapper.toDTO(direccionGuardada);
    }

    public DireccionDTO actualizarDireccion(Integer id, DireccionDTO direccionDTO) {
        Direccion direccion = direccionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Dirección no encontrada con id: " + id));

        // Si se marca como predeterminada, quitar predeterminado a las demás
        if (Boolean.TRUE.equals(direccionDTO.getEsPredeterminada()) && !Boolean.TRUE.equals(direccion.getEsPredeterminada())) {
            List<Direccion> direccionesPredeterminadas = direccionRepository
                    .findByUsuarioIdAndEsPredeterminada(direccion.getUsuario().getId(), true);
            direccionesPredeterminadas.forEach(d -> d.setEsPredeterminada(false));
            direccionRepository.saveAll(direccionesPredeterminadas);
        }

        direccion.setEtiqueta(direccionDTO.getEtiqueta());
        direccion.setDireccionCompleta(direccionDTO.getDireccionCompleta());
        direccion.setReferencia(direccionDTO.getReferencia());
        direccion.setEsPredeterminada(direccionDTO.getEsPredeterminada());

        Direccion direccionActualizada = direccionRepository.save(direccion);
        return direccionMapper.toDTO(direccionActualizada);
    }

    public void eliminarDireccion(Integer id) {
        if (!direccionRepository.existsById(id)) {
            throw new ResourceNotFoundException("Dirección no encontrada con id: " + id);
        }
        direccionRepository.deleteById(id);
    }
}

