package mx.unam.ciencias.icc.test;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;
import mx.unam.ciencias.icc.BaseDeDatosEstudiantes;
import mx.unam.ciencias.icc.CampoEstudiante;
import mx.unam.ciencias.icc.Conexion;
import mx.unam.ciencias.icc.Estudiante;
import mx.unam.ciencias.icc.EventoConexion;
import mx.unam.ciencias.icc.Lista;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

/**
 * Clase para pruebas unitarias de la clase {@link Conexion}, instanciada
 * con {@link Estudiante}.
 */
public class TestConexionEstudiante {

    /** Expiración para que ninguna prueba tarde más de 5 segundos. */
    @Rule public Timeout expiracion = Timeout.seconds(5);

    /* Enchufe del servidor. */
    private ServerSocket enchufeServidor;
    /* Enchufe. */
    private Socket enchufe;
    /* La conexión. */
    private Conexion<Estudiante> conexion;
    /* Generador de números aleatorios. */
    private Random random;
    /* Puerto. */
    private int puerto;

    /**
     * Crea un generador de números aleatorios.
     */
    public TestConexionEstudiante() {
        random = new Random();
        puerto = 1024 + random.nextInt(64000);
        iniciaServidor();
    }

    /* Inicia un servidor desechable. */
    public void iniciaServidor() {
        try {
            enchufeServidor = new ServerSocket(puerto);
            Runnable r = () -> {
                try {
                    enchufe = enchufeServidor.accept();
                    conexion = new Conexion<Estudiante>(enchufe);
                    new Thread(() -> conexion.manejaEventos()).start();
                } catch (IOException ioe) {
                    Assert.fail();
                }
            };
            new Thread(r).start();
        } catch (IOException ioe) {
            Assert.fail();
        }
    }

    /**
     * Prueba unitaria para {@link Conexion#getSerial}.
     */
    @Test public void testGetSerial() {
        try {
            Socket e = new Socket("localhost", puerto);
            Thread.sleep(10);
            int s = conexion.getSerial();
            Assert.assertTrue(s == conexion.getSerial());
            Assert.assertTrue(s == conexion.getSerial());
            Assert.assertTrue(s == conexion.getSerial());
            Conexion<Estudiante> cliente = new Conexion<Estudiante>(e);
            Assert.assertTrue(cliente.getSerial() == s+1);
            Assert.assertTrue(cliente.getSerial() == s+1);
            Assert.assertTrue(cliente.getSerial() == s+1);
            enchufeServidor.close();
            iniciaServidor();
            e = new Socket("localhost", puerto);
            Thread.sleep(10);
            int t = conexion.getSerial();
            Assert.assertTrue(t == s+2);
            cliente = new Conexion<Estudiante>(e);
            Assert.assertTrue(cliente.getSerial() == t+1);
        } catch (IOException ioe) {
            Assert.fail();
        } catch (InterruptedException ie) {}
    }

    /**
     * Prueba unitaria para {@link Conexion#desconecta}.
     */
    @Test public void testDesconecta() {
        try {
            Socket s = new Socket("localhost", puerto);
            Thread.sleep(10);
            conexion.agregaEscucha((c, e) -> {
                    Assert.assertTrue(c == conexion);
                    Assert.assertTrue(e == EventoConexion.CONEXION_TERMINADA);
                });
            Conexion<Estudiante> cliente = new Conexion<Estudiante>(s);
            cliente.desconecta();
            enchufeServidor.close();
        } catch (IOException ioe) {
            Assert.fail();
        } catch (InterruptedException ie) {}
    }

    /**
     * Prueba unitaria para {@link Conexion#manejaEventos}.
     */
    @Test public void testManejaEventos() {
        int[] evento = { 0 };
        EventoConexion[] evs = EventoConexion.values();
        try {
            Socket s = new Socket("localhost", puerto);
            Thread.sleep(10);
            Conexion<Estudiante> cliente = new Conexion<Estudiante>(s);
            new Thread(() -> cliente.manejaEventos()).start();
            Thread.sleep(10);
            cliente.agregaEscucha((c, e) -> {
                    Assert.assertTrue(c == cliente);
                    Assert.assertTrue(e == evs[evento[0]]);
                    evento[0]++;
                });
            for (EventoConexion ev : evs)
                conexion.enviaEvento(ev);
            Thread.sleep(10);
            enchufeServidor.close();
        } catch (IOException ioe) {
            Assert.fail();
        } catch (InterruptedException ie) {}
    }

    /**
     * Prueba unitaria para {@link Conexion#recibeRegistro}.
     */
    @Test public void testRecibeRegistro() {
        try {
            Socket s = new Socket("localhost", puerto);
            Thread.sleep(10);
            Estudiante e = TestEstudiante.estudianteAleatorio();
            conexion.enviaRegistro(e);
            Conexion<Estudiante> cliente = new Conexion<Estudiante>(s);
            BaseDeDatosEstudiantes bdd = new BaseDeDatosEstudiantes();
            Estudiante t = bdd.creaRegistro();
            cliente.recibeRegistro(t);
            Assert.assertFalse(e == t);
            Assert.assertTrue(e.equals(t));
            enchufeServidor.close();
        } catch (IOException ioe) {
            Assert.fail();
        } catch (InterruptedException ie) {}
    }

