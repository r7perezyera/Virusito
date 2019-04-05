package mx.itesm.equipo5.Objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Enemy extends Entity {

    private enemyType attack;
    private movementPattern move;
    private difficulty diff;

    public Enemy(enemyType attack, movementPattern move, difficulty diffc, float x, float y){
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
        batch.draw(texture, sprite.getX(), sprite.getY());
    }
    public void move(float x, float y){
        if (attack==enemyType.RAMMER){
            
            moveX(x-sprite.getX());
            moveY(y-sprite.getY());
        }
    }

}
