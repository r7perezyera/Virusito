package mx.itesm.equipo5.Pantallas;

import com.badlogic.gdx.graphics.Texture;

import mx.itesm.equipo5.MasterScreen;
import mx.itesm.equipo5.Virusito;

public class LoadingScreen extends MasterScreen {

    private Texture logo;

    //Tiempo
    private float timeCounter;

    public LoadingScreen(Virusito game) {
        super(game);
    }

    @Override
    public void show() {
        logo = new Texture("Pantallas/PantallaLoading.png");
    }

    @Override
    public void render(float delta) {
        eraseScreen(1,1,1);
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(logo, WIDTH /2-logo.getWidth()/2, HEIGHT /2-logo.getHeight()/2);
        batch.end();

        //prueba tiempo
        timeCounter +=delta;
        if (timeCounter >=3){
            //Conto 1 s
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
        logo.dispose();
    }
}
