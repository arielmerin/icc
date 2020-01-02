package mx.unam.ciencias.icc;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

/**
 * Clase abstracta para bases de datos genéricas. Provee métodos para agregar y
 * eliminar registros, y para guardarse y cargarse de una entrada y salida
 * dados.
 *
 * Las clases que extiendan a BaseDeDatos deben implementar el método {@link
 * #creaRegistro}, que crea un registro genérico en blanco. También deben
 * implementar el método {@link #buscaRegistros} para hacer consultas en la base
 * de datos. El método {@link #buscaRegistros} recibe una instancia de la
 * enumeración genérica para saber en qué campo deben realizar la búsqueda.
 *
 * @param <R> El tipo de los registros, que deben implementar la interfaz {@link
 * Registro}.
 * @param <C> El tipo de los campos de los registros, que debe ser una
 * enumeración {@link Enum}.
 */
public abstract class BaseDeDatos<R extends Registro, C extends Enum> {

    /** Lista de registros en la base de datos. */
    protected Lista<R> registros;

    /**
     * Constructor único.
     */
    public BaseDeDatos() {
        this.registros = new Lista<R>();
    }

    /**
     * Regresa el número de registros en la base de datos.
     * @return el número de registros en la base de datos.
     */
    public int getNumRegistros() {
        return registros.getLongitud();
    }

    /**
     * Regresa una lista con los registros en la base de datos. Modificar esta
     * lista no cambia a la información en la base de datos.
     * @return una lista con los registros en la base de datos.
     */
    public Lista<R> getRegistros() {
        return registros.copia();
    }

    /**
     * Agrega el registro recibido a la base de datos.
     * @param registro el registro que hay que agregar a la base de datos.
     */
    public void agregaRegistro(R registro) {
        registros.agregaFinal(registro);
    }

    /**
     * Elimina el registro recibido de la base de datos.
     * @param registro el registro que hay que eliminar de la base de datos.
     */
    public void eliminaRegistro(R registro) {
        registros.elimina(registro);
    }

    /**
     * Limpia la base de datos.
     */
    public void limpia() {
        registros.limpia();
    }

    /**
     * Guarda todos los registros en la base de datos en la salida recibida.
     * @param out la salida donde hay que guardar los registos.
     * @throws IOException si ocurre un error de entrada/salida.
     */
    public void guarda(BufferedWriter out) throws IOException {
        try {
            Lista<R>.Nodo nodo = registros.getCabeza();
            while(nodo != null) {
                R registro = nodo.get();
                registro.guarda(out);
                nodo = nodo.getSiguiente();
            }
        } catch(IOException ioe) {
            throw new IOException("Error de entrada/salida.");
        }
    }

    /**
     * Carga los registros de la entrada recibida en la base de datos. Si antes
     * de llamar el método había registros en la base de datos, estos son
     * eliminados.
     * @param in la entrada de donde hay que cargar los registos.
     * @throws IOException si ocurre un error de entrada/salida.
     */
    public void carga(BufferedReader in) throws IOException {
        limpia();
        try {
            do {
                R registro = creaRegistro();
                if(!(registro.carga(in)))
                    break;
                agregaRegistro(registro);
            } while(true);
        } catch(IOException ioe) {
            throw new IOException("Error de entrada/salida.");
        }
    }

    /**
     * Crea un registro en blanco.
     * @return un registro en blanco.
     */
    public abstract R creaRegistro();

    /**
     * Busca registros por un campo específico.
     * @param campo el campo del registro por el cuál buscar.
     * @param texto el texto a buscar.
     * @return una lista con los registros tales que en el campo especificado
     *         contienen el texto recibido.
     * @throws IllegalArgumentException si el campo no es válido.
     */
    public abstract Lista<R> buscaRegistros(C campo, String texto);
}
