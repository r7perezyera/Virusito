package mx.itesm.equipo5.Pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;
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
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.LinkedList;
import java.util.Random;

import mx.itesm.equipo5.B2DWorldCreator;
import mx.itesm.equipo5.Button;
import mx.itesm.equipo5.JoyStick;
import mx.itesm.equipo5.MasterScreen;
import mx.itesm.equipo5.Objects.EnemyBullet;
import mx.itesm.equipo5.Objects.FriendlyBullet;
import mx.itesm.equipo5.Objects.Item;
import mx.itesm.equipo5.Objects.Minion;
import mx.itesm.equipo5.Objects.Player;
import mx.itesm.equipo5.Objects.difficulty;
import mx.itesm.equipo5.Objects.enemyType;
import mx.itesm.equipo5.Objects.movementPattern;
import mx.itesm.equipo5.Objects.viewingDirection;
import mx.itesm.equipo5.Objects.weaponType;
import mx.itesm.equipo5.Text;
import mx.itesm.equipo5.Virusito;

class Level2 extends MasterScreen {

    private AssetManager assetManager;

    //Esto es para probar colisiones
    private LinkedList<Rectangle> walls;
    private LinkedList<Rectangle> doors;
    private LinkedList<Rectangle> damageFloors;
    private LinkedList<Rectangle> TVS;

    //timers
    private float timeSinceDamage;
    private float spawntimer= 2f;

    private LinkedList<FriendlyBullet> bullets = new LinkedList<FriendlyBullet>();
    private LinkedList<EnemyBullet> enemyBullets = new LinkedList<EnemyBullet>();
    private LinkedList<Item> pilas = new LinkedList<Item>();
    private LinkedList<Minion> enemies = new LinkedList<Minion>();
    private float timeSinceShot;
    private float friendlyShotCooldown;

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
    private Sound playerDeathSound;
    private Sound minionDeathSound;
    private Sound bossDeathSound;


    // Users preferences
    private Preferences lvlPrefs = Gdx.app.getPreferences("userPrefs");
    private boolean isSoundOn = lvlPrefs.getBoolean("soundOn");

    //rounds
    private difficulty diff = difficulty.EASY;
    private enemyType type;
    private int room = 1;


    private GameState gameState;

    private ImageButton pauseButton;

    private PauseScene pauseScene;
    private String movementType;


    //Box2D

    public Level2(Virusito juego) {
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
        getDoors();
        getDamageFloors();
        getTVs();

        player = new Player(WIDTH/2,HEIGHT/2,3,this.world, weaponType.NONE);
        friendlyShotCooldown = player.getCooldown();
        spawn();
        getEnemies();
        text = new Text();



        loadText();
        gameState = GameState.PLAYING;


        if (isSoundOn) {
            loadMusic();
            loadSFX();
        }



        Gdx.input.setCatchBackKey(true);
    }

    private void getTVs() {
        TVS = new LinkedList<Rectangle>();
        try {
            for (MapObject object : map.getLayers().get("TVS").getObjects()) {
                if (object instanceof RectangleMapObject) {
                    Rectangle rect = ((RectangleMapObject) object).getRectangle();
                    TVS.add(rect);
                }
            }
        }catch(NullPointerException e){}
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
        playerDeathSound = Gdx.audio.newSound(Gdx.files.internal("Music/SFX/PlayerDeath.wav"));
        minionDeathSound = Gdx.audio.newSound(Gdx.files.internal("Music/SFX/DeathMinion.wav"));
        bossDeathSound = Gdx.audio.newSound(Gdx.files.internal("Music/SFX/DeathBoss.wav"));
    }

