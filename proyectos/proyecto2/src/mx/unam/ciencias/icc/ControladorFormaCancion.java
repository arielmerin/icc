package mx.unam.ciencias.icc;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

/**
 * Clase para el controlador del contenido del diálogo para editar y crear
 * canciones.
 */
public class ControladorFormaCancion extends ControladorForma {

    /* La entrada verificable para el nombre. */
    @FXML private EntradaVerificable entradaNombre;
    /* La entrada verificable para la posición. */
    @FXML private EntradaVerificable entradaPosicion;
    /* La entrada verificable para la duración. */
    @FXML private EntradaVerificable entradaDuracion;
    /* La entrada verificable para el artista */
    @FXML private EntradaVerificable entradaArtista;
    /* La entrada verificable para el álbum */
    @FXML private EntradaVerificable entradaAlbum;

    /* El valor del nombre. */
    private String nombre;
    /* El valor de la posición. */
    private int posicion;
    /* El valor de la duración. */
    private double duracion;
    /* El valor del artista. */
    private String artista;
    /* El valor del álbum. */
    private String album;

    /* La canción creada o editada. */
    private Cancion cancion;

    /* Inicializa el estado de la forma. */
    @FXML private void initialize() {
        entradaNombre.setVerificador(n -> verificaNombre(n));
        entradaPosicion.setVerificador(p -> verificaPosicion(p));
        entradaDuracion.setVerificador(d -> verificaDuracion(d));
        entradaArtista.setVerificador(a -> verificaArtista(a));
        entradaAlbum.setVerificador(l -> verificaAlbum(l));

        entradaNombre.textProperty().addListener((o, v, n) -> verificaCancion());
        entradaPosicion.textProperty().addListener((o, v, n) -> verificaCancion());
        entradaDuracion.textProperty().addListener((o, v, n) -> verificaCancion());
        entradaArtista.textProperty().addListener((o, v, n) -> verificaCancion());
        entradaAlbum.textProperty().addListener((o, v, n) -> verificaCancion());
    }

    /* Manejador para cuando se activa el botón aceptar. */
    @FXML private void aceptar(ActionEvent evento) {
        actualizaCancion();
        aceptado = true;
        escenario.close();
    }

    /**
     * Define la canción del diálogo.
     * @param cancion la nueva canción del diálogo.
     */
    public void setCancion(Cancion cancion) {
        this.cancion = cancion;
        if (cancion == null)
            return;
        this.cancion = new Cancion(null, 0, 0.0, null, null);
        this.cancion.actualiza(cancion);
        entradaNombre.setText(cancion.getNombre());
        entradaPosicion.setText(String.format("%03d", cancion.getPosicion()));
        entradaDuracion.setText(String.format("%2.2f", cancion.getDuracion()));
        entradaArtista.setText(cancion.getArtista());
        entradaAlbum.setText(cancion.getAlbum());
    }

    /* Verifica que los cinco campos sean válidos. */
    private void verificaCancion() {
        boolean n = entradaNombre.esValida();
        boolean p = entradaPosicion.esValida();
        boolean d = entradaDuracion.esValida();
        boolean a = entradaArtista.esValida();
        boolean l = entradaAlbum.esValida();
        botonAceptar.setDisable(!n || !p || !d || !a || !l);
    }

    /* Verifica que el nombre sea válido. */
    private boolean verificaNombre(String n) {
        if (n == null || n.isEmpty())
            return false;
        nombre = n;
        return true;
    }

    /* Verifica que la posición sea válida. */
    private boolean verificaPosicion(String p) {
        if (p == null || p.isEmpty())
            return false;
        try {
            posicion = Integer.parseInt(p);
        } catch (NumberFormatException nfe) {
            return false;
        }
        if (posicion < 0)
            return false;
        return true;
    }

    /* Verifica que la duración sea válida. */
    private boolean verificaDuracion(String d) {
        if (d == null || d.isEmpty())
            return false;
        try {
            duracion = Double.parseDouble(d);
        } catch (NumberFormatException nfe) {
            return false;
        }
        if (duracion < 0.0)
            return false;
        return true;
    }

    /* Verifica que el artista sea válido. */
    private boolean verificaArtista(String a) {
        if (a == null || a.isEmpty())
            return false;
        artista = a;
        return true;
    }

    /* Verifica que el álbum sea válido. */
    private boolean verificaAlbum(String l) {
        if (l == null || l.isEmpty())
          return false;
        album = l;
        return true;
    }

    /* Actualiza la canción, o la crea si no existe. */
    private void actualizaCancion() {
        if (cancion != null) {
            cancion.setNombre(nombre);
            cancion.setPosicion(posicion);
            cancion.setDuracion(duracion);
            cancion.setArtista(artista);
            cancion.setAlbum(album);
        } else {
            cancion = new Cancion(nombre, posicion, duracion, artista, album);
        }
    }

    /**
     * Regresa la canción del diálogo.
     * @return la canción del diálogo.
     */
    public Cancion getCancion() {
        return cancion;
    }

    /**
     * Define el verbo del botón de aceptar.
     * @param verbo el nuevo verbo del botón de aceptar.
     */
    public void setVerbo(String verbo) {
        botonAceptar.setText(verbo);
    }

    /**
     * Define el foco incial del diálogo.
     */
    @Override public void defineFoco() {
        entradaNombre.requestFocus();
    }
}
