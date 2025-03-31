package org.falenda.springcloud.msvc.cursos.clients;

import org.falenda.springcloud.msvc.cursos.models.Usuario;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//Cliente para consumir los servicios del Usuario
//El parametro URl cdo integramos springCloudKubernetes, ya no se coloca.
@FeignClient(name = "msvc-usuarios", url = "localhost:8081")
public interface UsuarioClientRest {

    @GetMapping("/{id}")
    Usuario detalle(@PathVariable Long id);

    @PostMapping("/")
    Usuario crear(@RequestBody Usuario usuario);

    @GetMapping("/usuarios-por-curso")
    List<Usuario> listaDetalle(@RequestParam List<Long> ids);
}
