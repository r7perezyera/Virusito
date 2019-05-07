package mx.itesm.equipo5.Pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import mx.itesm.equipo5.Button;
import mx.itesm.equipo5.MasterScreen;
import mx.itesm.equipo5.Text;
import mx.itesm.equipo5.Virusito;

class AboutScreen extends MasterScreen {

    private Texture background;

    private Stage aboutStage;

    private ImageButton homeButton;

    private Text text;

    public AboutScreen(Virusito juego) {
        super(juego);
    }

    @Override
    public void show() {
        aboutStage = new Stage(view);

        background = new Texture("Pantallas/PantallaAcercaDe.jpg");

        createButtons();

        text = new Text();

        Gdx.input.setInputProcessor(aboutStage);
        Gdx.input.setCatchBackKey(true);
    }

    private void createButtons() {
        homeButton = new Button("Botones/Home_Bttn.png").getiButton();
        homeButton.setPosition(15, (MasterScreen.HEIGHT-homeButton.getHeight())-10);
        aboutStage.addActor(homeButton);
        homeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                // Responder al evento del boton
                game.setScreen(new MenuScreen(game));
            }
        });
    }

    @Override
    public void render(float delta) {
        eraseScreen();

        batch.begin();
        batch.draw(background, 0, 0);

        text.displayText(batch, "ABOUT US", MasterScreen.WIDTH/2, 5*(MasterScreen.HEIGHT/6)+100);

        // screen left-hand side
        text.displayHUDText(batch, "Rudolf Fanchini\nISC\nDeveloper", MasterScreen.WIDTH/4, 4*(MasterScreen.HEIGHT/6));
        text.displayHUDText(batch, "Joaquin Rios\nISC\nDeveloper", MasterScreen.WIDTH/4, 2*(MasterScreen.HEIGHT/6)-50);

        // screen right-hand side
        text.displayHUDText(batch, "Daniela Belmonte\nLAD\nGame Art", MasterScreen.WIDTH/2+140, 4*(MasterScreen.HEIGHT/6));
        text.displayHUDText(batch, "Roberto Tellez\nISC\nDeveloper", (MasterScreen.WIDTH/2)+140, 2*(MasterScreen.HEIGHT/6)-50);

        text.displayHUDText(batch, "Contact\nanborgesa.studios@gmail.com", 2*(MasterScreen.WIDTH/3)+140, 5*(MasterScreen.HEIGHT/6)+15);

        batch.end();

        aboutStage.draw();

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
    public void hide() {
        // moved lines from hide to dispose
    }

    @Override
    public void dispose() {
        batch.dispose();
        background.dispose();
        aboutStage.dispose();
    }
}
