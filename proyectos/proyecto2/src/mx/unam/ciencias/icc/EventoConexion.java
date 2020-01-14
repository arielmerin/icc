package mx.unam.ciencias.icc;

/**
 * Enumeración para los eventos que puede genera una conexión entre el servidor
 * y el cliente de bases de datos.
 */
public enum EventoConexion {

    /**
     * Si el servidor recibe este mensaje, contesta enviando toda la base de
     * datos. Si el cliente recibe este mensaje, entonces comienza a cargar la
     * base de datos.
     */
    BASE_DE_DATOS,

    /**
     * El interlocutor agregó un registro. Si este mensaje es recibido por el
     * servidor o el cliente, inmediatamente después recibirá un registro que
     * debe agregar a la base de datos.
     */
    REGISTRO_AGREGADO,

    /**
     * El interlocutor eliminó un registro. Si este mensaje es recibido por el
     * servidor o el cliente, inmediatamente después recibirá un registro que
     * debe eliminar de la base de datos.
     */
    REGISTRO_ELIMINADO,

    /**
     * El interlocutor modificó un registro. Si este mensaje es recibido por el
     * servidor o el cliente, inmediatamente después recibirá dos registros: el
     * primero será el registro original, y el segundo el registro modificado.
     */
    REGISTRO_MODIFICADO,

    /**
     * La conexión fue terminada.
     */
    CONEXION_TERMINADA,

    /**
     * El servidor debe detenerse, desconectando a todos los clientes que
     * pudieran estar conectados. Los clientes ignoran este mensaje, después de
     * imprimir una advertencia en consola.
     */
    DETENER_SERVICIO,

    /**
     * Mensaje de eco. El servidor y el cliente deben regresar el mensaje de eco
     * cuando reciban el mensaje de eco. Es necesario para poder probar el
     * servidor.
     */
    ECO,

    /**
     * Ocurrió un error no recuperable durante la comunicación. Si un cliente
     * recibe el mensaje, se desconecta después de avisar al usuario; el
     * servidor imprime una advertencia en la consola, cierra la conexión con
     * ese cliente y continúa ejecutándose para los demás clientes.
     */
    ERROR,

    /**
     * El evento no es reconocido.
     */
    EVENTO_INVALIDO;

    /* El prefijo para eventos. */
    private static final String EVENTO = "|=EVENTO:";

    /**
     * Descifra un cadena recibida y la traduce a un evento.
     * @param mensaje la cadena de texto con el evento. La cadena recibida debe
     *        comenzar con "|=EVENTO:", seguido del evento, de otra forma se le
     *        considerará inválido.
     * @return el evento correspondiente a la línea.
     */
    public static EventoConexion getEvento(String mensaje) {
        // Aquí va su código.
        CharSequence eve = "|=EVENTO:";
        if(!mensaje.contains(eve))
          return EVENTO_INVALIDO;
        switch(mensaje){
          case "|=EVENTO:BASE_DE_DATOS":
            return BASE_DE_DATOS;
          case "|=EVENTO:REGISTRO_AGREGADO":
            return REGISTRO_AGREGADO;
          case "|=EVENTO:REGISTRO_ELIMINADO":
            return REGISTRO_ELIMINADO;
          case "|=EVENTO:REGISTRO_MODIFICADO":
            return REGISTRO_MODIFICADO;
          case "|=EVENTO:CONEXION_TERMINADA":
            return CONEXION_TERMINADA;
          case "|=EVENTO:DETENER_SERVICIO":
            return DETENER_SERVICIO;
          case "|=EVENTO:ECO":
            return ECO;
          case "|=EVENTO:ERROR":
            return ERROR;
          default:
            return EVENTO_INVALIDO;
        }
    }

    /**
     * Genera una cadena con un mensaje válido para {@link getEvento}.
     * @param evento el evento del que queremos generar un mensaje.
     * @return la cadena con el mensaje correspondiente al evento.
     */
    public static String getMensaje(EventoConexion evento) {
        // Aquí va su código.
        String s = new String();
        s = "|=EVENTO:" + evento.toString();
        return s;

    }

    @Override public String toString(){
      switch(this){
        case BASE_DE_DATOS:
          return "BASE_DE_DATOS";
        case REGISTRO_AGREGADO:
          return "REGISTRO_AGREGADO";
        case REGISTRO_ELIMINADO:
          return "REGISTRO_ELIMINADO";
        case REGISTRO_MODIFICADO:
          return "REGISTRO_MODIFICADO";
        case CONEXION_TERMINADA:
          return "CONEXION_TERMINADA";
        case DETENER_SERVICIO:
          return "DETENER_SERVICIO";
        case ECO:
          return "ECO";
        case ERROR:
          return "ERROR";
        case EVENTO_INVALIDO:
          return "EVENTO_INVALIDO";
        default:
          return null;
      }
    }
}
