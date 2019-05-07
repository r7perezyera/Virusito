package mx.itesm.equipo5.Pantallas;

import static mx.itesm.equipo5.MasterScreen.PPM;
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
import java.util.Random;
import java.util.Vector;

import mx.itesm.equipo5.B2DWorldCreator;
import mx.itesm.equipo5.Button;
import mx.itesm.equipo5.JoyStick;
import mx.itesm.equipo5.MasterScreen;
import mx.itesm.equipo5.Objects.FriendlyBullet;
import mx.itesm.equipo5.Objects.Item;
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
    private LinkedList<Item> pilas = new LinkedList<Item>();
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
    private boolean isSoundOn = lvlPrefs.getBoolean("soundOn");
    private int highestRound = lvlPrefs.getInteger("endlessBestRound");

    //rounds
    private difficulty diff = difficulty.EASY;
    private enemyType type;
    private int round = 0;


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

        assetManager = new AssetManager();


        loadTextures();
        loadMap();
        setPhysics();
        buildHUD();
        createJoysticks();
        getWalls();
        player = new Player(300,300,3,this.world);
        spawn();
        getEnemies();



        loadText();
        gameState = GameState.PLAYING;


        if (isSoundOn) {
            loadMusic();
            loadSFX();
        }



        Gdx.input.setCatchBackKey(true);
    }

    private void loadTextures() {
        assetManager.load("Botones/Play_Bttn.png", Texture.class);
        assetManager.load("Botones/Home_Bttn.png", Texture.class);
    }

    private void setPhysics() {
        world = new World(new Vector2(0,0),true);
        b2dr = new Box2DDebugRenderer();

        new B2DWorldCreator(world,map);

    }

    private void loadText() {
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
        //AssetManager manager = new AssetManager();
        assetManager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
        assetManager.load("Mapa1/endless.tmx", TiledMap.class);
        assetManager.finishLoading();
        map = assetManager.get("Mapa1/endless.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(map,1/PPM);
    }

    private void buildHUD() {
        HUDcamera = new OrthographicCamera(WIDTH/PPM, HEIGHT/PPM);
        HUDcamera.position.set((WIDTH/2)/PPM, (HEIGHT/2)/PPM, 0);
        HUDcamera.update();
        HUDview = new StretchViewport(WIDTH/PPM, HEIGHT/PPM, HUDcamera);

        HUDstage = new Stage(HUDview);


        Texture textPauseButton = new Texture("Botones/Pause_Bttn.png");
        TextureRegionDrawable trdPauseButton = new TextureRegionDrawable(new TextureRegion(textPauseButton));
        ImageButton pauseButton = new Button("Botones/Pause_Bttn.png").getiButton();
        pauseButton.setPosition(MasterScreen.WIDTH - 1.5f*pauseButton.getWidth(), MasterScreen.HEIGHT - 2*pauseButton.getHeight());
        HUDstage.addActor(pauseButton);
        pauseButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gameState = GameState.PAUSED;
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
        movingStick.setPosition(16/PPM,16/PPM);
        shootingStick = new JoyStick("HUD/Pad/padBack.png", "HUD/Pad/padKnob.png", body).getPad();
        shootingStick.setPosition((WIDTH-256)/PPM,16/PPM);

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

        updateCharacter(movingStick.getKnobPercentX(), movingStick.getKnobPercentY(), true);

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
            lvlPrefs.putInteger("endlessBestRound",round);
        }


        batch.begin();
        player.render(batch);
        batch.draw(life, WIDTH/2-(life.getWidth()/2f),650);

        text.displayHUDText(batch, "Round: " +round, MasterScreen.WIDTH/6, 5*(MasterScreen.HEIGHT/6)+100);
        text.displayHUDText(batch, "Enemies: " +enemies.size(), MasterScreen.WIDTH*5/6, 5*(MasterScreen.HEIGHT/6)+100);


        if (!bullets.isEmpty()){
            updateBullet(true);
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
                if (!(gameState == GameState.PAUSED)) {
                    // code moved
                    minion.move(player.getPosition().x,player.getPosition().y);
                }else{
                    minion.setVelocity(0,0);
                }
            }
        }else {
            spawn();
            getEnemies();

        }
        if(!pilas.isEmpty()){
            for (int i = pilas.size()-1; i>=0; i--){
                Item pila = pilas.get(i);
                pila.render(batch);
                if (player.getRectangle().overlaps(pila.getRectangle()) ) {
                    System.out.println("puerk");
                    player.setHealth(player.getHealth() + 1);
                    pilas.remove(pila);
                }
            }
        }


        batch.end();

        if (gameState == GameState.PAUSED) {
            pauseScene.draw();
            updateCharacter(0,0,false);
            updateBullet(false);
        }

        if (gameState == GameState.PLAYING) {
            updateCharacter(0,0,true);
            updateBullet(true);
        }


        batch.setProjectionMatrix(HUDcamera.combined);
        HUDstage.draw();

        // pausa si presionamos Android Back
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
            Minion minion = new Minion(type.next(), movementPattern.ZIGZAG, diff, 500, 500,world);
            minion.setBoss();
            enemies.add(minion);
        }

        int[] nivelX = {2,3,4,5,6,2,3,4,5,6};
        int[] nivelY = {6,7,8,7,6,4,3,2,3,4};
        for (int i = 0; i<numEnemies; i++){

            Minion minion = new Minion(type, movementPattern.ZIGZAG, diff, WIDTH*nivelX[i]/7, HEIGHT*nivelY[i]/9,world);
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
        if ((0 < angle && angle <= 45) || (316 <= angle && angle <= 360)) {
            player.setDir(viewingDirection.RIGHT);
        } else if (46 <= angle && angle <= 136) {
            player.setDir(viewingDirection.BACK);
        } else if (136 <= angle && angle <= 225) {
            player.setDir(viewingDirection.LEFT);
        } else if (226 <= angle && angle <= 315) {
            player.setDir(viewingDirection.FRONT);
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

    private void updateCharacter(float dx, float dy, boolean update) {
        Rectangle checkRectangle;
        checkRectangle = new Rectangle();
        checkRectangle.set(player.getRectangle());


        if (update) {
            // code to move
            //animation
            float changeX = movingStick.getKnobPercentX();
            float changeY = movingStick.getKnobPercentY();
            Vector2 vector = new Vector2(changeX,changeY);
            float angle = vector.angle();


            //Box2D movement
            player.b2body.setLinearVelocity(changeX*10000,changeY*10000);
            player.setX(player.b2body.getPosition().x-player.getWidth()/2);//Medio ineficiente, pone sprite donde esta body
            player.setY(player.b2body.getPosition().y-player.getHeight()/2);

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
            // end of moved code
        }else{
            player.b2body.setLinearVelocity(0,0);
        }

        float newPosY = player.getSprite().getY();
        float newPosX = player.getSprite().getX();
        checkRectangle.setPosition(newPosX, newPosY);

        /* MOVIMIENTO SIN BOX2D
        boolean collides = collidesWith(walls, checkRectangle);
        if (!collides) {
            player.moveX(dx);
            player.moveY(dy);
        }
        */
        if (collidesWith(enemyRect, checkRectangle)) {
            if(timeSinceDamage>2){
                player.setHealth(player.getHealth()-1);
                timeSinceDamage=0;
            }
        }
    }


    private void updateBullet(boolean update){

        if (update) {
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
                            if (isSoundOn) {
                                minionDeathSound.play();
                            }
                            Random random = new Random();
                            if (random.nextInt(6) == 4){
                                Item pila = new Item(enemies.get(j).getPosition());
                                pilas.add(pila);
                            }
                            enemies.remove(j);
                            enemyRect.remove(j);
                        }

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
        map.dispose();
        world.dispose();
        b2dr.dispose();
    }


    private class PauseScene extends Stage {

        public PauseScene(Viewport view, SpriteBatch batch) {
            super(view, batch);
            // Creación de texturas
            Texture homeBttnTexture;
            Texture playBttnTexture;
            //Texture restartButton;

            Pixmap pixmap = new Pixmap((int) (WIDTH * 0.7f), (int) (HEIGHT * 0.8f), Pixmap.Format.RGBA8888);
            pixmap.setColor(0.3f, 0.75f, 0.3f, 0.5f);
            pixmap.fillRectangle(0, 0, pixmap.getWidth(), pixmap.getHeight());
            Texture texturaRectangulo = new Texture(pixmap);
            pixmap.dispose();
            Image rectImg = new Image(texturaRectangulo);
            rectImg.setPosition(0.15f * WIDTH, 0.1f * HEIGHT);
            this.addActor(rectImg);

            homeBttnTexture = assetManager.get("Botones/Home_Bttn.png");
            TextureRegionDrawable trdSalir = new TextureRegionDrawable(new TextureRegion(homeBttnTexture));
            ImageButton homeButton = new ImageButton(trdSalir);
            homeButton.setPosition((WIDTH/2 - homeButton.getWidth()/2)-250, (HEIGHT/2)+50);
            homeButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    // Regresa al menú
                    if (isSoundOn) {
                        music.dispose();
                    }
                    game.setScreen(new MenuScreen(game));

                }
            });
            this.addActor(homeButton);

            playBttnTexture = assetManager.get("Botones/Play_Bttn.png");
            TextureRegionDrawable trdContinuar = new TextureRegionDrawable(
                    new TextureRegion(playBttnTexture));
            ImageButton playButton = new ImageButton(trdContinuar);
            playButton.setPosition(WIDTH / 2 - playButton.getWidth() / 2 , HEIGHT / 4);
            playButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    // return to the game
                    loadMap();
                    Gdx.input.setInputProcessor(HUDstage);
                    gameState = GameState.PLAYING;
                }
            });
            this.addActor(playButton);


            /*restartButton = assetManager.get("Botones/noAssetForThatYet.png");

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
