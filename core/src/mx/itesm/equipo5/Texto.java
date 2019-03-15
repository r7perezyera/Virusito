package mx.itesm.equipo5;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Texto {

    private BitmapFont fuenteTexto;
    private BitmapFont fuenteHUD;

    public Texto() {
        // cargar fuente
        fuenteTexto = new BitmapFont(Gdx.files.internal("Fuentes/simplySquare_45.fnt"));  // font name
        fuenteHUD = new BitmapFont(Gdx.files.internal("Fuentes/disposable_droid_45.fnt"));
    }


    public void mostrarTexto(SpriteBatch batch, String mensaje, float x, float y) {
        GlyphLayout glyph = new GlyphLayout();
        glyph.setText(fuenteTexto, mensaje);
        fuenteTexto.setColor(1,1,1,1);
        float anchoTexto = glyph.width;
        fuenteTexto.draw(batch, glyph, x - anchoTexto/2, y); // centrado en x respecto al texto, NO la screen
    }

    public void mostrarTextoHUD(SpriteBatch batch, String mensaje, float x, float y) {
        GlyphLayout glyph = new GlyphLayout();
        glyph.setText(fuenteHUD, mensaje);
        fuenteHUD.setColor(1,1,1,1);
        float anchoTexto = glyph.width;
        fuenteHUD.draw(batch, glyph, x - anchoTexto/2, y); // centrado en x respecto al texto, NO la screen
    }

    // text will be shown (drawn) within the batch.begin and .end lines,
    // in the class containing those

}
