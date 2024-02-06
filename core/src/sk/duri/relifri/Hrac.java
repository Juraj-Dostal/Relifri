package sk.duri.relifri;

public class Hrac extends Postava{

    public Hrac() {
        super("Frickar", "fris.png", 150, 75);
        this.setPolohu(150, 150);
        this.setSila(10);
        this.setZivoty(1000);
    }

    @Override
    public void zautoc(Postava postava) {
        postava.uberZivot(this.getSila());
    }

    @Override
    public boolean uberZivot(int sila) {
        this.setZivoty(this.getZivoty() - sila);
        return true;
//      int bool = (int) (Math.random() * 3);
//
//      if (bool == 0) {
//          this.setZivoty(this.getZivoty() - sila);
//          return true;
//      } else {
//          return false;
//      }

    }

}
