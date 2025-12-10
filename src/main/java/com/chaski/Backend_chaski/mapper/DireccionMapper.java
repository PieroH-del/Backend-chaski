package com.chaski.Backend_chaski.mapper;

import com.chaski.Backend_chaski.dto.DireccionDTO;
import com.chaski.Backend_chaski.entity.Direccion;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DireccionMapper {

    @Mapping(source = "usuario.id", target = "usuarioId")
    DireccionDTO toDTO(Direccion direccion);

    @Mapping(source = "usuarioId", target = "usuario.id")
    Direccion toEntity(DireccionDTO direccionDTO);

    List<DireccionDTO> toDTOList(List<Direccion> direcciones);

    List<Direccion> toEntityList(List<DireccionDTO> direccionDTOs);
}

