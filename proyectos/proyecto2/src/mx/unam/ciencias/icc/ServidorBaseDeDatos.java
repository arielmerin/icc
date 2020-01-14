package mx.unam.ciencias.icc;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Clase abstracta para servidores de bases de datos genéricas.
 */
public abstract class ServidorBaseDeDatos<R extends Registro, C extends Enum> {

    /* La base de datos. */
    private BaseDeDatos<R, C> bdd;
    /* El servidor de enchufes. */
    private ServerSocket servidor;
    /* El puerto. */
    private int puerto;
    /* El archivo donde cargar/guardar la base de datos. */
    private String archivo;
    /* Lista con las conexiones. */
    private Lista<Conexion<R>> conexiones;
    /* Bandera de continuación. */
    private Boolean terminado;
    /* Escuchas del servidor. */
    private Lista<EscuchaServidor> escuchas;

    /**
     * Crea un nuevo servidor usando el archivo recibido para poblar la base de
     * datos.
     * @param puerto el puerto dónde escuchar por conexiones.
     * @param archivo el archivo en el disco del cual cargar/guardar la base de
     *                datos. Puede ser <code>null</code>, en cuyo caso se usará
     *                el nombre por omisión <code>base-de-datos.bd</code>.
     * @throws IOException si ocurre un error de entrada o salida.
     */
    public ServidorBaseDeDatos(int puerto, String archivo)
        throws IOException {
        this.puerto = puerto;
        this.archivo = (archivo != null) ? archivo : "base-de-datos.bd";
        terminado = false;
        servidor = new ServerSocket(puerto);
        conexiones = new Lista<Conexion<R>>();
        bdd = creaBaseDeDatos();
        escuchas = new Lista<EscuchaServidor>();
        carga();
    }

    /**
     * Comienza a escuchar por conexiones de clientes.
     */
    public void sirve() {
        imprimeMensaje("Escuchando en el puerto: %d.\n", puerto);
        terminado = false;
        while (!terminado) {
            try {
                Socket enchufe = servidor.accept();
                Conexion<R> conexion = new Conexion<R>(enchufe);
                imprimeMensaje("Conexión recibida de: %s.\n" +
                               "Serial de conexión: %d.\n",
                               enchufe.getInetAddress().getCanonicalHostName(),
                               conexion.getSerial());
                conexion.agregaEscucha((c, e) -> manejaEventoConexion(c, e));
                new Thread(() -> conexion.manejaEventos()).start();
                synchronized (conexiones) {
                    conexiones.agregaFinal(conexion);
                }
            } catch (IOException ioe) {
                if (!terminado)
                    imprimeMensaje("Error al recibir una conexión...\n");
            }
        }
        imprimeMensaje("La ejecución del servidor ha terminado.\n");
    }

    private void carga(){
      try{
        imprimeMensaje("Cargando base de datos de %s.\n", archivo);
        BufferedReader input = new BufferedReader(
                                  new InputStreamReader(
                                    new FileInputStream(archivo)));
        bdd.carga(input);
        input.close();
        imprimeMensaje("Base de datos cargada exitosamente de %s.\n",archivo);
      }catch(IOException ioe){
        imprimeMensaje("Ocurrió un error al tratar de cargar %s.\n",archivo);
        imprimeMensaje("La base de datos estará inicialmente vacía.\n");
      }
    }

    private void guarda(){
      try{
        imprimeMensaje("Guardando base de datos en %s.\n", archivo);
        BufferedWriter output = new BufferedWriter(
                                  new OutputStreamWriter(
                                    new FileOutputStream(archivo)));
        bdd.guarda(output);
        output.close();
        imprimeMensaje("Base de datos guardada.\n");
      }catch(IOException ioe){
        imprimeMensaje("Ocurrió un error al guardar la base de datos.\n");
      }
    }

    /**
     * Agrega un escucha de servidor.
     * @param escucha el escucha a agregar.
     */
    public void agregaEscucha(EscuchaServidor escucha) {
        escuchas.agregaFinal(escucha);
    }

    /**
     * Crea la base de datos concreta.
     * @return la base de datos concreta.
     */
    public abstract BaseDeDatos<R, C> creaBaseDeDatos();

    /* Maneja los distintos eventos de la conexión. */
    private void manejaEventoConexion(Conexion<R> conexion,
                                      EventoConexion evento) {
        if (terminado)
            return;
        switch (evento) {
        case BASE_DE_DATOS:
            baseDeDatos(conexion);
            break;
        case REGISTRO_AGREGADO:
            registroAlterado(conexion, AccionRegistro.AGREGAR);
            break;
        case REGISTRO_ELIMINADO:
            registroAlterado(conexion, AccionRegistro.ELIMINAR);
            break;
        case REGISTRO_MODIFICADO:
            registroModificado(conexion);
            break;
        case DETENER_SERVICIO:
            detenerServicio(conexion);
            break;
        case CONEXION_TERMINADA:
            imprimeMensaje("Conexión terminada para %d.\n",
                           conexion.getSerial());
            desconectaConexion(conexion);
            break;
        case ECO:
            eco(conexion);
            break;
        case ERROR:
            imprimeMensaje("Error recibido de %d; desconectando cliente.\n",
                           conexion.getSerial());
            desconectaConexion(conexion);
            break;
        case EVENTO_INVALIDO:
            imprimeMensaje("Evento inválido de %d ignorado.\n",
                           conexion.getSerial());
            break;
        }
    }

