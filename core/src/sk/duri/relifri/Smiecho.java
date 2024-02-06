package sk.duri.relifri;

public class Smiecho extends Postava {
    public Smiecho() {
        super("Smiecho", "smiecho.png", 115, 150);
        this.setPolohu(150, 50);
        this.setSila(2);
        this.setZivoty(87);
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
