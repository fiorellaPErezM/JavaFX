package upeu.encapsulamiento1;

public class Cuervo implements Animal {
    @Override
    public void comer() {
        System.out.println("Cocosh.....cocosh....!!");

    }

    @Override
    public void dormir() {
        System.out.println("Dormir....zzz...zzz...zzz");

    }

    @Override
    public String peso() {
        return "Estoy pesando 2 kilos";
    }

    @Override
    public void emitirSonido() {
        System.out.println("Hey!!.....que haces jugando!.....");

    }
}
