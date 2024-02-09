package sk.duri.relifri;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

import javax.swing.*;

public class GameScreen implements Screen {

    private final RelifriGame game;
    private OrthographicCamera camera;
    private Postava hrac;
    private Postava prisera;
    private Texture pozadie;
    private Sprite pozadieSprite;
    private boolean isSent = false;


    public GameScreen(final RelifriGame game) {
        this.game = game;

        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false, 700, 700);

        this.pozadie = new Texture(Gdx.files.internal("miestnost.png"));
        this.pozadieSprite = new Sprite(this.pozadie);
        this.hrac = new Hrac(this.game);
        this.prisera = new Jarda();
    }

    @Override
    public void render (float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        this.camera.update();
        this.game.batch.setProjectionMatrix(camera.combined);
        /*
         * Renderovanie grafiky
         */
        this.game.batch.begin();
        this.game.batch.disableBlending();
        this.pozadieSprite.draw(this.game.batch);
        this.game.batch.enableBlending();

        if (this.hrac != null) {
            this.game.batch.draw(this.hrac.getTextura(), this.hrac.getX(), this.hrac.getY());
            this.game.font.draw(this.game.batch, "Zivoty hrac: " + this.hrac.getZivoty(), 580,40 );
        }
        if (this.prisera != null) {
            this.game.batch.draw(this.prisera.getTextura(), this.prisera.getX(), this.prisera.getY());
            this.game.font.draw(this.game.batch, "Zivoty prisera: " + this.prisera.getZivoty(), 580,20 );
        }

        this.game.batch.end();


        /*
         * Riesenie pohybu hraca
         */
        if ( this.hrac != null) {
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) {
                this.hrac.setX(this.hrac.getX() - 400 * Gdx.graphics.getDeltaTime());
                if (this.hrac.getX() < 0) this.hrac.setX(1);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) {
                this.hrac.setX(this.hrac.getX() + 400 * Gdx.graphics.getDeltaTime());
                if (this.hrac.getX() > 700 - this.hrac.getWidth()) this.hrac.setX(700 - this.hrac.getWidth() - 1);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S)) {
                this.hrac.setY(this.hrac.getY() - 400 * Gdx.graphics.getDeltaTime());
                if (this.hrac.getY() < 0) this.hrac.setY(1);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)) {
                this.hrac.setY(this.hrac.getY() + 400 * Gdx.graphics.getDeltaTime());
                if (this.hrac.getY() > 700 - this.hrac.getHeight() - 1) this.hrac.setY(700 - this.hrac.getHeight() - 1);
            }

            /*
             * Riesenie utoku, blizky a daleky
             */

            if (this.prisera != null && this.hrac.getHitbox().overlaps(this.prisera.getHitbox())) {
                this.prisera.uberZivot(hrac.getSila() / 4);
            }

            if (Gdx.input.isTouched()) {
                Vector2 touchPos = new Vector2(Gdx.input.getX(), 700 - Gdx.input.getY()); // data z kliku su z laveho horneho rohu

                if (this.prisera.getHitbox().contains(touchPos)) {
                    this.prisera.uberZivot(hrac.getSila());
                } else {
                    this.hrac.uberZivot(prisera.getSila());
                }

            }

            if (!this.isSent && this.prisera.isDead()) {
                this.prisera.getTextura().dispose();
                posliSpravu(this.prisera.getName() + " zomrel! Vyhral si!!!");
                reset();
                this.prisera = dajPriseru();
            }

            if (!this.isSent && this.hrac.isDead()) {
                this.hrac = null;
                posliSpravu(this.prisera.getName() + " ta zabil! Hra skoncila!!!");
            }
        }


    }

    @Override
    public void dispose () {
        if (this.hrac != null) {
            hrac.getTextura().dispose();
        }
        if (this.prisera != null) {
            prisera.getTextura().dispose();
        }
        pozadie.dispose();
    }

    /**
     * Metoda, sluzi na poslanie spravy uzivatelovi.
     * @param sprava sprava
     */
    private void posliSpravu (String sprava) {
        JOptionPane.showMessageDialog(null, sprava);
        this.isSent = true;
    }

    /**
     * Metoda zmeny priseru hracovi
     *
     * @return Postava
     */
    private Postava dajPriseru () {
        int rand = (int)(Math.random() * 3);

        switch (rand) {
            case 0: return new Rudo();
            case 1: return new Smiecho();
            default: return new Jarda();
        }
    }

    /**
     * Vyresetuje nastavenia
     */
    private void reset() {
        this.isSent = false;
    }



    //________


    @Override
    public void show() {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }
}
