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
    private Texture[] backgrounds;

    //Timing
    private float[] backgroundOffsets;
    private float backgroundMaxScrollingSpeed;

    //Constructor
    public GameScreen() {
        camera = new OrthographicCamera();
        viewport = new StretchViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);

        setBackGrounds();
        backgroundMaxScrollingSpeed = (float) (WORLD_HEIGHT / 4);
        backgroundOffsets = new float[4];

        spriteBatch = new SpriteBatch();
    }

    //Initialise background
    private void setBackGrounds() {
        backgrounds = new Texture[4];
        backgrounds[0] = new Texture("Starscape00.png");
        backgrounds[1] = new Texture("Starscape01.png");
        backgrounds[2] = new Texture("Starscape02.png");
        backgrounds[3] = new Texture("Starscape03.png");
    }

    //Render the UI
    @Override
    public void render(float deltaTime) {
        spriteBatch.begin();

        //Scrolling background
        renderBackground(deltaTime);

        spriteBatch.end();
    }

    //Method to render the background
    private void renderBackground(float deltaTime) {
        backgroundOffsets[0] += deltaTime * backgroundMaxScrollingSpeed / 8;
        backgroundOffsets[1] += deltaTime * backgroundMaxScrollingSpeed / 4;
        backgroundOffsets[2] += deltaTime * backgroundMaxScrollingSpeed / 2;
        backgroundOffsets[3] += deltaTime * backgroundMaxScrollingSpeed;

        for (int i = 0; i < backgroundOffsets.length; i++) {
            if (backgroundOffsets[i] > WORLD_HEIGHT) {
                backgroundOffsets[i] = 0;
            }
            spriteBatch.draw(backgrounds[i],
                    0,
                    -backgroundOffsets[i],
                    WORLD_WIDTH, WORLD_HEIGHT);

            spriteBatch.draw(backgrounds[i],
                    0,
                    -backgroundOffsets[i] + WORLD_HEIGHT,
                    WORLD_WIDTH, WORLD_HEIGHT);
        }
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
