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

class HelpScreen extends MasterScreen {

    private Texture background;

    private Stage helpStage;

    private ImageButton homeButton;

    private Text text;

    public HelpScreen(Virusito juego) {
        super(juego);
    }

    @Override
    public void show() {
        helpStage = new Stage(view);

        background = new Texture("Pantallas/PantallaHowTo.jpg");

        createButtons();
        loadText();

        Gdx.input.setInputProcessor(helpStage);
        Gdx.input.setCatchBackKey(true);
    }

    private void loadText() {
        text = new Text();
    }

    private void createButtons() {
        homeButton = new Button("Botones/Home_Bttn.png").getiButton();
        homeButton.setPosition(15, (MasterScreen.HEIGHT-homeButton.getHeight())-10);
        helpStage.addActor(homeButton);
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

        text.displayText(batch, "HOW TO PLAY", MasterScreen.WIDTH/2, 5*(MasterScreen.HEIGHT/6)+100);

        text.displayText(batch, "Use the joystick on the left\nto move around the map",
                (MasterScreen.WIDTH/3)+10, 5*(MasterScreen.HEIGHT/6));

        text.displayText(batch, "Use the joystick on the right\nto aim and shoot at the enemies",
                (MasterScreen.WIDTH/3)+10, 4*(MasterScreen.HEIGHT/6));
        text.displayText(batch, "And remember: you are humanity's last hope!",
                (MasterScreen.WIDTH/3)+100, 3*(MasterScreen.HEIGHT/6)-10);



        batch.end();

        helpStage.draw();

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

    }

    @Override
    public void dispose() {

    }
}
