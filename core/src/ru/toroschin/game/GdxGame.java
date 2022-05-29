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

	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
	}

	@Override
	public void render () {
		ScreenUtils.clear(0.1f, 0.1f, 0.3f, 1);

		float x = Gdx.input.getX();
		float y = Gdx.graphics.getHeight() - Gdx.input.getY();

		time += Gdx.graphics.getDeltaTime();
		System.out.println(time);

		batch.begin();
		batch.draw(img, x, y, 0, 0, img.getWidth(), img.getHeight(), 1, 1, 0, 0, 0, img.getWidth(), img.getHeight(), false, false);
		batch.end();
		if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
			ScreenUtils.clear(0.1f, 0.1f, 0.3f, 1);
		}
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
