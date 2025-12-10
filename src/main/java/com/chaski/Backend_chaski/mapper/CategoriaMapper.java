package com.chaski.Backend_chaski.mapper;

import com.chaski.Backend_chaski.dto.CategoriaDTO;
import com.chaski.Backend_chaski.entity.Categoria;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoriaMapper {

    CategoriaDTO toDTO(Categoria categoria);

    @Mapping(target = "restauranteCategorias", ignore = true)
    Categoria toEntity(CategoriaDTO categoriaDTO);

    List<CategoriaDTO> toDTOList(List<Categoria> categorias);

    @Mapping(target = "restauranteCategorias", ignore = true)
    List<Categoria> toEntityList(List<CategoriaDTO> categoriaDTOs);
}

