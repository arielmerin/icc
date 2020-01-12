package mx.unam.ciencias.icc;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Optional;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Clase para el controlador de la ventana principal de la aplicación.
 */
public class ControladorInterfazPrincipal {

    /* Vista de la forma para conectarse. */
    private static final String CONECTAR_FXML =
        "fxml/forma-conectar.fxml";
    /* Vista de la forma para realizar búsquedas de estudiantes. */
    private static final String BUSQUEDA_ESTUDIANTES_FXML =
        "fxml/forma-busqueda-estudiantes.fxml";
    /* Vista de la forma para agregar/editar estudiantes. */
    private static final String ESTUDIANTE_FXML =
        "fxml/forma-estudiante.fxml";

    /* Opción de menu para conectar. */
    @FXML private MenuItem menuConectar;
    /* Opción de menu para desconectar. */
    @FXML private MenuItem menuDesconectar;
    /* Opción de menu para agregar. */
    @FXML private MenuItem menuAgregar;
    /* Opción de menu para editar. */
    @FXML private MenuItem menuEditar;
    /* Opción de menu para eliminar. */
    @FXML private MenuItem menuEliminar;
    /* Opción de menu para buscar. */
    @FXML private MenuItem menuBuscar;
    /* El botón de agregar. */
    @FXML private Button botonAgregar;
    /* El botón de editar. */
    @FXML private Button botonEditar;
    /* El botón de eliminar. */
    @FXML private Button botonEliminar;
    /* El botón de buscar. */
    @FXML private Button botonBuscar;

    /* La ventana. */
    private Stage escenario;
    /* El controlador de tabla. */
    private ControladorTablaEstudiantes controlTE;
    /* La base de datos. */
    private BaseDeDatosEstudiantes bdd;
    /* La conexión del cliente. */
    private Conexion<Estudiante> conexion;
    /* Si hay o no conexión. */
    private boolean conectado;
    /* Si ignoramos o no eventos de la base de datos. */
    private boolean ignorar;
    /* Número de estudiantes seleccionados. */
    private int seleccionados;

    /* Inicializa el controlador. */
    @FXML private void initialize() {
        setSeleccionados(0);
        setConectado(false);
        setBaseDeDatos(new BaseDeDatosEstudiantes());
    }

    /* Conecta el cliente con el servidor. */
    @FXML private void conectar(ActionEvent evento) {
        if (conectado)
            return;

        String servidor = null;
        int puerto = -1;

        try {
            FXMLLoader cargador = new FXMLLoader();
            ClassLoader cl = getClass().getClassLoader();
            cargador.setLocation(cl.getResource(CONECTAR_FXML));
            AnchorPane pagina = (AnchorPane)cargador.load();

            Stage escenario = new Stage();
            escenario.initOwner(this.escenario);
            escenario.initModality(Modality.WINDOW_MODAL);
            escenario.setTitle("Conectar a servidor");
            Scene escena = new Scene(pagina);
            escenario.setScene(escena);

            ControladorFormaConectar controlador = cargador.getController();
            controlador.setEscenario(escenario);

            escenario.setOnShown(w -> controlador.defineFoco());
            escenario.setResizable(false);
            escenario.showAndWait();
            controlTE.enfocaTabla();
            if (!controlador.isAceptado())
                return;

            servidor = controlador.getServidor();
            puerto = controlador.getPuerto();
        } catch (IOException | IllegalStateException e) {
            String mensaje =
                String.format("Ocurrió un error al tratar de " +
                              "cargar el diálogo '%s'.", CONECTAR_FXML);
            dialogoError("Error al cargar interfaz", mensaje);
            return;
        }

        try {
            setBaseDeDatos(new BaseDeDatosEstudiantes());
            Socket enchufe = new Socket(servidor, puerto);
            conexion = new Conexion<Estudiante>(enchufe);
            conexion.enviaEvento(EventoConexion.BASE_DE_DATOS);
            conexion.agregaEscucha((c, e) -> manejaEventoConexion(c, e));
            new Thread(() -> conexion.manejaEventos()).start();
        } catch (IOException ioe) {
            String mensaje =
                String.format("Ocurrió un error al tratar de " +
                              "conectarnos a %s:%d.\n", servidor, puerto);
            dialogoError("Error al establecer conexión", mensaje);
            return;
        }
        setConectado(true);
    }

    /* Desconecta el cliente del servidor. */
    @FXML private void desconectar(ActionEvent evento) {
        if (!conectado)
            return;
        setConectado(false);
        conexion.desconecta();
        conexion = null;
        bdd.limpia();
    }

