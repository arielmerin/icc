Introducción a Ciencias de la Computación
=========================================

Práctica 9: Interfaces gráficas
-------------------------------

### Fecha de entrega: martes 19 de diciembre, 2017

Deben hacer observables a las propiedades de su clase
[Estudiante](https://aztlan.fciencias.unam.mx/gitlab/2018-1-icc/practica9/blob/master/src/mx/unam/ciencias/icc/Estudiante.java),
y agregarle escuchas a su clase
[BaseDeDatos](https://aztlan.fciencias.unam.mx/gitlab/2018-1-icc/practica9/blob/master/src/mx/unam/ciencias/icc/BaseDeDatos.java).

Además la interfaz
[Registro](https://aztlan.fciencias.unam.mx/gitlab/2018-1-icc/practica9/blob/master/src/mx/unam/ciencias/icc/Registro.java)
ha sido modificada para agregarle un método que actualice un registro con otro,
así que el método también deben implementarlo en la clase
[Estudiante](https://aztlan.fciencias.unam.mx/gitlab/2018-1-icc/practica9/blob/master/src/mx/unam/ciencias/icc/Estudiante.java#L186).

Además de esto, deben completar las siguientes clases:

* [ControladorForma](https://aztlan.fciencias.unam.mx/gitlab/2018-1-icc/practica9/blob/master/src/mx/unam/ciencias/icc/ControladorForma.java),
* [ControladorFormaEstudiante](https://aztlan.fciencias.unam.mx/gitlab/2018-1-icc/practica9/blob/master/src/mx/unam/ciencias/icc/ControladorFormaEstudiante.java),
* [ControladorFormaBusquedaEstudiantes](https://aztlan.fciencias.unam.mx/gitlab/2018-1-icc/practica9/blob/master/src/mx/unam/ciencias/icc/ControladorFormaBusquedaEstudiantes.java),
* [ControladorTablaEstudiantes](https://aztlan.fciencias.unam.mx/gitlab/2018-1-icc/practica9/blob/master/src/mx/unam/ciencias/icc/ControladorTablaEstudiantes.java) y
* [ControladorInterfazPrincipal](https://aztlan.fciencias.unam.mx/gitlab/2018-1-icc/practica9/blob/master/src/mx/unam/ciencias/icc/ControladorInterfazPrincipal.java).

No hay pruebas unitarias para estas clases, por ser los controladores de la interfaz gráfica.

Una vez que hayan terminado sus clases, además de que deben de pasar todas sus
pruebas unitarias *compilando sin ninguna advertencia*, se debe ejecutar
correctamente el programa escrito en la clase
[Practica9](https://aztlan.fciencias.unam.mx/gitlab/2018-1-icc/practica9/blob/master/src/mx/unam/ciencias/icc/Practica9.java).

Los únicos archivos que deben modificar son:

* `Arreglos.java`,
* `BaseDeDatosEstudiantes.java`,
* `BaseDeDatos.java`,
* `CampoEstudiante.java`,
* `ControladorFormaBusquedaEstudiantes`,
* `ControladorFormaEstudiante`,
* `ControladorForma`,
* `ControladorInterfazPrincipal`,
* `ControladorTablaEstudiantes`,
* `Estudiante.java`,
* `Lista.java` y
* `Matriz2x2.java`.

*No deben modificar de ninguna manera ninguno de los otros archivos de la
práctica*.

### Repositorio

Pueden clonar la práctica con el siguiente comando:

```shell
$ git clone https://canek@aztlan.fciencias.unam.mx/gitlab/2018-1-icc/practica9.git
```

### Documentación

La documentación generada por JavaDoc la pueden consultar aquí:

[Documentación generada por JavaDoc para la práctica 9](https://aztlan.fciencias.unam.mx/~canek/2018-1-icc/practica9/)
