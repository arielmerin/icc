# Introducción a Ciencias de la Computación
## Proyecto 1

### Descripción 📌
Usando la práctica 5 del curso como base deben escribir una aplicación de base
de datos equivalente, pero en lugar de Estudiante deben crear algún otro tipo
de registro, que pueden elegir libremente. En este registro al menos un campo
debe ser cadena, al menos uno entero y al menos uno doble. El registro debe
tener por lo menos cinco campos.

El proyecto debe adaptar todo de la práctica 5, así que la aplicación debe
funcionar igual: o bien pidiendo varios registros campo por campo y guardando la
base de datos en un archivo especificado por la línea de comandos; o bien
cargando la base de datos de un un archivo especificado por la línea de
comandos. En ambos casos, el programa debe mostrar los registros en la base de
datos y hacer 2 consultas sobre campos diferentes de los registros.

No pueden importar ninguna clase en su proyecto, a menos que las prácticas
también la importen. No pueden modificar de ninguna manera las clases incluidas
en la práctica 5, incluyendo el nombre de los paquetes.

### Pre-requisitos 📋
Para compilar el proyecto, se requiere usar una versión a partir de Java 8.

### ¿Cómo ejecutar?
Una vez estemos dentro de la carpeta proyecto1, se debe hacer lo siguiente:

* Para compilar

```
      $ ant
```

* Para ejecutar el programa

```
      $ java -jar proyecto1.jar -g archivo.txt # para guardar en archivo.txt, o

      $ java -jar proyecto1.jar -c archivo.txt # para cargar de archivo.txt
```



