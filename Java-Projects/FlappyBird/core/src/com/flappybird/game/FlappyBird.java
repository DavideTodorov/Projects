package com.flappybird.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.flappybird.game.states.GameStateManager;
import com.flappybird.game.states.MenuState;

import java.security.PublicKey;

public class FlappyBird extends ApplicationAdapter {
    public static final int WIDTH = 480;
    public static final int HEIGHT = 800;
    public static final String TITLE = "Fllapy Bird";

    private GameStateManager gameStateManager;
    private SpriteBatch batch;
    private Music music;

    @Override
    public void create() {
        this.gameStateManager = new GameStateManager();
        this.batch = new SpriteBatch();
        this.music = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));
        this.music.setLooping(true);
        music.setVolume(0.05f);
        music.play();
        Gdx.gl.glClearColor(1, 0, 0, 1);
        this.gameStateManager.push(new MenuState(gameStateManager));
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        gameStateManager.update(Gdx.graphics.getDeltaTime());
        gameStateManager.render(batch);
    }

    @Override
    public void dispose() {
        super.dispose();
        music.dispose();
    }
}
