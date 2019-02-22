package mx.itesm.equipo5;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;


public class PantallaCargando implements Screen {

    //Variables de Juego
    public static final int Ancho = 1280;
    public static final int Alto = 720;
    private final Virusito juego;

    // Camara del juego
    private OrthographicCamera camera;
    private Viewport vista; //Escalar
    private SpriteBatch batch;  // Optimizar los gráficos

    // Imagen
    private Texture logo;


    //Tiempo
    private float contadorTiempo;



    public PantallaCargando(Virusito juego) { this.juego = juego; }

    @Override
    public void show() {
        // Constructor
        camera = new OrthographicCamera(Ancho, Alto);
        camera.position.set(Ancho/2, Alto/2,0);
        camera.update();
        //Vista
        vista = new StretchViewport(Ancho,Alto, camera);
        batch = new SpriteBatch();
        // Imagen
        logo = new Texture("Logo/TecMonterrey.png");
    }

    @Override
    public void render(float delta) {
        // Dibujar (60 fps)
        Gdx.gl.glClearColor(1,1,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); //Borra completo

        // Agregar Camara
        batch.setProjectionMatrix(camera.combined); // Escala adecuado
        batch.begin();

        batch.draw(logo, Ancho/2-logo.getWidth()/2, Alto/2-logo.getHeight()/2);
        batch.end();

        //prueba tiempo
        contadorTiempo +=delta;
        if (contadorTiempo>=2){
            //Conto 2 s
            juego.setScreen(new PantallaMenu(juego));
        }

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
    public void hide() { dispose(); }

    @Override
    public void dispose() {
        logo.dispose();
        batch.dispose();
    }
}
