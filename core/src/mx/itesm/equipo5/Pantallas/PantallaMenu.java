package mx.itesm.equipo5.Pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import mx.itesm.equipo5.Boton;
import mx.itesm.equipo5.Pantalla;
import mx.itesm.equipo5.Virusito;

public class PantallaMenu extends Pantalla {

    private Texture background;

    private ImageButton playBoton;
    private ImageButton confBoton;
    private ImageButton helpBoton;
    private ImageButton aboutBoton;

    //Menu escenas, Indp de la camara de mov
    private Stage escenaMenu; //Contenedor de Botones

    public PantallaMenu(Virusito juego) {
        super(juego);
    }

    @Override
    public void show() {

        background = new Texture("Pantallas/PantallaMenu.jpg");
        crearBotones();
        //Pasamoe el control de input a la escenea
        Gdx.input.setInputProcessor(escenaMenu);
        Gdx.input.setCatchBackKey(false);
    }

    private void crearBotones() {
        escenaMenu = new Stage(vista);

        //creacion, posicion y que hace el boton
        playBoton = new Boton("Botones/Play_Bttn.png").getiButton();
        playBoton.setPosition(PantallaCargando.ancho/2-playBoton.getWidth()/2,
                PantallaCargando.alto/3f-playBoton.getHeight()/4f);
        escenaMenu.addActor(playBoton);
        playBoton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                juego.setScreen(new PrimerNivel(juego));
            }
        });

        aboutBoton = new Boton("Botones/Info_Bttn.png").getiButton();
        aboutBoton.setPosition(PantallaCargando.ancho*(3/8f)-aboutBoton.getWidth()/2,0);
        escenaMenu.addActor(aboutBoton);
        aboutBoton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                juego.setScreen(new PantallaAbout(juego));
            }
        });

        confBoton = new Boton("Botones/Engrane_Bttn.png").getiButton();
        confBoton.setPosition(PantallaCargando.ancho*(4/8f)-confBoton.getWidth()/2,0);
        escenaMenu.addActor(confBoton);
        confBoton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                juego.setScreen(new PantallaAjustes(juego));
            }
        });

        helpBoton = new Boton("Botones/Help_Bttn.png").getiButton();
        helpBoton.setPosition(PantallaCargando.ancho*(5/8f)-helpBoton.getWidth()/2,0);
        escenaMenu.addActor(helpBoton);
        helpBoton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                juego.setScreen(new PantallaAyuda(juego));
            }
        });

    }



    @Override
    public void render(float delta) {

        borrarPantalla();
        batch.begin();

        //Dibujar

        batch.draw(background, 0,0);

        batch.end();

        escenaMenu.draw();

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
