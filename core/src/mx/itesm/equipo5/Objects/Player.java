package mx.itesm.equipo5.Objects;

import static mx.itesm.equipo5.MasterScreen.PPM;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import java.util.LinkedList;
import java.util.List;

public class Player extends Entity {

    //Box2D


    private List<Sprite> sprites = new LinkedList<Sprite>();

    //When we create bullet, power up, and passive classes, we will put them in here
    private float speedLimit; //This will for determining speed depending on joystick amount
    private int spriteIndex;
    private float dx, dy; //This will be percentage of joystick

    private Animation animationFront;
    private Animation animationLeft;
    private Animation animationRight;
    private Animation animationBack;
    private float animationTimer;
    private viewingDirection dir =  viewingDirection.FRONT;

    public void setWeapon(weaponType weapon) {
        this.weapon = weapon;
        if (weapon == weaponType.PISTOL){
            cooldown = 0.25f;
        }else if (weapon == weaponType.SHOTGUN){
            cooldown = 0.75f;
        }else if (weapon == weaponType.BAZOOKA){
            cooldown = 1.5f;
        }
    }

    private weaponType weapon;

    private Sound shootingSound = Gdx.audio.newSound(Gdx.files.internal("Music/SFX/Shoot.wav"));
    private Sound shotgunSound = Gdx.audio.newSound(Gdx.files.internal("Music/SFX/shotgun.wav"));
    private Sound BFGSound = Gdx.audio.newSound(Gdx.files.internal("Music/SFX/BFG.wav"));



    public Player(float x, float y, float health,World world, weaponType weapon) {
        width = 50;
        height = 57;
        this.health = health;
        this.speed = 7;
        this.weapon = weapon;
        if (weapon == weaponType.PISTOL){
            cooldown = 0.25f;
        }else if (weapon == weaponType.SHOTGUN){
            cooldown = 0.75f;
        }else if (weapon == weaponType.BAZOOKA){
            cooldown = 1.5f;
        }
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
        texturasPersonajes = region.split(54,height);

        textureRegionArray = new Array(texturasPersonajes[0]);

        animationLeft = new Animation(0.15f,textureRegionArray);
        animationLeft.setPlayMode(Animation.PlayMode.LOOP);

        //Load texture back
        texture = new Texture("Animaciones/Principal_Animacion_Back.png");

        region = new TextureRegion(texture);
        texturasPersonajes = region.split(width,height);

        textureRegionArray = new Array(texturasPersonajes[0]);

        animationBack = new Animation(0.15f,textureRegionArray);
        animationBack.setPlayMode(Animation.PlayMode.LOOP);

        //Sprites
        sprite = new Sprite(texturasPersonajes[0][0]);
        sprite.setPosition(x,y); //TODO quitar posicion de sprite, enemigos deben seguir a b2body

        rectangle.set(x-1,y-1,width+2,height+2);

        //Box2D
        this.world = world;
        definePlayer(x,y);


    }

    private void definePlayer(float x,float y) {
        BodyDef bdef = new BodyDef();
        bdef.position.set(x,y);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(sprite.getWidth()/2,sprite.getHeight()/2);

        fdef.shape = shape;
        b2body.createFixture(fdef);
    }

    public void slide(float dx,float dy){
        //Box2D movement
        b2body.applyLinearImpulse(dx*140,dy*140,b2body.getPosition().x,b2body.getPosition().y,true);
        this.setX(b2body.getPosition().x-getWidth()/2);//Medio ineficiente, pone sprite donde esta body
        this.setY(b2body.getPosition().y-getHeight()/2);
    }

    public void render(SpriteBatch batch) {
        animationTimer += Gdx.graphics.getDeltaTime();
        Vector2 position = b2body.getPosition();
        if (dir== viewingDirection.FRONT) {
            TextureRegion region = (TextureRegion) animationFront.getKeyFrame(animationTimer);
            batch.draw(region, position.x-sprite.getWidth()/2, position.y-sprite.getHeight()/2);
            rectangle.set(sprite.getX()-1,sprite.getY()-1,width+2,height+2);
        }
        else if (dir== viewingDirection.LEFT){
            TextureRegion region = (TextureRegion) animationLeft.getKeyFrame(animationTimer);
            batch.draw(region,position.x-sprite.getWidth()/2, position.y-sprite.getHeight()/2);
            rectangle.set(sprite.getX()-1,sprite.getY()-1,width+2,height+2);
        }
        else if (dir== viewingDirection.RIGHT){
            TextureRegion region = (TextureRegion) animationRight.getKeyFrame(animationTimer);
            batch.draw(region,position.x-sprite.getWidth()/2, position.y-sprite.getHeight()/2);
            rectangle.set(sprite.getX()-1,sprite.getY()-1,width+2,height+2);
        }else if (dir== viewingDirection.BACK){
            TextureRegion region = (TextureRegion) animationBack.getKeyFrame(animationTimer);
            batch.draw(region,position.x-sprite.getWidth()/2, position.y-sprite.getHeight()/2);
            rectangle.set(sprite.getX()-1,sprite.getY()-1,width+2,height+2);
        }

    }

    public void setDir(viewingDirection view){
        this.dir = view;
    }

    public LinkedList<FriendlyBullet> shoot(float dir, LinkedList<FriendlyBullet> bullets, boolean sound){
       if (weapon == weaponType.PISTOL){
            FriendlyBullet bullet = new FriendlyBullet(getX()+getWidth()/2, getY()+getHeight()/2, dir, weapon);
            bullets.add(bullet);
            if(sound){
                shootingSound.play();
            }
       }else if (weapon == weaponType.SHOTGUN){
           FriendlyBullet bullet = new FriendlyBullet(getX()+getWidth()/2, getY()+getHeight()/2, dir, weapon);
           bullets.add(bullet);
           bullet = new FriendlyBullet(getX()+getWidth()/2, getY()+getHeight()/2, (float) (dir + (Math.PI/4)), weapon);
           bullets.add(bullet);
           bullet = new FriendlyBullet(getX()+getWidth()/2, getY()+getHeight()/2, (float) (dir - (Math.PI/4)), weapon);
           bullets.add(bullet);
           if(sound){
               shotgunSound.play();
           }
       }else if (weapon == weaponType.BAZOOKA){
           FriendlyBullet bullet = new FriendlyBullet(getX(), getY(), dir, weapon);
           bullets.add(bullet);
           if(sound){
               BFGSound.play();
           }
       }
        return bullets;

    }



}
