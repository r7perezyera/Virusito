package mx.itesm.equipo5.Pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.LinkedList;

import mx.itesm.equipo5.JoyStick;
import mx.itesm.equipo5.MasterScreen;
import mx.itesm.equipo5.Objects.FriendlyBullet;
import mx.itesm.equipo5.Objects.Player;
import mx.itesm.equipo5.Text;
import mx.itesm.equipo5.Virusito;

class Level extends MasterScreen {

    private LinkedList<FriendlyBullet> friendlyBullets = new LinkedList<FriendlyBullet>();
    private FriendlyBullet bullet;

    private TiledMap map;
    private OrthogonalTiledMapRenderer mapRenderer;

    // HUD
    private OrthographicCamera HUDcamera;
    private Viewport HUDview;
    private Stage HUDstage;

    private Text text;


    private Touchpad shootingStick;
    private Touchpad movingStick;
    //private Stage escenaHUD;

    // BOX2D FISICA
    // vamos a agregar una simulacion de fisica
    private World mundo;    // simulacion
    private Body cuerpo;    // quien recibe / esta dentro de la simulacion
    private Box2DDebugRenderer debug;

    private Player player; //Personaje


    //Menu escenas, Indp de la camara de mov
    //private Stage escenaMenu; //Contenedor de Botones
    //private Viewport vistaHUD;
    //private OrthographicCamera camaraHUD;

    public Level(Virusito juego) {
        super(juego);
    }

    @Override
    public void show() {
    // Agregar la escena, finalmente

        loadMap();
        buildHUD();
        createJoysticks();

        player = new Player(300,300,20);

        Gdx.input.setCatchBackKey(false);
    }




    private void loadMap() {
        AssetManager manager = new AssetManager();
        manager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
        manager.load("Mapa1/1-1.tmx", TiledMap.class);
        manager.finishLoading();
        map = manager.get("Mapa1/1-1.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(map);
        System.out.println("cargo el mapa");
    }

    private void buildHUD() {


        HUDcamera = new OrthographicCamera(WIDTH, HEIGHT);
        HUDcamera.position.set(WIDTH/2, HEIGHT/2, 0);
        HUDcamera.update();
        HUDview = new StretchViewport(WIDTH, HEIGHT, HUDcamera);

        HUDstage = new Stage(HUDview);


        // ahora la escena es quien atiende los eventos
        Gdx.input.setInputProcessor(HUDstage);

        // siguiendo el codigo de roman, aqui irian los pads, pero no se si haya una forma mejor



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

        /*
        HUDcamera = new OrthographicCamera(WIDTH, HEIGHT);
        HUDcamera.position.set(WIDTH/2, HEIGHT/2, 0);
        HUDcamera.update();
        HUDview = new StretchViewport(WIDTH, HEIGHT, HUDcamera);
        */

        // Agregar la escena, finalmente
        HUDstage.addActor(shootingStick);
        HUDstage.addActor(movingStick);



    }



    @Override
    public void render(float delta) {


        shoot();

        updateCharacter(movingStick.getKnobPercentX(), movingStick.getKnobPercentY());


        eraseScreen();

        batch.setProjectionMatrix(camera.combined);
        // render the game map
        mapRenderer.setView(camera);
        mapRenderer.render();

        batch.begin();
        player.render(batch);
        batch.end();

        //escenaMenu.draw();
        HUDstage.draw();



    }

    private void shoot() {
        float changeX = shootingStick.getKnobPercentX();
        float changeY = shootingStick.getKnobPercentY();
        //Checks angle of shot
        Vector2 vector = new Vector2(changeX,changeY);
        float angle = vector.angle();


    }

    private void updateCharacter(float dx, float dy) {
        player.moveX(dx);
        player.moveY(dy);
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
