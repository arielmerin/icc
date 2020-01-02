Introducción a Ciencias de la Computación
=========================================

Práctica 1: Orientación a Objetos y sintaxis de Java
----------------------------------------------------

### Fecha de entrega: martes 22 de agosto, 2017

Deben implementar los métodos faltantes de la clase Matriz2x2:

**`Matriz2x2.java`:**

```java
package mx.unam.ciencias.icc;

/**
 * <p>Clase para matrices de 2×2.</p>
 *
 * <p>Las matrices de 2×2 pueden sumarse, multiplicarse y sacar su
 * determinante.</p>
 *
 * <p>Las matrices se crean con cuatro dobles a, b, c y d, tales que representan
 * a la matriz:</p>
 *
<pre>
 ⎛ a  b ⎞
 ⎝ c  d ⎠
</pre>
 */
public class Matriz2x2 {

    /* La primera entrada de la matriz. */
    private double a;
    /* La segunda entrada de la matriz. */
    private double b;
    /* La tercera entrada de la matriz. */
    private double c;
    /* La cuarta entrada de la matriz. */
    private double d;

    /**
     * Constructor único. Dado que no proveemos <em>setters</em>, nuestras
     * matrices de 2×2 son <em>inmutables</em>; no podemos cambiar sus valores.
     * @param a la primera entrada de la matriz.
     * @param b la segunda entrada de la matriz.
     * @param c la tercera entrada de la matriz.
     * @param d la cuarta entrada de la matriz.
     */
    public Matriz2x2(double a, double b,
                     double c, double d) {
        this.a = a;
        // Aquí va su código.
    }

    /**
     * Regresa el elemento <tt>a</tt> de la matriz de 2×2.
     * @return El elemento <tt>a</tt> de la matriz de 2×2.
     */
    public double getA() {
        return a;
    }

    /**
     * Regresa el elemento <tt>b</tt> de la matriz de 2×2.
     * @return El elemento <tt>b</tt> de la matriz de 2×2.
     */
    public double getB() {
        // Aquí va su código.
    }

    /**
     * Regresa el elemento <tt>c</tt> de la matriz de 2×2.
     * @return El elemento <tt>c</tt> de la matriz de 2×2.
     */
    public double getC() {
        // Aquí va su código.
    }

    /**
     * Regresa el elemento <tt>d</tt> de la matriz de 2×2.
     * @return El elemento <tt>d</tt> de la matriz de 2×2.
     */
    public double getD() {
        // Aquí va su código.
    }

    /**
     * Suma la matriz de 2×2 con la matriz de 2×2 que recibe como parámetro.
     * @param m La matriz de 2×2 con la que hay que sumar.
     * @return La suma con la matriz de 2×2 <tt>m</tt>.
     */
    public Matriz2x2 suma(Matriz2x2 m) {
        return new Matriz2x2(a + m.a, b + m.b, c + m.c, d + m.d);
    }

    /**
     * Multiplica la matriz de 2×2 con la matriz de 2×2 que recibe como
     * parámetro.
     * @param m La matriz de 2×2 con la que hay que multiplicar.
     * @return La multiplicación con la matriz de 2×2 <tt>m</tt>.
     */
    public Matriz2x2 multiplica(Matriz2x2 m) {
        // Aquí va su código.
    }

    /**
     * Multiplica la matriz de 2×2 con la constante que recibe como parámetro.
     * @param x La constante con la que hay que multiplicar.
     * @return La multiplicación con la constante <tt>x</tt>.
     */
    public Matriz2x2 multiplica(double x) {
        // Aquí va su código.
    }

    /**
     * Calcula el determinante de la matriz de 2×2.
     * @return El determinante de la matriz de 2×2.
     */
    public double determinante() {
        // Aquí va su código.
    }

    /**
     * Regresa una cadena con la representación de la matriz.
     * @return una cadena con la representación de la matriz.
     */
    public String toString() {
        // Aquí va su código.
    }
}
```

Una vez que hayan terminado la clase Matriz2x2, además de que debe de pasar
todas sus pruebas unitarias, debe ejecutar correctamente el programa escrito en
`Practica1.java`:

**`Practica1.java`:**

```java
package mx.unam.ciencias.icc;

import java.util.Random;

/**
 * Práctica 1: Orientación a Objetos y sintaxis de Java.
 */
public class Practica1 {

    public static void main(String[] args) {
        Random r = new Random();

        Matriz2x2 m = new Matriz2x2(r.nextDouble() * 100,
                                    r.nextDouble() * 100,
                                    r.nextDouble() * 100,
                                    r.nextDouble() * 100);
        Matriz2x2 n = new Matriz2x2(r.nextDouble() * 100,
                                    r.nextDouble() * 100,
                                    r.nextDouble() * 100,
                                    r.nextDouble() * 100);
        System.out.println("m:");
        System.out.println(m);
        System.out.println("n:");
        System.out.println(n);

        System.out.println("m + n:");
        System.out.println(m.suma(n));
        System.out.println("m * n:");
        System.out.println(m.multiplica(n));

        double x = r.nextDouble() * 20;
        System.out.printf("m * %2.3f:\n", x);
        System.out.println(m.multiplica(x));
        System.out.printf("det(m): %2.3f\n",
                          m.determinante());
    }
}
```

El único archivo que deben modificar es `Matriz2x2.java`. *No deben modificar de
ninguna manera ninguno de los otros archivos de la práctica*.

### Repositorio

Pueden clonar la práctica con el siguiente comando:

```shell
$ git clone https://canek@aztlan.fciencias.unam.mx/gitlab/2018-1-icc/practica1.git
```

### Documentación

La documentación generada por JavaDoc la pueden consultar aquí:

[Documentación generada por JavaDoc para la práctica 1](https://aztlan.fciencias.unam.mx/~canek/2018-1-icc/practica1/)
