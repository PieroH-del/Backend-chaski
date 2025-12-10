package com.chaski.Backend_chaski.mapper;

import com.chaski.Backend_chaski.dto.ProductoDTO;
import com.chaski.Backend_chaski.entity.Producto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {GrupoOpcionMapper.class})
public interface ProductoMapper {

    @Mapping(source = "restaurante.id", target = "restauranteId")
    @Mapping(source = "restaurante.nombre", target = "restauranteNombre")
    ProductoDTO toDTO(Producto producto);

    @Mapping(source = "restauranteId", target = "restaurante.id")
    @Mapping(target = "detallesPedido", ignore = true)
    Producto toEntity(ProductoDTO productoDTO);

    List<ProductoDTO> toDTOList(List<Producto> productos);

    List<Producto> toEntityList(List<ProductoDTO> productoDTOs);
}

