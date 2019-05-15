package mx.itesm.equipo5.Pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;

import mx.itesm.equipo5.MasterScreen;
import mx.itesm.equipo5.Text;
import mx.itesm.equipo5.Virusito;

public class SplashScreen extends MasterScreen {

    private Texture background;
    private Texture logo;
    private float timeCounter;

    public SplashScreen(Virusito game) {
        super(game);
    }

    @Override
    public void show() {
        background = new Texture("Pantallas/PantallaLoading.png");
        logo = new Texture("Pantallas/splash_amborgesa.png");


    }

    @Override
    public void render(float delta) {
        eraseScreen(0,0,0);
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        batch.draw(logo, WIDTH/2-logo.getWidth()/2f, HEIGHT/2-logo.getHeight()/2f);
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
        logo.dispose();
        batch.dispose();
    }
}
