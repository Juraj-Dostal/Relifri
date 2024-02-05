package sk.duri.relifri;

public class Jarda extends Postava {
    public Jarda() {
        super("Jarda", "jarda.png", 183, 87);
        this.setPolohu(450, 350);
        this.setSila(3);
        this.setZivoty(20);
    }

    @Override
    public void zautoc(Postava postava) {
        postava.uberZivot(this.getSila());
    }

    @Override
    public boolean uberZivot(int sila) {

        int bool = (int) Math.round(Math.random() );

        if (bool == 0) {
            this.setZivoty(this.getZivoty() - sila);
            return true;
        } else {
            return false;
        }
    }
}
