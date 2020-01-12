package mx.unam.ciencias.icc.test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.Socket;
import java.util.Iterator;
import java.util.Random;
import mx.unam.ciencias.icc.BaseDeDatos;
import mx.unam.ciencias.icc.BaseDeDatosEstudiantes;
import mx.unam.ciencias.icc.CampoEstudiante;
import mx.unam.ciencias.icc.Estudiante;
import mx.unam.ciencias.icc.EventoBaseDeDatos;
import mx.unam.ciencias.icc.EventoConexion;
import mx.unam.ciencias.icc.Lista;
import mx.unam.ciencias.icc.ServidorBaseDeDatos;
import mx.unam.ciencias.icc.ServidorBaseDeDatosEstudiantes;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.rules.Timeout;

/**
 * Clase para pruebas unitarias de la clase {@link
 * ServidorBaseDeDatosEstudiantes}.
 */
public class TestServidorBaseDeDatosEstudiantes {

    /** Expiración para que ninguna prueba tarde más de 5 segundos. */
    @Rule public Timeout expiracion = Timeout.seconds(5);
    /** Directorio para archivos temporales. */
    @Rule public TemporaryFolder directorio = new TemporaryFolder();

    /* Clase interna para manejar una conexión de pruebas. */
    private class Conexion {

        /* El enchufe. */
        private Socket enchufe;
        /* La entrada. */
        private BufferedReader in;
        /* La salida. */
        private BufferedWriter out;

        /* Construye una nueva conexión en el puerto. */
        public Conexion(int puerto) throws IOException {
            enchufe = new Socket("localhost", puerto);
            in =
                new BufferedReader(
                    new InputStreamReader(
                        enchufe.getInputStream()));
            out =
                new BufferedWriter(
                    new OutputStreamWriter(
                        enchufe.getOutputStream()));
        }

        /* Manda un mensaje por la conexión. */
        public void mandaEvento(EventoConexion evento) throws IOException {
            out.write(EventoConexion.getMensaje(evento));
            out.newLine();
            out.flush();
        }

        /* Manda un estudiante por la conexión. */
        public void mandaEstudiante(Estudiante estudiante) throws IOException {
            estudiante.guarda(out);
            out.flush();
        }

        /* Manda una nueva línea por la conexión. */
        public void mandaLinea() throws IOException {
            out.newLine();
            out.flush();
        }

        /* Recibe un evento por la conexión. */
        public EventoConexion recibeEvento() throws IOException {
            return EventoConexion.getEvento(recibeCadena());
        }

        /* Recibe una cadena por la conexión. */
        public String recibeCadena() throws IOException {
            return in.readLine();
        }

        /* Recibe una base de datos por la conexión. */
        public BaseDeDatosEstudiantes recibeBaseDeDatos() throws IOException {
            BaseDeDatosEstudiantes bdd = new BaseDeDatosEstudiantes();
            bdd.carga(in);
            return bdd;
        }

        /* Recibe un estudiante por la conexión. */
        public Estudiante recibeEstudiante() throws IOException {
            Estudiante e = new Estudiante(null, 0, 0, 0);
            e.carga(in);
            return e;
        }
    }

    /* Generador de números aleatorios. */
    private Random random;
    /* Servidor de base de datos. */
    private ServidorBaseDeDatosEstudiantes sbdd;
    /* Los estudiantes. */
    private Estudiante[] estudiantes;
    /* El total de estudiantes. */
    private int total;
    /* El archivo temporal de la base de datos. */
    private String archivo;
    /* El puerto. */
    private int puerto;

    /**
     * Crea un generador de números aleatorios para cada prueba y una base de
     * datos de estudiantes.
     */
    public TestServidorBaseDeDatosEstudiantes() {
        random = new Random();
        total = 10 + random.nextInt(100);
        puerto = 1024 + random.nextInt(64000);
    }

    /**
     * Método que se ejecuta antes de cada prueba unitaria; crea el archivo de
     * la base de datos y hace servir el servidor.
     */
    @Before public void arma() {
        try {
            estudiantes = new Estudiante[total];
            BaseDeDatosEstudiantes bdd = new BaseDeDatosEstudiantes();
            for (int i = 0; i < total; i++) {
                estudiantes[i] = TestEstudiante.estudianteAleatorio();
                bdd.agregaRegistro(estudiantes[i]);
            }
            File f = null;
            String s = String.format("test-base-de-datos-%04d.db",
                                     random.nextInt(9999));
            f = directorio.newFile(s);
            archivo = f.getAbsolutePath();

            BufferedWriter out =
                new BufferedWriter(
                    new OutputStreamWriter(
                        new FileOutputStream(archivo)));
            bdd.guarda(out);
            out.close();

            sbdd = new ServidorBaseDeDatosEstudiantes(puerto, archivo);

            new Thread(() -> sbdd.sirve()).start();

        } catch (IOException ioe) {
            Assert.fail();
        }
    }

