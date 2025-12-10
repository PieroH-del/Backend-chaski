package com.chaski.Backend_chaski.service;

import com.chaski.Backend_chaski.dto.RestauranteDTO;
import com.chaski.Backend_chaski.entity.Restaurante;
import com.chaski.Backend_chaski.exception.ResourceNotFoundException;
import com.chaski.Backend_chaski.mapper.RestauranteMapper;
import com.chaski.Backend_chaski.repository.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
public class RestauranteService {

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private RestauranteMapper restauranteMapper;

    public List<RestauranteDTO> obtenerTodos() {
        return restauranteMapper.toDTOList(restauranteRepository.findAll());
    }

    public List<RestauranteDTO> obtenerRestaurantesAbiertos() {
        return restauranteMapper.toDTOList(restauranteRepository.findByEstaAbierto(true));
    }

    public RestauranteDTO obtenerPorId(Integer id) {
        Restaurante restaurante = restauranteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurante no encontrado con id: " + id));
        return restauranteMapper.toDTO(restaurante);
    }

    public List<RestauranteDTO> filtrarPorCategoria(Integer categoriaId) {
        return restauranteMapper.toDTOList(restauranteRepository.findByCategoriaId(categoriaId));
    }

    public List<RestauranteDTO> buscarPorNombre(String keyword) {
        return restauranteMapper.toDTOList(restauranteRepository.buscarPorNombre(keyword));
    }

    public List<RestauranteDTO> filtrarPorCalificacion(BigDecimal minCalificacion) {
        return restauranteMapper.toDTOList(
                restauranteRepository.findByCalificacionPromedioGreaterThanEqual(minCalificacion));
    }

    public RestauranteDTO crearRestaurante(RestauranteDTO restauranteDTO) {
        Restaurante restaurante = restauranteMapper.toEntity(restauranteDTO);
        Restaurante restauranteGuardado = restauranteRepository.save(restaurante);
        return restauranteMapper.toDTO(restauranteGuardado);
    }

    public RestauranteDTO actualizarRestaurante(Integer id, RestauranteDTO restauranteDTO) {
        Restaurante restaurante = restauranteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurante no encontrado con id: " + id));

        restaurante.setNombre(restauranteDTO.getNombre());
        restaurante.setDescripcion(restauranteDTO.getDescripcion());
        restaurante.setImagenLogoUrl(restauranteDTO.getImagenLogoUrl());
        restaurante.setImagenPortadaUrl(restauranteDTO.getImagenPortadaUrl());
        restaurante.setDireccion(restauranteDTO.getDireccion());
        restaurante.setCostoEnvioBase(restauranteDTO.getCostoEnvioBase());
        restaurante.setTiempoEsperaMinutos(restauranteDTO.getTiempoEsperaMinutos());

        Restaurante restauranteActualizado = restauranteRepository.save(restaurante);
        return restauranteMapper.toDTO(restauranteActualizado);
    }

    public RestauranteDTO cambiarEstadoApertura(Integer id, Boolean estaAbierto) {
        Restaurante restaurante = restauranteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurante no encontrado con id: " + id));

        restaurante.setEstaAbierto(estaAbierto);
        Restaurante restauranteActualizado = restauranteRepository.save(restaurante);
        return restauranteMapper.toDTO(restauranteActualizado);
    }

    public void eliminarRestaurante(Integer id) {
        if (!restauranteRepository.existsById(id)) {
            throw new ResourceNotFoundException("Restaurante no encontrado con id: " + id);
        }
        restauranteRepository.deleteById(id);
    }
}

