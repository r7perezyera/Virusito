package mx.itesm.equipo5.Pantallas;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import mx.itesm.equipo5.Pantalla;
import mx.itesm.equipo5.Text;
import mx.itesm.equipo5.Virusito;

class Level extends Pantalla {



    private Text text;

    public Level(Virusito juego) {
        super(juego);
    }

    @Override
    public void show() {

        camera = new OrthographicCamera(LoadingScreen.WIDTH, LoadingScreen.HEIGHT);
        camera.position.set(LoadingScreen.WIDTH /2, LoadingScreen.HEIGHT /2,0);
        camera.update();
        // Vista
        view = new StretchViewport(LoadingScreen.WIDTH, LoadingScreen.HEIGHT, camera);
        batch = new SpriteBatch();

    }

    @Override
    public void render(float delta) {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
