package mx.itesm.equipo5.Objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

public class EnemyBullet extends Projectile {
    public EnemyBullet(float x, float y, float direction) {
        super(x, y, direction);
        this.texture = new Texture("Balas/Bala_enemy.png");
        this.speed = 5;
        this.damage = 2;

        sprite = new Sprite(texture);
        sprite.setPosition(x,y);
        rectangle.set(x,y,sprite.getWidth(),sprite.getHeight());
    }

    public Rectangle getRectangle(){
        return rectangle;
    }

}
