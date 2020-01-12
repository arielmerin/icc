package mx.unam.ciencias.icc;

/**
 * Enumeración para los campos de una {@link Cancion}.
 */
public enum CampoCancion {

    /** El nombre de la canción. */
    NOMBRE,
    /** El nombre del artista. */
    ARTISTA,
    /** El nombre del álbum al que pertenece la canción. */
    ALBUM,
    /** La duración de la canción. */
    DURACION,
    /** La cantidad de reproducciones. */
    REPRODUCCIONES;

    /**
     * Regresa una representación en cadena del campo para ser usada en
     * interfaces gráficas.
     * @return una representación en cadena del campo.
     */
    @Override public String toString() {
        switch(this){
            case NOMBRE:
                return "Nombre";
            case ARTISTA:
                return "Artista";
            case ALBUM:
                return "Álbum";
            case DURACION:
                return "Duración";
            case REPRODUCCIONES:
                return "Reproducciones";
            default:
                return null;
        }
    }
}
