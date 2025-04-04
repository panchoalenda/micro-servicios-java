package org.falenda.springcloud.msvc.usuarios.services;

import org.falenda.springcloud.msvc.usuarios.models.entity.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioService {

    List<Usuario> listar();

    Optional<Usuario> porId(Long id);

    List<Usuario> listarPorIds(List<Long> ids);

    Usuario guardar(Usuario usuario);

    void eliminar(Long id );

    Optional<Usuario> porEmail(String email);









}