    /**
     * Prueba unitaria para {@link Conexion#enviaRegistro}.
     */
    @Test public void testEnviaRegistro() {
        try {
            Socket s = new Socket("localhost", puerto);
            Thread.sleep(10);
            Estudiante e = TestEstudiante.estudianteAleatorio();
            conexion.agregaEscucha((c, v) -> {
                    Assert.assertTrue(c == conexion);
                    Assert.assertTrue(v == EventoConexion.REGISTRO_AGREGADO);
                    BaseDeDatosEstudiantes bdd = new BaseDeDatosEstudiantes();
                    try {
                        Estudiante t = bdd.creaRegistro();
                        conexion.recibeRegistro(t);
                        Assert.assertFalse(e == t);
                        Assert.assertTrue(e.equals(t));
                    } catch (IOException ioe) {
                        Assert.fail();
                    }
                });
            Conexion<Estudiante> cliente = new Conexion<Estudiante>(s);
            cliente.enviaEvento(EventoConexion.REGISTRO_AGREGADO);
            cliente.enviaRegistro(e);
            Thread.sleep(10);
            enchufeServidor.close();
        } catch (IOException ioe) {
            Assert.fail();
        } catch (InterruptedException ie) {}
    }

    /**
     * Prueba unitaria para {@link Conexion#recibeBaseDeDatos}.
     */
    @Test public void testRecibeBaseDeDatos() {
        int total = 10 + random.nextInt(90);
        try {
            Socket s = new Socket("localhost", puerto);
            Thread.sleep(10);
            BaseDeDatosEstudiantes bdd1 = new BaseDeDatosEstudiantes();
            for (int i = 0; i < total; i++)
                bdd1.agregaRegistro(TestEstudiante.estudianteAleatorio());
            conexion.enviaBaseDeDatos(bdd1);
            Conexion<Estudiante> cliente = new Conexion<Estudiante>(s);
            BaseDeDatosEstudiantes bdd2 = new BaseDeDatosEstudiantes();
            cliente.recibeBaseDeDatos(bdd2);
            Assert.assertFalse(bdd1 == bdd2);
            Assert.assertTrue(bdd1.getRegistros().equals(bdd2.getRegistros()));
            enchufeServidor.close();
        } catch (IOException ioe) {
            Assert.fail();
        } catch (InterruptedException ie) {}
    }

    /**
     * Prueba unitaria para {@link Conexion#enviaBaseDeDatos}.
     */
    @Test public void testEnviaBaseDeDatos() {
        int total = 10 + random.nextInt(90);
        try {
            Socket s = new Socket("localhost", puerto);
            Thread.sleep(10);
            BaseDeDatosEstudiantes bdd1 = new BaseDeDatosEstudiantes();
            for (int i = 0; i < total; i++)
                bdd1.agregaRegistro(TestEstudiante.estudianteAleatorio());
            conexion.agregaEscucha((c, v) -> {
                    Assert.assertTrue(c == conexion);
                    Assert.assertTrue(v == EventoConexion.BASE_DE_DATOS);
                    BaseDeDatosEstudiantes bdd2 = new BaseDeDatosEstudiantes();
                    try {
                        conexion.recibeBaseDeDatos(bdd2);
                        Assert.assertFalse(bdd1 == bdd2);
                        Lista<Estudiante> r1 = bdd1.getRegistros();
                        Lista<Estudiante> r2 = bdd2.getRegistros();
                        Assert.assertTrue(r1.equals(r2));
                    } catch (IOException ioe) {
                        Assert.fail();
                    }
                });
            Conexion<Estudiante> cliente = new Conexion<Estudiante>(s);
            cliente.enviaEvento(EventoConexion.BASE_DE_DATOS);
            cliente.enviaBaseDeDatos(bdd1);
            Thread.sleep(10);
            enchufeServidor.close();
        } catch (IOException ioe) {
            Assert.fail();
        } catch (InterruptedException ie) {}
    }

    /**
     * Prueba unitaria para {@link Conexion#enviaEvento}.
     */
    @Test public void testEnviaEvento() {
        int[] evento = { 0 };
        EventoConexion[] evs = EventoConexion.values();
        try {
            Socket s = new Socket("localhost", puerto);
            Thread.sleep(10);
            conexion.agregaEscucha((c, e) -> {
                    Assert.assertTrue(c == conexion);
                    Assert.assertTrue(e == evs[evento[0]]);
                    evento[0]++;
                });
            Conexion<Estudiante> cliente = new Conexion<Estudiante>(s);
            for (EventoConexion ev : evs)
                cliente.enviaEvento(ev);
            enchufeServidor.close();
        } catch (IOException ioe) {
            Assert.fail();
        } catch (InterruptedException ie) {}
    }

    /**
     * Prueba unitaria para {@link Conexion#agregaEscucha}.
     */
    @Test public void testAgregaEscucha() {
        int[] evento = { 0 };
        try {
            Socket s = new Socket("localhost", puerto);
            Thread.sleep(10);
            Conexion<Estudiante> cliente = new Conexion<Estudiante>(s);
            cliente.enviaEvento(EventoConexion.ECO);
            Thread.sleep(10);
            Assert.assertFalse(evento[0] == 1);
            conexion.agregaEscucha((c, e) -> {
                    Assert.assertTrue(c == conexion);
                    Assert.assertTrue(e == EventoConexion.ECO);
                    evento[0]++;
                });
            cliente.enviaEvento(EventoConexion.ECO);
            Thread.sleep(10);
            Assert.assertTrue(evento[0] == 1);
            enchufeServidor.close();
        } catch (IOException ioe) {
            Assert.fail();
        } catch (InterruptedException ie) {}
    }
}
