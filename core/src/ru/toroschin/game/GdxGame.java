package ru.toroschin.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.ScreenUtils;

public class GdxGame extends ApplicationAdapter {
    SpriteBatch batch;
    Texture img;
    TextureRegion region;
    float time;
    float scale;
    MenAnimation menAnimation;
    Label label;

    private int x, y;

    @Override
    public void create() {
        batch = new SpriteBatch();
        img = new Texture("Header.png");
        scale = 1;
        region = new TextureRegion(img);
        menAnimation = new MenAnimation("mario.png", 9, 1, 16, Animation.PlayMode.LOOP);

        label = new Label(36);
    }

    @Override
    public void render() {
        ScreenUtils.clear(0.1f, 0.1f, 0.3f, 1);

//        float x = Gdx.input.getX();
//        float y = Gdx.graphics.getHeight() - Gdx.input.getY();

//        time += Gdx.graphics.getDeltaTime();

        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            scale -= 0.1;
        }
        if (Gdx.input.isButtonJustPressed(Input.Buttons.RIGHT)) {
            scale += 0.1;
        }

        int prevX = x;
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) x--;
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) x++;

        if (prevX == x) {
            menAnimation.reSetTime();
        }

        menAnimation.step(Gdx.graphics.getDeltaTime());
        batch.begin();
        batch.draw(img, 0, 0);
        batch.draw(menAnimation.getFrame(), x, y);
        label.draw(batch, "Привет, мир!", 10, Gdx.graphics.getHeight() - 10);
//        batch.draw(img, x, y, 0, 0, img.getWidth(), img.getHeight(), scale, scale, 0, 0, 0, img.getWidth(), img.getHeight(), false, false);
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        img.dispose();
        menAnimation.dispose();
        label.dispose();
    }
}
