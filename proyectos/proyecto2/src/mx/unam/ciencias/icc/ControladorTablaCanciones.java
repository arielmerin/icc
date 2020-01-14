package mx.unam.ciencias.icc;

import javafx.collections.ListChangeListener.Change;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;

/**
 * Clase para el controlador de la tabla para mostrar la base de datos.
 */
public class ControladorTablaCanciones {

    /* La tabla. */
    @FXML private TableView<Cancion> tabla;

    /* La columna del nombre. */
    @FXML private TableColumn<Cancion, String> columnaNombre;
    /* La columna del número de cuenta. */
    @FXML private TableColumn<Cancion, Number> columnaPosicion;
    /* La columna del promedio. */
    @FXML private TableColumn<Cancion, Number> columnaDuracion;
    /* La columna de la edad. */
    @FXML private TableColumn<Cancion, String> columnaArtista;
    /* La columna de la edad. */
    @FXML private TableColumn<Cancion, String> columnaAlbum;

    /* El modelo de la selección. */
    TableView.TableViewSelectionModel<Cancion> modeloSeleccion;
    /* La selección. */
    private ObservableList<TablePosition> seleccion;
    /* Lista de escuchas de selección. */
    private Lista<EscuchaSeleccion> escuchas;
    /* Los renglones en la tabla. */
    private ObservableList<Cancion> renglones;

    /* Inicializa el controlador. */
    @FXML private void initialize() {
        renglones = tabla.getItems();
        modeloSeleccion = tabla.getSelectionModel();
        modeloSeleccion.setSelectionMode(SelectionMode.MULTIPLE);
        seleccion = modeloSeleccion.getSelectedCells();
        ListChangeListener<TablePosition> lcl = c -> cambioEnSeleccion();
        seleccion.addListener(lcl);
        columnaNombre.setCellValueFactory(c -> c.getValue().nombreProperty());
        columnaPosicion.setCellValueFactory(c -> c.getValue().posicionProperty());
        columnaDuracion.setCellValueFactory(c -> c.getValue().duracionProperty());
        columnaArtista.setCellValueFactory(c -> c.getValue().artistaProperty());
        columnaAlbum.setCellValueFactory(c -> c.getValue().albumProperty());
        escuchas = new Lista<EscuchaSeleccion>();
    }

    /* Notifica a los escuchas que hubo un cambio en la selección. */
    private void cambioEnSeleccion() {
        for (EscuchaSeleccion escucha : escuchas)
            escucha.renglonesSeleccionados(seleccion.size());
    }

    /**
     * Limpia la tabla.
     */
    public void limpiaTabla() {
        renglones.clear();
    }

    /**
     * Agrega un renglón a la tabla.
     * @param cancion el renglón a agregar.
     */
    public void agregaRenglon(Cancion cancion) {
        renglones.add(cancion);
        tabla.sort();
    }

    /**
     * Elimina un renglón de la tabla.
     * @param cancion el renglón a eliminar.
     */
    public void eliminaRenglon(Cancion cancion) {
        renglones.remove(cancion);
        tabla.sort();
    }

    /**
     * Selecciona renglones de la tabla.
     * @param Cancion los renglones a seleccionar.
     */
    public void seleccionaRenglones(Lista<Cancion> canciones) {
        modeloSeleccion.clearSelection();
        for (Cancion cancion : canciones)
            modeloSeleccion.select(cancion);
    }

    /**
     * Regresa una lista con los registros seleccionados en la tabla.
     * @return una lista con los registros seleccionados en la tabla.
     */
    public Lista<Cancion> getSeleccion() {
        Lista<Cancion> seleccionados = new Lista<Cancion>();
        for (TablePosition tp : seleccion) {
            int r = tp.getRow();
            seleccionados.agregaFinal(renglones.get(r));
        }
        return seleccionados;
    }

    /**
     * Regresa el estudiante seleccionado en la tabla.
     * @return el estudiante seleccionado en la tabla.
     */
    public Cancion getRenglonSeleccionado() {
        int r = seleccion.get(0).getRow();
        return renglones.get(r);
    }

    /**
     * Agrega un escucha de selección.
     * @param escucha el escucha a agregar.
     */
    public void agregaEscuchaSeleccion(EscuchaSeleccion escucha) {
        escuchas.agregaFinal(escucha);
    }

    /**
     * Fuerza un reordenamiento de la tabla.
     */
    public void reordena() {
        tabla.sort();
    }

    /**
     * Enfoca la tabla.
     */
    public void enfocaTabla() {
        tabla.requestFocus();
    }
}
