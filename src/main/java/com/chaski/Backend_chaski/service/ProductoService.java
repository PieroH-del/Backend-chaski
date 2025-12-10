package com.chaski.Backend_chaski.service;

import com.chaski.Backend_chaski.dto.ProductoDTO;
import com.chaski.Backend_chaski.entity.Producto;
import com.chaski.Backend_chaski.entity.Restaurante;
import com.chaski.Backend_chaski.exception.ResourceNotFoundException;
import com.chaski.Backend_chaski.mapper.ProductoMapper;
import com.chaski.Backend_chaski.repository.ProductoRepository;
import com.chaski.Backend_chaski.repository.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private ProductoMapper productoMapper;

    public List<ProductoDTO> obtenerTodos() {
        return productoMapper.toDTOList(productoRepository.findAll());
    }

    public List<ProductoDTO> obtenerPorRestaurante(Integer restauranteId) {
        return productoMapper.toDTOList(productoRepository.findByRestauranteId(restauranteId));
    }

    public List<ProductoDTO> obtenerDisponiblesPorRestaurante(Integer restauranteId) {
        return productoMapper.toDTOList(productoRepository.findByRestauranteIdAndDisponible(restauranteId, true));
    }

    public ProductoDTO obtenerPorId(Integer id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con id: " + id));
        return productoMapper.toDTO(producto);
    }

    public List<ProductoDTO> buscarProductos(String keyword) {
        return productoMapper.toDTOList(productoRepository.buscarPorNombreODescripcion(keyword));
    }

    public ProductoDTO crearProducto(ProductoDTO productoDTO) {
        Restaurante restaurante = restauranteRepository.findById(productoDTO.getRestauranteId())
                .orElseThrow(() -> new ResourceNotFoundException("Restaurante no encontrado"));

        Producto producto = productoMapper.toEntity(productoDTO);
        producto.setRestaurante(restaurante);
        Producto productoGuardado = productoRepository.save(producto);
        return productoMapper.toDTO(productoGuardado);
    }

    public ProductoDTO actualizarProducto(Integer id, ProductoDTO productoDTO) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con id: " + id));

        producto.setNombre(productoDTO.getNombre());
        producto.setDescripcion(productoDTO.getDescripcion());
        producto.setPrecio(productoDTO.getPrecio());
        producto.setImagenUrl(productoDTO.getImagenUrl());

        Producto productoActualizado = productoRepository.save(producto);
        return productoMapper.toDTO(productoActualizado);
    }

    public ProductoDTO cambiarDisponibilidad(Integer id, Boolean disponible) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con id: " + id));

        producto.setDisponible(disponible);
        Producto productoActualizado = productoRepository.save(producto);
        return productoMapper.toDTO(productoActualizado);
    }

    public void eliminarProducto(Integer id) {
        if (!productoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Producto no encontrado con id: " + id);
        }
        productoRepository.deleteById(id);
    }
}

