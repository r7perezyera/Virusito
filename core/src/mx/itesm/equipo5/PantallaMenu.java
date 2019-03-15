package mx.itesm.equipo5;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

class PantallaMenu implements Screen {

    private final Virusito juego;

    // Camara del juego
    private OrthographicCamera camera;
    private Viewport vista; //Escalar
    private SpriteBatch batch;  // Optimizar los gráficos

    //Botones
    ImageButton playBoton;
    ImageButton confBoton;
    ImageButton helpBoton;
    ImageButton aboutBoton;

    //BackGround
    Texture fondo;

    //Menu escenas, Indp de la camara de mov
    private Stage escenaMenu; //Contenedor de Botones

    public PantallaMenu(Virusito juego) { this.juego=juego; }

    @Override
    public void show() {
        // Constructor
        camera = new OrthographicCamera(PantallaCargando.Ancho, PantallaCargando.Alto);
        camera.position.set(PantallaCargando.Ancho/2, PantallaCargando.Alto/2,0);
        camera.update();
        //Vista
        vista = new StretchViewport(PantallaCargando.Ancho,PantallaCargando.Alto, camera);
        batch = new SpriteBatch();
        // Imagen
        fondo = new Texture("Pantallas/PantallaMenu.jpg");

        //Menu, inicializar botones
        crearMenu();
        //Pasamoe el control de input a la escenea
        Gdx.input.setInputProcessor(escenaMenu);
        Gdx.input.setCatchBackKey(false);

    }

    private void crearMenu() {
        escenaMenu = new Stage(vista);

        //creacion, posicion y que hace el boton
        playBoton = new Boton("Botones/Play_Bttn.png").getiButton();
        playBoton.setPosition(PantallaCargando.Ancho/2-playBoton.getWidth()/2,
                PantallaCargando.Alto/3f-playBoton.getHeight()/4f);
        escenaMenu.addActor(playBoton);
        playBoton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                juego.setScreen(new PrimerNivel(juego));
            }
        });

        aboutBoton = new Boton("Botones/Info_Bttn.png").getiButton();
        aboutBoton.setPosition(PantallaCargando.Ancho*(3/8f)-aboutBoton.getWidth()/2,0);
        escenaMenu.addActor(aboutBoton);
        aboutBoton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                juego.setScreen(new PantallaAbout(juego));
            }
        });

        confBoton = new Boton("Botones/Engrane_Bttn.png").getiButton();
        confBoton.setPosition(PantallaCargando.Ancho*(4/8f)-confBoton.getWidth()/2,0);
        escenaMenu.addActor(confBoton);
        confBoton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                juego.setScreen(new PantallaAjustes(juego));
            }
        });

        helpBoton = new Boton("Botones/Help_Bttn.png").getiButton();
        helpBoton.setPosition(PantallaCargando.Ancho*(5/8f)-helpBoton.getWidth()/2,0);
        escenaMenu.addActor(helpBoton);
        helpBoton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                juego.setScreen(new PantallaTutorial(juego));
            }
        });

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Agregar Camara
        batch.setProjectionMatrix(camera.combined); // Escala adecuado
        batch.begin();

        //Dibujar

        batch.draw(fondo, 0,0);

        batch.end();

        escenaMenu.draw();

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

    }
}
