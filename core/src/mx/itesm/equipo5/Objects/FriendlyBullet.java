package mx.itesm.equipo5.Objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class FriendlyBullet extends Projectile {
    public FriendlyBullet(float x, float y, float direction, weaponType weapon) {
        super(x, y, direction);
        if (weapon == weaponType.PISTOL){
            this.texture = new Texture("Balas/Bala.png");
            this.damage = 1;
        }else if (weapon == weaponType.SHOTGUN){
            this.texture = new Texture("Balas/Bala_3.png");
            this.damage = 2;
        }else if (weapon == weaponType.BAZOOKA){
            this.texture = new Texture("Balas/Bala_big.png");
            this.damage = 4;
        }
        this.speed = 10;

        sprite = new Sprite(texture);
        sprite.setPosition(x,y);
        rectangle.set(x,y,sprite.getWidth(),sprite.getHeight());
    }

    public Rectangle getRectangle(){
        return rectangle;
    }

}
