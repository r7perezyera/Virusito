package mx.itesm.equipo5.Pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import mx.itesm.equipo5.MasterScreen;
import mx.itesm.equipo5.Virusito;

class SettingsScreen extends MasterScreen {

    private Texture background;

    private Stage settingsStage;

    public SettingsScreen(Virusito juego) {
        super(juego);
    }

    @Override
    public void show() {
        background = new Texture("Pantallas/SettingsScreen.jpg");
        camera = new OrthographicCamera(LoadingScreen.WIDTH, LoadingScreen.HEIGHT);
        camera.position.set(LoadingScreen.WIDTH /2, LoadingScreen.HEIGHT /2,0);
        camera.update();
        // Vista
        view = new StretchViewport(LoadingScreen.WIDTH, LoadingScreen.HEIGHT, camera);
        batch = new SpriteBatch();

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camera.combined);        // para escalar


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
