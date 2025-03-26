package org.falenda.springcloud.msvc.cursos.models;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
public class Usuario {
    private Long id;

    private String nombre;

    private String email;

    private String password;

}