    /**
     * Método que se ejecuta despué de cada prueba unitaria; elimina el archivo
     * de la base de datos y detiene el servidor. Esto hace un método
     * testSirveDetenerServidor innecesario.
     */
    @After public void desarma() {
        File f = new File(archivo);
        f.delete();

        try {
            Conexion c = new Conexion(puerto);
            c.mandaEvento(EventoConexion.DETENER_SERVICIO);
            Assert.assertTrue(c.recibeCadena() == null);
        } catch (IOException ioe) {
            Assert.fail();
        }
    }

    /* Crea una nueva conexión, mandando y recibiendo un eco para probarla de
     * inmediato. */
    private Conexion nuevaConexion() {
        Conexion c = null;
        try {
            c = new Conexion(puerto);
            c.mandaEvento(EventoConexion.ECO);
            Assert.assertTrue(c.recibeEvento() == EventoConexion.ECO);
        } catch (IOException ioe) {
            Assert.fail();
        }
        return c;
    }

    /**
     * Prueba unitaria para el evento {@link EventoConexion#BASE_DE_DATOS} en el
     * método {@link ServidorBaseDeDatos#sirve}.
     */
    @Test public void testSirveBaseDeDatos() {
        try {
            Conexion c = nuevaConexion();
            c.mandaEvento(EventoConexion.BASE_DE_DATOS);
            Assert.assertTrue(c.recibeEvento() == EventoConexion.BASE_DE_DATOS);
            BaseDeDatosEstudiantes bdd = c.recibeBaseDeDatos();
            Lista<Estudiante> l = bdd.getRegistros();
            Assert.assertTrue(l.getLongitud() == total);
            int i = 0;
            for (Estudiante e : l)
                Assert.assertTrue(e.equals(estudiantes[i++]));
        } catch (IOException ioe) {
            Assert.fail();
        }
    }

    /**
     * Prueba unitaria para el evento {@link EventoConexion#REGISTRO_AGREGADO}
     * en el método {@link ServidorBaseDeDatos#sirve}.
     */
    @Test public void testSirveRegistroAgregado() {
        try {
            Conexion c1 = nuevaConexion();
            Conexion c2 = nuevaConexion();

            Estudiante estudiante = new Estudiante("A", 1, 1, 1);
            c1.mandaEvento(EventoConexion.REGISTRO_AGREGADO);
            c1.mandaEstudiante(estudiante);
            c1.mandaLinea();

            c1.mandaEvento(EventoConexion.BASE_DE_DATOS);
            Assert.assertTrue(c1.recibeEvento() ==
                              EventoConexion.BASE_DE_DATOS);
            BaseDeDatosEstudiantes bdd = c1.recibeBaseDeDatos();
            Lista<Estudiante> l = bdd.getRegistros();
            Assert.assertTrue(l.contiene(estudiante));

            Assert.assertTrue(c2.recibeEvento() ==
                              EventoConexion.REGISTRO_AGREGADO);
            Estudiante t = c2.recibeEstudiante();
            Assert.assertTrue(t.equals(estudiante));
        } catch (IOException ioe) {
            Assert.fail();
        }
    }

    /**
     * Prueba unitaria para el evento {@link EventoConexion#REGISTRO_ELIMINADO}
     * en el método {@link ServidorBaseDeDatos#sirve}.
     */
    @Test public void testSirveRegistroEliminado() {
        try {
            Conexion c1 = nuevaConexion();
            Conexion c2 = nuevaConexion();

            Estudiante estudiante = estudiantes[random.nextInt(total)];
            c1.mandaEvento(EventoConexion.REGISTRO_ELIMINADO);
            c1.mandaEstudiante(estudiante);
            c1.mandaLinea();

            c1.mandaEvento(EventoConexion.BASE_DE_DATOS);
            Assert.assertTrue(c1.recibeEvento() ==
                              EventoConexion.BASE_DE_DATOS);
            BaseDeDatosEstudiantes bdd = c1.recibeBaseDeDatos();
            Lista<Estudiante> l = bdd.getRegistros();
            Assert.assertFalse(l.contiene(estudiante));

            Assert.assertTrue(c2.recibeEvento() ==
                              EventoConexion.REGISTRO_ELIMINADO);
            Estudiante t = c2.recibeEstudiante();
            Assert.assertTrue(t.equals(estudiante));
        } catch (IOException ioe) {
            Assert.fail();
        }
    }

