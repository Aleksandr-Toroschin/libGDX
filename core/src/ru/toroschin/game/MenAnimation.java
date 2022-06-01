package ru.toroschin.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import javax.xml.soap.Text;

public class MenAnimation {
    Animation<TextureRegion> animation;
    Texture texture;
    private float time;
    private Animation.PlayMode loop;

    public MenAnimation(String name, int width, int height, float fps, Animation.PlayMode mode) {
        this.loop = mode;
        texture = new Texture(name);
        TextureRegion region = new TextureRegion(texture);
        TextureRegion[][] regions = region.split(region.getRegionWidth() / width, region.getRegionHeight() / height);

        TextureRegion[] regions1 = new TextureRegion[width * height];

        int cnt = 0;
        for (TextureRegion[] textureRegions : regions) {
            for (int j = 0; j < regions[0].length; j++) {
                regions1[cnt++] = textureRegions[j];
            }
        }
        animation = new Animation<>(1.0f / fps, regions1);

        animation.setPlayMode(mode);
    }

    public void step(float time) {
        this.time += time;
    }

    public  void reSetTime() {
        time = 0;
    }

    public boolean isFinished() {
        return animation.isAnimationFinished(time);
    }

    public TextureRegion getFrame() {
        return animation.getKeyFrame(time);
    }

    public void dispose() {
        texture.dispose();
    }
}
