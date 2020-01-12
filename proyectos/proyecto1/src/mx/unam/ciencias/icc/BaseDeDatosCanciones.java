package mx.unam.ciencias.icc;

/**
 * Clase para bases de datos de canciones.
 */
public class BaseDeDatosCanciones extends BaseDeDatos {

    /**
     * Crea una canción en blanco.
     * @return una canción en blanco.
     */
    @Override public Registro creaRegistro() {
        return new Cancion(null, null, null, 0.0, 0);
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
    @Override public Lista buscaRegistros(Enum campo, String texto) {
        Lista lista = new Lista();

        if(campo instanceof CampoCancion){
            CampoCancion camp = (CampoCancion)campo;

            switch(camp) {
                case NOMBRE:
                    Lista.Nodo nodo1 = registros.getCabeza();
                    while(nodo1 != null) {
                        Cancion cancion = (Cancion)nodo1.get();
                        if(cancion.getNombre().matches(".*" + texto + ".*"))
                            lista.agregaFinal(cancion);
                        nodo1 = nodo1.getSiguiente();
                    }

                    break;

                case ARTISTA:
                    Lista.Nodo nodo2 = registros.getCabeza();
                    while(nodo2 != null) {
                        Cancion cancion = (Cancion)nodo2.get();
                        if(cancion.getArtista().matches(".*" + texto + ".*"))
                            lista.agregaFinal(cancion);
                        nodo2 = nodo2.getSiguiente();
                    }

                    break;

                case ALBUM:
                    Lista.Nodo nodo3 = registros.getCabeza();
                    while(nodo3 != null) {
                        Cancion cancion = (Cancion)nodo3.get();
                        if(cancion.getAlbum().matches(".*" + texto + ".*"))
                            lista.agregaFinal(cancion);
                        nodo3 = nodo3.getSiguiente();
                    }

                    break;

                case DURACION:
                    Lista.Nodo nodo4 = registros.getCabeza();
                    while(nodo4 != null) {
                        Cancion cancion = (Cancion)nodo4.get();
                        String s = String.format("%2.2f", cancion.getDuracion());
                        if(s.matches(".*" + texto + ".*"))
                            lista.agregaFinal(cancion);
                        nodo4 = nodo4.getSiguiente();
                    }

                    break;
                    
                case REPRODUCCIONES:
                    Lista.Nodo nodo5 = registros.getCabeza();
                    while(nodo5 != null) {
                        Cancion cancion = (Cancion)nodo5.get();
                        String s = String.valueOf(cancion.getReproducciones());
                        if(s.matches(".*" + texto + ".*"))
                            lista.agregaFinal(cancion);
                        nodo5 = nodo5.getSiguiente();
                    }

                    break;
            }

            return lista;
        }

        throw new IllegalArgumentException("El campo no es instancia de " +
                                           "CampoCancion.");
    }
}
