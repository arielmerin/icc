package mx.unam.ciencias.icc;

/**
 * Interfaz funcional para verificar cadenas de texto.
 */
@FunctionalInterface
public interface Verificador {

    /**
     * Verifica la cadena de texto.
     * @param texto la cadena de texto.
     * @return <code>true</code> si la cadena de texto es válida,
     *         <code>false</code> en otro caso.
     */
    public boolean verifica(String texto);
}
