package org.falenda.springcloud.msvc.cursos.repositories;

import org.falenda.springcloud.msvc.cursos.models.entity.Curso;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CursoRepository extends CrudRepository<Curso, Long> {

    @Modifying  //Esta anotación es necesaria para que se pueda usar un delete en una anotación
                // query, ya que sin ella solo es para realizar select. Siempre que hacemos un
                // delet, insert o update se debe complementar con esta anotación.
    @Query("delete from CursoUsuario cu where cu.usuarioId=?1")
    void eliminarCursoUsuarioPorId(Long id);


}
