package org.falenda.springcloud.msvc.cursos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients //Con esto habilitamos en nuestra apicacion en el contexto de Feign
// y poder implementar nuestras APIs Rest de forma declarativa
@SpringBootApplication
public class MsvcCursoApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsvcCursoApplication.class, args);
	}

}
