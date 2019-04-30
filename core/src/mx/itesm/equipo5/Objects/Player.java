package mx.itesm.equipo5.Objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import com.badlogic.gdx.utils.Array;
import java.util.LinkedList;
import java.util.List;

import mx.itesm.equipo5.Text;

public class Player extends Entity {

    private List<Sprite> sprites = new LinkedList<Sprite>();

    //When we create bullet, power up, and passive classes, we will put them in here
    private float speedLimit; //This will for determining speed depending on joystick amount
    private int spriteIndex;
    private float dx, dy; //This will be percentage of joystick

    private Animation animation;
    private float animationTimer;


    public Player(float x, float y, float health) {
        width = 50;
        height = 57;
        this.health = health;
        this.speed = 7;
        //Load texture
        texture = new Texture("Animaciones/Principal_Animacion.png");

        TextureRegion region = new TextureRegion(texture);
        TextureRegion[][] texturasPersonajes = region.split(width,height);

        Array<TextureRegion> textureRegionArray = new Array(texturasPersonajes[0]);

        animation = new Animation(0.15f,textureRegionArray);
        animation.setPlayMode(Animation.PlayMode.LOOP);
        animationTimer = 0;

        //Sprites
        sprite = new Sprite(texturasPersonajes[0][0]);
        sprite.setPosition(x,y);

        rectangle.set(x,y,width,height);


    }

    public void render(SpriteBatch batch) {
        animationTimer += Gdx.graphics.getDeltaTime();
        TextureRegion region = (TextureRegion) animation.getKeyFrame(animationTimer);
        batch.draw(region, sprite.getX(), sprite.getY());
    }

}
