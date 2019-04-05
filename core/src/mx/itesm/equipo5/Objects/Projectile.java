package mx.itesm.equipo5.Objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public abstract class Projectile {

    //AQUI FALTA UN protected Pantalla pantalla, tenemos que definir la superclase primero
    protected float direction, x, y, speed, damage;
    protected int width, height; //In pixels
    protected boolean destroyed = false;
    protected Sprite sprite;
    protected Texture texture;

    public Projectile(float x, float y, float direction){
        this.direction = direction;
        this.x = x;
        this.y = y;
    }

    public void update(){
        while(!destroyed){
            moveX(speed);
            moveY(speed);
        }
    }

    public void moveX(float dx){  //DX is with vector2 and .angle
        float factor = (float) Math.cos(direction);
        sprite.setX(sprite.getX()+(factor));
    }

    public void moveY(float dy){
        float factor = (float) Math.sin(direction);
        sprite.setY(sprite.getY()+(dy*factor));
    }

    public void destroy(){
        destroyed = true;
    }
}