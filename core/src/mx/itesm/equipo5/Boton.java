package mx.itesm.equipo5;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class Boton {

    private String direOn;
    private String direOff;
    private Texture botonOn;
    private Texture botonOff;
    private ImageButton iButton;

    //Crear un boton con dos estados
    public Boton(String direOn, String direOff) {
        this.direOn = direOn;
        this.direOff = direOff;

        Texture botonOn = new Texture(direOn);
        TextureRegionDrawable trdBotonOn =
                new TextureRegionDrawable(new TextureRegion(botonOn));

        Texture botonOff = new Texture(direOn);
        TextureRegionDrawable trdBotonOff =
                new TextureRegionDrawable(new TextureRegion(botonOff));



        iButton = new ImageButton(trdBotonOn, trdBotonOff);
    }

    //Crear un boton con un estado
    public Boton(String direOn) {
        this.direOn = direOn;
        Texture botonOn = new Texture(direOn);
        TextureRegionDrawable trdBotonOn =
                new TextureRegionDrawable(new TextureRegion(botonOn));
        iButton = new ImageButton(trdBotonOn);
    }

    public ImageButton getiButton() {
        return iButton;
    }
}