    /* Cambia la interfaz gráfica dependiendo de hay o no conexión. */
    public void setConectado(boolean conectado) {
        this.conectado = conectado;
        menuConectar.setDisable(conectado);
        menuDesconectar.setDisable(!conectado);
        menuAgregar.setDisable(!conectado);
        menuBuscar.setDisable(!conectado);
        botonAgregar.setDisable(!conectado);
        botonBuscar.setDisable(!conectado);
    }

    /**
     * Termina el programa.
     * @param evento el evento que generó la acción.
     */
    @FXML public void salir(ActionEvent evento) {
        desconectar(evento);
        Platform.exit();
    }

    /* Agrega un nuevo estudiante. */
    @FXML private void agregaEstudiante(ActionEvent evento) {
        ControladorFormaEstudiante controlador =
            construyeDialogoEstudiante("Agregar estudiante", null);
        if (controlador == null)
            return;
        controlador.setVerbo("Agregar");
        controlador.getEscenario().showAndWait();
        controlTE.enfocaTabla();
        if (!controlador.isAceptado())
            return;
        bdd.agregaRegistro(controlador.getEstudiante());
    }

    /* Edita un estudiante. */
    @FXML private void editaEstudiante(ActionEvent evento) {
        Estudiante estudiante = controlTE.getRenglonSeleccionado();
        ControladorFormaEstudiante controlador =
            construyeDialogoEstudiante("Editar estudiante", estudiante);
        if (controlador == null)
            return;
        controlador.setVerbo("Actualizar");
        controlador.getEscenario().showAndWait();
        controlTE.enfocaTabla();
        if (!controlador.isAceptado())
            return;
        bdd.modificaRegistro(estudiante, controlador.getEstudiante());
    }

    /* Elimina uno o varios estudiantes. */
    @FXML private void eliminaEstudiantes(ActionEvent evento) {
        String sujeto = (seleccionados == 1) ? "estudiante" : "estudiantes";
        String titulo = "Eliminar " + sujeto;
        String mensaje = (seleccionados == 1) ?
            "Esto eliminará al estudiante seleccionado" :
            "Esto eliminará a los estudiantes seleccionados";
        if (!dialogoDeConfirmacion(titulo, mensaje, "¿Está seguro?",
                                   "Eliminar " + sujeto,
                                   "Conservar " + sujeto))
            return;
        for (Estudiante estudiante : controlTE.getSeleccion())
            bdd.eliminaRegistro(estudiante);
    }

    /* Busca estudiantes. */
    @FXML private void buscaEstudiantes(ActionEvent evento) {
        try {
            ClassLoader cl = getClass().getClassLoader();
            FXMLLoader cargador =
                new FXMLLoader(cl.getResource(BUSQUEDA_ESTUDIANTES_FXML));
            AnchorPane pagina = (AnchorPane)cargador.load();

            Stage escenario = new Stage();
            escenario.setTitle("Buscar estudiantes");
            escenario.initOwner(this.escenario);
            escenario.initModality(Modality.WINDOW_MODAL);
            Scene escena = new Scene(pagina);
            escenario.setScene(escena);

            ControladorFormaBusquedaEstudiantes controlador;
            controlador = cargador.getController();
            controlador.setEscenario(escenario);

            escenario.setOnShown(w -> controlador.defineFoco());
            escenario.setResizable(false);
            escenario.showAndWait();
            controlTE.enfocaTabla();
            if (!controlador.isAceptado())
                return;

            Lista<Estudiante> resultados =
                bdd.buscaRegistros(controlador.getCampo(),
                                   controlador.getValor());

            controlTE.seleccionaRenglones(resultados);
        } catch (IOException | IllegalStateException e) {
            String mensaje =
                String.format("Ocurrió un error al tratar de " +
                              "cargar el diálogo '%s'.",
                              BUSQUEDA_ESTUDIANTES_FXML);
            dialogoError("Error al cargar interfaz", mensaje);
        }
    }

    /* Muestra un diálogo con información del programa. */
    @FXML private void acercaDe(ActionEvent evento) {
        Alert dialogo = new Alert(AlertType.INFORMATION);
        dialogo.initOwner(escenario);
        dialogo.initModality(Modality.WINDOW_MODAL);
        dialogo.setTitle("Acerca de Administrador de Estudiantes.");
        dialogo.setHeaderText(null);
        dialogo.setContentText("Aplicación para administrar estudiantes.\n"  +
                               "Copyright © 2017 Facultad de Ciencias, UNAM.");
        dialogo.showAndWait();
        controlTE.enfocaTabla();
    }

