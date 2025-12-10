package com.chaski.Backend_chaski.service;

import com.chaski.Backend_chaski.dto.CategoriaDTO;
import com.chaski.Backend_chaski.entity.Categoria;
import com.chaski.Backend_chaski.exception.ResourceNotFoundException;
import com.chaski.Backend_chaski.mapper.CategoriaMapper;
import com.chaski.Backend_chaski.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private CategoriaMapper categoriaMapper;

    public List<CategoriaDTO> obtenerTodas() {
        return categoriaMapper.toDTOList(categoriaRepository.findAll());
    }

    public CategoriaDTO obtenerPorId(Integer id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada con id: " + id));
        return categoriaMapper.toDTO(categoria);
    }

    public CategoriaDTO crearCategoria(CategoriaDTO categoriaDTO) {
        Categoria categoria = categoriaMapper.toEntity(categoriaDTO);
        Categoria categoriaGuardada = categoriaRepository.save(categoria);
        return categoriaMapper.toDTO(categoriaGuardada);
    }

    public CategoriaDTO actualizarCategoria(Integer id, CategoriaDTO categoriaDTO) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada con id: " + id));

        categoria.setNombre(categoriaDTO.getNombre());
        categoria.setImagenUrl(categoriaDTO.getImagenUrl());

        Categoria categoriaActualizada = categoriaRepository.save(categoria);
        return categoriaMapper.toDTO(categoriaActualizada);
    }

    public void eliminarCategoria(Integer id) {
        if (!categoriaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Categoría no encontrada con id: " + id);
        }
        categoriaRepository.deleteById(id);
    }
}

