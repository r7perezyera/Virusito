package mx.itesm.equipo5;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

import mx.itesm.equipo5.Pantallas.SplashScreen;

public class Virusito extends Game {

    // Asset Manager
    /*
    will try initially to manage music and SFX
    we could eventually turn it into THE only manager for the whole game,
    i.e. for all the resources that require the asset manager
     */
    //private AssetManager audioManager;
    /*private boolean canPlayMusic;
    private boolean canPlaySound;*/

    //private Music music;

    public Virusito() {

    }

    @Override
    public void create(){
        // por ahora NO necesitamos prepararlo para que cargue mapas
        //assetManager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));

        // Pone la pantalla inicial
        setScreen(new SplashScreen(this));     // original, keep this
        /*setCanPlayMusic(true);
        setCanPlaySound(true);*/

        // create prefs
        Preferences prefs = Gdx.app.getPreferences("userPrefs");

        prefs.putBoolean("soundON", true);


        prefs.putBoolean("level1Passed", true);


        if (!prefs.getBoolean("level1Passed")) {
            prefs.putBoolean("level1Passed", true);
        }
        if (!prefs.getBoolean("level2Passed")) {
            prefs.putBoolean("level2Passed", true);
        }
        if (!prefs.getBoolean("level3Passed")) {
            prefs.putBoolean("level3Passed", false);
        }

        prefs.flush();
    }



    /*// Para que las otras pantallas usen el audioManager
    public AssetManager getAudioManager() {
        return audioManager;
    }*/

    /*public boolean getCanPlayMusic() {  // isCanPlayMusic   // isMusicEnabled
        return canPlayMusic;
    }

    public void setCanPlayMusic(boolean canPlayMusic) {
        this.canPlayMusic = canPlayMusic;
    }

    public boolean getCanPlaySound() {  // isCanPlayMusic   // isMusicEnabled
        return canPlaySound;
    }

    public void setCanPlaySound(boolean canPlaySound) {
        this.canPlaySound = canPlaySound;
    }*/

    @Override
    public void dispose() {
        super.dispose();
    }

}
