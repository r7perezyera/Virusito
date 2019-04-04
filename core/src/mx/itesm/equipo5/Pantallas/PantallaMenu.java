package mx.itesm.equipo5.Pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
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

import mx.itesm.equipo5.JoyStick;
import mx.itesm.equipo5.MasterScreen;
import mx.itesm.equipo5.Virusito;

public class PantallaMenu extends MasterScreen {

    private Texture background;

    private ImageButton playBoton;
    private ImageButton confBoton;
    private ImageButton helpBoton;
    private ImageButton aboutBoton;
    private Touchpad shootingStick;
    private Touchpad movingStick;
    private Stage escenaHUD;

    // BOX2D FISICA
    // vamos a agregar una simulacion de fisica
    private World mundo;    // simulacion
    private Body cuerpo;    // quien recibe / esta dentro de la simulacion
    private Box2DDebugRenderer debug;


    //Menu escenas, Indp de la camara de mov
    private Stage escenaMenu; //Contenedor de Botones
    private Viewport vistaHUD;
    private OrthographicCamera camaraHUD;


    public PantallaMenu(Virusito juego) {
        super(juego);
    }

    @Override
    public void show() {

        background = new Texture("Pantallas/PantallaMenu.jpg");
        createButtons();

        Gdx.input.setCatchBackKey(false);
    }

    private void createButtons() {
        escenaMenu = new Stage(view);


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

        camaraHUD = new OrthographicCamera(WIDTH, HEIGHT);
        camaraHUD.position.set(WIDTH/2, HEIGHT/2, 0);
        camaraHUD.update();
        vistaHUD = new StretchViewport(WIDTH, HEIGHT, camaraHUD);

        // Agregar la escena, finalmente
        escenaHUD = new Stage(vistaHUD);
        escenaHUD.addActor(shootingStick);
        escenaHUD.addActor(movingStick);

        // ahora la escena es quien atiende los eventos
        Gdx.input.setInputProcessor(escenaHUD);

    }



    @Override
    public void render(float delta) {

        eraseScreen();
        batch.begin();

        //Dibujar

        batch.draw(background, 0,0);

        batch.end();

        escenaMenu.draw();
        escenaHUD.draw();

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
    background.dispose();
    escenaMenu.dispose();
    }
}
