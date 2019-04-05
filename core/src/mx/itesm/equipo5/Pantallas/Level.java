package mx.itesm.equipo5.Pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.Iterator;
import java.util.LinkedList;

import mx.itesm.equipo5.JoyStick;
import mx.itesm.equipo5.MasterScreen;
import mx.itesm.equipo5.Objects.Enemy;
import mx.itesm.equipo5.Objects.FriendlyBullet;
import mx.itesm.equipo5.Objects.Player;
<<<<<<< HEAD
import mx.itesm.equipo5.Objects.difficulty;
import mx.itesm.equipo5.Objects.enemyType;
import mx.itesm.equipo5.Objects.movementPattern;
=======
import mx.itesm.equipo5.Objects.ShootingEnemy;
>>>>>>> f780a7d5a6b13b5fd4524e393f7716c1ad717a08
import mx.itesm.equipo5.Text;
import mx.itesm.equipo5.Virusito;

class Level extends MasterScreen {


    //Esto es para probar colisiones
    private Array<Rectangle> walls;
    private Array<Rectangle> tvs;
    private ShapeRenderer sr;

    private LinkedList<FriendlyBullet> bullets = new LinkedList<FriendlyBullet>();
    private LinkedList<Enemy> enemies = new LinkedList<Enemy>();
    private float timeSinceShot;

    private LinkedList<ShootingEnemy> shootingEnemies = new LinkedList<ShootingEnemy>();
    private float enemyShotCooldown;


    private TiledMap map;
    private OrthogonalTiledMapRenderer mapRenderer;

    // HUD
    private OrthographicCamera HUDcamera;
    private Viewport HUDview;
    private Stage HUDstage;

    private Text text;


    private Touchpad shootingStick;
    private Touchpad movingStick;


    // BOX2D FISICA
    // vamos a agregar una simulacion de fisica
    private World mundo;    // simulacion
    private Body cuerpo;    // quien recibe / esta dentro de la simulacion
    private Box2DDebugRenderer debug;

    private Player player; //Personaje
    private Array<Rectangle> doors;

    private ShootingEnemy enemy1;


    public Level(Virusito juego) {
        super(juego);
    }

    @Override
    public void show() {
    // Agregar la escena, finalmente

        loadMap();
        buildHUD();
        createJoysticks();
        getWalls();
<<<<<<< HEAD
        spawn();
=======
        getTVs();
        getDoors();
>>>>>>> f780a7d5a6b13b5fd4524e393f7716c1ad717a08

        player = new Player(300,300,20);

        enemy1 = new ShootingEnemy(500,500,10);
        shootingEnemies.add(enemy1);

        Gdx.input.setCatchBackKey(false);
    }




    private void loadMap() {
        AssetManager manager = new AssetManager();
        manager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
        manager.load("Mapa1/1-1.tmx", TiledMap.class);
        manager.finishLoading();
        map = manager.get("Mapa1/1-1.tmx");
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


        // TODO en caso de tener un pad, agregarlo como actor a HUDstage justo aqui


    }

    private void createJoysticks() {



        Box2D.init();
        mundo = new World(new Vector2(0f,-9.81f), true);
        BodyDef def = new BodyDef();
        def.type = BodyDef.BodyType.DynamicBody;
        def.position.set(0, 0);
        cuerpo = mundo.createBody(def);

        movingStick = new JoyStick("HUD/Pad/padBack.png", "HUD/Pad/padKnob.png", cuerpo).getPad();
        movingStick.setPosition(16,16);
        shootingStick = new JoyStick("HUD/Pad/padBack.png", "HUD/Pad/padKnob.png", cuerpo).getPad();
        shootingStick.setPosition(WIDTH-256,16);


        // Agregar la escena, finalmente
        HUDstage.addActor(shootingStick);
        HUDstage.addActor(movingStick);



    }