    /* Envía la base de datos a un cliente. */
    private void baseDeDatos(Conexion<R> conexion) {
        imprimeMensaje("La conexión %d solicitó la base de datos.\n",conexion.getSerial());
        try{
          conexion.enviaEvento(EventoConexion.BASE_DE_DATOS);
          conexion.enviaBaseDeDatos(bdd);
          imprimeMensaje("Base de datos enviada a %d.\n",conexion.getSerial());
        }catch(IOException ioe){
          imprimeMensaje("Advertencia: error al enviar " + "la base de datos a \"%d\".",
                           conexion.getSerial());
        }

    }

    /* Agrega o elimina un registro de un cliente. */
    private void registroAlterado(Conexion<R> conexion,
                                  AccionRegistro accion) {
        EventoConexion evento = EventoConexion.ECO;
        String g = new String();
        switch(accion){
          case AGREGAR:
            evento = EventoConexion.REGISTRO_AGREGADO;
            g = "agregado";
            break;
          case ELIMINAR:
            evento = EventoConexion.REGISTRO_ELIMINADO;
            g = "eliminado";
            break;
          }
        imprimeMensaje("La conexión %d solicita %s un registro.\n",
                       conexion.getSerial(), accion.toString());
        try{
          R r = bdd.creaRegistro();
          conexion.recibeRegistro(r);
          imprimeMensaje("Registro recibido de %d.\n", conexion.getSerial());
          switch(accion){
            case AGREGAR:
              bdd.agregaRegistro(r);
              break;
            case ELIMINAR:
              bdd.eliminaRegistro(r);
              break;
          }
          imprimeMensaje("Registro recibido de %d %s.\n",
                           conexion.getSerial(), g);
          for(Conexion<R> a : conexiones){
            if(a == conexion)
              continue;
            a.enviaEvento(evento);
            a.enviaRegistro(r);
          }
        imprimeMensaje("Registro recibido de %d enviado a " + "las demás conexiones para %s.\n",
                                                        conexion.getSerial(), accion.toString());
        }catch(IOException | NullPointerException |IllegalArgumentException e){
          imprimeMensaje("Error al tratar de %s registro de %d; " + "desconectando al cliente.\n",
                                                          accion.toString(),conexion.getSerial());
          desconectaConexion(conexion);
          return;
        }
        guarda();
        imprimeMensaje("Registro %s por %d.\n", g , conexion.getSerial());
    }

    /* Modifica un registro de un cliente. */
    private void registroModificado(Conexion<R> conexion) {
        imprimeMensaje("La conexión %d solicita modificar un registro.\n", conexion.getSerial());
        try{
          R r1 = bdd.creaRegistro();
          conexion.recibeRegistro(r1);
          imprimeMensaje("Registro original recibido de %d.\n", conexion.getSerial());
          R r2 = bdd.creaRegistro();
          conexion.recibeRegistro(r2);
          imprimeMensaje("Registro modificado recibido de %d.\n", conexion.getSerial());
          for(Conexion<R> c : conexiones){
            if(c == conexion)
              continue;
            c.enviaEvento(EventoConexion.REGISTRO_MODIFICADO);
            c.enviaRegistro(r1);
            c.enviaRegistro(r2);
          }
          imprimeMensaje("Registros recibidos de %d enviados a las demás " + "conexiones para modificar.\n",
                                                                                  conexion.getSerial());
          imprimeMensaje("Registro recibido de %d modificado.\n", conexion.getSerial());
          bdd.modificaRegistro(r1,r2);
        }catch(IOException | NullPointerException |
                 IllegalArgumentException e){
          imprimeMensaje("Error al tratar de modificar registro de %d; " +"desconectando al cliente.\n",
                           conexion.getSerial());
          desconectaConexion(conexion);
          return;
        }
        guarda();
        imprimeMensaje("Registro modificado por %d.\n", conexion.getSerial());
    }

    /* Detiene el servidor. */
    private void detenerServicio(Conexion<R> conexion) {
        synchronized(terminado){terminado = true;}
        imprimeMensaje("Solicitud de terminación de %d\n", conexion.getSerial());
        synchronized(conexiones){
          for(Conexion<R> c : conexiones)
            c.desconecta();
        }
        conexiones.limpia();
        try{servidor.close();}catch(IOException ioe){}
    }

    /* Regresa el eco. */
    private void eco(Conexion<R> conexion) {
        imprimeMensaje("La conexión %d solicita un eco.\n", conexion.getSerial());
        try{
          conexion.enviaEvento(EventoConexion.ECO);
        }catch(IOException ioe){
          imprimeMensaje("Error al tratar de contestar un eco de %d; " + "desconectando al cliente.\n",
                           conexion.getSerial());
          desconectaConexion(conexion);
        }
        imprimeMensaje("Eco enviado a la conexión %d.\n", conexion.getSerial());
    }

    /* Desconecta un conexion. */
    private void desconectaConexion(Conexion<R> conexion) {
        conexion.desconecta();
        synchronized(conexiones){
          conexiones.elimina(conexion);
        }
    }

    /* Procesa los mensajes de todos los escuchas. */
    private void imprimeMensaje(String formato, Object ... argumentos) {
        for (EscuchaServidor escucha : escuchas)
            escucha.procesaMensaje(formato, argumentos);
    }
}
