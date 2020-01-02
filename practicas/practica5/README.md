Introducción a Ciencias de la Computación
=========================================

Práctica 5: Excepciones, entrada/salida y enumeraciones
-------------------------------------------------------

### Fecha de entrega: martes 31 de octubre, 2017

Deben agregarle excepciones a de las clases
[Lista](https://aztlan.fciencias.unam.mx/gitlab/2018-1-icc/practica5/blob/master/src/mx/unam/ciencias/icc/Lista.java)
y
[Matriz2x2](https://aztlan.fciencias.unam.mx/gitlab/2018-1-icc/practica5/blob/master/src/mx/unam/ciencias/icc/Matriz2x2.java).

Una vez hayan hecho esto, deben implementar los métodos de la clase
[Estudiante](https://aztlan.fciencias.unam.mx/gitlab/2018-1-icc/practica5/blob/master/src/mx/unam/ciencias/icc/Estudiante.java).
La clase Estudiante implementa la interfaz
[Registro](https://aztlan.fciencias.unam.mx/gitlab/2018-1-icc/practica5/blob/master/src/mx/unam/ciencias/icc/Registro.java),
por lo que en particular deberán implementar los métodos
[guarda()](https://aztlan.fciencias.unam.mx/gitlab/2018-1-icc/practica5/blob/master/src/mx/unam/ciencias/icc/Estudiante.java#L126)
y
[carga()](https://aztlan.fciencias.unam.mx/gitlab/2018-1-icc/practica5/blob/master/src/mx/unam/ciencias/icc/Estudiante.java#L135).

Teniendo lista su clase Estudiante, deberán implementar los métodos concretos de
la clase abstracta
[BaseDeDatos](https://aztlan.fciencias.unam.mx/gitlab/2018-1-icc/practica5/blob/master/src/mx/unam/ciencias/icc/BaseDeDatos.java),
así como la clase concreta
[BaseDeDatosEstudiantes](https://aztlan.fciencias.unam.mx/gitlab/2018-1-icc/practica5/blob/master/src/mx/unam/ciencias/icc/BaseDeDatosEstudiantes.java)
que extiende a BaseDeDatos.

Una vez que hayan terminado sus clases, además de que deben de pasar todas sus
pruebas unitarias, se debe ejecutar correctamente el programa escrito en la clase
[Practica5](https://aztlan.fciencias.unam.mx/gitlab/2018-1-icc/practica5/blob/master/src/mx/unam/ciencias/icc/Practica5.java).

Los únicos archivos que deben modificar son:

* `BaseDeDatosEstudiantes.java`,
* `BaseDeDatos.java`,
* `CampoEstudiante.java`,
* `Estudiante.java`,
* `Lista.java` y
* `Matriz2x2.java`.

*No deben modificar de ninguna manera ninguno de los otros archivos de la
práctica*.

### Repositorio

Pueden clonar la práctica con el siguiente comando:

```shell
$ git clone https://canek@aztlan.fciencias.unam.mx/gitlab/2018-1-icc/practica5.git
```

### Documentación

La documentación generada por JavaDoc la pueden consultar aquí:

[Documentación generada por JavaDoc para la práctica 5](https://aztlan.fciencias.unam.mx/~canek/2018-1-icc/practica5/)
