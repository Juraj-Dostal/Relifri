package sk.duri.relifri;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;

import javax.swing.*;
import java.util.Vector;

public class Game extends ApplicationAdapter {
	private SpriteBatch batch;
	private OrthographicCamera camera;
	private BitmapFont font;
	private Postava hrac;
	private Postava prisera;
	private Texture pozadie;
	private boolean isSent = false;

	
	@Override
	public void create () {
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 700,700);
		font = new BitmapFont();

		pozadie = new Texture(Gdx.files.internal("miestnost.png"));
		hrac = new Hrac();
		prisera = dajPriseru();
	}

	@Override
	public void render () {
		ScreenUtils.clear(1, 0, 0, 1);

		this.camera.update();
		this.batch.setProjectionMatrix(camera.combined);
		/**
		 * Renderovanie grafiky
		 */
		this.batch.begin();
		this.batch.draw(this.pozadie, 0, 0);
		if (this.hrac != null) {
			this.batch.draw(this.hrac.getTextura(), this.hrac.getX(), this.hrac.getY());
			this.font.draw(batch, "Zivoty hrac: " + this.hrac.getZivoty(), 580,40 );
		}
		if (this.prisera != null) {
			this.batch.draw(this.prisera.getTextura(), this.prisera.getX(), this.prisera.getY());
			this.font.draw(batch, "Zivoty prisera: " + this.prisera.getZivoty(), 580,20 );
		}

		this.batch.end();


		/**
		 * Riesenie pohybu hraca
		 */
		if (this.hrac != null) {
			if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) {
				this.hrac.setX(this.hrac.getX() - 200 * Gdx.graphics.getDeltaTime());
				if (this.hrac.getX() < 0) this.hrac.setX(0);
			}
			if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) {
				this.hrac.setX(this.hrac.getX() + 200 * Gdx.graphics.getDeltaTime());
				if (this.hrac.getX() > 700 - this.hrac.getWidth()) this.hrac.setX(700 - this.hrac.getWidth());
			}
			if (Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S)) {
				this.hrac.setY(this.hrac.getY() - 200 * Gdx.graphics.getDeltaTime());
				if (this.hrac.getY() < 0) this.hrac.setY(0);
			}
			if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)) {
				this.hrac.setY(this.hrac.getY() + 200 * Gdx.graphics.getDeltaTime());
				if (this.hrac.getY() > 700 - this.hrac.getHeight()) this.hrac.setY(700 - this.hrac.getHeight());
			}

			/**
			 * Riesenie utoku, blizky a daleky
			 */

			if (this.prisera != null && this.hrac.getHitbox().overlaps(this.prisera.getHitbox())) {
				this.prisera.uberZivot(hrac.getSila() / 2);
			}

			if (Gdx.input.isTouched()) {
				Rectangle rozsah = new Rectangle(Gdx.input.getX() - 25, Gdx.input.getY() - 25, 50, 50);

				if (this.prisera.getHitbox().contains(rozsah)) {
					this.prisera.uberZivot(hrac.getSila());
				} else {
					this.hrac.uberZivot(prisera.getSila());
				}
			}

			if (!this.isSent && this.prisera.isDead()) {
				this.prisera.getTextura().dispose();
				this.prisera = dajPriseru();
				posliSpravu(this.prisera.getName() + " zomrel! Vyhral si!!!");
				reset();
			}

			if (!this.isSent && this.hrac.isDead()) {
				this.hrac = null;
				posliSpravu(this.prisera.getName() + " ta zabil! Hra skoncila!!!");
			}
		}
	}
	
	@Override
	public void dispose () {
		batch.dispose();
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
	 * @param sprava
	 */
	private void posliSpravu (String sprava) {
		JOptionPane.showMessageDialog(null, sprava);
		this.isSent = true;
	}

	/**
	 * Metoda zmeny priseru hracovi
	 *
	 * @return
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
}
