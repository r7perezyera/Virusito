package mx.itesm.equipo5.Pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import mx.itesm.equipo5.Button;
import mx.itesm.equipo5.MasterScreen;
import mx.itesm.equipo5.Virusito;

public class MenuScreen extends MasterScreen {

    private Texture background;

    private ImageButton playBoton;
    private ImageButton confBoton;
    private ImageButton helpBoton;
    private ImageButton aboutBoton;

    //Menu escenas, Indp de la camera de mov
    private Stage menuStage; //Contenedor de Botones

    public MenuScreen(Virusito juego) {
        super(juego);
    }

    @Override
    public void show() {
        menuStage = new Stage(view);

        background = new Texture("Pantallas/PantallaMenu.jpg");
        createButtons();
        //Pasamoe el control de input a la escenea
        Gdx.input.setInputProcessor(menuStage);
        Gdx.input.setCatchBackKey(false);
    }

    private void createButtons() {
        // Play Button
        playBoton = new Button("Botones/Play_Bttn.png").getiButton();
        playBoton.setPosition(MasterScreen.WIDTH /2-playBoton.getWidth()/2, MasterScreen.HEIGHT /2-playBoton.getHeight()/2);
        menuStage.addActor(playBoton);
        playBoton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                // Responder al evento del boton
                game.setScreen(new Level(game));
            }
        });

        // Help button
        helpBoton = new Button("Botones/Help_Bttn.png").getiButton();
        helpBoton.setPosition((MasterScreen.WIDTH /3-helpBoton.getWidth()/2)+50, MasterScreen.HEIGHT /6f-helpBoton.getHeight()/2);
        menuStage.addActor(helpBoton);
        helpBoton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                // Responder al evento del boton
                game.setScreen(new HelpScreen(game));
            }
        });

        // Settings button
        confBoton = new Button("Botones/Engrane_Bttn.png").getiButton();
        confBoton.setPosition(MasterScreen.WIDTH /2-confBoton.getWidth()/2, MasterScreen.HEIGHT /6f-confBoton.getHeight()/2);
        menuStage.addActor(confBoton);
        confBoton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                // Responder al evento del boton
                game.setScreen(new SettingsScreen(game));
            }
        });

        // About button
        aboutBoton = new Button("Botones/Info_Bttn.png").getiButton();
        aboutBoton.setPosition((2* MasterScreen.WIDTH /3-helpBoton.getWidth()/2)-50, MasterScreen.HEIGHT /6f-aboutBoton.getHeight()/2);
        menuStage.addActor(aboutBoton);
        aboutBoton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                // Responder al evento del boton
                game.setScreen(new AboutScreen(game));
            }
        });


    }



    @Override
    public void render(float delta) {

        eraseScreen();
        batch.begin();

        //Dibujar

        batch.draw(background, 0,0);

        batch.end();

        menuStage.draw();

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
    menuStage.dispose();
    }
}
