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



        playBoton = new Boton("Botones/Play_Bttn.png").getiButton();
        playBoton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                // Responder al evento del boton
                juego.setScreen(new Nivel(juego));
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
