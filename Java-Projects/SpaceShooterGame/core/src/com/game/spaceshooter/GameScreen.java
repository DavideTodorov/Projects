package com.game.spaceshooter;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class GameScreen implements Screen {
    //Constants
    private final int WORLD_WIDTH = 72;
    private final int WORLD_HEIGHT = 128;

    //Screen
    private Camera camera;
    private Viewport viewport;

    //Graphics
    private SpriteBatch spriteBatch;
    private Texture background;

    //Timing
    private int backgroundOffset;

    //Constructor
    public GameScreen() {
        camera = new OrthographicCamera();
        viewport = new StretchViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);
        spriteBatch = new SpriteBatch();
        background = new Texture("darkPurpleStarscape.png");
        backgroundOffset = 0;
    }

    //Render objects to the UI
    @Override
    public void render(float deltaTime) {
        spriteBatch.begin();

        //Scrolling background
        backgroundOffset++;
        if (backgroundOffset == WORLD_WIDTH) {
            backgroundOffset = 0;
        }


        spriteBatch.draw(background, 0, -backgroundOffset,
                WORLD_WIDTH, WORLD_HEIGHT);
        spriteBatch.draw(background, 0, -backgroundOffset + WORLD_HEIGHT,
                WORLD_WIDTH, WORLD_HEIGHT);

        spriteBatch.end();
    }

    //Method to resize our game UI
    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        spriteBatch.setProjectionMatrix(camera.combined);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void show() {

    }

    @Override
    public void dispose() {

    }
}
