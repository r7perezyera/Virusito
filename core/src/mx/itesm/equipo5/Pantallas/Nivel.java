package mx.itesm.equipo5.Pantallas;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import mx.itesm.equipo5.Pantalla;
import mx.itesm.equipo5.Texto;
import mx.itesm.equipo5.Virusito;

class Nivel extends Pantalla {



    private Texto text;

    public Nivel(Virusito juego) {
        super(juego);
    }

    @Override
    public void show() {

        camara = new OrthographicCamera(PantallaCargando.ANCHO, PantallaCargando.ALTO);
        camara.position.set(PantallaCargando.ANCHO/2, PantallaCargando.ALTO/2,0);
        camara.update();
        // Vista
        vista = new StretchViewport(PantallaCargando.ANCHO, PantallaCargando.ALTO,camara);
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
