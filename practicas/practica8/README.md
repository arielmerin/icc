Introducción a Ciencias de la Computación
=========================================

Práctica 8: Ordenamientos, búsquedas y lambdas
----------------------------------------------

### Fecha de entrega: martes 28 de noviembre, 2017

Deben agregarle a su clase
[Lista](https://aztlan.fciencias.unam.mx/gitlab/2018-1-icc/practica8/blob/master/src/mx/unam/ciencias/icc/Lista.java)
los métodos
[mergeSort()](https://aztlan.fciencias.unam.mx/gitlab/2018-1-icc/practica8/blob/master/src/mx/unam/ciencias/icc/Lista.java#L291)
y
[busquedaLineal()](https://aztlan.fciencias.unam.mx/gitlab/2018-1-icc/practica8/blob/master/src/mx/unam/ciencias/icc/Lista.java#L316),
que implementan los algoritmos MergeSort y BusquedaLineal respectivamente.

Además de versiones estáticas y genéricas acotadas a la interfaz
[Comparable](https://docs.oracle.com/javase/8/docs/api/java/lang/Comparable.html?is-external=true),
habrá versiones no estáticas y genéricas no acotadas tanto para
[mergeSort()](https://aztlan.fciencias.unam.mx/gitlab/2018-1-icc/practica8/blob/master/src/mx/unam/ciencias/icc/Lista.java#L303)
como para
[busquedaLineal()](https://aztlan.fciencias.unam.mx/gitlab/2018-1-icc/practica8/blob/master/src/mx/unam/ciencias/icc/Lista.java#L330).
Estas versiones funcionan utilizando una lambda para hacer las comparaciones,
que se define con la interfaz funcional
[Comparator](https://docs.oracle.com/javase/8/docs/api/java/util/Comparator.html?is-external=true) de Java.

Además deben implementar los métodos faltantes de la clase
[Arreglos](https://aztlan.fciencias.unam.mx/gitlab/2018-1-icc/practica8/blob/master/src/mx/unam/ciencias/icc/Arreglos.java).
Igual que con listas, habrá métodos para ordenar y realizar búsquedas, pero para
arreglos implementarán dos algoritmos de ordenamiento; SelectionSort en el método
[selectionSort()](https://aztlan.fciencias.unam.mx/gitlab/2018-1-icc/practica8/blob/master/src/mx/unam/ciencias/icc/Arreglos.java#L15)
y QuickSort en el método
[quickSort()](https://aztlan.fciencias.unam.mx/gitlab/2018-1-icc/practica8/blob/master/src/mx/unam/ciencias/icc/Arreglos.java#L36).
El algoritmo BusquedaBinaria estará implementado en el método
[busquedaBinaria()](https://aztlan.fciencias.unam.mx/gitlab/2018-1-icc/practica8/blob/master/src/mx/unam/ciencias/icc/Arreglos.java#L60).

De la misma manera que en la clase Lista, los tres algoritmos en la clase
Arreglos tendrán implementaciones estáticas y genéricas acotadas a Comparable, y
versiones estáticas y genéricas no acotadas que utilicen una lambda definida por
Comparator. Como no podemos tener instancias de la clase Arreglos (su único
constructor es privado), todos sus métodos son estáticos.

Una vez que hayan terminado sus clases, además de que deben de pasar todas sus
pruebas unitarias *compilando sin ninguna advertencia*, se debe ejecutar
correctamente el programa escrito en la clase
[Practica8](https://aztlan.fciencias.unam.mx/gitlab/2018-1-icc/practica8/blob/master/src/mx/unam/ciencias/icc/Practica8.java).

Los únicos archivos que deben modificar son:

* `Arreglos.java`,
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
$ git clone https://canek@aztlan.fciencias.unam.mx/gitlab/2018-1-icc/practica8.git
```

### Documentación

La documentación generada por JavaDoc la pueden consultar aquí:

[Documentación generada por JavaDoc para la práctica 8](https://aztlan.fciencias.unam.mx/~canek/2018-1-icc/practica8/)
