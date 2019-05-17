package mx.itesm.equipo5.Objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import java.util.LinkedList;
import java.util.Random;

import mx.itesm.equipo5.MasterScreen;

public class Minion extends Entity {

    private enemyType type;
    private movementPattern move;
    private difficulty diff;
    private boolean boss;

    private float zigzagTimer = 0;
    private float avoiderTimer = 0;
    private float distanceAvoid=0;
    private int signX=0;
    private int signY=0;

    private Vector2 zigzagVector;
    private Vector2 avoiderVector;

    private Animation animation;
    private float animationTimer;

    public Minion(enemyType type, movementPattern move, difficulty diffc, float x, float y, World world){
        this.type = type;
        this.move = move;
        this.diff = diffc;
        if (this.type == enemyType.FLOATER){
            texture = new Texture("Personajes/Enemigo_1.png");
            width = texture.getWidth();
            height = texture.getHeight();
            sprite = new Sprite(texture);
            sprite.setPosition(x,y);
            rectangle.set(x,y,width,height);

        }else if(this.type == enemyType.FLOATBOSS){
            texture = new Texture("Personajes/Boss_1.png");
            width = texture.getWidth();
            height = texture.getHeight();
            sprite = new Sprite(texture);
            sprite.setPosition(x,y);
            rectangle.set(x,y,width,height);

        }else if(this.type == enemyType.CRAWLER){
            width= 50;
            height=48;
            setAnimation("Animaciones/Enemigo_3.png", x, y);

        }else if(this.type == enemyType.CRAWLBOSS) {
            width = 87;
            height = 100;
            setAnimation("Animaciones/Boss_3.png", x, y);
        }else if(this.type == enemyType.TEEHTBOSS) {
            width = 100;
            height = 100;
            setAnimation("Animaciones/Boss_2.png", x, y);
        }else if(this.type == enemyType.TEETH) {
            width = 40;
            height = 49;
            setAnimation("Animaciones/Enemigo_2.png", x, y);
        }


        if (this.diff == difficulty.EASY){
            health=3;
            speed =1;
        }
        if (this.diff == difficulty.MEDIUM){
            health=8;
            speed =1;
        }
        if (this.diff == difficulty.HARD){
            health=10;
            speed =1;
        }

        //Box2D
        this.world = world;
        defineMinion(x,y);
        boss = false;

    }

    public void setBoss(){
        health +=3;
        boss = true;
    }

    public boolean isBoss(){
        return boss;
    }

    private void defineMinion(float x, float y) {
        BodyDef bdef = new BodyDef();
        bdef.position.set(x,y);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(sprite.getWidth()/2,sprite.getHeight()/2);

        fdef.shape = shape;
        fdef.restitution = 0.1f;
        b2body.createFixture(fdef);
    }

    private void setAnimation(String dirAnim, float x, float y){
            texture = new Texture(dirAnim);

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

    public void render(SpriteBatch batch){
        //Box2D
        Vector2 position = b2body.getPosition();

        if (!destroyed) {
            if (position.x -width/2 <=0 || position.y-width/2<=0){
                isDestroyed();
            }else if(position.x -width/2 >= MasterScreen.WIDTH || position.y-width/2 >= MasterScreen.WIDTH){
                isDestroyed();
            }
            if (type == enemyType.FLOATBOSS || type == enemyType.FLOATER) {
                batch.draw(texture, position.x - width/2, position.y-height/2);
                rectangle.set(position.x - width/2, position.y-height/2, width, height);
            }else {
                animationTimer += Gdx.graphics.getDeltaTime();
                TextureRegion region = (TextureRegion) animation.getKeyFrame(animationTimer);
                batch.draw(region, position.x - width/2, position.y-height/2);
                rectangle.set(position.x - width/2, position.y-height/2, width, height);
            }
        }

    }

    public void destroy(){
        world.destroyBody(b2body);
    }

    public LinkedList<EnemyBullet> move(float x, float y, LinkedList<EnemyBullet> bullets){
            Vector2 position = b2body.getPosition();
            if (move == movementPattern.FOLLOWER) {
                Vector2 vector = new Vector2(x - position.x - width / 2, y - position.y - height / 2);
                float angle = vector.angle();
                float dx = (float) (speed * Math.cos(angle));
                float dy = (float) (speed * Math.sin(angle));
                b2body.setLinearVelocity(dx * 80, dy * 80);
            }
            else if (move == movementPattern.AVOIDER) {
                if(avoiderTimer==0) {
                    Random random = new Random();
                    distanceAvoid = random.nextInt(100);
                    signX = random.nextInt(2);
                    if (signX == 0) {
                        signX = -1;
                    }
                    signY = random.nextInt(2);
                    if (signY == 0) {
                        signY = -1;
                    }
                    avoiderVector = new Vector2(x - 100 + distanceAvoid * signX - position.x - width / 2, y - 100 + distanceAvoid * signY - position.y - height / 2);
                }
                if(avoiderTimer < 4) {
                    float angle = avoiderVector.angle();
                    float dx = (float) (speed * Math.cos(angle));
                    float dy = (float) (speed * Math.sin(angle));
                    b2body.setLinearVelocity(dx * 80, dy * 80);
                    avoiderTimer += .05f;
                }else{
                    avoiderTimer =0;
                    Vector2 vector = new Vector2( x+50/2-position.x,  y+57/2-position.y);
                    float angle = vector.angle();
                    bullets = shoot(MathUtils.degreesToRadians * angle, bullets);
                }

            } else if (move == movementPattern.ZIGZAG) {
                if (zigzagTimer == 0) {
                    zigzagVector = new Vector2(x -position.x - width / 2, y - position.y - height / 2);
                }
                if (zigzagTimer < 4) {
                    if (zigzagTimer < 2) {
                        float angle = MathUtils.degreesToRadians * (30 + zigzagVector.angle());
                        float dx = (float) (speed * Math.cos(angle));
                        float dy = (float) (speed * Math.sin(angle));
                        b2body.setLinearVelocity(dx * 80, dy * 80);
                    } else {
                        float angle = MathUtils.degreesToRadians * (-30 + zigzagVector.angle());
                        float dx = (float) (speed * Math.cos(angle));
                        float dy = (float) (speed * Math.sin(angle));
                        b2body.setLinearVelocity(dx * 80, dy * 80);
                    }
                    zigzagTimer += .05;
                } else{
                    zigzagTimer = 0;
                }
            }
        return bullets;

    }

    public void setVelocity(float dx, float dy){
        b2body.setLinearVelocity(dx * 80, dy * 80);
    }

    public LinkedList<EnemyBullet>  shoot(float dir, LinkedList<EnemyBullet> bullets){
        Vector2 position = b2body.getPosition();
        EnemyBullet bullet = new EnemyBullet(position.x-width/2, position.y-height/2, dir);
        bullets.add(bullet);
        return bullets;
    }

}
