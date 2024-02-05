package sk.duri.relifri;

public class Smiecho extends Postava {
    public Smiecho() {
        super("Smiecho", "smiecho.png", 115, 150);
        this.setPolohu(450, 350);
        this.setSila(1);
        this.setZivoty(47);
    }

    @Override
    public void zautoc(Postava postava) {
        postava.uberZivot(this.getSila());
    }

    @Override
    public boolean uberZivot(int sila) {
        this.setZivoty(this.getZivoty() - sila);
        return true;
    }
}
