package upeu.encapsulamiento1;

public class Loro implements Animal {

    @Override
    public void comer() {
        System.out.println("Hola manito no molestes que"+
                "estoy comiendo mi comida favorita");

    }

    @Override
    public void dormir() {
        System.out.println("Zzz...zzz.zzz");

    }

    @Override
    public String peso() {
        return "esto pesando 1/2 kilos";
    }

    @Override
    public void emitirSonido() {
        System.out.println("Oye atiente pues a tu profesor!!!");

    }
}
