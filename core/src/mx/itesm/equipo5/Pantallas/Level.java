package mx.itesm.equipo5.Pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import mx.itesm.equipo5.MasterScreen;
import mx.itesm.equipo5.Text;
import mx.itesm.equipo5.Virusito;

class Level extends MasterScreen {

    private TiledMap map;
    private OrthogonalTiledMapRenderer mapRenderer;

    // HUD
    private OrthographicCamera HUDcamera;
    private Viewport HUDview;
    private Stage HUDstage;

    private Text text;

    public Level(Virusito juego) {
        super(juego);
    }

    @Override
    public void show() {
        /*
        // esto ya no, right?
        camera = new OrthographicCamera(LoadingScreen.WIDTH, LoadingScreen.HEIGHT);
        camera.position.set(LoadingScreen.WIDTH /2, LoadingScreen.HEIGHT /2,0);
        camera.update();
        // Vista
        view = new StretchViewport(LoadingScreen.WIDTH, LoadingScreen.HEIGHT, camera);
        batch = new SpriteBatch();
        */

        loadMap();
        buildHUD();


    }

    private void loadMap() {
        AssetManager manager = new AssetManager();
        manager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
        manager.load("Mapa 1/1-1.tmx", TiledMap.class);
        manager.finishLoading();
        map = manager.get("Mapa 1/1-1.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(map);
        System.out.println("cargo el mapa");
    }

    private void buildHUD() {
        HUDcamera = new OrthographicCamera(WIDTH, HEIGHT);
        HUDcamera.position.set(WIDTH/2, HEIGHT/2, 0);
        HUDcamera.update();
        HUDview = new StretchViewport(WIDTH, HEIGHT, HUDcamera);

        // siguiendo el codigo de roman, aqui irian los pads, pero no se si haya una forma mejor


        // Agregar la escena, finalmente
        HUDstage = new Stage(HUDview);
        // TODO en caso de tener un pad, agregarlo como actor a HUDstage justo aqui

        // ahora la escena es quien atiende los eventos
        Gdx.input.setInputProcessor(HUDstage);
    }



    @Override
    public void render(float delta) {
        eraseScreen();

        // render the game map
        mapRenderer.render();


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
