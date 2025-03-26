package org.falenda.springcloud.msvc.cursos.models.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.falenda.springcloud.msvc.cursos.models.Usuario;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cursos")
@AllArgsConstructor
@Getter
@Setter
public class Curso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombres")
    @NotEmpty
    private String nombre;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "curso_id")
    private List<CursoUsuario> cursosUsuarios;

    @Transient //Con esta anotacion le digo que no se va a persistir, solo lo vamos a usar para poblar los datos de los usuarios
    private List<Usuario> usuarios;


    public Curso() {
        //Inicio las listas
        this.cursosUsuarios = new ArrayList<>();
        this.usuarios = new ArrayList<>();
    }

    //Para agregar un curso a la lista
    public void addCursoUsuario(CursoUsuario cursoUsuario) {
        cursosUsuarios.add(cursoUsuario);
    }

    //Para remover un curso de la lista
    public void removeCursoUsuario(CursoUsuario cursoUsuario) {
        cursosUsuarios.remove(cursoUsuario);
    }
}
