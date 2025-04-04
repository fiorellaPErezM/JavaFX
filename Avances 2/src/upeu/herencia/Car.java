package upeu.herencia;

public class Car extends Vehicle {
    protected String modelo;
    public void mostrarInformacion(){
        tipo="Electrinico";
        setMarca("Toyota");
        modelo="Hilux";
        sonido();
    }

    public void sonido(){
        System.out.println("tititi.....");
        System.out.println("Mis caracteristicas son: \n"+
                "Marca:"+getMarca()+"\nTipo:"+tipo);
        System.out.println("Modelo: "+modelo);
    }

    public static void main(String[] args) {
        new Car().mostrarInformacion();
    }

}