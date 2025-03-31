package org.falenda.springcloud.msvc.usuarios.models.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Entity
@Table(name = "usuarios")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombres")
//    @NotEmpty(message = "El campo nombre no puede estar vacío")
    @NotBlank
    private String nombre;

    @Column(name = "emails", unique = true)
//    @NotEmpty(message = "El campo email no puede estar vacío")
//    @Email(message = "No tiene formato de email")
    @NotBlank
    @Email
    private String email;

    @Column(name = "passwords")
//    @NotEmpty(message = "El campo password no puede estar vacío")
    @NotBlank
    private String password;

}
