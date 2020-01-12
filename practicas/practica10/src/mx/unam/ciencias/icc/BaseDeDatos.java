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
 * Las modificaciones a la base de datos son notificadas a los escuchas {@link
 * EscuchaBaseDeDatos}.
 *
 * @param <R> El tipo de los registros, que deben implementar la interfaz {@link
 * Registro}.
 * @param <C> El tipo de los campos de los registros, que debe ser una
 * enumeración {@link Enum}.
 */
public abstract class BaseDeDatos<R extends Registro, C extends Enum> {

    /** Lista de registros en la base de datos. */
    protected Lista<R> registros;
    /** Lista de escuchas de la base de datos. */
    protected Lista<EscuchaBaseDeDatos<R>> escuchas;

    /**
     * Constructor único.
     */
    public BaseDeDatos() {
        this.registros = new Lista<R>();
        this.escuchas = new Lista<EscuchaBaseDeDatos<R>>();
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
     * Agrega el registro recibido a la base de datos. Los escuchas son
     * notificados con {@link EscuchaBaseDeDatos#baseDeDatosModificada} con el
     * evento {@link EventoBaseDeDatos#REGISTRO_AGREGADO}.
     * @param registro el registro que hay que agregar a la base de datos.
     */
    public void agregaRegistro(R registro) {
        registros.agregaFinal(registro);
        for(EscuchaBaseDeDatos<R> escucha : escuchas)
            escucha.baseDeDatosModificada(EventoBaseDeDatos.REGISTRO_AGREGADO, 
                                          registro, null);
    }

    /**
     * Elimina el registro recibido de la base de datos. Los escuchas son
     * notificados con {@link EscuchaBaseDeDatos#baseDeDatosModificada} con el
     * evento {@link EventoBaseDeDatos#REGISTRO_ELIMINADO}.
     * @param registro el registro que hay que eliminar de la base de datos.
     */
    public void eliminaRegistro(R registro) {
        registros.elimina(registro);
        for(EscuchaBaseDeDatos<R> escucha: escuchas)
            escucha.baseDeDatosModificada(EventoBaseDeDatos.REGISTRO_ELIMINADO, 
                                          registro, null);
    }

    /**
     * Modifica el primer registro en la base de datos para que sea idéntico al
     * segundo. Si el primer registro no está en la base de datos, ésta no es
     * modificada. Los escuchas son notificados con {@link
     * EscuchaBaseDeDatos#baseDeDatosModificada} con el evento {@link
     * EventoBaseDeDatos#REGISTRO_MODIFICADO} <em>antes</em> de modificar el
     * registro.
     * @param registro1 el registro a modificar.
     * @param registro2 el registro con los nuevos valores.
     * @throws IllegalArgumentException si registro1 o registro2 son
     *         <code>null</code>.
     */
    public void modificaRegistro(R registro1, R registro2) {
        if(registro1 == null && registro2 == null)
            throw new IllegalArgumentException("Registros nulos.");
        if(registros.contiene(registro1) == false)
            return;
        else {
            int i = registros.indiceDe(registro1);
            R r = registros.get(i);
            for(EscuchaBaseDeDatos<R> escucha : escuchas)
            escucha.baseDeDatosModificada(EventoBaseDeDatos.REGISTRO_MODIFICADO, 
                                          registro1, registro2);
            r.actualiza(registro2);
        }
    }

    /**
     * Limpia la base de datos. Los escuchas son notificados con {@link
     * EscuchaBaseDeDatos#baseDeDatosModificada} con el evento {@link
     * EventoBaseDeDatos#BASE_LIMPIADA}
     */
    public void limpia() {
        registros.limpia();
        for(EscuchaBaseDeDatos<R> escucha : escuchas)
            escucha.baseDeDatosModificada(EventoBaseDeDatos.BASE_LIMPIADA, 
                                          null, null);
    }

    /**
     * Guarda todos los registros en la base de datos en la salida recibida.
     * @param out la salida donde hay que guardar los registos.
     * @throws IOException si ocurre un error de entrada/salida.
     */
    public void guarda(BufferedWriter out) throws IOException {
        try {
            for(R r : registros)
                r.guarda(out);
        } catch (IOException ioe){
            throw new IOException("Error de entrada/salida.");
        }
    }

    /**
     * Carga los registros de la entrada recibida en la base de datos. Si antes
     * de llamar el método había registros en la base de datos, estos son
     * eliminados. Los escuchas son notificados con {@link
     * EscuchaBaseDeDatos#baseDeDatosModificada} con el evento {@link
     * EventoBaseDeDatos#BASE_LIMPIADA}, y por cada registro cargado con {@link
     * EscuchaBaseDeDatos#baseDeDatosModificada} con el evento {@link
     * EventoBaseDeDatos#REGISTRO_AGREGADO}.
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

    /**
     * Agrega un escucha a la base de datos.
     * @param escucha el escucha a agregar.
     */
    public void agregaEscucha(EscuchaBaseDeDatos<R> escucha) {
        escuchas.agregaFinal(escucha);
    }
}
