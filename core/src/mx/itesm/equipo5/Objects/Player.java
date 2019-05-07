package mx.itesm.equipo5.Objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import java.util.LinkedList;
import java.util.List;

public class Player extends Entity {

    //Box2D
    public World world;
    public Body b2body;

    private List<Sprite> sprites = new LinkedList<Sprite>();

    //When we create bullet, power up, and passive classes, we will put them in here
    private float speedLimit; //This will for determining speed depending on joystick amount
    private int spriteIndex;
    private float dx, dy; //This will be percentage of joystick

    private Animation animationFront;
    private Animation animationLeft;
    private Animation animationRight;
    private float animationTimer;
    private viewingDirection dir =  viewingDirection.FRONT;


    public Player(float x, float y, float health,World world) {

        width = 50;
        height = 57;
        this.health = health;
        this.speed = 7;
        //Load texture front
        texture = new Texture("Animaciones/Principal_Animacion.png");

        TextureRegion region = new TextureRegion(texture);
        TextureRegion[][] texturasPersonajes = region.split(width,height);

        Array<TextureRegion> textureRegionArray = new Array(texturasPersonajes[0]);

        animationFront = new Animation(0.15f,textureRegionArray);
        animationFront.setPlayMode(Animation.PlayMode.LOOP);
        animationTimer = 0;

        //Load texture right
        texture = new Texture("Animaciones/Principal_Anim_D.png");

        region = new TextureRegion(texture);
        texturasPersonajes = region.split(53,height);

        textureRegionArray = new Array(texturasPersonajes[0]);

        animationRight = new Animation(0.15f,textureRegionArray);
        animationRight.setPlayMode(Animation.PlayMode.LOOP);

        //Load texture right
        texture = new Texture("Animaciones/Principal_Anim_I.png");

        region = new TextureRegion(texture);
        texturasPersonajes = region.split(53,height);

        textureRegionArray = new Array(texturasPersonajes[0]);

        animationLeft = new Animation(0.15f,textureRegionArray);
        animationLeft.setPlayMode(Animation.PlayMode.LOOP);

        //Sprites
        sprite = new Sprite(texturasPersonajes[0][0]);
        sprite.setPosition(x,y);

        rectangle.set(x,y,width,height);

        //Box2D
        this.world = world;
        definePlayer();


    }

    private void definePlayer() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(640,360);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(50);

        fdef.shape = shape;
        b2body.createFixture(fdef);
    }

    public void render(SpriteBatch batch) {
        animationTimer += Gdx.graphics.getDeltaTime();
        if (dir== viewingDirection.FRONT) {
            TextureRegion region = (TextureRegion) animationFront.getKeyFrame(animationTimer);
            batch.draw(region, sprite.getX(), sprite.getY());
        }
        else if (dir== viewingDirection.LEFT){
            TextureRegion region = (TextureRegion) animationLeft.getKeyFrame(animationTimer);
            batch.draw(region, sprite.getX(), sprite.getY());
        }
        else if (dir== viewingDirection.RIGHT){
            TextureRegion region = (TextureRegion) animationRight.getKeyFrame(animationTimer);
            batch.draw(region, sprite.getX(), sprite.getY());
        }

    }

    public void setDir(viewingDirection view){
        this.dir = view;
    }
}
