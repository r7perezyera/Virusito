package mx.itesm.equipo5.Pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import mx.itesm.equipo5.Pantalla;
import mx.itesm.equipo5.Virusito;

class PantallaAjustes extends Pantalla {
    public PantallaAjustes(Virusito juego) {
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
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camara.combined);        // para escalar


        batch.begin();


        batch.end();

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
        batch.dispose();
    }

    @Override
    public void dispose() {

    }
}
