package mx.itesm.equipo5.Pantallas;

import com.badlogic.gdx.Gdx;
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

        Gdx.input.setInputProcessor(aboutStage);
        Gdx.input.setCatchBackKey(false);
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

        // construir Text
        text = new Text();
        text.displayText(batch, "ABOUT US", MasterScreen.WIDTH/2, 5*(MasterScreen.HEIGHT/6)+100);

        // screen left-hand side
        text.displayText(batch, "Rudy\nLead Programmer", MasterScreen.WIDTH/4, 5*(MasterScreen.HEIGHT/6));
        text.displayText(batch, "Joaquin\nLead Programmer", MasterScreen.WIDTH/4, 3*(MasterScreen.HEIGHT/6));

        // screen right-hand side
        text.displayText(batch, "Dany\nGame Art", MasterScreen.WIDTH/2, 5*(MasterScreen.HEIGHT/6));
        text.displayText(batch, "Bobby\nProgrammer", (MasterScreen.WIDTH/2)+50, 3*(MasterScreen.HEIGHT/6));

        batch.end();

        aboutStage.draw();

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
        background.dispose();
        aboutStage.dispose();

    }

    @Override
    public void dispose() {

    }
}
