package mx.itesm.equipo5.Pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;

import mx.itesm.equipo5.MasterScreen;
import mx.itesm.equipo5.Text;
import mx.itesm.equipo5.Virusito;

public class SpashScreen extends MasterScreen {

    private Texture background;

    private Stage aboutStage;

    private Text text;

    private float timeCounter;

    public SpashScreen(Virusito game) {
        super(game);
    }

    @Override
    public void show() {
        background = new Texture("Pantallas/PantallaLoading.png");

    }

    @Override
    public void render(float delta) {
        eraseScreen(0,0,0);
        batch.setProjectionMatrix(camera.combined);

        batch.begin();

        batch.end();

        //prueba tiempo
        timeCounter +=delta;
        if (timeCounter >=2){
            //Conto 1 s
            game.setScreen(new LoadingScreen(game));
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
        batch.dispose();
    }
}
