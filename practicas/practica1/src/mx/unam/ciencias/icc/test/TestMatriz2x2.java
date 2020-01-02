package mx.unam.ciencias.icc.test;

import java.util.Random;
import mx.unam.ciencias.icc.Matriz2x2;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

/**
 * Clase para pruebas unitarias de la clase {@link Matriz2x2}.
 */
public class TestMatriz2x2 {

    /** Expiración para que ninguna prueba tarde más de 5 segundos. */
    @Rule public Timeout expiracion = Timeout.seconds(5);

    /* Generador de números aleatorios. */
    private Random random;

    /**
     * Crea un generador de números aleatorios para cada prueba.
     */
    public TestMatriz2x2() {
        random = new Random();
    }

    /**
     * Prueba unitaria para {@link Matriz2x2#Matriz2x2}.
     */
    @Test public void testMatriz2x2() {
        double a = random.nextDouble() * 1000;
        double b = random.nextDouble() * 1000;
        double c = random.nextDouble() * 1000;
        double d = random.nextDouble() * 1000;

        Matriz2x2 m = new Matriz2x2(a, b, c, d);
        Assert.assertTrue(m.getA() == a);
        Assert.assertTrue(m.getB() == b);
        Assert.assertTrue(m.getC() == c);
        Assert.assertTrue(m.getD() == d);
    }

    /**
     * Prueba unitaria para {@link Matriz2x2#getA}.
     */
    @Test public void testGetA() {
        double a = random.nextDouble() * 1000;
        double b = random.nextDouble() * 1000;
        double c = random.nextDouble() * 1000;
        double d = random.nextDouble() * 1000;

        Matriz2x2 m = new Matriz2x2(a, b, c, d);

        Assert.assertTrue(m.getA() == a);
    }

    /**
     * Prueba unitaria para {@link Matriz2x2#getB}.
     */
    @Test public void testGetB() {
        double a = random.nextDouble() * 1000;
        double b = random.nextDouble() * 1000;
        double c = random.nextDouble() * 1000;
        double d = random.nextDouble() * 1000;

        Matriz2x2 m = new Matriz2x2(a, b, c, d);

        Assert.assertTrue(m.getB() == b);
    }

    /**
     * Prueba unitaria para {@link Matriz2x2#getC}.
     */
    @Test public void testGetC() {
        double a = random.nextDouble() * 1000;
        double b = random.nextDouble() * 1000;
        double c = random.nextDouble() * 1000;
        double d = random.nextDouble() * 1000;

        Matriz2x2 m = new Matriz2x2(a, b, c, d);

        Assert.assertTrue(m.getC() == c);
    }

    /**
     * Prueba unitaria para {@link Matriz2x2#getD}.
     */
    @Test public void testGetD() {
        double a = random.nextDouble() * 1000;
        double b = random.nextDouble() * 1000;
        double c = random.nextDouble() * 1000;
        double d = random.nextDouble() * 1000;

        Matriz2x2 m = new Matriz2x2(a, b, c, d);

        Assert.assertTrue(m.getD() == d);
    }

    /**
     * Prueba unitaria para {@link Matriz2x2#suma}.
     */
    @Test public void testSuma() {
        double a1 = random.nextDouble() * 1000;
        double b1 = random.nextDouble() * 1000;
        double c1 = random.nextDouble() * 1000;
        double d1 = random.nextDouble() * 1000;

        double a2 = random.nextDouble() * 1000;
        double b2 = random.nextDouble() * 1000;
        double c2 = random.nextDouble() * 1000;
        double d2 = random.nextDouble() * 1000;

        Matriz2x2 m1 = new Matriz2x2(a1, b1, c1, d1);
        Matriz2x2 m2 = new Matriz2x2(a2, b2, c2, d2);
        Matriz2x2 s = m1.suma(m2);

        Assert.assertTrue(s.getA() == a1 + a2);
        Assert.assertTrue(s.getB() == b1 + b2);
        Assert.assertTrue(s.getC() == c1 + c2);
        Assert.assertTrue(s.getD() == d1 + d2);
    }

    /**
     * Prueba unitaria para {@link Matriz2x2#multiplica}.
     */
    @Test public void testMultiplicaMatriz2x2() {
        double a1 = random.nextDouble() * 1000;
        double b1 = random.nextDouble() * 1000;
        double c1 = random.nextDouble() * 1000;
        double d1 = random.nextDouble() * 1000;

        double a2 = random.nextDouble() * 1000;
        double b2 = random.nextDouble() * 1000;
        double c2 = random.nextDouble() * 1000;
        double d2 = random.nextDouble() * 1000;

        Matriz2x2 m1 = new Matriz2x2(a1, b1, c1, d1);
        Matriz2x2 m2 = new Matriz2x2(a2, b2, c2, d2);
        Matriz2x2 m = m1.multiplica(m2);

        Assert.assertTrue(m.getA() == a1 * a2 + b1 * c2);
        Assert.assertTrue(m.getB() == a1 * b2 + b1 * d2);
        Assert.assertTrue(m.getC() == c1 * a2 + d1 * c2);
        Assert.assertTrue(m.getD() == c1 * b2 + d1 * d2);
    }

    /**
     * Prueba unitaria para {@link Matriz2x2#multiplica(double)}.
     */
    @Test public void testMultiplicaDouble() {
        double a = random.nextDouble() * 1000;
        double b = random.nextDouble() * 1000;
        double c = random.nextDouble() * 1000;
        double d = random.nextDouble() * 1000;

        double x = random.nextDouble() * 1000;

        Matriz2x2 m = new Matriz2x2(a, b, c, d);
        Matriz2x2 mm = m.multiplica(x);

        Assert.assertTrue(mm.getA() == a * x);
        Assert.assertTrue(mm.getB() == b * x);
        Assert.assertTrue(mm.getC() == c * x);
        Assert.assertTrue(mm.getD() == d * x);
    }

    /**
     * Prueba unitaria para {@link Matriz2x2#determinante}.
     */
    @Test public void testDeterminante() {
        double a = random.nextDouble() * 1000;
        double b = random.nextDouble() * 1000;
        double c = random.nextDouble() * 1000;
        double d = random.nextDouble() * 1000;

        Matriz2x2 m = new Matriz2x2(a, b, c, d);
        double det = m.determinante();

        Assert.assertTrue(det == a * d - b * c);
    }

    /**
     * Prueba unitaria para {@link Matriz2x2#toString}.
     */
    @Test public void testToString() {
        double a = random.nextDouble() * 1000;
        double b = random.nextDouble() * 1000;
        double c = random.nextDouble() * 1000;
        double d = random.nextDouble() * 1000;

        Matriz2x2 m = new Matriz2x2(a, b, c, d);
        String s =
            String.format("⎛ %g, %g ⎞\n", a, b) +
            String.format("⎝ %g, %g ⎠",   c, d);

        Assert.assertTrue(s.equals(m.toString()));
    }
}
