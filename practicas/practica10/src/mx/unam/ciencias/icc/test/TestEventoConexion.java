package mx.unam.ciencias.icc.test;

import java.util.Random;
import mx.unam.ciencias.icc.EventoConexion;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

/**
 * Clase para pruebas unitarias de la clase {@link EventoConexion}.
 */
public class TestEventoConexion {

    /** Expiración para que ninguna prueba tarde más de 5 segundos. */
    @Rule public Timeout expiracion = Timeout.seconds(5);

    /* Generador de números aleatorios. */
    private Random random;

    /**
     * Crea un generador de números aleatorios.
     */
    public TestEventoConexion() {
        random = new Random();
    }

    /**
     * Prueba unitaria para {@link EventoConexion#getEvento}.
     */
    @Test public void testGetEvento() {
        for (EventoConexion e : EventoConexion.values()) {
            String evento = "|=EVENTO:" + e.toString();
            Assert.assertTrue(EventoConexion.getEvento(evento) == e);
        }
        String s = String.valueOf(random.nextInt());
        Assert.assertTrue(EventoConexion.getEvento("|=EVENTO:" + s) ==
                          EventoConexion.EVENTO_INVALIDO);
        Assert.assertTrue(EventoConexion.getEvento(s) ==
                          EventoConexion.EVENTO_INVALIDO);
    }

    /**
     * Prueba unitaria para {@link EventoConexion#getMensaje}.
     */
    @Test public void testGetMensaje() {
        for (EventoConexion e : EventoConexion.values()) {
            String m = "|=EVENTO:" + e.toString();
            Assert.assertTrue(EventoConexion.getMensaje(e).equals(m));
        }
    }
}
