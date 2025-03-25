package org.falenda.springcloud.msvc.usuarios.repository;

import org.falenda.springcloud.msvc.usuarios.models.entitys.Usuario;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioRepository extends CrudRepository<Usuario, Long> {
}
