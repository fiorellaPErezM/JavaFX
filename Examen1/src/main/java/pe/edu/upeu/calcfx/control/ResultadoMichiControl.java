package pe.edu.upeu.calcfx.control;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import pe.edu.upeu.calcfx.modelo.ResultadoMichi;
import pe.edu.upeu.calcfx.servicio.ResultadoMichiServicio;
import javafx.scene.text.Text;

@Controller
public class ResultadoMichiControl {


    @Autowired
    private ResultadoMichiServicio resultadoMichiServicio;

    @FXML private GridPane tablero;
    @FXML private TableView<ResultadoMichi> tableView;
    @FXML private Text turnoText;
    @FXML private Text jugador1Text;
    @FXML private Text jugador2Text;
    @FXML private TextField jugador1Field;
    @FXML private TextField jugador2Field;

    private Button[][] botones = new Button[3][3];
    private boolean turnoJugador1 = true;
    private String jugador1 = "Fiorella";
    private String jugador2 = "Perez";


    private ObservableList<ResultadoMichi> datos;
    private String[][] tableroJuego = new String[3][3];

    @FXML
    public void initialize() {

        inicializarTablero();
        cargarDatosTabla();
        actualizarTextoTurno();
        actualizarNombresJugadores();
    }

    private void inicializarTablero() {
        for (int fila = 0; fila < 3; fila++) {
            for (int col = 0; col < 3; col++) {
                int index = fila * 3 + col;
                Button btn = (Button) tablero.getChildren().get(index);
                final int f = fila, c = col;
                btn.setOnAction(e -> procesarMovimiento(f, c, btn));
                botones[fila][col] = btn;
                tableroJuego[fila][col] = "";

                btn.setDisable(true);
            }
        }
    }

