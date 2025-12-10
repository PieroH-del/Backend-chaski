package com.chaski.Backend_chaski.mapper;

import com.chaski.Backend_chaski.dto.RestauranteCategoriaDTO;
import com.chaski.Backend_chaski.entity.RestauranteCategoria;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RestauranteCategoriaMapper {

    @Mapping(source = "restaurante.id", target = "restauranteId")
    @Mapping(source = "categoria.id", target = "categoriaId")
    RestauranteCategoriaDTO toDTO(RestauranteCategoria restauranteCategoria);

    @Mapping(source = "restauranteId", target = "restaurante.id")
    @Mapping(source = "categoriaId", target = "categoria.id")
    RestauranteCategoria toEntity(RestauranteCategoriaDTO restauranteCategoriaDTO);

    List<RestauranteCategoriaDTO> toDTOList(List<RestauranteCategoria> restauranteCategorias);

    List<RestauranteCategoria> toEntityList(List<RestauranteCategoriaDTO> restauranteCategoriaDTOs);
}

