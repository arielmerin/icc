package mx.unam.ciencias.icc;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * Práctica 9: Interfaces gráficas.
 */
public class Practica9 extends Application {

    /* Vista de la interfaz principal. */
    private static final String INTERFAZ_PRINCIPAL_FXML =
        "fxml/interfaz-principal.fxml";
    /* Vista de la tabla de estudiantes. */
    private static final String TABLA_ESTUDIANTES_FXML =
        "fxml/tabla-estudiantes.fxml";
    /* Icono de la Facultad de Ciencias. */
    private static final String ICONO_CIENCIAS =
        "icons/ciencias.png";

    /**
     * Inicia la aplicación.
     * @param escenario la ventana principal de la aplicación.
     * @throws Exception si algo sale mal. Literalmente así está definido.
     */
    @Override public void start(Stage escenario) throws Exception {
        ClassLoader cl = getClass().getClassLoader();
        String url = cl.getResource(ICONO_CIENCIAS).toString();
        escenario.getIcons().add(new Image(url));
        escenario.setTitle("Administrador de Estudiantes");

        FXMLLoader cargador;
        cargador = new FXMLLoader(cl.getResource(TABLA_ESTUDIANTES_FXML));
        GridPane tablaEstudiante = (GridPane)cargador.load();
        ControladorTablaEstudiantes controlTE = cargador.getController();

        cargador = new FXMLLoader(cl.getResource(INTERFAZ_PRINCIPAL_FXML));
        BorderPane interfazPrincipal = (BorderPane)cargador.load();
        interfazPrincipal.setCenter(tablaEstudiante);
        ControladorInterfazPrincipal controlIP = cargador.getController();
        controlIP.setEscenario(escenario);
        controlIP.setControladorTablaEstudiantes(controlTE);

        Scene escena = new Scene(interfazPrincipal);
        escenario.setScene(escena);
        escenario.setOnCloseRequest(e -> controlIP.salir(null));
        escenario.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
