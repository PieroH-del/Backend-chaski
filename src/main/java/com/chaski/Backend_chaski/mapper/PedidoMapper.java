package com.chaski.Backend_chaski.mapper;

import com.chaski.Backend_chaski.dto.PedidoDTO;
import com.chaski.Backend_chaski.entity.Pedido;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {DetallePedidoMapper.class, PagoMapper.class})
public interface PedidoMapper {

    @Mapping(source = "usuario.id", target = "usuarioId")
    @Mapping(source = "usuario.nombre", target = "usuarioNombre")
    @Mapping(source = "restaurante.id", target = "restauranteId")
    @Mapping(source = "restaurante.nombre", target = "restauranteNombre")
    @Mapping(source = "direccionEntrega.id", target = "direccionEntregaId")
    @Mapping(source = "direccionEntrega.direccionCompleta", target = "direccionEntregaCompleta")
    PedidoDTO toDTO(Pedido pedido);

    @Mapping(source = "usuarioId", target = "usuario.id")
    @Mapping(source = "restauranteId", target = "restaurante.id")
    @Mapping(source = "direccionEntregaId", target = "direccionEntrega.id")
    Pedido toEntity(PedidoDTO pedidoDTO);

    List<PedidoDTO> toDTOList(List<Pedido> pedidos);

    List<Pedido> toEntityList(List<PedidoDTO> pedidoDTOs);
}