    /**
     * Define el controlador de tabla.
     * @param controlTE el controlador de tabla.
     */
    public void
    setControladorTablaEstudiantes(ControladorTablaEstudiantes controlTE) {
        this.controlTE = controlTE;
        controlTE.agregaEscuchaSeleccion(n -> setSeleccionados(n));
    }

    /**
     * Define el escenario.
     * @param escenario el escenario.
     */
    public void setEscenario(Stage escenario) {
        this.escenario = escenario;
    }

    /* Actualiza la interfaz con una nueva base de datos. */
    private void setBaseDeDatos(BaseDeDatosEstudiantes bdd) {
        if (this.bdd != null)
            this.bdd.limpia();
        this.bdd = bdd;
        for (Estudiante e : bdd.getRegistros())
            controlTE.agregaRenglon(e);
        bdd.agregaEscucha((e, r1, r2) -> manejaEventoBaseDeDatos(e, r1, r2));
    }

    /* Maneja un evento de cambio en la base de datos. */
    private void manejaEventoBaseDeDatos(EventoBaseDeDatos evento,
                                         Estudiante estudiante1,
                                         Estudiante estudiante2) {
        if (conectado && !ignorar)
            avisaServidor(evento, estudiante1, estudiante2);

        switch (evento) {
        case BASE_LIMPIADA:
            Platform.runLater(() -> controlTE.limpiaTabla());
            break;
        case REGISTRO_AGREGADO:
            Platform.runLater(() -> controlTE.agregaRenglon(estudiante1));
            break;
        case REGISTRO_ELIMINADO:
            Platform.runLater(() -> controlTE.eliminaRenglon(estudiante1));
            break;
        case REGISTRO_MODIFICADO:
            Platform.runLater(() -> controlTE.reordena());
            break;
        }
    }

    /* Actualiza la interfaz dependiendo del número de renglones
     * seleccionados. */
    private void setSeleccionados(int seleccionados) {
        this.seleccionados = seleccionados;
        menuEliminar.setDisable(seleccionados == 0);
        menuEditar.setDisable(seleccionados != 1);
        botonEliminar.setDisable(seleccionados == 0);
        botonEditar.setDisable(seleccionados != 1);
    }

    /* Crea un diálogo con una pregunta que hay que confirmar. */
    private boolean dialogoDeConfirmacion(String titulo,
                                          String mensaje, String pregunta,
                                          String aceptar, String cancelar) {
        Alert dialogo = new Alert(AlertType.CONFIRMATION);
        dialogo.setTitle(titulo);
        dialogo.setHeaderText(mensaje);
        dialogo.setContentText(pregunta);

        ButtonType si = new ButtonType(aceptar);
        ButtonType no = new ButtonType(cancelar, ButtonData.CANCEL_CLOSE);
        dialogo.getButtonTypes().setAll(si, no);

        Optional<ButtonType> resultado = dialogo.showAndWait();
        controlTE.enfocaTabla();
        return resultado.get() == si;
    }

    /* Maneja los distintos eventos de la conexión. */
    private void manejaEventoConexion(Conexion<Estudiante> conexion,
                                      EventoConexion evento) {
        if (!conectado)
            return;
        ignorar = true;
        switch (evento) {
        case BASE_DE_DATOS:
            baseDeDatos(conexion);
            break;
        case REGISTRO_AGREGADO:
            registroAlterado(conexion, AccionRegistro.AGREGAR);
            break;
        case REGISTRO_ELIMINADO:
            registroAlterado(conexion, AccionRegistro.ELIMINAR);
            break;
        case REGISTRO_MODIFICADO:
            registroModificado(conexion);
            break;
        case DETENER_SERVICIO:
            System.err.printf("El cliente no puede detener el " +
                              "servicio; ignorando.\n");
            break;
        case CONEXION_TERMINADA:
            conexionTerminada();
            break;
        case ECO:
            eco();
            break;
        case ERROR:
            error("Hubo un error al comunicarnos con el servidor.");
            break;
        case EVENTO_INVALIDO:
            error("Recibimos un evento inválido del servidor.");
            break;
        }
        ignorar = false;
    }

    /* Recibe la base de datos del servidor. */
    private void baseDeDatos(Conexion<Estudiante> conexion) {
        try {
            conexion.recibeBaseDeDatos(bdd);
        } catch (IOException ioe) {
            error("Ocurrió un error al tratar de cargar " +
                  "la base de datos.");
        }
    }

