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
        EventoConexion evento = EVENTO_INVALIDO;
        if(mensaje.startsWith("|=EVENTO:")) {
            if(mensaje.equals("|=EVENTO:BASE_DE_DATOS"))
                evento = BASE_DE_DATOS;
            else if(mensaje.equals("|=EVENTO:REGISTRO_AGREGADO"))
                evento = REGISTRO_AGREGADO;
            else if(mensaje.equals("|=EVENTO:REGISTRO_ELIMINADO"))
                evento = REGISTRO_ELIMINADO;
            else if(mensaje.equals("|=EVENTO:REGISTRO_MODIFICADO"))
                evento = REGISTRO_MODIFICADO;
            else if(mensaje.equals("|=EVENTO:CONEXION_TERMINADA"))
                evento = CONEXION_TERMINADA;
            else if(mensaje.equals("|=EVENTO:DETENER_SERVICIO"))
                evento = DETENER_SERVICIO;
            else if(mensaje.equals("|=EVENTO:ECO"))
                evento = ECO;
            else if(mensaje.equals("|=EVENTO:ERROR"))
                evento = ERROR;
        }
        
        return evento;
    }

    /**
     * Genera una cadena con un mensaje válido para {@link getEvento}.
     * @param evento el evento del que queremos generar un mensaje.
     * @return la cadena con el mensaje correspondiente al evento.
     */
    public static String getMensaje(EventoConexion evento) {
        String s = "|=EVENTO:EVENTO_INVALIDO";
        if(evento == BASE_DE_DATOS)
            s = "|=EVENTO:BASE_DE_DATOS";
        else if(evento == REGISTRO_AGREGADO)
            s = "|=EVENTO:REGISTRO_AGREGADO";
        else if(evento == REGISTRO_ELIMINADO)
            s = "|=EVENTO:REGISTRO_ELIMINADO";
        else if(evento == REGISTRO_MODIFICADO)
            s = "|=EVENTO:REGISTRO_MODIFICADO";
        else if(evento == CONEXION_TERMINADA)
            s = "|=EVENTO:CONEXION_TERMINADA";
        else if(evento == DETENER_SERVICIO)
            s = "|=EVENTO:DETENER_SERVICIO";
        else if(evento == ECO)
            s = "|=EVENTO:ECO";
        else if(evento == ERROR)
            s = "|=EVENTO:ERROR";
        return s;
    }
}
