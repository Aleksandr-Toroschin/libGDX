package ru.toroschin.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class GdxGame extends ApplicationAdapter {
    SpriteBatch batch;
    Texture img;
    float time;
    float scale;

    @Override
    public void create() {
        batch = new SpriteBatch();
        img = new Texture("badlogic.jpg");
        scale = 1;
    }

    @Override
    public void render() {
        ScreenUtils.clear(0.1f, 0.1f, 0.3f, 1);

        float x = Gdx.input.getX();
        float y = Gdx.graphics.getHeight() - Gdx.input.getY();

        time += Gdx.graphics.getDeltaTime();

        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            scale -= 0.1;
        }
        if (Gdx.input.isButtonJustPressed(Input.Buttons.RIGHT)) {
            scale += 0.1;
        }
        batch.begin();
        batch.draw(img, x, y, 0, 0, img.getWidth(), img.getHeight(), scale, scale, 0, 0, 0, img.getWidth(), img.getHeight(), false, false);
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        img.dispose();
    }
}
