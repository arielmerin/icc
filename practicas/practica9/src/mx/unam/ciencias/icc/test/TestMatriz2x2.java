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

    /* Zero virtual para lidiar con los inevitables errores de redondeo. */
    private static final double ERROR_REDONDEO = 0.000000000001;

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

    /* Nos dice si dos dobles son virtualmente idénticos. */
    private boolean casiIguales(double a, double b) {
        return Math.abs(a - b) < ERROR_REDONDEO;
    }

    /**
     * Prueba unitaria para {@link Matriz2x2#inversa}.
     */
    @Test public void testInversa() {
        double a = random.nextDouble() * 1000;
        double b = random.nextDouble() * 1000;
        double c = random.nextDouble() * 1000;
        double d = random.nextDouble() * 1000;

        Matriz2x2 m = new Matriz2x2(a, b, c, d);
        double det = m.determinante();
        Matriz2x2 inv;

        if (det == 0.0) {
            try {
                inv = m.inversa();
                Assert.fail();
            } catch (IllegalStateException ise) {}
        } else {
            inv = m.inversa();
            Matriz2x2 id = m.multiplica(inv);
            Assert.assertTrue(casiIguales(id.getA(), 1.0));
            Assert.assertTrue(casiIguales(id.getB(), 0.0));
            Assert.assertTrue(casiIguales(id.getC(), 0.0));
            Assert.assertTrue(casiIguales(id.getD(), 1.0));
        }

        m = new Matriz2x2(0, 0, 0, 0);
        try {
            inv = m.inversa();
            Assert.fail();
        } catch (IllegalStateException ise) {}
    }

    /**
     * Prueba unitaria para {@link Matriz2x2#potencia}.
     */
    @Test public void testPotencia() {
        double a = random.nextDouble() * 1000;
        double b = random.nextDouble() * 1000;
        double c = random.nextDouble() * 1000;
        double d = random.nextDouble() * 1000;

        Matriz2x2 m = new Matriz2x2(a, b, c, d);
        int n = 1 + random.nextInt(100);
        Matriz2x2 p = m.potencia(n);

        double ra = a;
        double rb = b;
        double rc = c;
        double rd = d;

        while (n > 1) {
            double t1 = ra * a + rb * c;
            double t2 = ra * b + rb * d;
            double t3 = rc * a + rd * c;
            double t4 = rc * b + rd * d;
            ra = t1;
            rb = t2;
            rc = t3;
            rd = t4;
            n--;
        }

        Assert.assertTrue(p.getA() == ra);
        Assert.assertTrue(p.getB() == rb);
        Assert.assertTrue(p.getC() == rc);
        Assert.assertTrue(p.getD() == rd);
    }

    /* Agrega espacios a la cadena hasta que tenga longitud n. */
    private String agregaEspacios(String s, int n) {
        String r = s;
        while (r.length() < n)
            r = " " + r;
        return r;
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

        String sa = String.format("%2.3f", a);
        String sb = String.format("%2.3f", b);
        String sc = String.format("%2.3f", c);
        String sd = String.format("%2.3f", d);

        int n = Math.max(Math.max(sa.length(), sb.length()),
                         Math.max(sc.length(), sd.length()));

        sa = agregaEspacios(sa, n);
        sb = agregaEspacios(sb, n);
        sc = agregaEspacios(sc, n);
        sd = agregaEspacios(sd, n);

        String s =
            String.format("⎛ %s, %s ⎞\n", sa, sb) +
            String.format("⎝ %s, %s ⎠",   sc, sd);

        Assert.assertTrue(s.equals(m.toString()));

        a = 1.0;
        b = Math.pow(2, 28);
        c = Math.pow(2, 28);
        d = 1.0;
        m = new Matriz2x2(a, b, c, d);
        s = String.format("⎛         %2.3f, %2.3f ⎞\n", 1.0, Math.pow(2, 28)) +
            String.format("⎝ %2.3f,         %2.3f ⎠",   Math.pow(2, 28), 1.0);

        Assert.assertTrue(s.equals(m.toString()));
    }

    /**
     * Prueba unitaria para {@link Matriz2x2#equals}.
     */
    @Test public void testEquals() {
        double a = random.nextDouble() * 1000;
        double b = random.nextDouble() * 1000;
        double c = random.nextDouble() * 1000;
        double d = random.nextDouble() * 1000;

        Matriz2x2 m = new Matriz2x2(a, b, c, d);
        Matriz2x2 n = new Matriz2x2(a, b, c, d);
        Assert.assertTrue(m.equals(n));
        double x = 10000 + random.nextDouble() * 1000;
        n = new Matriz2x2(x, b, c, d);
        Assert.assertFalse(m.equals(n));
        n = new Matriz2x2(a, x, c, d);
        Assert.assertFalse(m.equals(n));
        n = new Matriz2x2(a, b, x, d);
        Assert.assertFalse(m.equals(n));
        n = new Matriz2x2(a, b, c, x);
        Assert.assertFalse(m.equals(n));
        Matriz2x2 p = new Matriz2x2(a+1, b+1, c+1, d+1);
        Assert.assertFalse(m.equals(p));
        Assert.assertFalse(m.equals(""));
        Assert.assertFalse(m.equals(null));
    }
}
