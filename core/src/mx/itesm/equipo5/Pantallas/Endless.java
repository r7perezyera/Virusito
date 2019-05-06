package mx.itesm.equipo5.Pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.LinkedList;
import java.util.Vector;

import mx.itesm.equipo5.JoyStick;
import mx.itesm.equipo5.MasterScreen;
import mx.itesm.equipo5.Objects.FriendlyBullet;
import mx.itesm.equipo5.Objects.Minion;
import mx.itesm.equipo5.Objects.Player;
import mx.itesm.equipo5.Objects.difficulty;
import mx.itesm.equipo5.Objects.enemyType;
import mx.itesm.equipo5.Objects.movementPattern;
import mx.itesm.equipo5.Objects.viewingDirection;
import mx.itesm.equipo5.Text;
import mx.itesm.equipo5.Virusito;

class Endless extends MasterScreen {

    //Esto es para probar colisiones
    private LinkedList<Rectangle> walls;
    private float timeSinceDamage;

    private LinkedList<FriendlyBullet> bullets = new LinkedList<FriendlyBullet>();
    private LinkedList<Minion> enemies = new LinkedList<Minion>();
    private float timeSinceShot;
    private float friendlyShotCooldown = 0.5f;

    private Text text;
    private TiledMap map;
    private OrthogonalTiledMapRenderer mapRenderer;

    // HUD
    private OrthographicCamera HUDcamera;
    private Viewport HUDview;
    private Stage HUDstage;
    private Texture life;

    private Touchpad shootingStick;
    private Touchpad movingStick;

    // vamos a agregar una simulacion de fisica
    private World world;    // simulacion
    private Body body;    // quien recibe / esta dentro de la simulacion
    private Box2DDebugRenderer b2dr;

    private Player player; //Personaje

    private LinkedList<Rectangle> enemyRect;
    private Music music;

    // Users preferences
    private Preferences lvlPrefs = Gdx.app.getPreferences("userPrefs");
    boolean isSoundOn = lvlPrefs.getBoolean("soundOn");

    //rounds
    private difficulty diff = difficulty.EASY;
    private enemyType type;
    private int round = 0;



    //Box2D

    public Endless(Virusito juego) {
        super(juego);
        world = new World(new Vector2(0,0),true);
        b2dr = new Box2DDebugRenderer();

        BodyDef bdef = new BodyDef();
        bdef.position.set(640,360);
        bdef.type = BodyDef.BodyType.StaticBody;

        Body body = world.createBody(bdef);
        //static never move, dynamic are affected, kinematic not affected by world forces


    }

    //Update world
    public void updateWorld(float dt){
        world.step(dt,6,2);

    }

    @Override
    public void show() {
    // Agregar la escena, finalmente

        loadMap();
        buildHUD();
        createJoysticks();
        getWalls();
        spawn();
        getEnemies();

        if (isSoundOn) {
            loadMusic();
        }

        player = new Player(300,300,3);

        Gdx.input.setCatchBackKey(false);
    }

    private void loadMusic() {
        music = Gdx.audio.newMusic(Gdx.files.internal("Music/DifferentHeaven-Nekozilla.mp3"));
        music.setLooping(true);
        music.play();
    }


