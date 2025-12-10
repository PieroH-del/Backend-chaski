package com.chaski.Backend_chaski.mapper;

import com.chaski.Backend_chaski.dto.UsuarioDTO;
import com.chaski.Backend_chaski.entity.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {DireccionMapper.class})
public interface UsuarioMapper {

    UsuarioDTO toDTO(Usuario usuario);

    @Mapping(target = "pedidos", ignore = true)
    Usuario toEntity(UsuarioDTO usuarioDTO);

    List<UsuarioDTO> toDTOList(List<Usuario> usuarios);

    List<Usuario> toEntityList(List<UsuarioDTO> usuarioDTOs);
}

