package mx.itesm.equipo5.Objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

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

        this.health = health;
        this.speed = 5;
        //Load texture
        texture = new Texture("Personajes/Main_Down.png");

        animation = new Animation(0.15f,texture);
        animation.setPlayMode(Animation.PlayMode.LOOP);
        animationTimer = 0;

        //Sprites
        sprite = new Sprite(texture);
        sprite.setPosition(x,y);
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, sprite.getX(), sprite.getY());
    }
}
