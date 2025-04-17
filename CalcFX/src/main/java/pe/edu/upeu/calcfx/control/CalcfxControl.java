package pe.edu.upeu.calcfx.control;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.springframework.boot.autoconfigure.info.ProjectInfoProperties;
import org.springframework.stereotype.Controller;

@Controller
public class CalcfxControl {

    private final ProjectInfoProperties projectInfoProperties;
    @FXML
    private TextField txtResultado;

    public CalcfxControl(ProjectInfoProperties projectInfoProperties) {
        this.projectInfoProperties = projectInfoProperties;
    }

    private void escribirNumero(String numero) {
        txtResultado.appendText(numero);
    }

    private void agregarOperador(String operador) {
        if(!txtResultado.getText().isEmpty() && txtResultado.getText().length()>=4){
            char op = txtResultado.getText().charAt(txtResultado.getText().length()-2);
            if(txtResultado.getText().toString().contentEquals(String.valueOf(op))){
                txtResultado.appendText(" " + operador + " ");
            }
        }else{
            txtResultado.appendText(" " + operador + " ");
        }
    }

    private void calcularResultado() {
        try {
            String expression = txtResultado.getText().trim();
            String[] tokens = expression.split(" ");

            if (tokens.length == 1) {
                switch (tokens[0]) {
                    case "π":
                        txtResultado.setText(String.valueOf(Math.PI));
                        return;
                    default:
                        try {
                            Double.parseDouble(tokens[0]);
                            return;
                        } catch (NumberFormatException e) {
                            txtResultado.setText("Error: Entrada inválida");
                            return;
                        }
                }

            } else if (tokens.length == 2) {
                String operador = tokens[0];
                String operandoStr = tokens[1];
                try {
                    double operando = Double.parseDouble(operandoStr);
                    switch (operador) {
                        case "√":
                            if (operando >= 0) {
                                txtResultado.setText(String.valueOf(Math.sqrt(operando)));
                            } else {
                                txtResultado.setText("Error: Raíz negativa");
                            }
                            return;
                        case "1/x":
                            if (operando != 0) {
                                txtResultado.setText(String.valueOf(1 / operando));
                            } else {
                                txtResultado.setText("Error: Div/0");
                            }
                            return;
                        case "=>":
                            txtResultado.setText("Binario: " + Integer.toBinaryString((int) operando));
                            return;
                        default:
                            txtResultado.setText("Error: Formato inválido");
                            return;
                    }
                } catch (NumberFormatException e) {
                    txtResultado.setText("Error: Operando inválido");
                    return;
                }

            } else if (tokens.length == 3) {
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
                    case "^":
                        resultado = Math.pow(num1, num2);
                        break;
                    default:
                        txtResultado.setText("Error: Operador inválido");
                        return;
                }

                String[] dd = String.valueOf(resultado).split("\\.");
                if (dd.length == 2 && dd[1].equals("0")) {
                    txtResultado.setText(String.valueOf(dd[0]));
                } else {
                    txtResultado.setText(String.valueOf(resultado));
                }
            } else {
                txtResultado.setText("Error: Formato inválido");
            }

        } catch (Exception e) {
            txtResultado.setText("Error");
            System.out.println(e.getMessage());
        }

    }


    @FXML
    private void controlClick(ActionEvent event) {
        Button boton = (Button) event.getSource();
       switch (boton.getId()){
           case "btn0", "btn1","btn2","btn3", "btn4", "btn5", "btn6", "btn7", "btn8", "btn9": {escribirNumero(boton.getText());}break;
           case "btnDiv", "btnMult","btnRest", "btnSum", "btnPot", "btnRaiz", "btnSobre", "btnPi", "btnBinario":{ agregarOperador(boton.getText()); }break;
           case "btnBorrar":{ txtResultado.setText(""); }break;
           case "btnIgual":{  calcularResultado();  }break;
           default: {} break;
       }
    }

}
