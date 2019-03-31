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

        camara = new OrthographicCamera(LoadingScreen.ANCHO, LoadingScreen.ALTO);
        camara.position.set(LoadingScreen.ANCHO/2, LoadingScreen.ALTO/2,0);
        camara.update();
        // Vista
        vista = new StretchViewport(LoadingScreen.ANCHO, LoadingScreen.ALTO,camara);
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
