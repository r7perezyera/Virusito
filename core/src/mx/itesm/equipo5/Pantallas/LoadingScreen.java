package mx.itesm.equipo5.Pantallas;

import com.badlogic.gdx.graphics.Texture;

import mx.itesm.equipo5.Pantalla;
import mx.itesm.equipo5.Virusito;

public class LoadingScreen extends Pantalla {

    private Texture logo;

    //Tiempo
    private float timeCounter;

    public LoadingScreen(Virusito juego) {
        super(juego);
    }

    @Override
    public void show() {
        logo = new Texture("Logo/TecMonterrey.png");
    }

    @Override
    public void render(float delta) {
        borrarPantalla(1,1,1);
        batch.setProjectionMatrix(camara.combined);
        batch.begin();
        batch.draw(logo, ANCHO /2-logo.getWidth()/2, ALTO /2-logo.getHeight()/2);
        batch.end();

        //prueba tiempo
        timeCounter +=delta;
        if (timeCounter >=4){
            //Conto 4 s
            juego.setScreen(new MenuScreen(juego));
        }

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        logo.dispose();
    }
}

