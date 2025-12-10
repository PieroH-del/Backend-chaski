package com.chaski.Backend_chaski.mapper;

import com.chaski.Backend_chaski.dto.OpcionDTO;
import com.chaski.Backend_chaski.entity.Opcion;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OpcionMapper {

    @Mapping(source = "grupoOpcion.id", target = "grupoOpcionId")
    OpcionDTO toDTO(Opcion opcion);

    @Mapping(source = "grupoOpcionId", target = "grupoOpcion.id")
    @Mapping(target = "opcionesDetallePedido", ignore = true)
    Opcion toEntity(OpcionDTO opcionDTO);

    List<OpcionDTO> toDTOList(List<Opcion> opciones);

    List<Opcion> toEntityList(List<OpcionDTO> opcionDTOs);
}