    private void loadMap() {
        //AssetManager manager = new AssetManager();
        if (room <=1){
            assetManager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
            assetManager.load("Mapa2/2-1.tmx", TiledMap.class);
            assetManager.finishLoading();
            map = assetManager.get("Mapa2/2-1.tmx");
            mapRenderer = new OrthogonalTiledMapRenderer(map,1/PPM);
        }else if (room == 2){
            assetManager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
            assetManager.load("Mapa2/2-2.tmx", TiledMap.class);
            assetManager.finishLoading();
            map = assetManager.get("Mapa2/2-2.tmx");
            mapRenderer = new OrthogonalTiledMapRenderer(map,1/PPM);
        }else if (room == 3){
            assetManager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
            assetManager.load("Mapa2/2-3.tmx", TiledMap.class);
            assetManager.finishLoading();
            map = assetManager.get("Mapa2/2-3.tmx");
            mapRenderer = new OrthogonalTiledMapRenderer(map,1/PPM);
        }else if (room == 4){
            assetManager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
            assetManager.load("Mapa2/2-4.tmx", TiledMap.class);
            assetManager.finishLoading();
            map = assetManager.get("Mapa2/2-4.tmx");
            mapRenderer = new OrthogonalTiledMapRenderer(map,1/PPM);
        }

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
        world.step(1 / 60f, 6, 2);

        shoot();



        batch.setProjectionMatrix(camera.combined);
        // render the game map
        mapRenderer.setView(camera);
        mapRenderer.render();


        if (player.getHealth() == 3) {
            life = new Texture("HUD/Bateria/Bateria_Llena.png");
        } else if (player.getHealth() == 2) {
            life = new Texture("HUD/Bateria/Bateria_Agotando.png");
        } else if (player.getHealth() == 1) {
            life = new Texture("HUD/Bateria/Bateria_Ultima.png");
        } else {
            if (isSoundOn) {
                playerDeathSound.play();
                music.stop();
            }
            game.setScreen(new LoseScreen(game));
        }


        batch.begin();
        player.render(batch);
        batch.draw(life, WIDTH / 2 - (life.getWidth() / 2f), 650);




        if (!enemies.isEmpty()) {
            for (Minion minion : enemies) {
                minion.render(batch);
                if (!(gameState == GameState.PAUSED)) {
                    // code moved
                    enemyBullets = minion.move(player.getPosition().x, player.getPosition().y, enemyBullets);
                } else {
                    minion.setVelocity(0, 0);
                }
            }
        }
        else {
            spawntimer += delta;
            if (collidesWith(doors,player.getRectangle())) {
                spawntimer = 0f;
                if (room < 4 ) {
                    room++;
                    loadMap();
                    getDamageFloors(); //TODO cambiar tipo de piso
                    getTVs();
                    pilas = new LinkedList<Item>();
                    bullets = new LinkedList<FriendlyBullet>();
                    player.b2body.setTransform(WIDTH/2,HEIGHT/2, 0f);

                } else {
                    if (isSoundOn) {
                        playerDeathSound.play();
                        music.stop();
                    }
                    lvlPrefs.putBoolean("level1Passed", true);
                    lvlPrefs.flush();
                    game.setScreen(new WinScreen(game));
                }
            }
            if (spawntimer >= 1f && spawntimer <= 1.5f){
                spawn();
                getEnemies();
                spawntimer = 2;
            }
        }

        if (!pilas.isEmpty()) {
            for (int i = pilas.size() - 1; i >= 0; i--) {
                Item pila = pilas.get(i);
                pila.render(batch);
                if (player.getRectangle().overlaps(pila.getRectangle()) && player.getHealth() < 3) {
                    player.setHealth(player.getHealth() + 1);
                    pilas.remove(pila);
                }
            }
        }

        if (gameState == GameState.PLAYING) {
            updateCharacter(movingStick.getKnobPercentX(), movingStick.getKnobPercentY(), true);
            updateBullet(true);
            updateEnemyBullet(true);
            timeSinceShot += delta;
            timeSinceDamage += delta;
            if (!bullets.isEmpty()) {
                for (int i = bullets.size() - 1; i >= 0; i--) {
                    if (bullets.get(i).isDestroyed()) {
                        bullets.remove(i);
                    } else {
                        FriendlyBullet bullet = bullets.get(i);
                        bullet.render(batch);
                        bullet.update();
                    }

                }
            }

            if (!enemyBullets.isEmpty()) {
                for (int i = enemyBullets.size() - 1; i >= 0; i--) {
                    if (enemyBullets.get(i).isDestroyed()) {
                        enemyBullets.remove(i);
                    } else {
                        EnemyBullet bullet = enemyBullets.get(i);
                        bullet.render(batch);
                        bullet.update();
                    }

                }
            }

        }

        batch.end();

        if (gameState == GameState.PAUSED) {
            pauseScene.draw();
            updateCharacter(0,0,false);
            updateBullet(false);
            updateEnemyBullet(false);
            shootingStick.remove();
            movingStick.remove();
            createJoysticks();

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
        //b2dr.render(world,camera.combined);
    }



    private void spawn() {
        enemies = new LinkedList<Minion>();
        int numEnemies = 0;
        type = enemyType.TEETH;


        if (diff == difficulty.EASY){
            numEnemies = 3;

        }else if (diff == difficulty.MEDIUM){
            numEnemies = 4;
        }else if (diff == difficulty.HARD){
            numEnemies = 4;
        }

        if (room%4 == 0){
            numEnemies = 7;
            Minion minion = new Minion(type.next(), movementPattern.AVOIDER, diff, 500, 500,world);
            minion.setBoss();
            enemies.add(minion);
        }

        int[] nivelX = {2,3,4,5,6,2,3,4,5,6};
        int[] nivelY = {6,7,8,7,6,4,3,2,3,4};
        for (int i = 0; i<numEnemies; i++){
            Minion minion = new Minion(type, movementPattern.ZIGZAG, diff, WIDTH*nivelX[i]/7, HEIGHT*nivelY[i]/9,world);
            enemies.add(minion);
        }
        diff = diff.next();


    }

    private void shoot() {
        float changeX = shootingStick.getKnobPercentX();
        float changeY = shootingStick.getKnobPercentY();
        //Checks angle of shot
        Vector2 vector = new Vector2(changeX,changeY);
        float angle = vector.angle();

        if(timeSinceShot<=friendlyShotCooldown) {
            if ((0 < angle && angle <= 22.5) || (337.5 <= angle && angle <= 360)) {
                bullets  = player.shoot(0.0f, bullets, isSoundOn);
                timeSinceShot=friendlyShotCooldown+ 0.1f;

            } else if (22.5 <= angle && angle <= 67.5) {
                bullets  = player.shoot((float) Math.PI / 4, bullets, isSoundOn);
                timeSinceShot=friendlyShotCooldown+ 0.1f;

            } else if (67.5 <= angle && angle <= 112.5) {
                bullets  = player.shoot((float) Math.PI / 2, bullets, isSoundOn);
                timeSinceShot=friendlyShotCooldown+ 0.1f;

            } else if (112.5 <= angle && angle <= 157.5) {
                bullets  = player.shoot((float) (3 * Math.PI / 4), bullets, isSoundOn);
                timeSinceShot=friendlyShotCooldown+ 0.1f;

            } else if (157.5 <= angle && angle <= 202.5) {
                bullets  = player.shoot((float) Math.PI, bullets, isSoundOn);
                timeSinceShot=friendlyShotCooldown+ 0.1f;

            } else if (202.5 <= angle && angle <= 247.5) {
                bullets  = player.shoot((float) (5*Math.PI / 4), bullets, isSoundOn);
                timeSinceShot=friendlyShotCooldown+ 0.1f;

            } else if (247.5 <= angle && angle <= 292.5) {
                bullets  = player.shoot((float) (3*Math.PI) / 2, bullets, isSoundOn);
                timeSinceShot=friendlyShotCooldown+ 0.1f;

            } else if (292.5 <= angle && angle <= 337.5) {
                bullets = player.shoot((float) (7*Math.PI / 4), bullets, isSoundOn);
                timeSinceShot = friendlyShotCooldown + 0.1f;
            }

        }else if (timeSinceShot >= 2*friendlyShotCooldown) {
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

    private void getDoors(){
        doors = new LinkedList<Rectangle>();
        for(MapObject object : map.getLayers().get("Puertas").getObjects()){
            if(object instanceof  RectangleMapObject){
                Rectangle rect = ((RectangleMapObject) object).getRectangle();
                doors.add(rect);
            }
        }
    }

    private void getDamageFloors(){
        damageFloors = new LinkedList<Rectangle>();
        try {
            for (MapObject object : map.getLayers().get("Piso Lastima").getObjects()) {
                if (object instanceof RectangleMapObject) {
                    Rectangle rect = ((RectangleMapObject) object).getRectangle();
                    damageFloors.add(rect);
                }
            }
       }catch(NullPointerException e){}
    }

    private String checkFloorType(){
        if(collidesWith(damageFloors,player.getRectangle())){
            if(timeSinceDamage>2) {
                player.doDamage(1);
                timeSinceDamage = 0;
            }
            return "move";
        }else{
            if (!TVS.isEmpty()){
                if (player.getRectangle().overlaps(TVS.get(1))){
                    System.out.println("pistol equipped");
                    player.setWeapon(weaponType.PISTOL);
                    friendlyShotCooldown = player.getCooldown();
                }else if (player.getRectangle().overlaps(TVS.get(0))){
                    System.out.println("shotgun equipped");
                    player.setWeapon(weaponType.SHOTGUN); //TODO add sound
                    friendlyShotCooldown = player.getCooldown();
                }
            }
            return "move";
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


            movementType = checkFloorType();
            //Box2D movement
            if(movementType=="move") {
                player.move(changeX, changeY);

            }else if(movementType=="slide"){
                player.slide(changeX,changeY);
            }


            float newPosY = player.getSprite().getY() + (dy * player.getSpeed());
            float newPosX = player.getSprite().getX() + (dx * player.getSpeed());
            checkRectangle.setPosition(newPosX, newPosY);
            // end of moved code
            if (collidesWith(enemyRect, checkRectangle)) {
                if(timeSinceDamage>2){
                    player.doDamage(1);
                    timeSinceDamage=0;
                }
            }

        }
        else{
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

    }


    private void updateBullet(boolean update){

        if (update && !bullets.isEmpty()) {
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
                        enemies.get(j).doDamage(bullet.getDamage());
                        if (enemies.get(j).isDestroyed()) {
                            if (isSoundOn) {
                                if (enemies.get(j).isBoss()){
                                    bossDeathSound.play();
                                }else{
                                    minionDeathSound.play();
                                }
                            }
                            Random random = new Random();
                            if (random.nextInt(6) == 4 && !enemies.get(j).isBoss()){
                                Item pila = new Item(enemies.get(j).getPosition());
                                pilas.add(pila);
                            }else if (enemies.get(j).isBoss()){
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

    private void updateEnemyBullet(boolean update){

        if (update && !enemyBullets.isEmpty()) {
            for(int i =enemyBullets.size()-1;i>=0;i--){
                EnemyBullet bullet = enemyBullets.get(i);
                Rectangle checkRectangle;
                checkRectangle = new Rectangle();
                checkRectangle.set(bullet.getRectangle());
                float newPosY = bullet.getSprite().getY() + bullet.getSpeed();
                float newPosX = bullet.getSprite().getX() + bullet.getSpeed();
                checkRectangle.setPosition(newPosX, newPosY);

                if(collidesWith(walls,checkRectangle)) {
                    bullet.destroy();
                    enemyBullets.remove(i);
                }

                Rectangle playerRect = player.getRectangle();
                if (checkRectangle.overlaps(playerRect)){
                    player.doDamage(bullet.getDamage());
                    bullet.destroy();
                    enemyBullets.remove(i);
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

            float pixmapWidth = WIDTH * 0.7f;
            float pixmapHeight = HEIGHT * 0.8f;
            Pixmap pixmap = new Pixmap((int) (pixmapWidth), (int) (pixmapHeight), Pixmap.Format.RGBA8888);
            pixmap.setColor(1f, 1f, 1f, 0.75f);
            pixmap.fillRectangle(0, 0, pixmap.getWidth(), pixmap.getHeight());
            Texture texturaRectangulo = new Texture(pixmap);
            pixmap.dispose();
            Image rectImg = new Image(texturaRectangulo);
            rectImg.setPosition(0.15f * WIDTH, 0.1f * HEIGHT);
            this.addActor(rectImg);

            homeBttnTexture = assetManager.get("Botones/Home_Bttn.png");
            TextureRegionDrawable trdSalir = new TextureRegionDrawable(new TextureRegion(homeBttnTexture));
            ImageButton homeButton = new ImageButton(trdSalir);
            homeButton.setPosition(pixmapWidth/6 + homeButton.getWidth()/2, pixmapHeight - homeButton.getHeight()/2);
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
            playButton.setPosition(pixmapWidth - playButton.getWidth()/2 , pixmapHeight/6);
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


        }
    }

}
