package mx.itesm.equipo5.Pantallas;
import mx.itesm.equipo5.Virusito;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.Viewport;

import mx.itesm.equipo5.MasterScreen;
import mx.itesm.equipo5.MasterScreen;

public class PauseStage extends Stage {

    private Preferences lvlPrefs = Gdx.app.getPreferences("userPrefs");
    private boolean isSoundOn = lvlPrefs.getBoolean("soundOn");

    private AssetManager assetManager;

    public void PauseScene(Viewport view, SpriteBatch batch) {
        //super(view, batch);
        // Creación de texturas
        Texture homeBttnTexture;
        Texture playBttnTexture;
        //Texture restartButton;

        Pixmap pixmap = new Pixmap((int) (MasterScreen.WIDTH * 0.7f), (int) (MasterScreen.HEIGHT * 0.8f), Pixmap.Format.RGBA8888);
        pixmap.setColor(0.5f, 0.5f, 0.6f, 0.75f);
        pixmap.fillRectangle(0, 0, pixmap.getWidth(), pixmap.getHeight());
        Texture texturaRectangulo = new Texture(pixmap);
        pixmap.dispose();
        Image rectImg = new Image(texturaRectangulo);
        rectImg.setPosition(0.15f * MasterScreen.WIDTH, 0.1f * MasterScreen.HEIGHT);
        this.addActor(rectImg);

        homeBttnTexture = assetManager.get("Botones/Home_Bttn.png");
        TextureRegionDrawable trdSalir = new TextureRegionDrawable(new TextureRegion(homeBttnTexture));
        ImageButton homeButton = new ImageButton(trdSalir);
        homeButton.setPosition((MasterScreen.WIDTH/2 - homeButton.getWidth()/2)-250, (MasterScreen.HEIGHT/2)+50);
        homeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Regresa al menú
                if (isSoundOn) {
                    Endless.music.dispose();
                }
                game.setScreen(new MenuScreen(game));

            }
        });
        this.addActor(homeButton);

        playBttnTexture = assetManager.get("Botones/Play_Bttn.png");
        TextureRegionDrawable trdContinuar = new TextureRegionDrawable(
                new TextureRegion(playBttnTexture));
        ImageButton playButton = new ImageButton(trdContinuar);
        playButton.setPosition(MasterScreen.WIDTH / 2 - playButton.getWidth() / 2 , MasterScreen.HEIGHT / 4);
        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // return to the game
                Endless.loadMap();
                Gdx.input.setInputProcessor(Endless.HUDstage);
                Endless.gameState = GameState.PLAYING;
            }
        });
        this.addActor(playButton);

        // TODO now that we have the asset, create and place the level restart button for the pause menu
            /*restartButton = assetManager.get("Botones/Replay_Bttn.png");

            TextureRegionDrawable trdRestart = new TextureRegionDrawable(new TextureRegion(restartButton));

            ImageButton restartBttn = new ImageButton(trdRestart);

            restartBttn.setPosition(WIDTH/2 - restartBttn.getWidth()/2 + 150, HEIGHT/4);

            restartBttn.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if(isSoundOn) {
                        music.stop();
                    }
                    // we should get rid of the current screen tho, call dispose i guess
                    game.setScreen(new Endless(game));
                }
            });
            this.addActor(restartBtn);*/
    }
}
