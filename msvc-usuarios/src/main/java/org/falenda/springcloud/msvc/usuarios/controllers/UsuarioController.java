package org.falenda.springcloud.msvc.usuarios.controllers;

import jakarta.validation.Valid;
import org.falenda.springcloud.msvc.usuarios.models.entity.Usuario;
import org.falenda.springcloud.msvc.usuarios.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/")
    public ResponseEntity<List<Usuario>> listar() {
        return ResponseEntity.ok(usuarioService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> detalle(@PathVariable Long id) {
        Optional<Usuario> usuarioOp = usuarioService.porId(id);
        return usuarioOp.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/usuarios-por-curso")
    public ResponseEntity<List<Usuario>> listaDetalle(@RequestParam List<Long> ids) {
        return ResponseEntity.status(HttpStatus.OK).body(usuarioService.listarPorIds(ids));
    }

    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody Usuario usuario, BindingResult bindingResult) {

        Optional<Usuario> o = usuarioService.porEmail(usuario.getEmail());

        if (bindingResult.hasErrors()) {
            return validar(bindingResult);
        }

        if (!usuario.getEmail().isEmpty() && o.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.singletonMap("mensaje", "Ya existe un usuario/a con este correo electrónico!!"));
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.guardar(usuario));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@PathVariable Long id, @Valid @RequestBody Usuario usuario, BindingResult bindingResult) {
        Optional<Usuario> usuarioOptional = usuarioService.porId(id);

        Optional<Usuario> o = usuarioService.porEmail(usuario.getEmail());

        if (bindingResult.hasErrors()) {
            return validar(bindingResult);
        }

        if (usuarioOptional.isPresent()) {
            if (!usuario.getEmail().isEmpty() && !usuario.getEmail().equalsIgnoreCase(usuarioOptional.get().getEmail()) && o.isPresent()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.singletonMap("mensaje", "Ya existe un usuario con este correo electrónico!!"));
            }
            Usuario newUsuario = usuarioOptional.get();
            newUsuario.setNombre(usuario.getNombre());
            newUsuario.setEmail(usuario.getEmail());
            newUsuario.setPassword(usuario.getPassword());
            return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.guardar(newUsuario));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Usuario> eliminar(@PathVariable Long id) {
        Optional<Usuario> usuarioOptional = usuarioService.porId(id);
        if (usuarioOptional.isPresent()) {
            usuarioService.eliminar(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    //Valida si hay errores con BindingResult y @Valid
    private static ResponseEntity<Map<String, String>> validar(BindingResult bindingResult) {
        Map<String, String> errores = new HashMap<>();
        bindingResult.getFieldErrors().forEach(err -> {
            errores.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
        });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errores);
    }

}
