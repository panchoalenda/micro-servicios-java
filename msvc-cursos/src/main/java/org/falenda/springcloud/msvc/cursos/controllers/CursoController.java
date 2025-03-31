package org.falenda.springcloud.msvc.cursos.controllers;

import feign.FeignException;
import jakarta.validation.Valid;
import org.falenda.springcloud.msvc.cursos.models.Usuario;
import org.falenda.springcloud.msvc.cursos.models.entity.Curso;
import org.falenda.springcloud.msvc.cursos.services.CursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/")
public class CursoController {

    @Autowired
    private CursoService cursoService;

    @GetMapping
    public ResponseEntity<List<Curso>> listar() {
        return ResponseEntity.ok(cursoService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Curso> porId(@PathVariable Long id) {
        Optional<Curso> o = cursoService.porIdConUsuario(id); //cursoService.porId(id);
        return o.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Curso> guardar(@Valid @RequestBody Curso curso, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            validar(bindingResult);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(cursoService.guardar(curso));
    }


    @PutMapping("/{id}")
    public ResponseEntity<Curso> actualizar(@Valid @RequestBody Curso curso, BindingResult bindingResult, @PathVariable Long id) {
        Optional<Curso> cursoOp = cursoService.porId(id);

        if (bindingResult.hasErrors()) {
            validar(bindingResult);
        }

        if (cursoOp.isPresent()) {
            Curso newCurso = cursoOp.get();
            newCurso.setNombre(curso.getNombre());
            return ResponseEntity.status(HttpStatus.CREATED).body(cursoService.guardar(newCurso));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Curso> eliminar(@PathVariable Long id) {
        Optional<Curso> cursoBd = cursoService.porId(id);
        if (cursoBd.isPresent()) {
            cursoService.eliminar(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    /***********************REST API EXTERNA**************************/
    @PutMapping("/asignar-usuario/{cursoId}")
    public ResponseEntity<?> asignarUsuario(@RequestBody Usuario usuario, @PathVariable Long cursoId) {
        Optional<Usuario> o;
        try {
            o = cursoService.asignarUsuario(usuario, cursoId);

            if (o.isPresent()) {
                return ResponseEntity.status(HttpStatus.CREATED).body(o.get());
            }
        } catch (FeignException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("Mensaje",
                            "No existe el USUARIO con ese ID o Error en la comunicación: "
                                    + e.getMessage()));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PostMapping("/crear-usuario/{cursoId}")
    public ResponseEntity<?> crearUsuario(@RequestBody Usuario usuario, @PathVariable Long cursoId) {
        Optional<Usuario> o;
        try {
            o = cursoService.crearUsuario(usuario, cursoId);

            if (o.isPresent()) {
                return ResponseEntity.status(HttpStatus.CREATED).body(o.get());
            }
        } catch (FeignException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("Mensaje",
                            "No se pudo crear el USUARIO o Error en la comunicación: "
                                    + e.getMessage()));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @DeleteMapping("/eliminar-usuario/{cursoId}")
    public ResponseEntity<?> eliminarUsuario(@RequestBody Usuario usuario, @PathVariable Long cursoId) {
        Optional<Usuario> o;
        try {
            o = cursoService.eliminarUsuario(usuario, cursoId);

            if (o.isPresent()) {
                return ResponseEntity.status(HttpStatus.OK).body(o.get());
            }
        } catch (FeignException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("Mensaje",
                            "No existe el USUARIO con ese ID o Error en la comunicación: "
                                    + e.getMessage()));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

//    @GetMapping("/usuarios-por-curso")
//    public ResponseEntity<List<Usuario>> listaDetalle(@RequestParam List<Long> ids){
//
//        Optional<Usuario> o;
//        try {
//            o = cursoService.asignarUsuario(usuario, cursoId);
//
//            if (o.isPresent()) {
//                return ResponseEntity.status(HttpStatus.CREATED).body(o.get());
//            }
//        } catch (FeignException e) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                    .body(Collections.singletonMap("Mensaje",
//                            "No existe el USUARIO con ese ID o Error en la comunicación: "
//                                    + e.getMessage()));
//        }
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//    }


    /*************************************************/

    //Valida si hay errores con BindingResult y @Valid
    private static ResponseEntity<Map<String, String>> validar(BindingResult bindingResult) {
        Map<String, String> errores = new HashMap<>();
        bindingResult.getFieldErrors().forEach(err -> {
            errores.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
        });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errores);
    }
}
