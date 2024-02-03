package sk.duri.relifri;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class Game extends ApplicationAdapter {
	SpriteBatch batch;
	OrthographicCamera camera;
	private Postava hrac;
	private Postava prisera;
	private Texture pozadie;

	
	@Override
	public void create () {
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 700,700);

		pozadie = new Texture(Gdx.files.internal("miestnost.png"));
		hrac = new Hrac();
		prisera = new Jarda();
	}

	@Override
	public void render () {
		ScreenUtils.clear(1, 0, 0, 1);

		camera.update();
		batch.setProjectionMatrix(camera.combined);

		batch.begin();
		batch.draw(this.pozadie, 0, 0);
		batch.draw(hrac.getTextura(), this.hrac.getX(), this.hrac.getY());
		if (this.prisera != null) {
			batch.draw(prisera.getTextura(), this.prisera.getX(), this.prisera.getY());
		}
		batch.end();

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

		if (this.prisera != null && this.hrac.getHitbox().overlaps(this.prisera.getHitbox())) {
			this.prisera = null;
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
}
