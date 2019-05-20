package mx.itesm.equipo5;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

import mx.itesm.equipo5.Pantallas.SplashScreen;

public class Virusito extends Game {

    public Virusito() {

    }

    @Override
    public void create(){
        // por ahora NO necesitamos prepararlo para que cargue mapas
        //assetManager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));

        // Pone la pantalla inicial


        // create prefs
        Preferences prefs = Gdx.app.getPreferences("userPrefs");
        // initial sound prefs
        prefs.putBoolean("soundON", true);
        // initial story progress (unlocked levels) prefs

        if (!prefs.getBoolean("level1Passed")) {
            prefs.putBoolean("level1Passed", false);
            prefs.putBoolean("level2Passed", false);
            if (!prefs.getBoolean("level2Passed")) {
                System.out.println("false");
                prefs.putBoolean("level3Passed", false);
            }
        }

        prefs.flush();
        setScreen(new SplashScreen(this));
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
