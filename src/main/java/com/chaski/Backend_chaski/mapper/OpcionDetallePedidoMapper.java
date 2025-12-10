package com.chaski.Backend_chaski.mapper;

import com.chaski.Backend_chaski.dto.OpcionDetallePedidoDTO;
import com.chaski.Backend_chaski.entity.OpcionDetallePedido;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OpcionDetallePedidoMapper {

    @Mapping(source = "detallePedido.id", target = "detallePedidoId")
    @Mapping(source = "opcion.id", target = "opcionId")
    @Mapping(source = "opcion.nombre", target = "opcionNombre")
    OpcionDetallePedidoDTO toDTO(OpcionDetallePedido opcionDetallePedido);

    @Mapping(source = "detallePedidoId", target = "detallePedido.id")
    @Mapping(source = "opcionId", target = "opcion.id")
    OpcionDetallePedido toEntity(OpcionDetallePedidoDTO opcionDetallePedidoDTO);

    List<OpcionDetallePedidoDTO> toDTOList(List<OpcionDetallePedido> opcionesDetallePedido);

    List<OpcionDetallePedido> toEntityList(List<OpcionDetallePedidoDTO> opcionDetallePedidoDTOs);
}

