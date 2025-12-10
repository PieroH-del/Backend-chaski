package com.chaski.Backend_chaski.mapper;

import com.chaski.Backend_chaski.dto.RestauranteDTO;
import com.chaski.Backend_chaski.entity.Restaurante;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = {CategoriaMapper.class})
public interface RestauranteMapper {

    @Mapping(target = "categorias", expression = "java(mapCategoriasFromRestauranteCategorias(restaurante))")
    RestauranteDTO toDTO(Restaurante restaurante);

    @Mapping(target = "productos", ignore = true)
    @Mapping(target = "restauranteCategorias", ignore = true)
    @Mapping(target = "pedidos", ignore = true)
    Restaurante toEntity(RestauranteDTO restauranteDTO);

    List<RestauranteDTO> toDTOList(List<Restaurante> restaurantes);

    List<Restaurante> toEntityList(List<RestauranteDTO> restauranteDTOs);

    default List<com.chaski.Backend_chaski.dto.CategoriaDTO> mapCategoriasFromRestauranteCategorias(Restaurante restaurante) {
        if (restaurante.getRestauranteCategorias() == null) {
            return null;
        }
        return restaurante.getRestauranteCategorias().stream()
                .map(rc -> {
                    com.chaski.Backend_chaski.dto.CategoriaDTO dto = new com.chaski.Backend_chaski.dto.CategoriaDTO();
                    dto.setId(rc.getCategoria().getId());
                    dto.setNombre(rc.getCategoria().getNombre());
                    dto.setImagenUrl(rc.getCategoria().getImagenUrl());
                    return dto;
                })
                .collect(Collectors.toList());
    }
}

