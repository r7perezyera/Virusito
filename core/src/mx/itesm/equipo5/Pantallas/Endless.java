package mx.itesm.equipo5.Pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.LinkedList;
import java.util.Vector;

import mx.itesm.equipo5.Button;
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
import sun.security.util.Length;

class Endless extends MasterScreen {

    private AssetManager assetManager;

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

    // Box2D variables
    private World world;    // simulacion
    private Body body;    // quien recibe / esta dentro de la simulacion
    private Box2DDebugRenderer b2dr;

    private Player player; //Personaje

    private LinkedList<Rectangle> enemyRect;
    private Music music;
    private Sound shootingSound;
    private Sound playerDeathSound;
    private Sound minionDeathSound;
    private Sound bossDeathSound;


    // Users preferences
    private Preferences lvlPrefs = Gdx.app.getPreferences("userPrefs");
    boolean isSoundOn = lvlPrefs.getBoolean("soundOn");

    //rounds
    private difficulty diff = difficulty.EASY;
    private enemyType type;
    private int round = 0;
    private Texture item;

    private GameState gameState;

    private ImageButton pauseButton;

    private PauseScene pauseScene;



    //Box2D

    public Endless(Virusito juego) {
        super(juego);


    }


    @Override
    public void show() {
    // Agregar la escena, finalmente

        loadMap();
        setPhysics();
        buildHUD();
        createJoysticks();
        getWalls();
        spawn();
        getEnemies();

        item = new Texture("Items/Battery.png");

        loadText();
        gameState = GameState.PLAYING;

        if (isSoundOn) {
            loadMusic();
            loadSFX();
        }

        player = new Player(300,300,3,this.world);

        Gdx.input.setCatchBackKey(false);
    }

