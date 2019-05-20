package mx.itesm.equipo5;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;


public class JoyStick {

    float dx=0;
    float dy=0;
    Body cuerpo;
    Skin skin = new Skin(); // objeto que guarda texturas y las convierte a un tipo especial
    Touchpad.TouchpadStyle style = new Touchpad.TouchpadStyle();
    Touchpad pad;

    public JoyStick(String fondo, String boton, Body cuerpo){
        this.cuerpo = cuerpo;
        skin.add("fondo", new Texture("invicible.png"));
        skin.add("boton", new Texture("invicible.png"));

        style.background = skin.getDrawable("fondo");
        style.knob = skin.getDrawable("boton");

        // Crea el pad
        pad = new Touchpad(5, style);    // puedo mover x pixeles la palanquita y no responde, es hasta despues
        pad.setBounds(16, 16, 240, 240);

        // Listener del pad
        pad.addListener(new ChangeListener() {

            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Touchpad p = (Touchpad) actor;
                dx = p.getKnobPercentX() * 10;
                dy = p.getKnobPercentY() * 10;
                //updateCamera(dx);     // ya se esta actualizando dentro de render
                momentum();
            }
        });
        // Color
        pad.setColor(1,1,1,0.5f);

    }

    private void momentum() {
        cuerpo.applyLinearImpulse(new Vector2(500,500),cuerpo.getWorldCenter(),true);
    }

    public Touchpad getPad() {
        return pad;
    }

    public void setPosition(int x, int y){
        pad.setBounds(x,y,256,256);
    }

    public float getDx() {
        return dx;
    }

    public float getDy() {
        return dy;
    }
}
