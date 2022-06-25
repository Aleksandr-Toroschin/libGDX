package ru.toroschin.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Iterator;

import ru.toroschin.game.Coin;
import ru.toroschin.game.Label;
import ru.toroschin.game.Person;

import static java.lang.Thread.sleep;

public class LoadScreen implements Screen {
    final Game game;
    Texture texture;
    SpriteBatch batch;
    Music music;
    private Person mario;
    private Label label;
    State state;

    public LoadScreen(Game game, State state) {
        this.game = game;
        this.state = state;
        batch = new SpriteBatch();
        texture = new Texture("fon.png");
        mario = new Person();
        mario.setWalk(true);
        mario.setDir(false);
        label = new Label(20, Color.BLACK);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        String text = "нажми Enter или коснись экрана";
        batch.begin();
        batch.draw(texture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.draw(mario.getFrame(), 30, Gdx.graphics.getHeight() / 2 - mario.getRect().height / 2);
        label.draw(batch, "Марио", 40 + mario.getRect().width, Gdx.graphics.getHeight() / 2);
        switch (state) {
            case BEGIN:
                label.draw(batch, "Для начала игры ", 40 + mario.getRect().width, Gdx.graphics.getHeight() / 2 - 50);
                label.draw(batch, text, 40 + mario.getRect().width, Gdx.graphics.getHeight() / 2 - 80);
                break;
            case VIN:
                label.draw(batch, "Поздравляем, ты собрал все монетки!!!", 40 + mario.getRect().width, Gdx.graphics.getHeight() / 2 - 50);
                label.draw(batch, "Для начала новой игры ", 40 + mario.getRect().width, Gdx.graphics.getHeight() / 2 - 80);
                label.draw(batch, text, 40 + mario.getRect().width, Gdx.graphics.getHeight() / 2 - 110);
                break;
            case GAME_OVER:
                label.draw(batch, "Очень жаль, но ты проиграл...", 40 + mario.getRect().width, Gdx.graphics.getHeight() / 2 - 50);
                label.draw(batch, "Попробуй еще раз", 40 + mario.getRect().width, Gdx.graphics.getHeight() / 2 - 80);
                label.draw(batch, "Для начала новой игры ", 40 + mario.getRect().width, Gdx.graphics.getHeight() / 2 - 110);
                label.draw(batch, text, 40 + mario.getRect().width, Gdx.graphics.getHeight() / 2 - 140);
        }
        batch.end();

        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER) || Gdx.input.justTouched()) {
            dispose();
            game.setScreen(new GameScreen(game));
        }

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

    @Override
    public void dispose() {
        texture.dispose();
        label.dispose();
        batch.dispose();
    }
}
