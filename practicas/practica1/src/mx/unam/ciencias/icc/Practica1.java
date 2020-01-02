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
