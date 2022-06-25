package ru.toroschin.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Person {
    private AnimationPlayer idle, jump, walkRight;
    private boolean isJump, isWalk, dir;
    private Vector2 pos;
    private Rectangle rect;
    private TextureRegion tmpTex;

    public Person() {
        idle = new AnimationPlayer("idle.png", 1, 1, 16f, Animation.PlayMode.LOOP);
        jump = new AnimationPlayer("jump.png", 3, 1, 16f, Animation.PlayMode.LOOP);
        walkRight = new AnimationPlayer("walk.png", 6, 1, 16f, Animation.PlayMode.LOOP);
        pos = new Vector2(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
        rect = new Rectangle(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2, walkRight.getFrame().getRegionWidth(), walkRight.getFrame().getRegionHeight());
    }

    public void setStates(boolean dir, boolean walk, boolean jump) {
        setJump(jump);
        isWalk = walk;
        this.dir = dir;
    }

    public void setJump(boolean jump) {
        isJump = jump;
    }

    public void setWalk(boolean walk) {
        isWalk = walk;
    }

    public void setDir(boolean dir) {
        this.dir = dir;
    }

    public TextureRegion getFrame() {
//        TextureRegion tmpTex = this.getFrame();
        if (!isJump && !isWalk) {
            idle.step(Gdx.graphics.getDeltaTime());
            if ((dir && !idle.getFrame().isFlipX()) || (!dir && idle.getFrame().isFlipX()))
                idle.getFrame().flip(true, false);
            tmpTex = idle.getFrame();
        } else if (!isJump && isWalk) {
            walkRight.step(Gdx.graphics.getDeltaTime());
            if ((dir && !walkRight.getFrame().isFlipX()) || (!dir && walkRight.getFrame().isFlipX()))
                walkRight.getFrame().flip(true, false);
            tmpTex = walkRight.getFrame();
        } else if (isJump && !isWalk) {
            jump.step(Gdx.graphics.getDeltaTime());
            if ((dir && !jump.getFrame().isFlipX()) || (!dir && jump.getFrame().isFlipX()))
                jump.getFrame().flip(true, false);
            tmpTex = jump.getFrame();
        } else {
            idle.step(Gdx.graphics.getDeltaTime());
            if ((dir && !idle.getFrame().isFlipX()) || (!dir && idle.getFrame().isFlipX()))
                idle.getFrame().flip(true, false);
            tmpTex = idle.getFrame();
        }
        return tmpTex;
    }

    public Vector2 getPos() {
        return pos;
    }

    public Rectangle getRect() {
        return rect;
    }

    public void dispose() {
        idle.dispose();
        jump.dispose();
        walkRight.dispose();
    }
}
