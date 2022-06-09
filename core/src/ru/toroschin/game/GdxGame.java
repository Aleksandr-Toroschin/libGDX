package ru.toroschin.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GdxGame extends ApplicationAdapter {
    private SpriteBatch batch;
    private TextureRegion region;
    private float time;
    private float scale;
//    private AnimationPLayer animationMario;
    private Person marioNew;
    private Label label;
    private TiledMap map;
    private OrthogonalTiledMapRenderer mapRenderer;
    private OrthographicCamera camera;
    private List<Coin> coins;
    private Texture fon;
    private ShapeRenderer shapeRenderer;
//    private Rectangle heroRect;

    private int[] foreGround, backGround;
    private int x, y;
    private Color heroColor;
    private int score;

    @Override
    public void create() {

        marioNew = new Person();

        fon = new Texture("fon.png");
        batch = new SpriteBatch();
//        shapeRenderer = new ShapeRenderer();

        scale = 1;
//        animationMario = new AnimationPLayer("mario.png", 9, 1, 16, Animation.PlayMode.LOOP);
//        heroRect = new Rectangle(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2, animationMario.getFrame().getRegionWidth(), animationMario.getFrame().getRegionHeight());

        label = new Label(20);
        map = new TmxMapLoader(). load("maps/map2.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(map);

        foreGround = new int[1];
        foreGround[0] = map.getLayers().getIndex("Tiles2");
        backGround = new int[1];
        backGround[0] = map.getLayers().getIndex("Tiles1");


        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.update();
        RectangleMapObject o = (RectangleMapObject) map.getLayers().get("camera").getObjects().get("camera");
        camera.position.x = o.getRectangle().x;
        camera.position.y = o.getRectangle().y;
        camera.zoom = 0.65f;
        camera.update();

        coins = new ArrayList<>();
        MapLayer mapLayerCoin = map.getLayers().get("Coins");
        if (mapLayerCoin != null) {
            MapObjects mo = mapLayerCoin.getObjects();
            if (mo.getCount() > 0) {
                for (MapObject mapObject : mo) {
                    RectangleMapObject rect = (RectangleMapObject) mapObject;
                    coins.add(new Coin(new Vector2(rect.getRectangle().x, rect.getRectangle().y)));
                }
            }

        }
    }

    @Override
    public void render() {
        ScreenUtils.clear(0.1f, 0.1f, 0.3f, 1);

        float prevX = camera.position.x;
        float prevY = camera.position.y;
        marioNew.setWalk(false);
        marioNew.setJump(false);
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            camera.position.x--;
            marioNew.setDir(true);
            marioNew.setWalk(true);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            camera.position.x++;
            marioNew.setDir(false);
            marioNew.setWalk(true);
        }
//        if (Gdx.input.isKeyPressed(Input.Keys.UP)) camera.position.y++;
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) camera.position.y--;
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            camera.position.y += 60;
            marioNew.setWalk(false);
            marioNew.setJump(true);
        }
        camera.update();

        batch.begin();
        batch.draw(fon, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();

        mapRenderer.setView(camera);
        mapRenderer.render(backGround);

//        if ((Math.abs(prevX - camera.position.x) < 0.01) && (Math.abs(prevY - camera.position.y) < 0.01)) {
//            animationMario.reSetTime();
//        }
//        animationMario.step(Gdx.graphics.getDeltaTime());

        batch.begin();
        batch.draw(marioNew.getFrame(), Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
        label.draw(batch, "Монеток: " + score, 10, Gdx.graphics.getHeight() - 10);

        Iterator<Coin> iterator = coins.iterator();
        while (iterator.hasNext()) {
            Coin coin = iterator.next();
            coin.draw(batch, camera);
            if (coin.isOverlaps(marioNew.getRect(), camera)) {
                iterator.remove();
                score++;
            }
        }
        batch.end();

        mapRenderer.render(foreGround);

    }

    @Override
    public void dispose() {
        batch.dispose();
//        animationMario.dispose();
        label.dispose();
        for (Coin coin : coins) {
            coin.dispose();
        }

        fon.dispose();
    }
}
