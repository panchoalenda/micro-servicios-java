package org.falenda.springcloud.msvc.cursos.services;

import com.sun.tools.jconsole.JConsoleContext;
import org.falenda.springcloud.msvc.cursos.clients.UsuarioClientRest;
import org.falenda.springcloud.msvc.cursos.models.Usuario;
import org.falenda.springcloud.msvc.cursos.models.entity.Curso;
import org.falenda.springcloud.msvc.cursos.models.entity.CursoUsuario;
import org.falenda.springcloud.msvc.cursos.repositories.CursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;

@Service
public class CursoServiceImp implements CursoService {

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private UsuarioClientRest clientRest;

    @Override
    @Transactional(readOnly = true)
    public List<Curso> listar() {
        return (List<Curso>) cursoRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Curso> porId(Long id) {
        return  cursoRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Curso> porIdConUsuario(Long id) {
        Optional<Curso> cursoOptional = cursoRepository.findById(id);

        if (cursoOptional.isPresent()) {
            Curso curso = cursoOptional.get();
            if (!curso.getCursosUsuarios().isEmpty()) {

                /*FORMA 1 - USANDO FOREACH*/
//                List<Long> usuariosId = new ArrayList<>(); //Usando
//            curso.getCursosUsuarios().forEach(usuario -> {
//                usuariosId.add(usuario.getUsuarioId());
//            });

                /*FORMA 2 - USANDO MAP*/
//              List<Long> usuariosId = curso.getCursosUsuarios().stream().map(uc-> uc.getUsuarioId()).collect(Collectors.toList());
                List<Long> usuariosId = curso.getCursosUsuarios().stream().map(CursoUsuario::getUsuarioId).toList(); //Forma simplificada de lo anterior
                List<Usuario> usuarios = clientRest.listaDetalle(usuariosId);
                curso.setUsuarios(usuarios);
                return Optional.of(curso);
            }
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public Curso guardar(Curso curso) {
        return cursoRepository.save(curso);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        cursoRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void eliminarCursoUsuarioPorId(Long id) {
        cursoRepository.eliminarCursoUsuarioPorId(id);
    }

    @Override
    @Transactional
    public Optional<Usuario> asignarUsuario(Usuario usuario, Long cursoId) {
        Optional<Curso> o = cursoRepository.findById(cursoId);
        if (o.isPresent()) {
            Usuario usuarioMsvc = clientRest.detalle(usuario.getId());

            Curso curso = o.get();

            CursoUsuario cursoUsuario = new CursoUsuario();
            cursoUsuario.setUsuarioId(usuarioMsvc.getId());

            curso.addCursoUsuario(cursoUsuario);
            cursoRepository.save(curso);

            return Optional.of(usuarioMsvc);
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public Optional<Usuario> crearUsuario(Usuario usuario, Long cursoId) {
        Optional<Curso> o = cursoRepository.findById(cursoId);
        if (o.isPresent()) {
            Usuario usuarioNuevoMsvc = clientRest.crear(usuario);

            Curso curso = o.get();

            CursoUsuario cursoUsuario = new CursoUsuario();
            cursoUsuario.setUsuarioId(usuarioNuevoMsvc.getId());

            curso.addCursoUsuario(cursoUsuario);
            cursoRepository.save(curso);

            return Optional.of(usuarioNuevoMsvc);
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public Optional<Usuario> eliminarUsuario(Usuario usuario, Long cursoId) {
        Optional<Curso> o = cursoRepository.findById(cursoId);

        if (o.isPresent()) {
            Usuario usuarioMsvc = clientRest.detalle(usuario.getId());

            Curso curso = o.get();

            CursoUsuario cursoUsuario = new CursoUsuario();
            cursoUsuario.setUsuarioId(usuarioMsvc.getId());

            curso.removeCursoUsuario(cursoUsuario);
            cursoRepository.save(curso);

            return Optional.of(usuarioMsvc);
        }
        return Optional.empty();
    }
}
