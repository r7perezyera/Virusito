package mx.itesm.equipo5.Pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Texture;

import mx.itesm.equipo5.MasterScreen;
import mx.itesm.equipo5.Virusito;

class StoryScreen extends MasterScreen {

    private Texture slides;
    private float timer = 2f;

    private Preferences lvlPrefs = Gdx.app.getPreferences("userPrefs");
    boolean isSoundOn = lvlPrefs.getBoolean("soundOn");

    private int currentSlide=1;

    // Music and SFX
    private Music music;

    public StoryScreen(Virusito game) {
        super(game);
    }

    @Override
    public void show() {

        loadMusic();

        if (isSoundOn) {
            music.play();
        }

        Gdx.input.setInputProcessor(new InputAdapter(){

            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {

                timer = 2f;

                return true;
            }

        });
    }

    private void loadMusic() {
        music = Gdx.audio.newMusic(Gdx.files.internal("Music/Cataclysmic_Molten_Core.mp3"));
        music.setLooping(true);
    }

    @Override
    public void render(float delta) {
        if (timer>=2) {
            System.out.println(currentSlide);
            if(currentSlide == 9){
                game.setScreen(new LvlSelectScreen(game));
                if (isSoundOn) {
                    music.stop();
                }
            }
            else{slides = new Texture("Slides/Story_" + currentSlide++ + ".png");
                timer =0;
            }
        }
        timer+=delta;
        eraseScreen();
        batch.begin();
        //Dibujar
        batch.draw(slides, 0,0);
        batch.end();

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}
