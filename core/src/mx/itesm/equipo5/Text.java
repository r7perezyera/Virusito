package mx.itesm.equipo5;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Text {

    private BitmapFont textFont;
    private BitmapFont HUDFont;
    private BitmapFont dialogFont;
    private BitmapFont buttonFont;

    public Text() {
        // load font
        textFont = new BitmapFont(Gdx.files.internal("Fuentes/simplySquare_45.fnt"));
        HUDFont = new BitmapFont(Gdx.files.internal("Fuentes/disposableDroid_45.fnt"));
        dialogFont = new BitmapFont(Gdx.files.internal("Fuentes/dataControl_45.fnt"));
        buttonFont = new BitmapFont(Gdx.files.internal("Fuentes/kemcoSmooth_35.fnt"));
    }


    public void displayText(SpriteBatch batch, String message, float x, float y) {
        GlyphLayout glyph = new GlyphLayout();
        glyph.setText(textFont, message);
        textFont.setColor(1,1,1,1);
        float textWidth = glyph.width;
        textFont.draw(batch, glyph, x - textWidth/2, y); // centrado en x respecto al texto, NO la screen
    }


    public void displayHUDText(SpriteBatch batch, String message, float x, float y) {
        GlyphLayout glyph = new GlyphLayout();
        glyph.setText(HUDFont, message);
        HUDFont.setColor(1,1,1,1);
        float textWidth = glyph.width;
        HUDFont.draw(batch, glyph, x - textWidth/2, y);
    }


    public void displayDialogText(SpriteBatch batch, String message, float x, float y) {
        GlyphLayout glyph = new GlyphLayout();
        glyph.setText(dialogFont, message);
        dialogFont.setColor(1,1,1,1);
        float textWidth = glyph.width;
        dialogFont.draw(batch, glyph, x - textWidth/2, y);
    }


    public void displayButtonText(SpriteBatch batch, String message, float x, float y) {
        GlyphLayout glyph = new GlyphLayout();
        glyph.setText(buttonFont, message);
        buttonFont.setColor(1,1,1,1);
        float textWidth = glyph.width;
        buttonFont.draw(batch, glyph, x - textWidth/2, y);
    }

    // text will be shown (drawn) within the batch.begin and .end lines,
    // in the class containing those

}
