package mx.itesm.equipo5.Pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import mx.itesm.equipo5.Button;
import mx.itesm.equipo5.MasterScreen;
import mx.itesm.equipo5.Virusito;

class LoseScreen extends MasterScreen {
    private ImageButton playBoton;
    private Texture background;
    //Menu escenas, Indp de la camera de mov
    private Stage menuStage; //Contenedor de Botones

    public LoseScreen(Virusito game) {
        super(game);

    }


    @Override
    public void show() {
        menuStage = new Stage(view);
        createButtons();
        background = new Texture("Pantallas/GameOver_Screen.png");
        Gdx.input.setInputProcessor(menuStage);
        Gdx.input.setCatchBackKey(true);
    }

    private void createButtons() {
        // Play Button
        playBoton = new Button("Botones/Continue_Bttn.png").getiButton();
        playBoton.setPosition(MasterScreen.WIDTH / 2 - playBoton.getWidth() / 2, MasterScreen.HEIGHT / 3 - playBoton.getHeight() / 2);
        menuStage.addActor(playBoton);
        playBoton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                // Responder al evento del boton
                game.setScreen(new LvlSelectScreen(game));
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

        if (Gdx.input.isKeyPressed(Input.Keys.BACK)) {
            game.setScreen(new MenuScreen(game));
        }
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}

