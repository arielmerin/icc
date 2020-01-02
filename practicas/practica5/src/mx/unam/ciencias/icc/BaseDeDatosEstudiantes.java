package mx.unam.ciencias.icc;

/**
 * Clase para bases de datos de estudiantes.
 */
public class BaseDeDatosEstudiantes extends BaseDeDatos {

    /**
     * Crea un estudiante en blanco.
     * @return un estudiante en blanco.
     */
    @Override public Registro creaRegistro() {
        return new Estudiante(null, 0, 0.0, 0);
    }

    /**
     * Busca estudiantes por un campo específico.
     * @param campo el campo del registro por el cuál buscar.
     * @param texto el texto a buscar.
     * @return una lista con los estudiantes tales que en el campo especificado
     *         contienen el texto recibido, o una lista vacía si el texto es
     *         <code>null</code> o la cadena vacía.
     * @throws IllegalArgumentException si el campo no es instancia de
     *         {@link CampoEstudiante}.
     */
    @Override public Lista buscaRegistros(Enum campo, String texto) {
        Lista lista = new Lista();
        if(campo instanceof CampoEstudiante) {
            CampoEstudiante camp = (CampoEstudiante)campo;

            switch(camp) {
                case NOMBRE:
                    Lista.Nodo nodo1 = registros.getCabeza();
                    while(nodo1 != null) {
                        Estudiante estudiante = (Estudiante)nodo1.get();
                        if(estudiante.getNombre().matches(".*" + texto + ".*"))
                            lista.agregaFinal(estudiante);
                        nodo1 = nodo1.getSiguiente();
                    }

                    break;
                
                case CUENTA:
                    Lista.Nodo nodo2 = registros.getCabeza();
                    while(nodo2 != null) {
                        Estudiante estudiante = (Estudiante)nodo2.get();
                        String s = String.valueOf(estudiante.getCuenta());
                        if(s.matches(".*" + texto + ".*"))
                            lista.agregaFinal(estudiante);
                        nodo2 = nodo2.getSiguiente();
                    }

                    break;

                case PROMEDIO:
                    Lista.Nodo nodo3 = registros.getCabeza();
                    while(nodo3 != null) {
                        Estudiante estudiante = (Estudiante)nodo3.get();
                        String s = String.format("%2.2f", estudiante.getPromedio());
                        if(s.matches(".*" + texto + ".*"))
                            lista.agregaFinal(estudiante);
                        nodo3 = nodo3.getSiguiente();
                    }

                    break;

                case EDAD:
                    Lista.Nodo nodo4 = registros.getCabeza();
                    while(nodo4 != null) {
                        Estudiante estudiante = (Estudiante)nodo4.get();
                        String s = String.valueOf(estudiante.getEdad());
                        if(s.matches(".*" + texto + ".*"))
                            lista.agregaFinal(estudiante);
                        nodo4 = nodo4.getSiguiente();
                    }

                    break;
            }

            return lista;
        }

        throw new IllegalArgumentException("El campo no es instancia de " + 
                                           "CampoEstudiante");
    }
}