    private void loadMap() {
        AssetManager manager = new AssetManager();
        manager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
        manager.load("Mapa1/endless.tmx", TiledMap.class);
        manager.finishLoading();
        map = manager.get("Mapa1/endless.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(map);
    }

    private void buildHUD() {
        HUDcamera = new OrthographicCamera(WIDTH, HEIGHT);
        HUDcamera.position.set(WIDTH/2, HEIGHT/2, 0);
        HUDcamera.update();
        HUDview = new StretchViewport(WIDTH, HEIGHT, HUDcamera);

        HUDstage = new Stage(HUDview);

        // ahora la escena es quien atiende los eventos
        Gdx.input.setInputProcessor(HUDstage);
    }

    private void createJoysticks() {
        Box2D.init();
        BodyDef def = new BodyDef();
        def.type = BodyDef.BodyType.DynamicBody;
        def.position.set(0, 0);
        body = world.createBody(def);

        movingStick = new JoyStick("HUD/Pad/padBack.png", "HUD/Pad/padKnob.png", body).getPad();
        movingStick.setPosition(16,16);
        shootingStick = new JoyStick("HUD/Pad/padBack.png", "HUD/Pad/padKnob.png", body).getPad();
        shootingStick.setPosition(WIDTH-256,16);

        // Agregar la escena, finalmente
        HUDstage.addActor(shootingStick);
        HUDstage.addActor(movingStick);
    }


    @Override
    public void render(float delta) {
        //Box2D
        b2dr.render(world,camera.combined);

        eraseScreen();

        timeSinceShot += delta;
        timeSinceDamage += delta;
        shoot();

        updateCharacter(movingStick.getKnobPercentX(), movingStick.getKnobPercentY());

        batch.setProjectionMatrix(camera.combined);
        // render the game map
        mapRenderer.setView(camera);
        mapRenderer.render();

        if (player.getHealth()==3){
            life = new Texture("HUD/Bateria/Bateria_Llena.png");
        }else if (player.getHealth()==2){
            life = new Texture("HUD/Bateria/Bateria_Agotando.png");
        }else if (player.getHealth()==1){
            life = new Texture("HUD/Bateria/Bateria_Ultima.png");
        }else {
            game.setScreen(new LoseScreen(game));
            if (isSoundOn) {
                music.stop();
            }
        }

        batch.begin();
        player.render(batch);
        batch.draw(life, WIDTH/2-(life.getWidth()/2f),650);

        // construir Text
        text = new Text();
        text.displayText(batch, "Round: " +round, MasterScreen.WIDTH/6, 5*(MasterScreen.HEIGHT/6)+100);
        text.displayText(batch, "Enemies: " +enemies.size(), MasterScreen.WIDTH*5/6, 5*(MasterScreen.HEIGHT/6)+100);


        if (!bullets.isEmpty()){
            for (int i =bullets.size()-1;i>=0;i--){
                FriendlyBullet bullet = bullets.get(i);
                bullet.render(batch);
                bullet.update();
                updateBullet();
            }
        }
        if (!enemies.isEmpty()){
            for (Minion minion : enemies){
                minion.render(batch);
                minion.move(player.getX(), player.getY());
            }
        }else {
            spawn();
            getEnemies();

        }


        batch.end();
        batch.setProjectionMatrix(HUDcamera.combined);
        HUDstage.draw();
    }

    private void spawn() {
        enemies = new LinkedList<Minion>();
        int numEnemies = 0;

        round++;
        if (diff == difficulty.EASY){
            numEnemies = 5;
            type = enemyType.FLOATER;
        }else if (diff == difficulty.MEDIUM){
            numEnemies = 8;
            type = enemyType.TEETH;
        }else if (diff == difficulty.HARD){
            numEnemies = 10;
            type = enemyType.CRAWLER;
        }

        if (round%3 == 0){
            numEnemies = 7;
            diff.next();
            Minion minion = new Minion(type.next(), movementPattern.FOLLOWER, diff, 500, 500);
            enemies.add(minion);
        }

        int xbegin = 800;
        int ybegin = 500;
        for (int i = 0; i<numEnemies; i++){
            xbegin += 50;
            ybegin += 50;
            Minion minion = new Minion(type, movementPattern.ZIGZAG, diff, xbegin, ybegin);
            enemies.add(minion);
        }


    }

    private void shoot() {
        float changeX = shootingStick.getKnobPercentX();
        float changeY = shootingStick.getKnobPercentY();
        //Checks angle of shot
        Vector2 vector = new Vector2(changeX,changeY);
        float angle = vector.angle();

        if(timeSinceShot<=friendlyShotCooldown) {
            if ((0 < angle && angle <= 45) || (316 <= angle && angle <= 360)) {
                FriendlyBullet bullet = new FriendlyBullet(player.getX()+player.getWidth()/2, player.getY()+player.getHeight()/2, 0);
                bullets.add(bullet);
                timeSinceShot=friendlyShotCooldown+ 0.1f;
            } else if (46 <= angle && angle <= 136) {
                FriendlyBullet bullet = new FriendlyBullet(player.getX()+player.getWidth()/2, player.getY()+player.getHeight()/2, (float) Math.PI / 2);
                bullets.add(bullet);
                timeSinceShot=friendlyShotCooldown+ 0.1f;
            } else if (136 <= angle && angle <= 225) {
                FriendlyBullet bullet = new FriendlyBullet(player.getX()+player.getWidth()/2, player.getY()+player.getHeight()/2, (float) Math.PI);
                bullets.add(bullet);
                timeSinceShot=friendlyShotCooldown+ 0.1f;
            } else if (226 <= angle && angle <= 315) {
                FriendlyBullet bullet = new FriendlyBullet(player.getX()+player.getWidth()/2, player.getY()+player.getHeight()/2, (float) (3 * Math.PI) / 2);
                bullets.add(bullet);
                timeSinceShot=friendlyShotCooldown+ 0.1f;
            }

        }else if (timeSinceShot >= friendlyShotCooldown*2) {
            timeSinceShot=0.0f;
        }
    }

    private void getWalls(){
        walls = new LinkedList<Rectangle>();
        for(MapObject object : map.getLayers().get("Paredes").getObjects()){
            if(object instanceof RectangleMapObject){
                Rectangle rect = ((RectangleMapObject) object).getRectangle();
                walls.add(rect);
            }
        }
    }

    private void getEnemies(){
        enemyRect = new LinkedList<Rectangle>();
        for(Minion enemy : enemies){
                Rectangle rect = enemy.getRectangle();
                enemyRect.add(rect);
            }
    }

    private void updateCharacter(float dx, float dy) {
        Rectangle checkRectangle;
        checkRectangle = new Rectangle();
        checkRectangle.set(player.getRectangle());

        //animation
        float changeX = movingStick.getKnobPercentX();
        float changeY = movingStick.getKnobPercentY();
        Vector2 vector = new Vector2(changeX,changeY);
        float angle = vector.angle();

        if ((0 < angle && angle <= 45) || (316 <= angle && angle <= 360)) {
            player.setDir(viewingDirection.RIGHT);
        } else if (46 <= angle && angle <= 136) {
            player.setDir(viewingDirection.FRONT);
        } else if (136 <= angle && angle <= 225) {
            player.setDir(viewingDirection.LEFT);
        } else if (226 <= angle && angle <= 315) {
            player.setDir(viewingDirection.FRONT);
        }

        float newPosY = player.getSprite().getY() + (dy * player.getSpeed());
        float newPosX = player.getSprite().getX() + (dx * player.getSpeed());
        checkRectangle.setPosition(newPosX, newPosY);


        boolean collides = collidesWith(walls, checkRectangle);
        if (!collides) {
            player.moveX(dx);
            player.moveY(dy);
        }
        if (collidesWith(enemyRect, checkRectangle)) {
            if(timeSinceDamage>2){
                player.setHealth(player.getHealth()-1);
                timeSinceDamage=0;
            }
        }

            // TODO set Virusito's coordinates, load new map
    }


    private void updateBullet(){

        for(int i =bullets.size()-1;i>=0;i--){
            FriendlyBullet bullet = bullets.get(i);
            Rectangle checkRectangle;
            checkRectangle = new Rectangle();
            checkRectangle.set(bullet.getRectangle());
            float newPosY = bullet.getSprite().getY() + bullet.getSpeed();
            float newPosX = bullet.getSprite().getX() + bullet.getSpeed();
            checkRectangle.setPosition(newPosX, newPosY);

            if(collidesWith(walls,checkRectangle)) {
                bullet.destroy();
                bullets.remove(i);
            }

            for (int j = enemies.size()-1; j >= 0; j--){
                    if (checkRectangle.overlaps(enemies.get(j).getRectangle())){
                        bullet.destroy();
                        bullets.remove(i);
                        enemies.get(j).doDamage(1);
                        if (enemies.get(j).isDestroyed()) {
                            enemies.remove(j);
                            enemyRect.remove(j);
                        }

                    }
            }
        }
    }

    public boolean collidesWith(LinkedList<Rectangle> rectangles, Rectangle checkRectangle){
        for(Rectangle rectangle : rectangles){
            if(checkRectangle.overlaps(rectangle)) return true;
        }
        return false;
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
        HUDstage.dispose();
        mapRenderer.dispose();
    }
}
