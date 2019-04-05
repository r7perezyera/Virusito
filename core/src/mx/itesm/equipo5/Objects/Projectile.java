package mx.itesm.equipo5.Objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

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
            launchX();
            launchY();
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, sprite.getX(), sprite.getY(),10,10);
    }

    public void launchX(){  //DX is with vector2 and .angle
        float factor = (float) Math.cos(direction);
        sprite.setX(sprite.getX()+speed*factor);
    }

    public void launchY(){
        float factor = (float) Math.sin(direction);
        sprite.setY(sprite.getY()+speed*factor);
    }

    public void destroy(){
        destroyed = true;
    }

    public Sprite getSprite() {return sprite;}
}