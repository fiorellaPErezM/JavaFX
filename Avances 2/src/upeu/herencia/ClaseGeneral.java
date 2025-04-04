package upeu.herencia;

public class ClaseGeneral extends Car{
    public void mostrarInf(){
        tipo="Combustible";
        setMarca("Toyota");
        modelo="Hilux";
        sonido();
    }

    public static void main(String[] args) {
        ClaseGeneral car = new ClaseGeneral();
        car.mostrarInf();
    }
}