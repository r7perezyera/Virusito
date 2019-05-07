package mx.itesm.equipo5.Objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Item extends Entity {

    private Texture battery = new Texture("Items/Battery.png");

    public Item(Vector2 position){
        //Sprites
        float posX = position.x;
        float posY = position.y;
        sprite = new Sprite(battery);
        sprite.setPosition(posX,posY); //TODO quitar posicion de sprite, enemigos deben seguir a b2body

        rectangle.set(posX-1,posY-1,battery.getWidth()+2,battery.getHeight()+2);
    }


    public void render(SpriteBatch batch){
        batch.draw(battery, sprite.getX(), sprite.getY());
        rectangle.set(sprite.getX()-1,sprite.getY()-1,battery.getWidth()+2,battery.getHeight()+2);
    }
}
