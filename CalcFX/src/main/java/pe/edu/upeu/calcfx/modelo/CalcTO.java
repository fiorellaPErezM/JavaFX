package pe.edu.upeu.calcfx.modelo;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
@Entity(name = "calculadora")
@Data
public class CalcTO {
    @Id
    int id;

    String num1, num2, resultado;
    char operador;
}