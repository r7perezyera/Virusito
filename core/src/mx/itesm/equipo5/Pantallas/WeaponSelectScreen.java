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
import mx.itesm.equipo5.Objects.weaponType;
import mx.itesm.equipo5.Virusito;

public class WeaponSelectScreen extends MasterScreen {

    private Texture background;

    private Stage lvlSelectStage;

    private ImageButton homeButton;
    private ImageButton shotgunButton;
    private ImageButton pistolButton;
    private ImageButton bazookaButton;
    private ImageButton randomButton;


    private Preferences lvlPrefs = Gdx.app.getPreferences("userPrefs");
    boolean isSoundOn = lvlPrefs.getBoolean("soundOn");

    // Music and SFX
    private Sound playSound;

    public WeaponSelectScreen(Virusito game) {
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
        shotgunButton = new Button("Items/Weapon_SHOTGUN.png").getiButton();
        shotgunButton.setPosition(MasterScreen.WIDTH/5-shotgunButton.getWidth()/2, (MasterScreen.HEIGHT/2-shotgunButton.getHeight()/2));
        lvlSelectStage.addActor(shotgunButton);
        shotgunButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                // Responder al evento del boton
                if (isSoundOn) {
                    playSound.play();
                }
                game.setScreen(new Endless(game, weaponType.SHOTGUN));
            }
        });

        // level 2 button
        pistolButton = new Button("Items/Weapon_PISTOL.png").getiButton();
        pistolButton.setPosition((MasterScreen.WIDTH/3- pistolButton.getWidth()/2)+100, (MasterScreen.HEIGHT/2- pistolButton.getHeight()/2));
            lvlSelectStage.addActor(pistolButton);
        pistolButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                // Responder al evento del boton
                if (isSoundOn) {
                    playSound.play();
                }
                game.setScreen(new Endless(game, weaponType.PISTOL));
            }
        });

        bazookaButton = new Button("Items/Weapon_BAZOOKA.png").getiButton();
        bazookaButton.setPosition((MasterScreen.WIDTH/2- bazookaButton.getWidth()/2)+150, (MasterScreen.HEIGHT/2- bazookaButton.getHeight()/2));
        lvlSelectStage.addActor(bazookaButton);
        bazookaButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                // Responder al evento del boton
                if (isSoundOn) {
                    playSound.play();
                }
                game.setScreen(new Endless(game, weaponType.BAZOOKA));
            }
        });

        randomButton = new Button("Botones/Endless_Bttn.png").getiButton();
        randomButton.setPosition((4*MasterScreen.WIDTH/5)- randomButton.getWidth()/2+35, (MasterScreen.HEIGHT/2- randomButton.getHeight()/2));
        lvlSelectStage.addActor(randomButton);

        randomButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                // Responder al evento del boton
                if (isSoundOn) {
                    playSound.play();
                }
                game.setScreen(new Endless(game, weaponType.randomLetter()));
            }
        });

    }

    @Override
    public void render(float delta) {
        eraseScreen();

        batch.begin();
        batch.draw(background,0,0);



        batch.end();


        lvlSelectStage.draw();

        if (Gdx.input.isKeyPressed(Input.Keys.BACK)) {
            game.setScreen(new LvlSelectScreen(game));
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
