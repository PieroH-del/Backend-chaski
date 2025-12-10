package com.chaski.Backend_chaski.mapper;

import com.chaski.Backend_chaski.dto.PagoDTO;
import com.chaski.Backend_chaski.entity.Pago;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PagoMapper {

    @Mapping(source = "pedido.id", target = "pedidoId")
    PagoDTO toDTO(Pago pago);

    @Mapping(source = "pedidoId", target = "pedido.id")
    Pago toEntity(PagoDTO pagoDTO);

    List<PagoDTO> toDTOList(List<Pago> pagos);

    List<Pago> toEntityList(List<PagoDTO> pagoDTOs);
}

