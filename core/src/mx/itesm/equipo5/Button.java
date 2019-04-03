package mx.itesm.equipo5;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class Button {

    private String pathOn;
    private String pathOff;
    private Texture buttonOn;
    private Texture buttonOff;
    private ImageButton iButton;

    //Crear un boton con dos estados
    public Button(String pathOn, String pathOff) {
        this.pathOn = pathOn;
        this.pathOff = pathOff;

        Texture botonOn = new Texture(pathOn);
        TextureRegionDrawable trdBotonOn =
                new TextureRegionDrawable(new TextureRegion(botonOn));

        Texture botonOff = new Texture(pathOn);
        TextureRegionDrawable trdBotonOff =
                new TextureRegionDrawable(new TextureRegion(botonOff));



        iButton = new ImageButton(trdBotonOn, trdBotonOff);
    }

    //Crear un boton con un estado
    public Button(String pathOn) {
        this.pathOn = pathOn;
        Texture botonOn = new Texture(pathOn);
        TextureRegionDrawable trdBotonOn =
                new TextureRegionDrawable(new TextureRegion(botonOn));
        iButton = new ImageButton(trdBotonOn);
    }

    public ImageButton getiButton() {
        return iButton;
    }
}
