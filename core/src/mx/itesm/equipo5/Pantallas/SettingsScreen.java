package mx.itesm.equipo5.Pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import mx.itesm.equipo5.Button;
import mx.itesm.equipo5.MasterScreen;
import mx.itesm.equipo5.Text;
import mx.itesm.equipo5.Virusito;

class SettingsScreen extends MasterScreen {

    private Texture background;

    private Stage settingsStage;

    private Text text;

    private ImageButton homeButton;
    private ImageButton musicOnButton;
    private ImageButton musicOffButton;

    Texture musicLabelTexture = new Texture("Botones/Music.png");
    Texture SFXLabelTexture = new Texture("Botones/Vfx.png");

    private Preferences lvlPrefs = Gdx.app.getPreferences("userPrefs");


    public SettingsScreen(Virusito juego) {
        super(juego);
    }

    @Override
    public void show() {
        settingsStage = new Stage(view);

        background = new Texture("Pantallas/PantallaAjustes.jpg");

        createButtons();
        loadText();

        Gdx.input.setInputProcessor(settingsStage);
        Gdx.input.setCatchBackKey(false);
    }

    private void loadText() {
        text = new Text();
    }

    private void createButtons() {
        homeButton = new Button("Botones/Home_Bttn.png").getiButton();
        homeButton.setPosition(15, (MasterScreen.HEIGHT-homeButton.getHeight())-10);
        settingsStage.addActor(homeButton);
        homeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                // Responder al evento del boton
                game.setScreen(new MenuScreen(game));
            }
        });




        // music and sfx on/off buttons
        // turn music off
        musicOnButton = new Button("Botones/On_Bttn.png", "Botones/Off_Bttn.png").getiButton();
        musicOnButton.setPosition((MasterScreen.WIDTH/2)+120, (MasterScreen.HEIGHT/2)+50);
        musicOnButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                // Responder al evento del boton
                lvlPrefs.putBoolean("soundOn", false);
                lvlPrefs.flush();
                settingsStage.addActor(musicOffButton);
                musicOnButton.remove();
            }
        });

        // turn music on
        musicOffButton = new Button("Botones/Off_Bttn.png", "Botones/On_Bttn.png").getiButton();
        musicOffButton.setPosition((MasterScreen.WIDTH/2)+120, (MasterScreen.HEIGHT/2)+50);
        musicOffButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                // Responder al evento del boton
                lvlPrefs.putBoolean("soundOn", true);
                lvlPrefs.flush();
                settingsStage.addActor(musicOnButton);
                musicOffButton.remove();
            }
        });

        if (lvlPrefs.getBoolean("soundOn")) {
            settingsStage.addActor(musicOnButton);
        } else {
            settingsStage.addActor(musicOffButton);
        }

    }

    @Override
    public void render(float delta) {
        eraseScreen();

        batch.begin();
        batch.draw(background, 0, 0);

        text.displayText(batch, "SETTINGS", MasterScreen.WIDTH/2, 5*(MasterScreen.HEIGHT/6)+100);

        batch.draw(musicLabelTexture, (MasterScreen.WIDTH/2)-180, (MasterScreen.HEIGHT/2)+70);
        batch.draw(SFXLabelTexture, (MasterScreen.WIDTH/2)-20, (MasterScreen.HEIGHT/2)+30);

        batch.end();

        settingsStage.draw();

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
        settingsStage.dispose();
    }
}