    /**
     * Prueba unitaria para el evento {@link EventoConexion#REGISTRO_MODIFICADO}
     * en el método {@link ServidorBaseDeDatos#sirve}.
     */
    @Test public void testSirveRegistroModificado() {
        try {
            Conexion c1 = nuevaConexion();
            Conexion c2 = nuevaConexion();

            Estudiante e = estudiantes[random.nextInt(total)];
            Estudiante m = new Estudiante(null, 0, 0, 0);
            m.actualiza(e);
            m.setNombre("A");
            c1.mandaEvento(EventoConexion.REGISTRO_MODIFICADO);
            c1.mandaEstudiante(e);
            c1.mandaEstudiante(m);

            c1.mandaEvento(EventoConexion.BASE_DE_DATOS);
            Assert.assertTrue(c1.recibeEvento() ==
                              EventoConexion.BASE_DE_DATOS);
            BaseDeDatosEstudiantes bdd = c1.recibeBaseDeDatos();
            Lista<Estudiante> l = bdd.getRegistros();
            Assert.assertFalse(l.contiene(e));
            Assert.assertTrue(l.contiene(m));

            Assert.assertTrue(c2.recibeEvento() ==
                              EventoConexion.REGISTRO_MODIFICADO);
            Estudiante t = new Estudiante(null, 0, 0, 0);
            t = c2.recibeEstudiante();
            Assert.assertTrue(t.equals(e));
            t = c2.recibeEstudiante();
            Assert.assertTrue(t.equals(m));
        } catch (IOException ioe) {
            Assert.fail();
        }
    }

    /**
     * Prueba unitaria para el evento {@link EventoConexion#CONEXION_TERMINADA}
     * en el método {@link ServidorBaseDeDatos#sirve}.
     */
    @Test public void testSirveConexionTerminada() {
        try {
            Conexion c = nuevaConexion();
            c.mandaEvento(EventoConexion.CONEXION_TERMINADA);
            Assert.assertTrue(c.recibeCadena() == null);

            c = nuevaConexion();
            c.mandaEvento(EventoConexion.ECO);
            Assert.assertTrue(c.recibeEvento() == EventoConexion.ECO);
        } catch (IOException ioe) {
            Assert.fail();
        }
    }

    /**
     * Prueba unitaria para el evento {@link EventoConexion#ECO} en el método
     * {@link ServidorBaseDeDatos#sirve}.
     */
    @Test public void testSirveEco() {
        try {
            Conexion c = nuevaConexion();
            c.mandaEvento(EventoConexion.ECO);
            Assert.assertTrue(c.recibeEvento() == EventoConexion.ECO);
        } catch (IOException ioe) {
            Assert.fail();
        }
    }

    /**
     * Prueba unitaria para el evento {@link EventoConexion#ERROR}
     * en el método {@link ServidorBaseDeDatos#sirve}.
     */
    @Test public void testSirveError() {
        try {
            Conexion c = nuevaConexion();
            c.mandaEvento(EventoConexion.ERROR);
            Assert.assertTrue(c.recibeCadena() == null);

            c = nuevaConexion();
            c.mandaEvento(EventoConexion.ECO);
            Assert.assertTrue(c.recibeEvento() == EventoConexion.ECO);
        } catch (IOException ioe) {
            Assert.fail();
        }
    }

    /**
     * Prueba unitaria para el evento {@link EventoConexion#EVENTO_INVALIDO} en
     * el método {@link ServidorBaseDeDatos#sirve}.
     */
    @Test public void testSirveEventoInvalido() {
        try {
            Conexion c = nuevaConexion();
            c.mandaEvento(EventoConexion.EVENTO_INVALIDO);
            c.mandaEvento(EventoConexion.ECO);
            Assert.assertTrue(c.recibeEvento() == EventoConexion.ECO);
        } catch (IOException ioe) {
            Assert.fail();
        }
    }

    /**
     * Prueba unitaria para {@link
     * ServidorBaseDeDatosEstudiantes#agregaEscucha}.
     */
    @Test public void testAgregaEscucha() {
        sbdd.agregaEscucha((s, f) -> {
                String m = String.format(s, f);
                Assert.assertTrue(m.length() > 10);
            });
        try {
            Conexion c1 = nuevaConexion();
            Conexion c2 = nuevaConexion();

            Estudiante estudiante = new Estudiante("A", 1, 1, 1);
            c1.mandaEvento(EventoConexion.REGISTRO_AGREGADO);
            c1.mandaEstudiante(estudiante);
            c1.mandaLinea();

            try { Thread.sleep(100); } catch (InterruptedException ie) {}

            c1.mandaEvento(EventoConexion.BASE_DE_DATOS);
            try { Thread.sleep(100); } catch (InterruptedException ie) {}
            Assert.assertTrue(c1.recibeEvento() ==
                              EventoConexion.BASE_DE_DATOS);
            BaseDeDatosEstudiantes bdd = c1.recibeBaseDeDatos();
            Lista<Estudiante> l = bdd.getRegistros();
            Assert.assertTrue(l.contiene(estudiante));

            Assert.assertTrue(c2.recibeEvento() ==
                              EventoConexion.REGISTRO_AGREGADO);
            Estudiante t = c2.recibeEstudiante();
            Assert.assertTrue(t.equals(estudiante));
        } catch (IOException ioe) {
            Assert.fail();
        }
    }

    /**
     * Prueba unitaria para {@link
     * ServidorBaseDeDatosEstudiantes#creaBaseDeDatos}.
     */
    @Test public void testCreaBaseDeDatos() {
        BaseDeDatos<Estudiante, CampoEstudiante> bdd = sbdd.creaBaseDeDatos();
        Assert.assertTrue(bdd instanceof BaseDeDatosEstudiantes);
    }
}