    private void procesarMovimiento(int fila, int col, Button btn) {
        if (!tableroJuego[fila][col].isEmpty()) return;

        String simbolo = turnoJugador1 ? "X" : "O";
        btn.setText(simbolo);
        tableroJuego[fila][col] = simbolo;

        if (verificarGanador(simbolo)) {
            registrarGanador(simbolo);
            deshabilitarTablero();
        } else if (tableroLleno()) {
            registrarEmpate();
            deshabilitarTablero();
        } else {
            turnoJugador1 = !turnoJugador1;
            actualizarTextoTurno();
        }
    }
    private boolean tableroLleno() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (tableroJuego[i][j].isEmpty()) return false;
            }
        }
        return true;
    }


    private void registrarEmpate() {
        String ganador = "NADIE";

        ResultadoMichi partidaEnProgreso = datos.stream()
                .filter(r -> r.getEstado().equals("En progreso"))
                .findFirst()
                .orElse(null);

        if (partidaEnProgreso != null) {
            partidaEnProgreso.setGanador(ganador);

            partidaEnProgreso.setPunto(1);

            partidaEnProgreso.setEstado("Empate");

            resultadoMichiServicio.save(partidaEnProgreso);

            tableView.refresh();

            mostrarAlerta("¡Empate!", "El juego ha terminado en empate.");
        }
    }



    private void actualizarTextoTurno() {

        turnoText.setText("Turno: " + (turnoJugador1 ? jugador1 : jugador2));
    }


    private void actualizarNombresJugadores() {

        jugador1Text.setText(jugador1);

        jugador2Text.setText(jugador2);
    }


    private boolean verificarGanador(String simbolo) {

        for (int i = 0; i < 3; i++) {
            if (tableroJuego[i][0].equals(simbolo) && tableroJuego[i][1].equals(simbolo) && tableroJuego[i][2].equals(simbolo)) return true;

            if (tableroJuego[0][i].equals(simbolo) && tableroJuego[1][i].equals(simbolo) && tableroJuego[2][i].equals(simbolo)) return true;
        }

        return (tableroJuego[0][0].equals(simbolo) && tableroJuego[1][1].equals(simbolo) && tableroJuego[2][2].equals(simbolo)) ||
                (tableroJuego[0][2].equals(simbolo) && tableroJuego[1][1].equals(simbolo) && tableroJuego[2][0].equals(simbolo));
    }


    private void registrarGanador(String simbolo) {

        String ganador = simbolo.equals("X") ? jugador1 : jugador2;

        ResultadoMichi partidaEnProgreso = datos.stream()
                .filter(r -> r.getEstado().equals("En progreso"))
                .findFirst()
                .orElse(null);

        if (partidaEnProgreso != null) {
            partidaEnProgreso.setGanador(ganador);

            partidaEnProgreso.setPunto(3);

            partidaEnProgreso.setEstado("Finalizado");

            resultadoMichiServicio.save(partidaEnProgreso);

            tableView.refresh();

            mostrarAlerta("¡Ganador!", "El ganador es: " + ganador);
        }
    }



    private void deshabilitarTablero() {

        for (Button[] fila : botones) {
            for (Button btn : fila) {
                btn.setDisable(true);
            }
        }
    }


    private void cargarDatosTabla() {
        datos = FXCollections.observableArrayList(resultadoMichiServicio.findAll());

        tableView.setItems(datos);


        TableColumn<ResultadoMichi, String> col1 = crearColumna("Nombre Partido", r -> r.getNombrePartida());

        TableColumn<ResultadoMichi, String> col2 = crearColumna("Jugador 1", r -> r.getNombreJugador1());

        TableColumn<ResultadoMichi, String> col3 = crearColumna("Jugador 2", r -> r.getNombreJugador2());

        TableColumn<ResultadoMichi, String> col4 = crearColumna("Ganador", r -> r.getGanador());

        TableColumn<ResultadoMichi, String> col5 = crearColumna("Puntaje", r -> String.valueOf(r.getPunto()));

        TableColumn<ResultadoMichi, String> col6 = crearColumna("Estado", r -> r.getEstado());

        tableView.getColumns().setAll(col1, col2, col3, col4, col5, col6);
    }


    private TableColumn<ResultadoMichi, String> crearColumna(String titulo, java.util.function.Function<ResultadoMichi, String> valor) {

        TableColumn<ResultadoMichi, String> columna = new TableColumn<>(titulo);

        columna.setCellValueFactory(f -> new SimpleStringProperty(valor.apply(f.getValue())));

        return columna;
    }


    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        alert.setTitle(titulo);

        alert.setHeaderText(null);

        alert.setContentText(mensaje);

        alert.showAndWait();
    }


    @FXML
    private void iniciarNuevaPartida() {
        jugador1 = jugador1Field.getText().isEmpty() ? "FIORELLA" : jugador1Field.getText();

        jugador2 = jugador2Field.getText().isEmpty() ? "PEREZ" : jugador2Field.getText();

        String nombrePartida = "Partida #" + (datos.size() + 1);

        ResultadoMichi nuevaPartida = new ResultadoMichi();

        nuevaPartida.setNombrePartida(nombrePartida);
        nuevaPartida.setNombreJugador1(jugador1);
        nuevaPartida.setNombreJugador2(jugador2);
        nuevaPartida.setGanador("");
        nuevaPartida.setPunto(0);
        nuevaPartida.setEstado("En progreso");
        resultadoMichiServicio.save(nuevaPartida);

        datos.add(nuevaPartida);

        tableView.refresh();

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                tableroJuego[i][j] = "";
                botones[i][j].setText("");
                botones[i][j].setDisable(false);
            }
        }

        turnoJugador1 = true;

        actualizarTextoTurno();

        actualizarNombresJugadores();
    }



    @FXML
    private void anularPartida() {
        if (!datos.isEmpty()) {

            ResultadoMichi ultimaPartida = datos.get(datos.size() - 1);


            if (!ultimaPartida.getEstado().equals("Finalizado")) {

                ultimaPartida.setEstado("Anulado");


                ultimaPartida.setGanador("NADIE");


                ultimaPartida.setPunto(0);


                resultadoMichiServicio.save(ultimaPartida);


                tableView.refresh();
            }
        }


        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                tableroJuego[i][j] = "";
                botones[i][j].setText("");
                botones[i][j].setDisable(false);
            }
        }
    }



}
