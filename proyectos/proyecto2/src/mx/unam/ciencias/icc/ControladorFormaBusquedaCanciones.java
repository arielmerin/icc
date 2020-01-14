package mx.unam.ciencias.icc;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

/**
 * Clase para el controlador del contenido del diálogo para buscar canciones.
 */
public class ControladorFormaBusquedaCanciones extends ControladorForma {

    /* El combo del campo. */
    @FXML private ComboBox<CampoCancion> opcionesCampo;
    /* El campo de texto para el valor. */
    @FXML private TextField entradaValor;

    /* Manejador para cuando se activa el botón aceptar. */
    @FXML private void aceptar(ActionEvent evento) {
        aceptado = entradaValor.getText().length() > 0;
        escenario.close();
    }

    /**
     * Regresa el campo seleccionado.
     * @return el campo seleccionado.
     */
    public CampoCancion getCampo() {
        return opcionesCampo.getValue();

    }

    /**
     * Regresa el valor ingresado.
     * @return el valor ingresado.
     */
    public String getValor() {
        return entradaValor.getText();
    }

    /**
     * Define el foco incial del diálogo.
     */
    @Override public void defineFoco() {
        entradaValor.requestFocus();
    }
}
