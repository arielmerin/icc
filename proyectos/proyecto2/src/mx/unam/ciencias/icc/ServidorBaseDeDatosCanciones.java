package mx.unam.ciencias.icc;

import java.io.IOException;

/**
 * Clase para servidores de bases de datos de canciones
 */
public class ServidorBaseDeDatosCanciones
    extends ServidorBaseDeDatos<Cancion, CampoCancion> {

    /**
     * Construye un servidor de base de datos de canciones.
     * @param puerto el puerto dónde escuchar por conexiones.
     * @param archivo el archivo en el disco del cual cargar/guardar la base de
     *                datos.
     * @throws IOException si ocurre un error de entrada o salida.
     */
    public ServidorBaseDeDatosCanciones(int puerto, String archivo)
        throws IOException {
            super(puerto,archivo);
    }

    /**
     * Crea una base de datos de canciones.
     * @return una base de datos de cancioes.
     */
    @Override public BaseDeDatos<Cancion, CampoCancion> creaBaseDeDatos() {
        return new BaseDeDatosCanciones();
    }
}
