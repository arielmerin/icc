package mx.unam.ciencias.icc;

/**
 * Enumeración para acciones sobre registros.
 */
public enum AccionRegistro {

    /** Acción de agregar.*/
    AGREGAR,

    /** Acción de eliminar.*/
    ELIMINAR;

    /**
     * Regresa el verbo asociado con la acción.
     */
    @Override public String toString() {
        switch(this){
            case AGREGAR:
                return "Agregar";
            case ELIMINAR:
                return "Eliminar";
            default:
                return null;
        }
    }
}
