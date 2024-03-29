package sk.duri.relifri;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class RelifriGame extends Game {

    public SpriteBatch batch;
    public BitmapFont font;

    @Override
    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        this.setScreen(new GameScreen(this));
    }
    @Override
    public void render() {
        super.render();
    }
    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
    }


}