    @Override
    public void render(float delta) {

<<<<<<< HEAD

        timeSinceShot += delta;
        shoot();
        updateCharacter(movingStick.getKnobPercentX(), movingStick.getKnobPercentY());
=======
    timeSinceShot += delta;
    enemyShotCooldown += delta;
    shoot();


    updateCharacter(movingStick.getKnobPercentX(), movingStick.getKnobPercentY());

>>>>>>> f780a7d5a6b13b5fd4524e393f7716c1ad717a08

        batch.setProjectionMatrix(camera.combined);
        // render the game map
        mapRenderer.setView(camera);
        mapRenderer.render();

        batch.begin();
        enemy1.render(batch);
        player.render(batch);
        if (!bullets.isEmpty()){
            for (FriendlyBullet bullet: bullets){
                bullet.render(batch);
                bullet.update();
            }
        }
        if (!enemies.isEmpty()){
            for (Enemy enemy: enemies){
                enemy.render(batch);
                enemy.move(player.getX(), player.getY());
            }
        }
        batch.end();
        HUDstage.draw();


    }

<<<<<<< HEAD
    private void spawn() {
        Enemy enemy = new Enemy(enemyType.RAMMER, movementPattern.FOLLOWER, difficulty.EASY, 400, 400);
        enemies.add(enemy);
        enemy = new Enemy(enemyType.RAMMER, movementPattern.FOLLOWER, difficulty.EASY, 400, 300);
        enemies.add(enemy);
    }

=======
    /*
    //TODO arreglar angulo
    private void updateEnemies() {
        float playerX = player.getX();
        float playerY = player.getY();
        float enemyX, enemyY;
        for(ShootingEnemy shootingEnemy : shootingEnemies){
            enemyX = shootingEnemy.getX();
            enemyY = shootingEnemy.getY();
            float angle =(float) (MathUtils.atan2(enemyY-playerY,enemyX-playerX)*180.0)/ MathUtils.PI;


            if(enemyShotCooldown>=2){
                FriendlyBullet bullet = new FriendlyBullet(shootingEnemy.getX()+shootingEnemy.getWidth()/2, shootingEnemy.getY()+shootingEnemy.getHeight()/2, angle);
                bullets.add(bullet);
                enemyShotCooldown=0;
            }
        }
    }
*/
>>>>>>> f780a7d5a6b13b5fd4524e393f7716c1ad717a08
    private void shoot() {
        float changeX = shootingStick.getKnobPercentX();
        float changeY = shootingStick.getKnobPercentY();
        //Checks angle of shot
        Vector2 vector = new Vector2(changeX,changeY);
        float angle = vector.angle();

        System.out.println(angle);
        if(timeSinceShot>=1f) {
            if ((0 < angle && angle <= 45) || (316 <= angle && angle <= 360)) {
                FriendlyBullet bullet = new FriendlyBullet(player.getX()+player.getWidth()/2, player.getY()+player.getHeight()/2, 0);
                bullets.add(bullet);
                timeSinceShot = 0;
            } else if (46 <= angle && angle <= 136) {
                FriendlyBullet bullet = new FriendlyBullet(player.getX()+player.getWidth()/2, player.getY()+player.getHeight()/2, (float) Math.PI / 2);
                bullets.add(bullet);
                timeSinceShot=0;
            } else if (136 <= angle && angle <= 225) {
                FriendlyBullet bullet = new FriendlyBullet(player.getX()+player.getWidth()/2, player.getY()+player.getHeight()/2, (float) Math.PI);
                bullets.add(bullet);
                timeSinceShot=0;
            } else if (226 <= angle && angle <= 315) {
                FriendlyBullet bullet = new FriendlyBullet(player.getX()+player.getWidth()/2, player.getY()+player.getHeight()/2, (float) (3 * Math.PI) / 2);
                bullets.add(bullet);
                timeSinceShot=0;
            }
        }


    }

    private void getWalls(){
        walls = new Array<Rectangle>();
        for(MapObject object : map.getLayers().get("Paredes").getObjects()){
            if(object instanceof RectangleMapObject){
                Rectangle rect = ((RectangleMapObject) object).getRectangle();
                walls.add(rect);
            }
        }
    }

    private void getTVs(){
        tvs = new Array<Rectangle>();
        for(MapObject object : map.getLayers().get("Teles").getObjects()){
            if(object instanceof RectangleMapObject){
                Rectangle rect = ((RectangleMapObject) object).getRectangle();
                tvs.add(rect);
            }
        }
    }

    private void getDoors(){
        doors = new Array<Rectangle>();
        for(MapObject object : map.getLayers().get("Teles").getObjects()){
            if(object instanceof RectangleMapObject){
                Rectangle rect = ((RectangleMapObject) object).getRectangle();
                doors.add(rect);
            }
        }
    }

    private void updateCharacter(float dx, float dy) {
        Rectangle checkRectangle;
        checkRectangle = new Rectangle();
        checkRectangle.set(player.getRectangle());

        float newPosY = player.getSprite().getY() + (dy * player.getSpeed());
        float newPosX = player.getSprite().getX() + (dx * player.getSpeed());
        checkRectangle.setPosition(newPosX, newPosY);


        boolean collides = collidesWith(walls, checkRectangle) || collidesWith(tvs,checkRectangle);
        if (!collides) {
            player.moveX(dx);
            player.moveY(dy);
        }
    }

    private void updateBullet(){
        for(FriendlyBullet bullet : bullets){
            if(collidesWith(walls,bullet.getRectangle())||collidesWith(walls,bullet.getRectangle())){
                //TODO eliminar bullet
            };
        }
    }

    public boolean collidesWith(Array<Rectangle> rectangles,Rectangle checkRectangle){
        for(Rectangle rectangle : rectangles){
            if(checkRectangle.overlaps(rectangle)) return true;
        }
        return false;
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
        //escenaMenu.dispose();
        HUDstage.dispose();
        mapRenderer.dispose();
    }
}
