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
import mx.itesm.equipo5.Virusito;

public class LvlSelectScreen extends MasterScreen {

    private Texture background;

    private Stage lvlSelectStage;

    private ImageButton homeButton;
    private ImageButton lvl1Button;
    private ImageButton lvl2Button;
    private ImageButton lvl3Button;
    private ImageButton lvlEndlessButton;

    private Texture lvlLockedTexture = new Texture("Botones/Level_Blocked.png");

    private Preferences lvlPrefs = Gdx.app.getPreferences("userPrefs");

    public LvlSelectScreen(Virusito game) {
        super(game);
    }


    @Override
    public void show() {
        lvlSelectStage = new Stage(view);

        background = new Texture("Pantallas/PantallaNiveles.png");

        createButtons();

        Gdx.input.setInputProcessor(lvlSelectStage);
        Gdx.input.setCatchBackKey(true);
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
                game.setScreen(new Endless(game));
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
                game.setScreen(new Level(game));
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
                game.setScreen(new Level(game));
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
                game.setScreen(new Endless(game));
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
