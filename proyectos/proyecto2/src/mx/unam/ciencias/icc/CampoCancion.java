package mx.unam.ciencias.icc;

/**
 * Enumeración para los campos de un {@link Cancion}.
 */
public enum CampoCancion {

    /** El nombre de la canción. */
    NOMBRE,
    /** La posición en el ranking. */
    POSICION,
    /** La duración de la canción. */
    DURACION,
    /** El nombre del artista. */
    ARTISTA,
    /** El nombre del álbum. */
    ALBUM;

    /**
     * Regresa una representación en cadena del campo para ser usada en
     * interfaces gráficas.
     * @return una representación en cadena del campo.
     */
    @Override public String toString() {
        switch(this){
          case NOMBRE:
            return "Nombre";
          case POSICION:
            return "Posición";
          case DURACION:
            return "Duración";
          case ARTISTA:
            return "Artista";
          case ALBUM:
            return "Álbum";
          default:
            return null;
        }
    }
}
