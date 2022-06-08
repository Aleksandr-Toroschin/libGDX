package ru.toroschin.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Person {
    private AnimationPLayer idle, jump, walkRight;
    private boolean isJump, isWalk, dir;
    private Vector2 pos;
    private Rectangle rect;

    public Person() {
        idle = new AnimationPLayer("", 1, 1, 16f, Animation.PlayMode.LOOP);
        jump = new AnimationPLayer("", 1, 1, 16f, Animation.PlayMode.LOOP);
        walkRight = new AnimationPLayer("", 1, 1, 16f, Animation.PlayMode.LOOP);
        pos = new Vector2(0, 0);
        rect = new Rectangle(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2, walkRight.getFrame().getRegionWidth(), walkRight.getFrame().getRegionHeight());
    }

    public TextureRegion getRegion() {
        walkRight.step(Gdx.graphics.getDeltaTime());
        return walkRight.getFrame();
    }

    public Vector2 getPos() {
        return pos;
    }

    public Rectangle getRect() {
        return rect;
    }
}
