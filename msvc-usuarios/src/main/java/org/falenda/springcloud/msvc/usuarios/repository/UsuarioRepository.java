package org.falenda.springcloud.msvc.usuarios.repository;

import org.falenda.springcloud.msvc.usuarios.models.entity.Usuario;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends CrudRepository<Usuario, Long> {

    /*A continuacion tres metodos que realializan lo mismo*/

    //Usando palabra clave en el nombre del método (Usando Spring - JPA)
    Optional<Usuario> findByEmail(String email);

    //Consulta personalizada con Query
    @Query("SELECT u FROM Usuario u WHERE u.email=?1 ")
    Optional<Usuario> porEmail(String email);

    //Usando palabra clave en el nombre del método (Usando Spring - JPA) -
    // Si NO necesitamos el objeto usuario, y solo queremos saber si existe o no!
    boolean existsByEmail(String email);

}
