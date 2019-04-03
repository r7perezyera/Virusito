package mx.itesm.equipo5;

import com.badlogic.gdx.Game;

import mx.itesm.equipo5.Pantallas.LoadingScreen;

public class Virusito extends Game {

    @Override
    public void create(){

        setScreen(new LoadingScreen(this));
    }

}