    /* Agrega o elimina un registro del servidor. */
    private void registroAlterado(Conexion<Estudiante> conexion,
                                  AccionRegistro accion) {
        try {
            Estudiante e = bdd.creaRegistro();
            conexion.recibeRegistro(e);
            switch (accion) {
            case AGREGAR:
                bdd.agregaRegistro(e);
                break;
            case ELIMINAR:
                bdd.eliminaRegistro(e);
                break;
            }
        } catch (IOException | NullPointerException |
                 IllegalArgumentException e) {
            error("Ocurrió un error al tratar de " + accion.toString() +
                  " un registro.");
        }
    }

    /* Modifica un registro del servidor. */
    private void registroModificado(Conexion<Estudiante> conexion) {
        try {
            Estudiante e1 = bdd.creaRegistro();
            Estudiante e2 = bdd.creaRegistro();
            conexion.recibeRegistro(e1);
            conexion.recibeRegistro(e2);
            bdd.modificaRegistro(e1, e2);
        } catch (IOException | NullPointerException |
                 IllegalArgumentException e) {
            error("Ocurrió un error al tratar de modificar un registro.");
        }
    }

    /* Termina la conexión por parte del servidor. */
    private void conexionTerminada() {
        desconectar(null);
        Platform.runLater(
            () -> dialogoError("Servidor desconectado",
                               "El servidor terminó la conexión."));
    }

    /* Responde a un eco del servidor. */
    private void eco() {
        try {
            conexion.enviaEvento(EventoConexion.ECO);
        } catch (IOException ioe) {
            error("Ocurrió un error al contestar un eco.");
        }
    }

    /* Termina la conexión por un error. */
    private void error(String mensaje) {
        desconectar(null);
        Platform.runLater(() -> dialogoError("Terminando conexión",
                                             mensaje +
                                             "\nTerminando conexión."));
    }

    /* Avisa al servidor de un evento. */
    public void avisaServidor(EventoBaseDeDatos evento,
                              Estudiante e1, Estudiante e2) {
        String verbo = null;
        try {
            switch (evento) {
            case BASE_LIMPIADA:
                /* Sólo ocurre al desconectarnos. */
                return;
            case REGISTRO_AGREGADO:
                verbo = "agregar";
                conexion.enviaEvento(EventoConexion.REGISTRO_AGREGADO);
                conexion.enviaRegistro(e1);
                break;
            case REGISTRO_ELIMINADO:
                verbo = "eliminar";
                conexion.enviaEvento(EventoConexion.REGISTRO_ELIMINADO);
                conexion.enviaRegistro(e1);
                break;
            case REGISTRO_MODIFICADO:
                verbo = "modificar";
                conexion.enviaEvento(EventoConexion.REGISTRO_MODIFICADO);
                conexion.enviaRegistro(e1);
                conexion.enviaRegistro(e2);
                break;
            }
        } catch (IOException ioe) {
            error("Error al " + verbo + " un estudiante del servidor.");
        }
    }

    /* Construye un diálogo para crear o editar un estudiante. */
    private ControladorFormaEstudiante
    construyeDialogoEstudiante(String titulo,
                               Estudiante estudiante) {
        try {
            ClassLoader cl = getClass().getClassLoader();
            FXMLLoader cargador =
                new FXMLLoader(cl.getResource(ESTUDIANTE_FXML));
            AnchorPane pagina = (AnchorPane)cargador.load();

            Stage escenario = new Stage();
            escenario.setTitle(titulo);
            escenario.initOwner(this.escenario);
            escenario.initModality(Modality.WINDOW_MODAL);
            Scene escena = new Scene(pagina);
            escenario.setScene(escena);

            ControladorFormaEstudiante controlador = cargador.getController();
            controlador.setEscenario(escenario);
            controlador.setEstudiante(estudiante);

            escenario.setOnShown(w -> controlador.defineFoco());
            escenario.setResizable(false);
            return controlador;
        } catch (IOException | IllegalStateException e) {
            String mensaje =
                String.format("Ocurrió un error al tratar de cargar " +
                              "el diálogo '%s'.", ESTUDIANTE_FXML);
            dialogoError("Error al cargar interfaz", mensaje);
            return null;
        }
    }

    /* Muestra un diálogo de error. */
    private void dialogoError(String titulo, String mensaje) {
        Alert dialogo = new Alert(AlertType.ERROR);
        dialogo.setTitle(titulo);
        dialogo.setHeaderText(null);
        dialogo.setContentText(mensaje);
        dialogo.showAndWait();
        controlTE.enfocaTabla();
    }
}
