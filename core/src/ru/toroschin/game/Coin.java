package ru.toroschin.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Coin {
    private AnimationPlayer player;
    private Vector2 position;
    private Rectangle rectangle;
//    private Sound sound;
    private int state;

    public void setState() {
//        sound.play();
        state = 1;
    }

    public Coin(Vector2 position) {
        this.player = new AnimationPlayer("maps/Full_coins.png", 8, 1, 10, Animation.PlayMode.LOOP);
        this.position = position;
        rectangle = new Rectangle(position.x, position.y, player.getFrame().getRegionWidth(), player.getFrame().getRegionHeight());
//        sound = Gdx.audio.newSound(Gdx.files.internal(""));
    }

    public int draw(SpriteBatch batch, OrthographicCamera camera) {
        player.step(Gdx.graphics.getDeltaTime());
        float cx = (position.x - camera.position.x) / camera.zoom + Gdx.graphics.getWidth() / 2;
        float cy = (position.y - camera.position.y) / camera.zoom + Gdx.graphics.getHeight() / 2;

        batch.draw(player.getFrame(), cx, cy);
        return state;
    }

    public void shapeDraw(ShapeRenderer renderer, OrthographicCamera camera) {
        float cx = (position.x - camera.position.x) / camera.zoom + Gdx.graphics.getWidth() / 2;
        float cy = (position.y - camera.position.y) / camera.zoom + Gdx.graphics.getHeight() / 2;
//        renderer.begin(ShapeRenderer.ShapeType.Line);
        renderer.rect(cx, cy, rectangle.getWidth(), rectangle.getHeight());
//        renderer.end();
    }

    public boolean isOverlaps(Rectangle heroRect, OrthographicCamera camera) {
        float cx = (position.x - camera.position.x) / camera.zoom + Gdx.graphics.getWidth() / 2;
        float cy = (position.y - camera.position.y) / camera.zoom + Gdx.graphics.getHeight() / 2;
        Rectangle rect = new Rectangle(cx, cy, rectangle.width, rectangle.height);
        return rect.overlaps(heroRect);
    }

    public void dispose() {
        player.dispose();
//        sound.dispose();
    }
}
