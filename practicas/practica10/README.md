Introducción a Ciencias de la Computación
=========================================

Práctica 10: Hilos de ejecución y enchufes
------------------------------------------

### Fecha de entrega: martes 2 de enero, 2018

Deben completar los métodos de las clases
[Conexion](https://aztlan.fciencias.unam.mx/gitlab/2018-1-icc/practica10/blob/master/src/mx/unam/ciencias/icc/Conexion.java),
[ServidorBaseDeDatos](https://aztlan.fciencias.unam.mx/gitlab/2018-1-icc/practica10/blob/master/src/mx/unam/ciencias/icc/ServidorBaseDeDatos.java)
y
[ServidorBaseDeDatosEstudiantes](https://aztlan.fciencias.unam.mx/gitlab/2018-1-icc/practica10/blob/master/src/mx/unam/ciencias/icc/ServidorBaseDeDatosEstudiantes.java),
además de todas aquellas que incluyan el comentario `// Aquí va su código`.

Exceptuando por
[ControladorInterfazPrincipal](https://aztlan.fciencias.unam.mx/gitlab/2018-1-icc/practica10/blob/master/src/mx/unam/ciencias/icc/ControladorInterfazPrincipal.java)
y
[su archivo FXML correspondiente](https://aztlan.fciencias.unam.mx/gitlab/2018-1-icc/practica10/blob/master/resources/fxml/interfaz-principal.fxml),
todas las clases de la
[práctica 9](https://aztlan.fciencias.unam.mx/gitlab/2018-1-icc/practica9)
permanecen idénticas y funcionan igual en esta práctica.

Una vez que hayan terminado sus clases, además de que deben de pasar todas sus
pruebas unitarias *compilando sin ninguna advertencia*, se deben ejecutar
correctamente los programas escritos en las clases
[ServidorPractica10](https://aztlan.fciencias.unam.mx/gitlab/2018-1-icc/practica10/blob/master/src/mx/unam/ciencias/icc/ServidorPractica10.java)
y
[ClientePractica10](https://aztlan.fciencias.unam.mx/gitlab/2018-1-icc/practica10/blob/master/src/mx/unam/ciencias/icc/ClientePractica10.java).

El servidor se ejecuta de la siguiente manera:

```shell
$ java -jar servidor-practica10.jar 1234 estudiantes.bd
```

El puerto *siempre* debe especificarse; el nombre de archivo es opcional, si se
omite se usará `base-de-datos.bd`.

El cliente se ejectua como el resto de las demás prácticas:

```shell
$ java -jar cliente-practica10.jar
```

La conexión se puede realizar utilizando la interfaz gráfica.

Los únicos archivos que deben modificar son:

* `AccionRegistro.java`,
* `Arreglos.java`,
* `BaseDeDatos.java`,
* `BaseDeDatosEstudiantes.java`,
* `CampoEstudiante.java`,
* `ClientePractica10.java`,
* `Conexion.java`,
* `ControladorFormaBusquedaEstudiantes`,
* `ControladorFormaConectar.java`,
* `ControladorFormaEstudiante`,
* `ControladorForma`,
* `ControladorInterfazPrincipal`,
* `ControladorTablaEstudiantes`,
* `Estudiante.java`,
* `EventoConexion.java`,
* `Lista.java`,
* `Matriz2x2.java`,
* `ServidorBaseDeDatos.java`,
* `ServidorBaseDeDatosEstudiantes.java` y
* `ServidorPractica10.java`.

*No deben modificar de ninguna manera ninguno de los otros archivos de la
práctica*.

### Repositorio

Pueden clonar la práctica con el siguiente comando:

```shell
$ git clone https://canek@aztlan.fciencias.unam.mx/gitlab/2018-1-icc/practica10.git
```

### Documentación

La documentación generada por JavaDoc la pueden consultar aquí:

[Documentación generada por JavaDoc para la práctica 10](https://aztlan.fciencias.unam.mx/~canek/2018-1-icc/practica10/)
