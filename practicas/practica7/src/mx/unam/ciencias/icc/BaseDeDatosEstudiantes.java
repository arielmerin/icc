package mx.unam.ciencias.icc;

/**
 * Clase para bases de datos de estudiantes.
 */
public class BaseDeDatosEstudiantes
    extends BaseDeDatos<Estudiante, CampoEstudiante> {

    /**
     * Crea un estudiante en blanco.
     * @return un estudiante en blanco.
     */
    @Override public Estudiante creaRegistro() {
        return new Estudiante(null, 0, 0.0, 0);
    }

    /**
     * Busca estudiantes por un campo específico.
     * @param campo el campo del registro por el cuál buscar.
     * @param texto el texto a buscar.
     * @return una lista con los estudiantes tales que en el campo especificado
     *         contienen el texto recibido.
     */
    @Override public Lista<Estudiante> buscaRegistros(CampoEstudiante campo,
                                                      String texto) {
        Lista<Estudiante> lista = new Lista<Estudiante>();
        String s = "";
        if((texto == null) || (texto == ""))
            return lista;
        
        for(Estudiante estudiante: registros) {
            switch(campo){
                case NOMBRE:
                    s = estudiante.getNombre();
                    break;
                case CUENTA:    
                    s = String.valueOf(estudiante.getCuenta());
                    break;
                case PROMEDIO:
                    s = String.format("%2.2f", estudiante.getPromedio());
                    break;
                case EDAD:
                    s = String.valueOf(estudiante.getEdad());
                    break;
            }
            
            if(s.indexOf(texto)!= -1)
                lista.agregaFinal(estudiante);
        }
        
        return lista;
    }
}