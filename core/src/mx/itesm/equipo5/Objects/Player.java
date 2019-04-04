package mx.itesm.equipo5.Objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.LinkedList;
import java.util.List;

public class Player extends Entity {

    private List<Sprite> sprites = new LinkedList<Sprite>();

    //When we create bullet, power up, and passive classes, we will put them in here
    private float speedLimit; //This will for determining speed depending on joystick amount
    private int spriteIndex;
    private float dx, dy; //This will be percentage of joystick

    private Animation animation;
    private float animationTimer;

    public Player(float x, float y, float health) {

        this.health = health;

        //Load texture
        Texture texture = new Texture("Personajes/Main_Down.png");

        //Sprites
        sprite = new Sprite(texture);
        sprite.setPosition(x,y);
    }

    public void render(SpriteBatch batch) {
        animationTimer += Gdx.graphics.getDeltaTime();
        TextureRegion region = (TextureRegion) animation.getKeyFrame(animationTimer);
        batch.draw(region, sprite.getX(), sprite.getY());
    }
}
