package mx.itesm.equipo5.Pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import mx.itesm.equipo5.Button;
import mx.itesm.equipo5.MasterScreen;
import mx.itesm.equipo5.Text;
import mx.itesm.equipo5.Virusito;

class SettingsScreen extends MasterScreen {

    private Texture background;

    private Stage settingsStage;

    private ImageButton homeButton;
    private ImageButton musicOnButton;
    private ImageButton musicOffButton;
    private ImageButton endlessResetButton;
    private ImageButton storyResetButton;

    private Text text;

    private int bestRound;

    Texture musicLabelTexture = new Texture("Botones/Music.png");
    Texture SFXLabelTexture = new Texture("Botones/Vfx.png");
    Texture storyResetTexture = new Texture("Botones/Replay_Bttn.png");

    private Preferences lvlPrefs = Gdx.app.getPreferences("userPrefs");


    public SettingsScreen(Virusito juego) {
        super(juego);
    }

    @Override
    public void show() {
        settingsStage = new Stage(view);

        background = new Texture("Pantallas/PantallaAjustes.jpg");

        text = new Text();

        createButtons();

        Gdx.input.setInputProcessor(settingsStage);
        Gdx.input.setCatchBackKey(true);

        bestRound = lvlPrefs.getInteger("endlessBestRound");
        if (bestRound == 0) {
            bestRound = 0;
        } else  {
            bestRound = lvlPrefs.getInteger("endlessBestRound");
        }
        lvlPrefs.flush();
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

        storyResetButton = new Button("Botones/Replay_Bttn.png").getiButton();
        storyResetButton.setPosition((1*MasterScreen.WIDTH/3)-200, (MasterScreen.HEIGHT/2)-196);
        settingsStage.addActor(storyResetButton);
        storyResetButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                // Responder al evento del boton
                lvlPrefs.putBoolean("level1Passed", false);
                lvlPrefs.putBoolean("level2Passed", false);
                lvlPrefs.putBoolean("level3Passed", false);
                lvlPrefs.flush();
            }
        });

        endlessResetButton = new Button("Botones/Replay_Bttn.png").getiButton();
        endlessResetButton.setPosition((2*MasterScreen.WIDTH/3)+90, (MasterScreen.HEIGHT/2)-196);
        settingsStage.addActor(endlessResetButton);
        endlessResetButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                // Responder al evento del boton
                lvlPrefs.putInteger("endlessBestRound",0);
                bestRound = 0;
                lvlPrefs.flush();
            }
        });

    }

    @Override
    public void render(float delta) {
        eraseScreen();

        batch.begin();
        batch.draw(background, 0, 0);



        text.displayText(batch, "SETTINGS", MasterScreen.WIDTH/2, 5*(MasterScreen.HEIGHT/6)+100);

        text.displayButtonText(batch, "Reset story?", (1*MasterScreen.WIDTH/3)-140, (MasterScreen.HEIGHT/2)-30);
        text.displayButtonText(batch, "You'll lose progress!", (1*MasterScreen.WIDTH/3)-120, (MasterScreen.HEIGHT/2)-225);

        text.displayButtonText(batch, "Reset best round?", (4*MasterScreen.WIDTH/5)+10, (MasterScreen.HEIGHT/2)-30);
        text.displayButtonText(batch, "Highest round is " + bestRound, (4*MasterScreen.WIDTH/5), (MasterScreen.HEIGHT/2)-225);

        batch.draw(musicLabelTexture, (MasterScreen.WIDTH/2)-180, (MasterScreen.HEIGHT/2)+70);
        batch.draw(SFXLabelTexture, (MasterScreen.WIDTH/2)-20, (MasterScreen.HEIGHT/2)+30);

        batch.end();

        settingsStage.draw();

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
        background.dispose();
        settingsStage.dispose();
    }
}
