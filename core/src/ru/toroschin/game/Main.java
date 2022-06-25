package ru.toroschin.game;

import com.badlogic.gdx.Game;

import ru.toroschin.game.screens.LoadScreen;
import ru.toroschin.game.screens.State;

public class Main extends Game {
    @Override
    public void create() {
        this.setScreen(new LoadScreen(this, State.BEGIN));
    }

}
