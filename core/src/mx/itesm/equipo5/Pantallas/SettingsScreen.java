package mx.itesm.equipo5.Pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import mx.itesm.equipo5.Button;
import mx.itesm.equipo5.MasterScreen;
import mx.itesm.equipo5.Virusito;

class SettingsScreen extends MasterScreen {

    private Texture background;

    private Stage settingsStage;

    private ImageButton homeButton;

    public SettingsScreen(Virusito juego) {
        super(juego);
    }

    @Override
    public void show() {
        settingsStage = new Stage(view);

        background = new Texture("Pantallas/PantallaAjustes.jpg");

        createButtons();

        Gdx.input.setInputProcessor(settingsStage);
        Gdx.input.setCatchBackKey(false);
    }

    private void createButtons() {
        homeButton = new Button("Botones/Home_Bttn.png").getiButton();
        homeButton.setPosition(5, (MasterScreen.HEIGHT-homeButton.getHeight())-5);
        settingsStage.addActor(homeButton);
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

        batch.end();

        settingsStage.draw();

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
        settingsStage.dispose();
    }

    @Override
    public void dispose() {

    }
}
