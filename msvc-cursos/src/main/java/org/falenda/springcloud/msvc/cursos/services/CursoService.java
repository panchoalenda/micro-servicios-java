package org.falenda.springcloud.msvc.cursos.services;

import org.falenda.springcloud.msvc.cursos.models.Usuario;
import org.falenda.springcloud.msvc.cursos.models.entity.Curso;

import java.util.List;
import java.util.Optional;

public interface CursoService {
    List<Curso> listar();

    Optional<Curso> porId(Long id);

    Optional<Curso> porIdConUsuario(Long id);

    Curso guardar(Curso curso);

    void eliminar(Long id);

    //Métodos para obtener de afuera (remotos - API Rest)
    Optional<Usuario> asignarUsuario(Usuario usuario, Long cursoId);
    Optional<Usuario> crearUsuario(Usuario usuario, Long cursoId);
    Optional<Usuario> eliminarUsuario(Usuario usuario, Long cursoId);


}
