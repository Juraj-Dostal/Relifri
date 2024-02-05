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
		prisera = new Jarda();
	}

	@Override
	public void render () {
		ScreenUtils.clear(1, 0, 0, 1);

		camera.update();
		batch.setProjectionMatrix(camera.combined);
		/**
		 * Renderovanie grafiky
		 */
		batch.begin();
		batch.draw(this.pozadie, 0, 0);
		batch.draw(hrac.getTextura(), this.hrac.getX(), this.hrac.getY());
		if (this.prisera != null) {
			batch.draw(prisera.getTextura(), this.prisera.getX(), this.prisera.getY());
		}
		font.draw(batch, "Zivoty prisera: " + this.prisera.getZivoty(), 580,20 );
		font.draw(batch, "Zivoty hrac: " + this.hrac.getZivoty(), 580,40 );
		batch.end();


		/**
		 * Riesenie pohybu hraca
		 */
		if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) {
			hrac.setX(hrac.getX() - 200*Gdx.graphics.getDeltaTime());
			if (hrac.getX() < 0) hrac.setX(0);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) {
			hrac.setX(hrac.getX() + 200*Gdx.graphics.getDeltaTime());
			if (hrac.getX() > 700 - hrac.getWidth()) hrac.setX(700 - hrac.getWidth());
		}
		if (Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S)) {
			hrac.setY(hrac.getY() - 200*Gdx.graphics.getDeltaTime());
			if (hrac.getY() < 0) hrac.setY(0);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)) {
			hrac.setY(hrac.getY() + 200*Gdx.graphics.getDeltaTime());
			if (hrac.getY() > 700 - hrac.getHeight()) hrac.setY(700 - hrac.getHeight());
		}

		/**
		 * Riesenie utoku
		 */

		if (this.prisera != null && this.hrac.getHitbox().overlaps(this.prisera.getHitbox())) {

		}

		if (Gdx.input.isTouched()) {
			Vector2 rozsah = new Vector2(Gdx.input.getX(), Gdx.input.getY());

			if (prisera.getHitbox().contains(rozsah)) {
				this.prisera.uberZivot(hrac.getSila());
			} else {
				this.hrac.uberZivot(prisera.getSila());
			}
		}

		if (!this.isSent && this.prisera.isDead()) {
			posliSpravu( this.prisera.getName() + " zomrel! Vyhral si!!!");
			this.prisera = zmenPriseru(this.prisera.getName());
		}

		if (!this.isSent && this.hrac.isDead()) {
			posliSpravu(this.prisera.getName() + " ta zabil! Hra skoncila!!!");
		}
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		hrac.getTextura().dispose();
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
	 * @param nazovPriseri
	 * @return
	 */
	private Postava zmenPriseru (String nazovPriseri) {
		int rand = (int)Math.random();
		if (nazovPriseri.equals("jarda")) {
			switch (rand) {
				case 0: return new Rudo();
				case 1: return new Smiecho();
			}
		} else if (nazovPriseri.equals("rudo")) {
			switch (rand) {
				case 0: return new Jarda();
				case 1: return new Smiecho();
			}
		} else {
			switch (rand) {
				case 0: return new Rudo();
				case 1: return new Jarda();
			}
		}

		return null;

	}
}