    private void setPhysics() {
        world = new World(new Vector2(0,0),true);
        b2dr = new Box2DDebugRenderer();

        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;


        for (MapObject object : map.getLayers().get("Paredes").getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set(rect.getX()+rect.getWidth()/2,rect.getY()+rect.getHeight()/2);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth()/2,rect.getHeight()/2);
            fdef.shape = shape;

            body.createFixture(fdef);
        }
    }

    private void loadText() {
        // construir Text
        text = new Text();
    }

    private void loadMusic() {
        music = Gdx.audio.newMusic(Gdx.files.internal("Music/DifferentHeaven-Nekozilla.mp3"));
        music.setLooping(true);
        music.play();
    }

    private void loadSFX(){
        shootingSound = Gdx.audio.newSound(Gdx.files.internal("Music/SFX/Shoot.wav"));
        playerDeathSound = Gdx.audio.newSound(Gdx.files.internal("Music/SFX/PlayerDeath.wav"));
        minionDeathSound = Gdx.audio.newSound(Gdx.files.internal("Music/SFX/DeathMinion.wav"));
        bossDeathSound = Gdx.audio.newSound(Gdx.files.internal("Music/SFX/DeathBoss.wav"));
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


        Texture pauseButton = new Texture("Botones/Pause_Bttn.png");
        TextureRegionDrawable trdPauseButton = new TextureRegionDrawable(new TextureRegion(pauseButton));
        ImageButton imgPauseButton = new Button("Botones/Pause_Bttn.png").getiButton();
        imgPauseButton.setPosition(MasterScreen.WIDTH - imgPauseButton.getWidth(), MasterScreen.HEIGHT - imgPauseButton.getHeight());
        imgPauseButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // response
                //clairo.disableControls=true;
                gameState = GameState.PAUSED;

                // check if it works fine before sticking it to the whole block
                // turn pause scene on
                pauseScene = new PauseScene(HUDview, batch);
                Gdx.input.setInputProcessor(pauseScene);

            }
        });





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

        eraseScreen();

        //Update World
        world.step(1/60f,6,2);

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
                playerDeathSound.play();
                music.stop();
            }
        }

        batch.begin();
        player.render(batch);
        batch.draw(life, WIDTH/2-(life.getWidth()/2f),650);
        batch.draw(item, WIDTH/2-(item.getWidth()/2f), HEIGHT/2-(item.getWidth()/2f));

        text.displayHUDText(batch, "Round: " +round, MasterScreen.WIDTH/6, 5*(MasterScreen.HEIGHT/6)+100);
        text.displayHUDText(batch, "Enemies: " +enemies.size(), MasterScreen.WIDTH*5/6, 5*(MasterScreen.HEIGHT/6)+100);


        if (!bullets.isEmpty()){
            updateBullet();
            for (int i =bullets.size()-1;i>=0;i--){
                if (bullets.get(i).isDestroyed()){
                    bullets.remove(i);
                }else {
                    FriendlyBullet bullet = bullets.get(i);
                    bullet.render(batch);
                    bullet.update();
                }

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

        if(Gdx.input.isKeyPressed(Input.Keys.BACK)){
            gameState = GameState.PAUSED;
            pauseScene = new PauseScene(HUDview, batch);
            Gdx.input.setInputProcessor(pauseScene);
        }

        //Box2D
        b2dr.render(world,camera.combined);
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
            diff = diff.next();
            System.out.println(diff);
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
                if(isSoundOn){
                    shootingSound.play();
                }
            } else if (46 <= angle && angle <= 136) {
                FriendlyBullet bullet = new FriendlyBullet(player.getX()+player.getWidth()/2, player.getY()+player.getHeight()/2, (float) Math.PI / 2);
                bullets.add(bullet);
                timeSinceShot=friendlyShotCooldown+ 0.1f;
                if(isSoundOn){
                    shootingSound.play();
                }
            } else if (136 <= angle && angle <= 225) {
                FriendlyBullet bullet = new FriendlyBullet(player.getX()+player.getWidth()/2, player.getY()+player.getHeight()/2, (float) Math.PI);
                bullets.add(bullet);
                timeSinceShot=friendlyShotCooldown+ 0.1f;
                if(isSoundOn){
                    shootingSound.play();
                }
            } else if (226 <= angle && angle <= 315) {
                FriendlyBullet bullet = new FriendlyBullet(player.getX()+player.getWidth()/2, player.getY()+player.getHeight()/2, (float) (3 * Math.PI) / 2);
                bullets.add(bullet);
                timeSinceShot=friendlyShotCooldown+ 0.1f;
                if(isSoundOn){
                    shootingSound.play();
                }
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
                    if (checkRectangle.overlaps(enemies.get(j).getRectangle())) {
                        bullet.destroy();
                        enemies.get(j).doDamage(1);
                        if (enemies.get(j).isDestroyed()) {
                            minionDeathSound.play();
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

    private class PauseScene extends Stage {

        public PauseScene(Viewport view, SpriteBatch batch) {
            super(view, batch);
            // Creación de texturas
            Texture texturaBtnSalir;
            Texture texturaBtnContinuar;
            Texture restartButton;

            Pixmap pixmap = new Pixmap((int) (WIDTH * 0.7f), (int) (HEIGHT * 0.8f), Pixmap.Format.RGBA8888);
            pixmap.setColor(0f, 0, 0, 0f);
            pixmap.fillRectangle(0, 0, pixmap.getWidth(), pixmap.getHeight());
            Texture texturaRectangulo = new Texture(pixmap);
            pixmap.dispose();
            Image rectImg = new Image(texturaRectangulo);
            rectImg.setPosition(0.15f * WIDTH, 0.1f * HEIGHT);
            this.addActor(rectImg);

            texturaBtnSalir = assetManager.get("Botones/Home_Bttn.png");
            TextureRegionDrawable trdSalir = new TextureRegionDrawable(
                    new TextureRegion(texturaBtnSalir));
            ImageButton btnSalir = new ImageButton(trdSalir);
            btnSalir.setPosition(WIDTH / 2 - btnSalir.getWidth() / 2, HEIGHT / 2);
            btnSalir.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    // Regresa al menú
                    if (isSoundOn) {
                        music.dispose();
                    }
                    game.setScreen(new MenuScreen(game));

                }
            });
            this.addActor(btnSalir);

            texturaBtnContinuar = assetManager.get("Botones/Play_Bttn.png");
            TextureRegionDrawable trdContinuar = new TextureRegionDrawable(
                    new TextureRegion(texturaBtnContinuar));
            ImageButton btnContinuar = new ImageButton(trdContinuar);
            btnContinuar.setPosition(WIDTH / 2 - btnContinuar.getWidth() / 2 - 150, HEIGHT / 4);
            btnContinuar.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    // return to the game
                    loadMap();
                    Gdx.input.setInputProcessor(HUDstage);
                    gameState = GameState.PLAYING;
                }
            });
            this.addActor(btnContinuar);


            /*restartButton = assetManager.get("Botones/BotonReinicioN.png");

            TextureRegionDrawable trdRestart = new TextureRegionDrawable(new TextureRegion(restartButton));

            ImageButton restartBtn = new ImageButton(trdRestart);

            restartBtn.setPosition(WIDTH/2 - restartBtn.getWidth()/2 + 150, HEIGHT/4);

            restartBtn.addListener(new ClickListener() {

                @Override

                public void clicked(InputEvent event, float x, float y) {
                    if(isSoundOn) {
                        music.stop();
                    }
                    game.setScreen(new Endless(game));

                }

            });

            this.addActor(restartBtn);*/
        }
    }

}
