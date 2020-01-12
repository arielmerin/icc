package mx.unam.ciencias.icc;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.net.Socket;

/**
 * Clase para hilos de ejecución que manejan las conexiones de la base de datos.
 */
public class Conexion<R extends Registro> {

    /* Contador de números seriales. */
    private static int contadorSerial;

    /* El enchufe. */
    private Socket enchufe;
    /* La entrada de la conexión. */
    private BufferedReader in;
    /* La salida de la conexión. */
    private BufferedWriter out;
    /* Lista de escuchas de conexión. */
    private Lista<EscuchaConexion<R>> escuchas;
    /* El número serial único del hilo. */
    private int serial;

    /**
     * Construye una nueva conexión.
     * @param enchufe el enchufe de la conexión ya establecida.
     * @throws IOException si ocurre un error de entrada o salida.
     */
    public Conexion(Socket enchufe) throws IOException {        
        this.enchufe = enchufe;
        in =
            new BufferedReader(
                new InputStreamReader(
                    enchufe.getInputStream()));
        out =
            new BufferedWriter(
                new OutputStreamWriter(
                    enchufe.getOutputStream()));
        escuchas = new Lista<EscuchaConexion<R>>();
        this.serial = ++contadorSerial;
    }

    /**
     * Maneja los eventos de la conexión. El método no termina hasta que la
     * conexión sea cerrada. Al ir recibiendo eventos, la conexión avisará a sus
     * escuchas de los mismos.
     */
    public void manejaEventos() {
        try {
            do {
                String s = in.readLine();
                if(s == null)
                    break;
                for(EscuchaConexion<R> escucha : escuchas)
                    escucha.eventoOcurrido(this, EventoConexion.getEvento(s)); 
            } while(true);
        
        } catch(IOException ioe) {
            for(EscuchaConexion<R> escucha1 : escuchas)
                escucha1.eventoOcurrido(this, EventoConexion.ERROR);
        }
        
        for(EscuchaConexion<R> escucha2 : escuchas)
            escucha2.eventoOcurrido(this, EventoConexion.CONEXION_TERMINADA);
    }

    /**
     * Regresa un número serial único para cada conexión.
     * @return un número serial único para cada conexión.
     */
    public int getSerial() {
        return serial;
    }

    /**
     * Cierra la conexión.
     */
    public void desconecta() {
        try {
            enchufe.close();
        } catch (IOException ioe){
            /* No importa lo que ocurra, cierra la conexion. */
        }
    }

    /**
     * Recibe un registro de la conexión.
     * @param registro el registro para poder recibir los datos por la conexión.
     * @throws IOException si hay un error durante la transferencia.
     * @throws NullPointerException si el registro no puede cargarse.
     */
    public void recibeRegistro(R registro) throws IOException {
        if(!(registro.carga(in)))
            throw new NullPointerException("El registro no puede cargarse.");
    }

    /**
     * Envia un registro a través de la conexión, empujando la salida después.
     * @param registro el registro a enviar por la conexión.
     * @throws IOException si hay un error durante la transferencia.
     */
    public void enviaRegistro(R registro) throws IOException {
        registro.guarda(out);
        out.flush();
    }

    /**
     * Envia la base de datos y una nueva línea a través de la conexión,
     * empujando la salida después.
     * @param bdd la base de datos a enviar por la conexión.
     * @throws IOException si hay un error durante la transferencia.
     */
    public void enviaBaseDeDatos(BaseDeDatos<R, ? extends Enum> bdd)
        throws IOException {
            bdd.guarda(out);
            out.newLine();
            out.flush();
    }

    /**
     * Recibe la base de datos de la conexión.
     * @param bdd la base de datos para poder cargarse.
     * @throws IOException si hay un error durante la transferencia.
     */
    public void recibeBaseDeDatos(BaseDeDatos<R, ? extends Enum> bdd)
        throws IOException {
            bdd.carga(in);
    }

    /**
     * Envía un evento y una nueva línea por la conexión, empujando la salida
     * después.
     * @param evento el evento a enviar.
     * @throws IOException si ocurre un error de entrada/salida.
     */
    public void enviaEvento(EventoConexion evento) throws IOException {
        out.write(EventoConexion.getMensaje(evento));
        out.newLine();
        out.flush();
    }

    /**
     * Agrega un escucha de conexión.
     * @param escucha el escucha a agregar.
     */
    public void agregaEscucha(EscuchaConexion<R> escucha) {
        escuchas.agregaFinal(escucha);
    }
}
