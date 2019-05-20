package mx.itesm.equipo5;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Representa comportamiento genérico de cualquier pantalla que forma
 * parte del game
 */

public abstract class MasterScreen implements Screen
{


    // Atributos disponibles en todas las clases del proyecto
    public static final float WIDTH = 1280;
    public static final float HEIGHT = 720;
    public static final float PPM = 1;
    public final Virusito game;

    // Atributos disponibles solo en las subclases
    // Todas las pantallas tienen una cámara y una view
    protected OrthographicCamera camera;
    protected Viewport view;        
    // Todas las pantallas dibujan algo
    protected SpriteBatch batch;

    public MasterScreen(Virusito game) {
        this.game = game;
        // Crea la cámara con las dimensiones del mundo
        camera = new OrthographicCamera(WIDTH/PPM, HEIGHT/PPM);
        // En el centro de la pantalla
        camera.position.set((WIDTH / 2)/PPM, (HEIGHT / 2)/PPM, 0);
        camera.update();
        // La view que escala los elementos gráficos
        view = new StretchViewport(WIDTH/PPM, HEIGHT/PPM, camera);
        // El objeto que administra los trazos gráficos
        batch = new SpriteBatch();
    }

    // Borra la pantalla con fondo negro
    protected void eraseScreen() {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(camera.combined);
    }

    // Borra la pantalla con el color RGB (r,g,b)
    protected void eraseScreen(float r, float g, float b) {
        Gdx.gl.glClearColor(r,g,b,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(camera.combined);

    }

    @Override
    public void resize(int width, int height) {
        view.update(width, height);
    }

    @Override
    public void hide() {
        // Libera los recursos asignados por cada pantalla
        // Las subclases están obligadas a sobrescribir el método dispose()
        dispose();
    }
}
