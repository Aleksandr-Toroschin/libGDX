package ru.toroschin.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class Label {
    BitmapFont bitmapFont;

    public Label(int size) {
        FreeTypeFontGenerator fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("font.ttf"));

        FreeTypeFontGenerator.FreeTypeFontParameter fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontParameter.size = size;
        fontParameter.characters = "ЙЦУКЕНГШЩЗХЪФЫВАПРОЛДЖЭЯЧСМИТЬБЮ.1234567890-=йцукенгшщзхъфывапролджэячсмитьбю,!№;%:?*()_+";

        bitmapFont = fontGenerator.generateFont(fontParameter);

//        bitmapFont = new BitmapFont();
//        bitmapFont.getData().setScale(3);
    }

    public void draw(SpriteBatch batch, String text, float x, float y) {
        bitmapFont.draw(batch, text, x, y);
    }

    public void dispose() {
        bitmapFont.dispose();
    }
}
