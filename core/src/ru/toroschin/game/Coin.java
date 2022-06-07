package ru.toroschin.game;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Coin {
    private AnimationPLayer player;
    private Vector2 position;

    public Coin(Vector2 position) {
        this.player = new AnimationPLayer("Full Coins.png", 8, 1, 10, Animation.PlayMode.LOOP);
        this.position = position;
    }

    public void draw(SpriteBatch batch) {
        batch.draw(player.getFrame(), position.x, position.y);
    }

    public void dispose() {
        player.dispose();
    }
}
