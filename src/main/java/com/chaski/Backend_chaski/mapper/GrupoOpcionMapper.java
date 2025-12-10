package com.chaski.Backend_chaski.mapper;

import com.chaski.Backend_chaski.dto.GrupoOpcionDTO;
import com.chaski.Backend_chaski.entity.GrupoOpcion;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {OpcionMapper.class})
public interface GrupoOpcionMapper {

    @Mapping(source = "producto.id", target = "productoId")
    GrupoOpcionDTO toDTO(GrupoOpcion grupoOpcion);

    @Mapping(source = "productoId", target = "producto.id")
    GrupoOpcion toEntity(GrupoOpcionDTO grupoOpcionDTO);

    List<GrupoOpcionDTO> toDTOList(List<GrupoOpcion> gruposOpciones);

    List<GrupoOpcion> toEntityList(List<GrupoOpcionDTO> grupoOpcionDTOs);
}

