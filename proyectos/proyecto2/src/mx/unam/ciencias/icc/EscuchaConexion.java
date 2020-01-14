package mx.unam.ciencias.icc;

/**
 * Escucha para conexiones.
 */
@FunctionalInterface
public interface EscuchaConexion<R extends Registro> {

    /**
     * Notifica de un evento que ocurrió en la comunicación entre el cliente y
     * el servidor.
     * @param conexion la conexión donde ocurrió el evento.
     * @param evento el evento ocurrido.
     */
    public void eventoOcurrido(Conexion<R> conexion, EventoConexion evento);
}
