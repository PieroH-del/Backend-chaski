package com.chaski.Backend_chaski.mapper;

import com.chaski.Backend_chaski.dto.DetallePedidoDTO;
import com.chaski.Backend_chaski.entity.DetallePedido;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {OpcionDetallePedidoMapper.class})
public interface DetallePedidoMapper {

    @Mapping(source = "pedido.id", target = "pedidoId")
    @Mapping(source = "producto.id", target = "productoId")
    @Mapping(source = "producto.nombre", target = "productoNombre")
    DetallePedidoDTO toDTO(DetallePedido detallePedido);

    @Mapping(source = "pedidoId", target = "pedido.id")
    @Mapping(source = "productoId", target = "producto.id")
    DetallePedido toEntity(DetallePedidoDTO detallePedidoDTO);

    List<DetallePedidoDTO> toDTOList(List<DetallePedido> detallesPedido);

    List<DetallePedido> toEntityList(List<DetallePedidoDTO> detallePedidoDTOs);
}

