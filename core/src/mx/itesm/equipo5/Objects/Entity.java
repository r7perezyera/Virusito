package mx.itesm.equipo5.Objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public abstract class Entity {


    protected float x, y, speed; //Position and speed
    protected float height, width; //Sprite/object dimensions
    protected Sprite sprite;
    protected float direction; //defines where Sprite will face
    //AQUI FALTA UN protected Pantalla pantalla, tenemos que definir la superclase primero
    protected boolean destroyed = false; //When true, delete entity
    protected float health; //When health<0, destroyed = True
    protected Texture texture;

    public Entity(){
        //The constructor varies depending on the type of entity
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
    }

    public void moveY(float dy){
        float newPos = sprite.getY()+(dy*speed);
        sprite.setY(newPos);
    }

    public void doDamage(float damage){
        health -= damage;
    }

    public void destroy(){
        destroyed = true;
    }

    public void check(){
        //All of the conditions that would destroy the entity
    }
}
