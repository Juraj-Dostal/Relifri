package sk.duri.relifri;

public class Rudo extends Postava {
    public Rudo() {
        super("Rudo", "rudo.png", 125, 148);
        this.setPolohu(260, 550);
        this.setSila(10);
        this.setZivoty(18);
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
