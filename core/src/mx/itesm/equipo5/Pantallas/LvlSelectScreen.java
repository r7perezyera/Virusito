package mx.itesm.equipo5.Pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import mx.itesm.equipo5.Button;
import mx.itesm.equipo5.MasterScreen;
import mx.itesm.equipo5.Virusito;

public class LvlSelectScreen extends MasterScreen {

    private Texture background;

    private Stage lvlSelectStage;

    private Texture lvlLockedTexture = new Texture("Botones/Level_Blocked.png");

    private ImageButton homeButton;
    private ImageButton lvl1Button;
    private ImageButton lvl2Button;
    private ImageButton lvl3Button;
    private ImageButton lvlEndlessButton;
    private ImageButton cheatButton;


    private Preferences lvlPrefs = Gdx.app.getPreferences("userPrefs");
    boolean isSoundOn = lvlPrefs.getBoolean("soundOn");

    // Music and SFX
    private Sound playSound;

    public LvlSelectScreen(Virusito game) {
        super(game);
    }


    @Override
    public void show() {
        lvlSelectStage = new Stage(view);

        background = new Texture("Pantallas/PantallaNiveles.png");

        createButtons();
        if (isSoundOn) {
            loadSFX();
        }

        Gdx.input.setInputProcessor(lvlSelectStage);
        Gdx.input.setCatchBackKey(true);
    }

    private void loadSFX() {
        playSound = Gdx.audio.newSound(Gdx.files.internal("Music/SFX/PlayButton.wav"));
    }

    private void createButtons() {
        homeButton = new Button("Botones/Home_Bttn.png").getiButton();
        homeButton.setPosition(15, (MasterScreen.HEIGHT-homeButton.getHeight())-10);
        lvlSelectStage.addActor(homeButton);
        homeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                // Responder al evento del boton
                game.setScreen(new MenuScreen(game));
            }
        });

        // level 1 button - should be ALWAYS available
        lvl1Button = new Button("Botones/Level_1.png").getiButton();
        lvl1Button.setPosition(MasterScreen.WIDTH/5-lvl1Button.getWidth()/2, (MasterScreen.HEIGHT/2-lvl1Button.getHeight()/2));
        lvlSelectStage.addActor(lvl1Button);
        lvl1Button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                // Responder al evento del boton
                if (isSoundOn) {
                    playSound.play();
                }
                game.setScreen(new Level1(game));
            }
        });

        // level 2 button
        lvl2Button = new Button("Botones/Level_2.png").getiButton();
        lvl2Button.setPosition((MasterScreen.WIDTH/3-lvl2Button.getWidth()/2)+100, (MasterScreen.HEIGHT/2-lvl2Button.getHeight()/2));
        if (lvlPrefs.getBoolean("level1Passed")) {
            lvlSelectStage.addActor(lvl2Button);
        }
        lvl2Button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                // Responder al evento del boton
                if (isSoundOn) {
                    playSound.play();
                }
                game.setScreen(new Level1(game));
            }
        });

        lvl3Button = new Button("Botones/Level_3.png").getiButton();
        lvl3Button.setPosition((MasterScreen.WIDTH/2-lvl3Button.getWidth()/2)+150, (MasterScreen.HEIGHT/2-lvl3Button.getHeight()/2));
        if (lvlPrefs.getBoolean("level2Passed")) {
            lvlSelectStage.addActor(lvl3Button);
        }
        lvl3Button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                // Responder al evento del boton
                if (isSoundOn) {
                    playSound.play();
                }
                game.setScreen(new Level1(game));
            }
        });

        lvlEndlessButton = new Button("Botones/Endless_Bttn.png").getiButton();
        lvlEndlessButton.setPosition((4*MasterScreen.WIDTH/5)-lvlEndlessButton.getWidth()/2+35, (MasterScreen.HEIGHT/2-lvlEndlessButton.getHeight()/2));
        if (lvlPrefs.getBoolean("level3Passed")) {
            lvlSelectStage.addActor(lvlEndlessButton);
        }
        lvlEndlessButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                // Responder al evento del boton
                if (isSoundOn) {
                    playSound.play();
                }
                game.setScreen(new Endless(game));
            }
        });

        cheatButton = new Button("Botones/Level_Unblocked.png").getiButton();
        cheatButton.setPosition(((MasterScreen.WIDTH/2)-cheatButton.getWidth()/2)+24, (MasterScreen.HEIGHT/2-cheatButton.getHeight()/2)-225);
        lvlSelectStage.addActor(cheatButton);
        cheatButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                // Responder al evento del boton
                // unlock all levels
                lvlPrefs.putBoolean("level1Passed", true);
                lvlPrefs.putBoolean("level2Passed", true);
                lvlPrefs.putBoolean("level3Passed", true);
                lvlPrefs.flush();
                lvlSelectStage.addActor(lvl2Button);
                lvlSelectStage.addActor(lvl3Button);
                lvlSelectStage.addActor(lvlEndlessButton);
                // for test purposes - remove at end
            }
        });
    }

    @Override
    public void render(float delta) {
        eraseScreen();

        batch.begin();
        batch.draw(background,0,0);

        // dont draw lvl2 button yet
        if (!lvlPrefs.getBoolean("level1Passed")) {
            batch.draw(lvlLockedTexture, (MasterScreen.WIDTH/3-lvl2Button.getWidth()/2)+100, (MasterScreen.HEIGHT/2-lvl2Button.getHeight()/2));
        }
        // dont draw lvl3 button yet
        if (!lvlPrefs.getBoolean("level2Passed")) {
            batch.draw(lvlLockedTexture, (MasterScreen.WIDTH/2-lvl3Button.getWidth()/2)+150, (MasterScreen.HEIGHT/2-lvl3Button.getHeight()/2));
        }
        // dont draw endless button yet
        if (!lvlPrefs.getBoolean("level3Passed")) {
            batch.draw(lvlLockedTexture, (4*MasterScreen.WIDTH/5)-lvl3Button.getWidth()/2+35, (MasterScreen.HEIGHT/2-lvl3Button.getHeight()/2));
        }

        batch.end();


        lvlSelectStage.draw();

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
        lvlSelectStage.dispose();
    }
}
