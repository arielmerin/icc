# Introducción a Ciencias de la Computación
## Proyecto 2

### Descripción 📌
Usando la práctica 10 del curso como base deben escribir una aplicación de base
de datos equivalente, pero en lugar de Estudiante deben crear algún otro tipo
de registro, que pueden elegir libremente. En este registro al menos un campo
debe ser cadena, al menos uno entero y al menos uno doble. El registro debe
tener por lo menos cinco campos.

El proyecto debe adaptar todo de la práctica 10, así que la aplicación debe
funcionar igual: un servidor para la base de datos, y un cliente con interfaz
gráfica de usuario. La funcionalidad debe ser la misma entre la práctica 10 y el
proyecto 2.

No pueden importar ninguna clase en su proyecto, a menos que las prácticas
también la importen. No pueden modificar de ninguna manera las clases incluidas
en la práctica 10, incluyendo el nombre de los paquetes

### Pre-requisitos 📋
Para compilar el proyecto, se requiere una versión de Java a partir de la 8, y 
que 
### ¿Cómo ejecutar?
La ejecución consta de dos partes: el servidor y el cliente.

Primero, se debe ejecutar el servidor de la siguiente manera:

```
$ java -jar servidor-proyecto2.jar 1234 canciones.bd
```

El puerto siempre debe especificarse; el nombre del archivo es opcional, si se 
omite se usará base-de-datos.bd.

Posteriormente, el cliente debe ejecutarse de la siguiente manera:

```
$ java -jar cliente-proyecto2.jar
```

La conexión se puede realizar usando la interfaz gráfica.