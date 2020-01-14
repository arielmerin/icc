package mx.unam.ciencias.icc;

/**
 * Clase para bases de datos de canciones.
 */
public class BaseDeDatosCanciones
      extends BaseDeDatos<Cancion, CampoCancion> {

    /**
     * Crea una cancion en blanco.
     * @return una cancion en blanco.
     */
    @Override public Cancion creaRegistro() {
        return new Cancion(null, 0, 0.0, null, null);
    }

    /**
     * Busca canciones por un campo específico.
     * @param campo el campo del registro por el cuál buscar.
     * @param texto el texto a buscar.
     * @return una lista con las canciones tales que en el campo especificado
     *         contienen el texto recibido.
     * @throws IllegalArgumentException si el campo no es instancia de
     *         {@link CampoCancion}.
     */
    @Override public Lista<Cancion> buscaRegistros(CampoCancion campo,
                                                       String texto) {
      Lista<Cancion> lista = new Lista<Cancion>();
        String e = "";
        if ((texto == null) || (texto == ""))
            return lista;
        
        for (Cancion elemento: registros){
            switch(campo) {
                case NOMBRE:
                    e = elemento.getNombre();
                    break;
                case POSICION:    
                    e = String.valueOf(elemento.getPosicion());
                    break;
                case DURACION:
                    e = String.format("%2.2f", elemento.getDuracion());
                    break;
                case ARTISTA:
                    e = elemento.getArtista();
                    break;
                case ALBUM:
                    e = elemento.getAlbum();    
                default:    
                    throw new IllegalArgumentException("Problemas al buscar registros");
            }
            
            if (e.indexOf(texto)!= -1)
                lista.agregaFinal(elemento);
        }
        
        return lista;
    }
}
