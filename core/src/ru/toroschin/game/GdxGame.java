package ru.toroschin.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.ScreenUtils;

public class GdxGame extends ApplicationAdapter {
    SpriteBatch batch;
//    Texture img;
    TextureRegion region;
    float time;
    float scale;
    AnimationPLayer animationMario;
    Label label;
    private TiledMap map;
    private OrthogonalTiledMapRenderer mapRenderer;
    private OrthographicCamera camera;

    private int x, y;

    @Override
    public void create() {
        batch = new SpriteBatch();
//        img = new Texture("Header.png");
        scale = 1;
//        region = new TextureRegion(img);
        animationMario = new AnimationPLayer("mario.png", 9, 1, 16, Animation.PlayMode.LOOP);

        label = new Label(36);
        map = new TmxMapLoader().load("maps/map1.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(map);
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.update();
        RectangleMapObject o = (RectangleMapObject) map.getLayers().get("Слой объектов 1").getObjects().get("camera");
        camera.position.x = o.getRectangle().x;
        camera.position.y = o.getRectangle().y;
        camera.update();
    }

    @Override
    public void render() {
        ScreenUtils.clear(0.1f, 0.1f, 0.3f, 1);

//        float x = Gdx.input.getX();
//        float y = Gdx.graphics.getHeight() - Gdx.input.getY();

//        time += Gdx.graphics.getDeltaTime();

//        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
//            scale -= 0.1;
//        }
//        if (Gdx.input.isButtonJustPressed(Input.Buttons.RIGHT)) {
//            scale += 0.1;
//        }

        float prevX = camera.position.x;
        float prevY = camera.position.y;
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) camera.position.x--;
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) camera.position.x++;
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) camera.position.y++;
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) camera.position.y--;
        camera.update();

        mapRenderer.setView(camera);
        mapRenderer.render();

        if ((Math.abs(prevX - camera.position.x) < 0.01) && (Math.abs(prevY - camera.position.y) < 0.01)) {
            animationMario.reSetTime();
        }
        animationMario.step(Gdx.graphics.getDeltaTime());

        batch.begin();
//        batch.draw(img, 0, 0);
        batch.draw(animationMario.getFrame(), Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
        label.draw(batch, "Привет, мир!", 10, Gdx.graphics.getHeight() - 10);
//        batch.draw(img, x, y, 0, 0, img.getWidth(), img.getHeight(), scale, scale, 0, 0, 0, img.getWidth(), img.getHeight(), false, false);
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
//        img.dispose();
        animationMario.dispose();
        label.dispose();
    }
}
