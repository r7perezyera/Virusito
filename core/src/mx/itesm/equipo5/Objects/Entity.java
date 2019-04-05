package mx.itesm.equipo5.Objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public abstract class Entity {


    protected float x, y, speed; //Position and speed
    protected float height, width; //Sprite/object dimensions
    protected Sprite sprite;
    protected float direction; //defines where Sprite will face
    //AQUI FALTA UN protected Pantalla pantalla, tenemos que definir la superclase primero
    protected boolean destroyed = false; //When true, delete entity
    protected float health; //When health<0, destroyed = True
    protected Texture texture;
    protected Rectangle rectangle = new Rectangle();

    public Entity(){
        //The constructor varies depending on the type of entity
    }

    public float getHealth() {
        return health;
    }

    public void setHealth(float health) {
        this.health = health;
    }

    public float getX() {
        return sprite.getX();
    }

    public float getY() {
        return sprite.getY();
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
        rectangle.setPosition(newPos,sprite.getY());
    }

    public void moveY(float dy){
        float newPos = sprite.getY()+(dy*speed);
        sprite.setY(newPos);
        rectangle.setPosition(sprite.getX(),newPos);

    }


    public void doDamage(float damage){
        health -= damage;
    }

    public void destroy(){
        destroyed = true;
    }

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
}
