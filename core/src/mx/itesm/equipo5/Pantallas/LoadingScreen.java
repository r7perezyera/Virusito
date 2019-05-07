package mx.itesm.equipo5.Pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

import mx.itesm.equipo5.MasterScreen;
import mx.itesm.equipo5.Objects.viewingDirection;
import mx.itesm.equipo5.Virusito;

public class LoadingScreen extends MasterScreen {

    private Texture logo;
    private Texture texture;
    private float animationTimer;
    private Animation animation;
    private Sprite sprite;


    //Tiempo
    private float timeCounter;

    public LoadingScreen(Virusito game) {
        super(game);
        //Load texture front
        texture = new Texture("Animaciones/Main_Anim_Loading.png");

        TextureRegion region = new TextureRegion(texture);
        TextureRegion[][] texturasPersonajes = region.split(200,231);

        Array<TextureRegion> textureRegionArray = new Array(texturasPersonajes[0]);

        animation = new Animation(0.15f,textureRegionArray);
        animation.setPlayMode(Animation.PlayMode.LOOP);
        animationTimer = 0;

        sprite = new Sprite(texturasPersonajes[0][0]);
        sprite.setPosition((WIDTH/2)-sprite.getWidth()/2,(HEIGHT/2)-sprite.getHeight()/2);
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
        animationTimer += Gdx.graphics.getDeltaTime();
        TextureRegion region = (TextureRegion) animation.getKeyFrame(animationTimer);
        batch.draw(logo, WIDTH /2-logo.getWidth()/2, HEIGHT /2-logo.getHeight()/2);
        batch.draw(region, sprite.getX(), sprite.getY());
        batch.end();

        //prueba tiempo
        timeCounter +=delta;
        if (timeCounter >=4){
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
        batch.dispose();
    }
}
