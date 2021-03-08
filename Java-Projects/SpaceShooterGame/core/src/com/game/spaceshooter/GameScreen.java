package com.game.spaceshooter;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.game.spaceshooter.ships.Ship;

public class GameScreen implements Screen {
    //Constants
    private final int WORLD_WIDTH = 72;
    private final int WORLD_HEIGHT = 128;

    //Screen
    private Camera camera;
    private Viewport viewport;

    //Graphics
    private SpriteBatch spriteBatch;
    private TextureAtlas textureAtlas;

    private TextureRegion[] backgrounds;
    private float backgroundHeight;

    //Player texture regions
    private TextureRegion playerShipTextureRegion;
    private TextureRegion playerShieldTextureRegion;
    private TextureRegion playerLaserTextureRegion;

    //Enemy texture regions
    private TextureRegion enemyShipTextureRegion;
    private TextureRegion enemyShieldTextureRegion;
    private TextureRegion enemyLaserTextureRegion;

    //Timing
    private float[] backgroundOffsets;
    private float backgroundMaxScrollingSpeed;

    //Game objects
    private Ship playerShip;
    private Ship enemyShip;

    //Constructor
    public GameScreen() {
        camera = new OrthographicCamera();
        viewport = new StretchViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);
        backgroundHeight = WORLD_HEIGHT * 2;
        backgroundMaxScrollingSpeed = (float) (WORLD_HEIGHT / 4);
        backgroundOffsets = new float[4];

        //Set up TextureAtlas
        textureAtlas = new TextureAtlas("images.atlas");

        //Set up background
        setBackGrounds();

        //Set up Texture regions
        setTextureRegions();

        //Set up game objects
        setShips();

        spriteBatch = new SpriteBatch();
    }

    //Initialise background
    private void setBackGrounds() {
        backgrounds = new TextureRegion[4];
        backgrounds[0] = textureAtlas.findRegion("Starscape00");
        backgrounds[1] = textureAtlas.findRegion("Starscape01");
        backgrounds[2] = textureAtlas.findRegion("Starscape02");
        backgrounds[3] = textureAtlas.findRegion("Starscape03");
    }

    //Initialise ships
    private void setShips() {
        playerShip = new Ship(2, 3, WORLD_WIDTH / 2, WORLD_HEIGHT / 4,
                10, 10,
                playerShipTextureRegion, playerShieldTextureRegion);

        enemyShip = new Ship(2, 3, WORLD_WIDTH / 2, WORLD_HEIGHT * 3 / 4,
                10, 10,
                enemyShipTextureRegion, enemyShieldTextureRegion);
    }

    //Initialise Texture regions
    private void setTextureRegions() {
        playerShipTextureRegion = textureAtlas.findRegion("playerShip");
        playerShieldTextureRegion = textureAtlas.findRegion("shield1");
        playerLaserTextureRegion = textureAtlas.findRegion("laserPlayer");

        enemyShipTextureRegion = textureAtlas.findRegion("enemyShip");
        enemyShipTextureRegion.flip(false,true);
        enemyShieldTextureRegion = textureAtlas.findRegion("shield2");
        enemyShieldTextureRegion.flip(false,true);
        enemyLaserTextureRegion = textureAtlas.findRegion("laserEnemy");
    }


    //Render the UI
    @Override
    public void render(float deltaTime) {
        spriteBatch.begin();

        //Scrolling background
        renderBackground(deltaTime);

        //Display enemy ship
        enemyShip.draw(spriteBatch);

        //Display player ship
        playerShip.draw(spriteBatch);

        //Display lasers

        //Display explosions

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
                    WORLD_WIDTH,
                    backgroundHeight);
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
