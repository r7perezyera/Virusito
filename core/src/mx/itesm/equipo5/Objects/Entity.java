package mx.itesm.equipo5.Objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

public abstract class Entity{


    protected float x, y, speed; //Position and speed
    protected int height, width; //Sprite/object dimensions
    protected Sprite sprite;
    protected float direction; //defines where Sprite will face
    //AQUI FALTA UN protected Pantalla pantalla, tenemos que definir la superclase primero
    protected boolean destroyed = false; //When true, delete entity
    protected float health; //When health<0, destroyed = True
    protected Texture texture;
    protected Rectangle rectangle = new Rectangle();
    protected float cooldown;

    //Box2D
    public World world;
    public Body b2body;

    public Entity(){
        //The constructor varies depending on the type of entity
    }

    public float getHealth() {
        return health;
    }

    public void setHealth(float health) {
        this.health = health;
    }

    public float getX() { return sprite.getX();
    }

    public float getY() {
        return sprite.getY();
    }

    public Vector2 getPosition(){
        Vector2 position = b2body.getPosition();
        return position;
    }

    public float getHeight() {
        return sprite.getHeight();
    }

    public float getWidth() {
        return sprite.getWidth();
    }

    public Sprite getSprite() {
        return sprite;
    }

    public boolean isDestroyed() {
        return destroyed;
    }

    public void setX(float x) {
        sprite.setX(x);
    }

    public void setY(float y) {
        sprite.setY(y);
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public void moveX(float dx){
        float newPos = sprite.getX()+(dx*speed);
        sprite.setX(newPos);
        rectangle.setPosition(newPos-1,sprite.getY()-1);
    }

    public void moveY(float dy){
        float newPos = sprite.getY()+(dy*speed);
        sprite.setY(newPos);
        rectangle.setPosition(sprite.getX()-1,newPos-1);

    }

    //This will use Box2d
    public void move(float dx,float dy){
        //Box2D movement
        b2body.setLinearVelocity(dx*120,dy*120);
        this.setX(b2body.getPosition().x-getWidth()/2);//Medio ineficiente, pone sprite donde esta body
        this.setY(b2body.getPosition().y-getHeight()/2);
    }

    public void doDamage(float damage){
        health -= damage;
        if (health<=0){
            destroy();
            destroyed = true;
        }
    }

    public void destroy(){}

    public float getSpeed(){
        return speed;
    }

    public void check(){
        //All of the conditions that would destroy the entity
    }

    public boolean collides(){ //TODO, revisa colisión con paredes
        return true;
    }

    public Rectangle getRectangle() {return rectangle;}

    public  float getCooldown() {
        return cooldown;
    }
}
