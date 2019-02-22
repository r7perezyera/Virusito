package mx.itesm.equipo5;

import com.badlogic.gdx.Game;

public class Virusito extends Game {

    @Override
    public void create(){

        setScreen(new PantallaCargando(this));
    }

}
