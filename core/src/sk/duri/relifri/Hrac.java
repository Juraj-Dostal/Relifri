package sk.duri.relifri;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class Hrac extends Postava{

    public Hrac(final RelifriGame game) {
        super("Frickar", "fris.png", 150, 75);
        this.setPolohu(250, 250);
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
