package org.falenda.springcloud.msvc.usuarios.clients;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

//@FeignClient(name = "msvc-cursos", url = "localhost:8082") //Para maquina local
@FeignClient(name = "msvc-cursos", url = "host.docker.internal:8082") //Para docker
public interface CursoClienteRest {

    @DeleteMapping("/eliminar-curso-usuario/{id}")
    void eliminarUsuario(@PathVariable Long id) ;
}
