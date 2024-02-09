package sk.duri.relifri;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public abstract class Postava {
    private String name;
    private float x;
    private float y;
    private int height;
    private int width;
    private Texture textura;
    private Rectangle hitbox;
    private int zivoty;
    private int sila;

//    public Postava() {
//    }

    public Postava(String name, String cesta, int height, int width) {
        this.name = name;
        this.textura = new Texture(Gdx.files.internal(cesta));
        this.height = height;
        this.width = width;
        this.hitbox = new Rectangle();
        this.hitbox.setSize(width, height);
    }

    public String getName() {
        return name;
    }

    public float getX() {
        return x;
    }

    protected void setX(float x) {
        this.x = x;
        this.hitbox.setX(x);
    }

    public float getY() {
        return y;
    }

    protected void setY(float y) {
        this.y = y;
        this.hitbox.setY(y);
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public void setPolohu(float x, float y) {
        this.x = x;
        this.y = y;
        this.hitbox.setPosition(this.x, this.y);
    }

    public Rectangle getHitbox() {
        return hitbox;
    }

    public int getZivoty() {
        return zivoty;
    }

    public void setZivoty(int zivoty) {
        this.zivoty = zivoty;
    }

    public int getSila() {
        return sila;
    }

    public void setSila(int sila) {
        this.sila = sila;
    }

    public abstract void zautoc(Postava postava);

    public abstract boolean uberZivot(int sila);

    public boolean isDead() {
        return this.zivoty <= 0;
    }

    public Texture getTextura() {
        return textura;
    }


}