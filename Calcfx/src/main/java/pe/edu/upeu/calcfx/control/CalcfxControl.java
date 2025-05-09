package pe.edu.upeu.calcfx.control;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import pe.edu.upeu.calcfx.modelo.CalcTO;
import pe.edu.upeu.calcfx.servicio.CalcServicioI;

import java.util.List;

@Controller
public class CalcfxControl {

    @Autowired
    CalcServicioI servicioI;

    @FXML
    TableView<CalcTO> tableView;
    private ObservableList<CalcTO> datos;
    List<CalcTO> lista;

    @FXML
    TableColumn<CalcTO, String> num1x;
    @FXML
    TableColumn<CalcTO, String> num2x;
    @FXML
    TableColumn<CalcTO, Character> operx;
    @FXML
    TableColumn<CalcTO, String> resultx;
    @FXML
    TableColumn<CalcTO, Void> opcionesx;

    @FXML
    private TextField txtResultado;

    int indexID=-1;
    int idx=0;

    @FXML
    private void initialize() {
        listar();
    }

    private void escribirNumero(String numero) {
        txtResultado.appendText(numero);
    }

    private void agregarOperador(String operador) {

        if(!txtResultado.getText().isEmpty() && txtResultado.getText().length()>=4){
            char op = txtResultado.getText().charAt(txtResultado.getText().length()-2);
            if(op!='+' && op!='-' && op!='/'&& op!='*'){
                txtResultado.appendText(" " + operador + " ");
            }
        }else{
            txtResultado.appendText(" " + operador + " ");
        }

    }

    private void calcularResultado() {
        try {
            String[] tokens = txtResultado.getText().split(" ");
            if (tokens.length < 3) {
                return;
            }
            double num1 = Double.parseDouble(tokens[0]);
            String operador = tokens[1];
            double num2 = Double.parseDouble(tokens[2]);
            double resultado = 0;
            switch (operador) {
                case "+":
                    resultado = num1 + num2;
                    break;
                case "-":
                    resultado = num1 - num2;
                    break;
                case "*":
                    resultado = num1 * num2;
                    break;
                case "/":
                    if (num2 != 0) {
                        resultado = num1 / num2;
                    } else {
                        txtResultado.setText("Error: Div/0");
                        return;
                    }
                    break;
            }
            String[] dd=String.valueOf(resultado).split("\\.");
            System.out.println(dd.length);

            if (dd.length == 2 && dd[1].equals("0")) {
                txtResultado.setText(String.valueOf(dd[0]));
            }else{
                txtResultado.setText(String.valueOf(resultado));
            }
            CalcTO to = new CalcTO();
            to.setNum1(String.valueOf(num1));
            to.setNum2(String.valueOf(num2));
            to.setOperador(operador.charAt(0));
            to.setResultado(String.valueOf(resultado));
            if(indexID!=-1){
                servicioI.update(to, Long.valueOf(indexID));
            }else{
                servicioI.save(to);
            }


            listar();

        } catch (Exception e) {
            txtResultado.setText("Error");
            System.out.println(e.getMessage());
        }
    }


    public void listar(){
        lista=servicioI.findAll();
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        num1x.setCellValueFactory(new PropertyValueFactory<CalcTO,String>("num1"));
        num1x.setCellFactory(TextFieldTableCell.<CalcTO>forTableColumn());

        num2x.setCellValueFactory(new PropertyValueFactory<CalcTO,String>("num2"));
        num2x.setCellFactory(TextFieldTableCell.<CalcTO>forTableColumn());

        operx.setCellValueFactory(new PropertyValueFactory<>("operador"));
        operx.setCellFactory(ComboBoxTableCell.<CalcTO, Character>forTableColumn('+', '-','/','*'));

        resultx.setCellValueFactory(new PropertyValueFactory<CalcTO,String>("resultado"));
        resultx.setCellFactory(TextFieldTableCell.<CalcTO>forTableColumn());

        addActionButtonsToTable();

        datos = FXCollections.observableArrayList(lista);
        tableView.setItems(datos);

    }

    @FXML
    private void controlClick(ActionEvent event) {
        Button boton = (Button) event.getSource();
       switch (boton.getId()){
           case "btn0", "btn1","btn2","btn3", "btn4", "btn5", "btn6", "btn7", "btn8", "btn9": {escribirNumero(boton.getText());}break;
           case "btnDiv", "btnMult","btnRest", "btnSum":{ agregarOperador(boton.getText()); }break;
           case "btnBorrar":{ txtResultado.setText(""); }break;
           case "btnIgual":{  calcularResultado();  }break;
           default: {} break;
       }
    }

    private void addActionButtonsToTable() {
        Callback<TableColumn<CalcTO, Void>, TableCell<CalcTO, Void>>
                cellFactory = param -> new TableCell<>() {
            private final Button editButton = new Button("Edit");
            private final Button deleteButton = new Button("Delete");
            {
                editButton.getStyleClass().setAll("btn", "btn-success");
                editButton.setOnAction(event -> {
                    CalcTO cal = getTableView().getItems().get(getIndex());
                    editOperCalc(cal, getIndex());
                });
                deleteButton.getStyleClass().setAll("btn", "btn-danger");
                deleteButton.setOnAction(event -> {
                    CalcTO cal = getTableView().getItems().get(getIndex());
                    deleteOperCalc(cal);
                });
            }
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    HBox buttons = new HBox(editButton, deleteButton);
                    buttons.setSpacing(10);
                    setGraphic(buttons);
                }
            }
        };
        opcionesx.setCellFactory(cellFactory);
    }

    public void editOperCalc(CalcTO to, int index){
            txtResultado.setText(to.getNum1()+" "+to.getOperador()+" "+to.getNum2());
            indexID=index;
    }

    public void deleteOperCalc(CalcTO to){
        servicioI.delete(to);
        listar();
    }

}
