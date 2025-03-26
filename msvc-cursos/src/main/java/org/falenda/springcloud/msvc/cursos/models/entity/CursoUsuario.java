package org.falenda.springcloud.msvc.cursos.models.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Entity
@Table(name = "cursos_usuarios")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
public class CursoUsuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "usuario_id", unique = true)
    private Long usuarioId;

    @Override
    public boolean equals(Object o) {

        //comparo por instancia (por referencia)
        if (this == o) {
            return true; //Se trata del mismo objeto
        }

        //Verifico que sea del tipo CursoUsuario
        if (!(o instanceof CursoUsuario)) {
            return false; //No es del tipo CursoUsuario
        }

        //Ahora comparo por ID
        CursoUsuario obj = (CursoUsuario) o;
        return this.usuarioId != null && Objects.equals(this.usuarioId, obj.usuarioId);
    }



}
