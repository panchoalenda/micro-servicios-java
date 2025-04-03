FROM openjdk:22-ea-jdk

#Creamos un directorio de trabajo msvc-usuarios donde se van a copiar los archivos de msvc-usuarios y en
# /app se copiara el pom.xml del padre.
WORKDIR /app/msvc-usuarios

#Copiamos el pom.xml del proyecto padre (curso-kubernetes)
COPY ./pom.xml /app

#Adaptamos los COPY siguientes con el fin de que las dependencias de maven se descarguen solo la primera
#vez al generar la imagen de docker y no cada vez que hacemos un cambio en nuestro codigo y generemos
#nuevamente la imagen de docker. Ahorarmos tiempo.
COPY ./msvc-usuarios/.mvn ./.mvn
COPY ./msvc-usuarios/mvnw .
COPY ./msvc-usuarios/pom.xml .

#CON LAS SIGUIENTES INSTRUCCIONES:
########## -Dmaven.test.skip => Omite la ejecución de pruebas unitarias
########## -Dmaven.main.skip => Evita la compilación del código fuente. Se usa cuando solo necesitas
#empaquetar dependencias sin compilar la aplicación.
########## -Dspring-boot.repackage.skip => Evita la repaquetización del .jar de Spring Boot (que
#normalmente agrega dependencias y un MANIFEST.MF). Útil si ya tienes un .jar empaquetado correctamente.
########## && rm -r ./target/ => Elimina la carpeta target/ después del proceso.Evita que archivos
#innecesarios queden en la imagen de Docker, reduciendo su tamaño.
RUN ./mvnw clean package -Dmaven.test.skip -Dmaven.main.skip -Dspring-boot.repackage.skip  && rm -r ./target/
#Otra opción, pero menos recomendada que la anterior es la siguiente (usar solo si no funciona la aneterior):
#RUN ./mvnw  dependency:go-offline

#Ahora copiamos todo el codigo fuente en la carpeta src
COPY ./msvc-usuarios/src ./src

#Ya copiamos todo, ahora compilamos (empaquetamos y generamos el JAR)
RUN ./mvnw clean package -DskipTests

EXPOSE 8081

#Ahora debemos hacer referencia a que el JAR se va a encontrar en la carpeta TARGET, dentro
#del WORKDIR /app/msvc-usuarios
ENTRYPOINT ["java", "-jar", "./target/msvc-usuarios-0.0.1-SNAPSHOT.jar"]