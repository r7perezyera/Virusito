package mx.itesm.equipo5;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;

import mx.itesm.equipo5.Pantallas.LoadingScreen;

public class Virusito extends Game {

    // Asset Manager
    /*
    will try initially to manage music and SFX
    we could eventually turn it into THE only manager for the whole game,
    i.e. for all the resources that require the asset manager
     */
    private final AssetManager audioManager;  // should be final as well
    private boolean canPlayMusic;

    public Virusito() {
        audioManager = new AssetManager();
    }

    @Override
    public void create(){
        // por ahora NO necesitamos prepararlo para que cargue mapas
        //assetManager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));

        // Pone la pantalla inicial
        setScreen(new LoadingScreen(this));     // original, keep this
        setCanPlayMusic(true);
    }

    // Para que las otras pantallas usen el audioManager
    public AssetManager getAudioManager() {
        return audioManager;
    }

    public boolean getCanPlayMusic() {
        return canPlayMusic;
    }

    public void setCanPlayMusic(boolean canPlayMusic) {
        this.canPlayMusic = canPlayMusic;
    }

    @Override
    public void dispose() {
        super.dispose();
        audioManager.clear();
    }

}
