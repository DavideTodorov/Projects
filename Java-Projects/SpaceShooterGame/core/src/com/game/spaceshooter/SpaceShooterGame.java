package com.game.spaceshooter;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SpaceShooterGame extends Game {
    private GameScreen gameScreen;

    @Override
    public void create() {
        gameScreen = new GameScreen();
        setScreen(gameScreen);
    }


    //Render objects to the UI
    @Override
    public void render() {
        super.render();
    }

    //Method to resize our game UI
    @Override
    public void resize(int width, int height) {
        gameScreen.resize(width, height);
    }

    //Method to dispose resources
    @Override
    public void dispose() {
        gameScreen.dispose();
    }
}
