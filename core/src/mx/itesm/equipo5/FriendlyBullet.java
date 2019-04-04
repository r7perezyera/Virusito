package mx.itesm.equipo5;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class FriendlyBullet extends Projectile {
    public FriendlyBullet(float x, float y, float direction) {
        super(x, y, direction);
        this.texture = new Texture("texture.path");
        this.speed = 500;
        this.damage = 2;
    }

}
