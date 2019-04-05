package mx.itesm.equipo5.Objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class FriendlyBullet extends Projectile {
    public FriendlyBullet(float x, float y, float direction) {
        super(x, y, direction);
        this.texture = new Texture("Balas/Bala.png");
        this.speed = 10;
        this.damage = 2;

        sprite = new Sprite(texture);
        sprite.setPosition(x,y);
    }

}
