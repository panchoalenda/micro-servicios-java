package org.falenda.springcloud.msvc.cursos.repositories;

import org.falenda.springcloud.msvc.cursos.models.entity.Curso;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CursoRepository extends CrudRepository<Curso, Long> {

}
