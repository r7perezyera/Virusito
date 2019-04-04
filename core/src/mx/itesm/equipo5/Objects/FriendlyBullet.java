package mx.itesm.equipo5.Objects;

import com.badlogic.gdx.graphics.Texture;

public class FriendlyBullet extends Projectile {
    public FriendlyBullet(float x, float y, float direction) {
        super(x, y, direction);
        this.texture = new Texture("texture.path");
        this.speed = 500;
        this.damage = 2;
    }

}
