package upeu.obj;
import upeu.modelo.Persona;

public class ClaseGeneral {
    public static void main(String[] args) {
        Persona persona = new Persona();
        persona.saludo();
        persona.setNombre("Jose");
        persona.setEdad(12);
        //persona.saludo();

        persona.setNombre("Raul");
        persona.setEdad(18);
        persona.saludo();
    }
}
