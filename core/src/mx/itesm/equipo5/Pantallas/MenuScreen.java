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

public class MenuScreen extends Pantalla {

    private Texture background;

    private ImageButton playBoton;
    private ImageButton confBoton;
    private ImageButton helpBoton;
    private ImageButton aboutBoton;

    //Menu escenas, Indp de la camara de mov
    private Stage escenaMenu; //Contenedor de Botones

    public MenuScreen(Virusito juego) {
        super(juego);
    }

    @Override
    public void show() {
        escenaMenu = new Stage(vista);

        background = new Texture("Pantallas/PantallaMenu.jpg");
        crearBotones();
        //Pasamoe el control de input a la escenea
        Gdx.input.setInputProcessor(escenaMenu);
        Gdx.input.setCatchBackKey(false);
    }

    private void crearBotones() {
        // Play Button
        playBoton = new Boton("Botones/Play_Bttn.png").getiButton();
        playBoton.setPosition(Pantalla.ANCHO/2-playBoton.getWidth()/2, Pantalla.ALTO/2-playBoton.getHeight()/2);
        escenaMenu.addActor(playBoton);
        playBoton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                // Responder al evento del boton
                juego.setScreen(new Level(juego));
            }
        });

        // Help button
        helpBoton = new Boton("Botones/Help_Bttn.png").getiButton();
        helpBoton.setPosition((Pantalla.ANCHO/3-helpBoton.getWidth()/2)+50, Pantalla.ALTO/6f-helpBoton.getHeight()/2);
        escenaMenu.addActor(helpBoton);
        helpBoton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                // Responder al evento del boton
                juego.setScreen(new HelpScreen(juego));
            }
        });

        // Settings button
        confBoton = new Boton("Botones/Engrane_Bttn.png").getiButton();
        confBoton.setPosition(Pantalla.ANCHO/2-confBoton.getWidth()/2, Pantalla.ALTO/6f-confBoton.getHeight()/2);
        escenaMenu.addActor(confBoton);
        confBoton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                // Responder al evento del boton
                juego.setScreen(new SettingsScreen(juego));
            }
        });

        // About button
        aboutBoton = new Boton("Botones/Info_Bttn.png").getiButton();
        aboutBoton.setPosition((2*Pantalla.ANCHO/3-helpBoton.getWidth()/2)-50, Pantalla.ALTO/6f-aboutBoton.getHeight()/2);
        escenaMenu.addActor(aboutBoton);
        aboutBoton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                // Responder al evento del boton
                juego.setScreen(new AboutScreen(juego));
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
