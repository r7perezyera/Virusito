package mx.itesm.equipo5.Pantallas;

import com.badlogic.gdx.Gdx;
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

    private ImageButton homeButton;
    private ImageButton musicOnButton;
    private ImageButton musicOffButton;
    private ImageButton SFXOnButton;
    private ImageButton SFXOffButton;

    Texture musicLabelTexture = new Texture("Botones/Music.png");
    Texture SFXLabelTexture = new Texture("Botones/Vfx.png");

    //private final AssetManager audioManager;


    public SettingsScreen(Virusito juego) {
        super(juego);
        //audioManager = Virusito.getAudioManager();
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
        musicOnButton.setPosition(MasterScreen.WIDTH/2, (MasterScreen.HEIGHT/2)+50);
        settingsStage.addActor(musicOnButton);
        musicOnButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                // Responder al evento del boton
                System.out.println("turn music off");
                game.setCanPlayMusic(false);
                System.out.println(game.getCanPlayMusic());
                settingsStage.addActor(musicOffButton);
                musicOnButton.remove();
            }
        });

        // turn music on
        musicOffButton = new Button("Botones/Off_Bttn.png", "Botones/On_Bttn.png").getiButton();
        musicOffButton.setPosition(MasterScreen.WIDTH/2, (MasterScreen.HEIGHT/2)+50);
        //settingsStage.addActor(musicOffButton);
        musicOffButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                // Responder al evento del boton
                System.out.println("turn music on");
                game.setCanPlayMusic(true);
                System.out.println(game.getCanPlayMusic());
                settingsStage.addActor(musicOnButton);
                musicOffButton.remove();
            }
        });

        // turn sfx off
        SFXOnButton = new Button("Botones/On_Bttn.png", "Botones/Off_Bttn.png").getiButton();
        SFXOnButton.setPosition(MasterScreen.WIDTH/2, (MasterScreen.HEIGHT/2)-125);
        settingsStage.addActor(SFXOnButton);
        SFXOnButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                // Responder al evento del boton
                settingsStage.addActor(SFXOffButton);
                SFXOnButton.remove();
            }
        });

        // turn sfx on
        SFXOffButton = new Button("Botones/Off_Bttn.png", "Botones/On_Bttn.png").getiButton();
        SFXOffButton.setPosition(MasterScreen.WIDTH/2, (MasterScreen.HEIGHT/2)-125);
        //settingsStage.addActor(musicOffButton);
        SFXOffButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                // Responder al evento del boton
                settingsStage.addActor(SFXOnButton);
                SFXOffButton.remove();
            }
        });
    }

    @Override
    public void render(float delta) {
        eraseScreen();

        batch.begin();
        batch.draw(background, 0, 0);

        Text text = new Text();
        text.displayText(batch, "SETTINGS", MasterScreen.WIDTH/2, 5*(MasterScreen.HEIGHT/6)+100);

        batch.draw(musicLabelTexture, (MasterScreen.WIDTH/2)-160, (MasterScreen.HEIGHT/2)+50);
        batch.draw(SFXLabelTexture, (MasterScreen.WIDTH/2)-160, (MasterScreen.HEIGHT/2)-125);

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
