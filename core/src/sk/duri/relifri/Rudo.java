package sk.duri.relifri;

public class Rudo extends Postava {
    public Rudo() {
        super("Rudo", "rudo.png", 125, 148);
        this.setPolohu(450, 350);
        this.setSila(5);
        this.setZivoty(19);
    }

    @Override
    public void zautoc(Postava postava) {
        postava.uberZivot(this.getSila());
    }

    @Override
    public boolean uberZivot(int sila) {
        return false;
    }
}
