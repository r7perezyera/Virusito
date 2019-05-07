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

public class Minion extends Entity {

    private enemyType type;
    private movementPattern move;
    private difficulty diff;

    private float zigzagTimer = 0;
    private Vector2 zigzagVector;
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
            health=1;
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
    public void move(float x, float y,boolean update){
        if(update) {
            Vector2 position = b2body.getPosition();
            if (move == movementPattern.FOLLOWER) {
                Vector2 vector = new Vector2(x - position.x - width / 2, y - position.y - height / 2);
                float angle = vector.angle();
                float dx = (float) (speed * Math.cos(angle));
                float dy = (float) (speed * Math.sin(angle));
                b2body.setLinearVelocity(dx * 80, dy * 80);
            } else if (move == movementPattern.AVOIDER) {
                Vector2 vector = new Vector2(x - 100 - position.x - width / 2, y - 100 - position.y - height / 2);
                float angle = vector.angle();
                float dx = (float) (speed * Math.cos(angle));
                float dy = (float) (speed * Math.sin(angle));
                b2body.setLinearVelocity(dx * 80, dy * 80);
            } else if (move == movementPattern.ZIGZAG) { //TODO
                if (zigzagTimer == 0) {
                    zigzagVector = new Vector2(x - position.x - width / 2, y - position.y - height / 2);
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
                } else zigzagTimer = 0;
            }
        }else{
          b2body.setLinearVelocity(0,0);
        }

    }


}
