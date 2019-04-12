package mx.itesm.equipo5.Objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Minion extends Entity {

    private enemyType attack;
    private movementPattern move;
    private difficulty diff;

    public Minion(enemyType attack, movementPattern move, difficulty diffc, float x, float y){
        this.attack = attack;
        this.move = move;
        this.diff = diffc;
        if (attack == enemyType.RAMMER){
            texture = new Texture("Personajes/Enemigo_1.png");
        }
        if (diff == diff.EASY){
            health=10;
            speed =1;
        }
        //Sprites
        sprite = new Sprite(texture);
        sprite.setPosition(x,y);

        rectangle.set(x,y,texture.getWidth(),texture.getHeight());
    }

    public void render(SpriteBatch batch){
        if (!destroyed) {
            batch.draw(texture, sprite.getX(), sprite.getY());
            rectangle.set(sprite.getX(), sprite.getY(), texture.getWidth(), texture.getHeight());
        }
    }
    public void move(float x, float y){
        if (attack==enemyType.RAMMER){
            Vector2 vector = new Vector2(x-sprite.getX(),y-sprite.getY());
            float angle = vector.angle();
            float dx = (float) (speed*Math.cos(angle));
            float dy = (float) (speed*Math.sin(angle));
            moveX(dx);
            moveY(dy);
        }
    }

}
