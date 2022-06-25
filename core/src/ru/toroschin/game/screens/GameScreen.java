package ru.toroschin.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
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
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import ru.toroschin.game.AnimationPlayer;
import ru.toroschin.game.Coin;
import ru.toroschin.game.Label;
import ru.toroschin.game.Person;
import ru.toroschin.game.PhysX;

import static java.lang.Thread.sleep;

public class GameScreen implements Screen {
    final Game game;
    private SpriteBatch batch;
    private TextureRegion region;
    private float time;
    private float scale;
    private Person mario;
    private Label label;
    private TiledMap map;
    private OrthogonalTiledMapRenderer mapRenderer;
    private OrthographicCamera camera;
    private List<Coin> coins;
    private Texture fon;
    private ShapeRenderer shapeRenderer;

    private int[] foreGround, backGround;
    private Color heroColor;
    private int score;
    private int countCoins;

    private PhysX physX;
    private AnimationPlayer barrelTexture;

    private Music music;

    public GameScreen(Game game) {
        this.game = game;
        map = new TmxMapLoader().load("maps/map2.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(map);

        physX = new PhysX();
        if (map.getLayers().get("Земля") != null) {
            MapObjects mo = map.getLayers().get("Земля").getObjects();
            physX.addObjects(mo);
            MapObject ho = map.getLayers().get("camera").getObjects().get("hero");
            physX.addObject(ho);
        }

        barrelTexture = new AnimationPlayer("barrel.png", 1, 1, 10, Animation.PlayMode.LOOP);
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.update();
        RectangleMapObject o = (RectangleMapObject) map.getLayers().get("camera").getObjects().get("camera");
        camera.position.x = physX.getHero().getPosition().x; //o.getRectangle().x;
        camera.position.y = physX.getHero().getPosition().y; //o.getRectangle().y;
        camera.zoom = 0.65f;
        camera.update();

        mario = new Person();

        fon = new Texture("fon.png");
        batch = new SpriteBatch();

        scale = 1;

        label = new Label(20, Color.CYAN);

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
        countCoins = coins.size();

        music = Gdx.audio.newMusic(Gdx.files.internal("fon.mp3"));
        music.setLooping(true);
        music.setVolume(0.125f);
        music.play();

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.1f, 0.1f, 0.3f, 1);

        mario.setWalk(false);
        mario.setJump(false);
//        boolean left=false;
//        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || (Gdx.input.isButtonPressed(Input.Buttons.LEFT) && Gdx.input.getX()<Gdx.graphics.getWidth()/2)) left = true;
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || (Gdx.input.isButtonPressed(Input.Buttons.LEFT) && Gdx.input.getX()<Gdx.graphics.getWidth()/2 && Math.abs(Gdx.input.getY() - Gdx.graphics.getHeight()/2) < 50)) {
            physX.setHeroForce(new Vector2(-3000.0f, 0));
            mario.setDir(true);
            mario.setWalk(true);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || (Gdx.input.isButtonPressed(Input.Buttons.LEFT) && Gdx.input.getX()>Gdx.graphics.getWidth()/2 && Math.abs(Gdx.input.getY() - Gdx.graphics.getHeight()/2) < 50)) {
            physX.setHeroForce(new Vector2(3000.0f, 0));
            mario.setDir(false);
            mario.setWalk(true);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP) || (Gdx.input.isButtonPressed(Input.Buttons.LEFT) && Gdx.input.getY()<Gdx.graphics.getHeight()/2 && Math.abs(Gdx.input.getY() - Gdx.graphics.getHeight()/2) > 50)) {
            if (physX.getSens().isOnLadder()) {
                physX.setHeroForce(new Vector2(0, 20000));
            } else if (physX.getSens().isOnGround()) {
                physX.setHeroForce(new Vector2(0, 50000));
                mario.setWalk(false);
                mario.setJump(true);
            }
        }
        boolean badContact = physX.getSens().isBadContact();


        camera.position.x = physX.getHero().getPosition().x;
        camera.position.y = physX.getHero().getPosition().y;
        camera.update();

        batch.begin();
        batch.draw(fon, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();

        mapRenderer.setView(camera);
        mapRenderer.render(backGround);

        batch.begin();
        batch.draw(mario.getFrame(), Gdx.graphics.getWidth() / 2 - mario.getRect().width / 2, Gdx.graphics.getHeight() / 2 - mario.getRect().height / 2);
//        batch.draw(marioNew.getFrame(), physX.getHero().getPosition().x - marioNew.getRect().width/2, physX.getHero().getPosition().y - marioNew.getRect().height/2);
        label.draw(batch, "Монеток: " + score + " из " + countCoins, 10, Gdx.graphics.getHeight() - 10);

        Iterator<Coin> iterator = coins.iterator();
        while (iterator.hasNext()) {
            Coin coin = iterator.next();
            coin.draw(batch, camera);
            if (coin.isOverlaps(mario.getRect(), camera)) {
                iterator.remove();
                score++;
            }
        }
        for (Body barrel : physX.getBarrels()) {
//            float radius = barrel.getFixtureList().get(0).getShape().getRadius();
            float cx = (barrel.getPosition().x - camera.position.x) / camera.zoom + Gdx.graphics.getWidth() / 2 - 16;
            float cy = (barrel.getPosition().y - camera.position.y) / camera.zoom + Gdx.graphics.getHeight() / 2 - 16;
            batch.draw(barrelTexture.getFrame(), cx, cy);
        }

        batch.end();

        mapRenderer.render(foreGround);

        physX.step();
        physX.debugDraw(camera);

        if (score >= countCoins) changeScreen("Vin");
        if (badContact) changeScreen("Over");
    }

    private void changeScreen(String type) {
        dispose();
        try {
            sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        switch (type) {
            case "Vin":
                game.setScreen(new LoadScreen(game, State.VIN));
                break;
            case "Over":
                game.setScreen(new LoadScreen(game, State.GAME_OVER));
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        label.dispose();
        for (Coin coin : coins) {
            coin.dispose();
        }
        mapRenderer.dispose();
        barrelTexture.dispose();
        fon.dispose();
        physX.dispose();
        mario.dispose();
        batch.dispose();
        music.dispose();
    }
}
