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
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
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

    private World world;
    private Box2DDebugRenderer debugRenderer;
    private Body heroBody;

    @Override
    public void create() {

        world = new World(new Vector2(0, -9.81f), true);
        debugRenderer = new Box2DDebugRenderer();

        BodyDef def = new BodyDef();
        FixtureDef fdef = new FixtureDef();
        PolygonShape polygonShape = new PolygonShape();

        map = new TmxMapLoader(). load("maps/map2.tmx");
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.update();
        RectangleMapObject o = (RectangleMapObject) map.getLayers().get("camera").getObjects().get("camera");
        camera.position.x = o.getRectangle().x;
        camera.position.y = o.getRectangle().y;
        camera.zoom = 0.65f;
        camera.update();
//        def.position.set(new Vector2(0, 50));
//        def.type = BodyDef.BodyType.StaticBody;
//
//        fdef.density = 1; //плотность
//        fdef.friction = 1f; // шершавость
//        fdef.restitution = 0f; // упругость
//
//        polygonShape.setAsBox(100, 10);
//        fdef.shape = polygonShape;
//
//        world.createBody(def).createFixture(fdef);

//        def.position.set(new Vector2(50, 150));
//        def.type = BodyDef.BodyType.StaticBody;
//
//        fdef.density = 1; //плотность
//        fdef.friction = 0.5f; // шершавость
//        fdef.restitution = 1.0f; // упругость
//
//        polygonShape.setAsBox(100, 10);
//        fdef.shape = polygonShape;
//
//        world.createBody(def).createFixture(fdef);

        def.type = BodyDef.BodyType.DynamicBody;
        for (int i = 0; i < 10; i++) {
            def.position.set(new Vector2(MathUtils.random(50f, 160f), 230f));
            def.gravityScale = MathUtils.random(0.5f, 15f);

            float size = MathUtils.random(3f, 15f);
            polygonShape.setAsBox(size, size);
            fdef.shape = polygonShape;
            fdef.friction = 0.5f;
            fdef.restitution = 0.3f; // упругость
            world.createBody(def).createFixture(fdef);
        }


        def.type = BodyDef.BodyType.StaticBody;
        def.gravityScale = 10;

        mapRenderer = new OrthogonalTiledMapRenderer(map);
        MapLayer mapLayerGround = map.getLayers().get("Земля");
        if (mapLayerGround != null) {
            MapObjects moGround = mapLayerGround.getObjects();
            if (moGround.getCount() > 0) {
                for (MapObject mapObject : moGround) {
                    RectangleMapObject rect = (RectangleMapObject) mapObject;
                    def.position.set(new Vector2(rect.getRectangle().x + rect.getRectangle().width / 2, rect.getRectangle().y + rect.getRectangle().height / 2));
                    polygonShape.setAsBox(rect.getRectangle().width / 2, rect.getRectangle().height / 2);
                    fdef.shape = polygonShape;
                    fdef.friction = 0.5f;
                    fdef.restitution = 0.1f; // упругость
                    world.createBody(def).createFixture(fdef);
                }
            }

        }

        def.type = BodyDef.BodyType.DynamicBody;
        float size = 10f;
        def.position.set(new Vector2(camera.position.x + size/2, camera.position.y + size/2 + 30));
        def.gravityScale = 1f;

        polygonShape.setAsBox(size, size);
        fdef.shape = polygonShape;
        fdef.density = 0.2f;
        fdef.friction = 1f;
        heroBody = world.createBody(def);
        heroBody.createFixture(fdef);

//        Body body = world.createBody(def);


//        body.createFixture(fdef);
//        body.createFixture(fdef);
//        body.createFixture(fdef);


        polygonShape.dispose();

        marioNew = new Person();

        fon = new Texture("fon.png");
        batch = new SpriteBatch();
//        shapeRenderer = new ShapeRenderer();

        scale = 1;
//        animationMario = new AnimationPLayer("mario.png", 9, 1, 16, Animation.PlayMode.LOOP);
//        heroRect = new Rectangle(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2, animationMario.getFrame().getRegionWidth(), animationMario.getFrame().getRegionHeight());

        label = new Label(20);


        foreGround = new int[1];
        foreGround[0] = map.getLayers().getIndex("Tiles2");
        backGround = new int[1];
        backGround[0] = map.getLayers().getIndex("Tiles1");




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
            heroBody.applyForceToCenter(new Vector2(-1000.0f, 0), true);
//            camera.position.x--;
            marioNew.setDir(true);
            marioNew.setWalk(true);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            heroBody.applyForceToCenter(new Vector2(1000.0f, 0), true);
//            camera.position.x++;
            marioNew.setDir(false);
            marioNew.setWalk(true);
        }
//        if (Gdx.input.isKeyPressed(Input.Keys.UP)) camera.position.y++;
//        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) camera.position.y--;
//        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
//            camera.position.y += 60;
//            marioNew.setWalk(false);
//            marioNew.setJump(true);
//        }
        camera.position.x = heroBody.getPosition().x;
        camera.position.y = heroBody.getPosition().y;
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
        batch.draw(marioNew.getFrame(), Gdx.graphics.getWidth()/2 - 20, Gdx.graphics.getHeight()/2 - 20);
//        batch.draw(marioNew.getFrame(), heroBody.getPosition().x, heroBody.getPosition().y);
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

        world.step(1/60.0f, 3, 3);
        debugRenderer.render(world, camera.combined);
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
        world.dispose();
    }
}
