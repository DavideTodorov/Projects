package com.game.spaceshooter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.game.spaceshooter.explosions.Explosion;
import com.game.spaceshooter.lasers.Laser;
import com.game.spaceshooter.ships.EnemyShip;
import com.game.spaceshooter.ships.PlayerShip;

import java.awt.*;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.ListIterator;

public class GameScreen implements Screen {
    //Constants
    private final float WORLD_WIDTH = 72;
    private final float WORLD_HEIGHT = 128;
    private final float MOVEMENT_THRESHOLD = 0.5f;

    //Screen
    private Camera camera;
    private Viewport viewport;

    //Graphics
    private SpriteBatch spriteBatch;
    private TextureAtlas textureAtlas;
    private Texture explosionTexture;

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
    private float timeBetweenEnemySpawns = 3f;
    private float enemySpawnTimer = 0f;

    //Game objects
    private PlayerShip playerShip;
    private LinkedList<Laser> playerLasers;

    private LinkedList<EnemyShip> enemyShipsList;
    private LinkedList<Laser> enemyLasers;

    private LinkedList<Explosion> explosionsList;

    private int score;

    //Heads-Up Display
    private BitmapFont font;
    private float hudVerticalMargin;
    private float hudLeftX;
    private float hudRightX;
    private float hudCentreX;
    private float hudRow1Y;
    private float hudRow2Y;
    private float hudSectionWidth;

    //Constructor
    public GameScreen() {
        camera = new OrthographicCamera();
        viewport = new StretchViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);
        backgroundHeight = WORLD_HEIGHT * 2;
        backgroundMaxScrollingSpeed = (float) (WORLD_HEIGHT / 4);
        backgroundOffsets = new float[4];
        playerLasers = new LinkedList<>();
        enemyLasers = new LinkedList<>();
        explosionTexture = new Texture("explosion.png");
        explosionsList = new LinkedList<>();
        score = 0;

        //Set up TextureAtlas
        textureAtlas = new TextureAtlas("images.atlas");

        //Set up background
        setBackGrounds();

        //Set up Texture regions
        setTextureRegions();

        //Set up game objects
        setShips();

        spriteBatch = new SpriteBatch();

        //Set up HUD
        setUpHUD();
    }

    //Set up HUD
    private void setUpHUD() {
        //Create a BitmapFont
        FreeTypeFontGenerator fontGenerator = new FreeTypeFontGenerator
                (Gdx.files.internal("EdgeOfTheGalaxyRegular-OVEa6.otf"));
        FreeTypeFontGenerator.FreeTypeFontParameter fontParameter =
                new FreeTypeFontGenerator.FreeTypeFontParameter();

        fontParameter.size = 72;
        fontParameter.borderWidth = 3.6f;
        fontParameter.color = new Color(1, 1, 1, 0.3f);
        fontParameter.borderColor = new Color(0, 0, 0, 0.3f);

        font = fontGenerator.generateFont(fontParameter);

        //Scale the font to fit the game screen
        font.getData().setScale(0.08f);

        //Calculate margins
        hudVerticalMargin = font.getCapHeight() / 2;
        hudLeftX = hudVerticalMargin;
        hudRightX = WORLD_WIDTH * 2 / 3 - hudLeftX;
        hudCentreX = WORLD_WIDTH / 3;
        hudRow1Y = WORLD_HEIGHT - hudVerticalMargin;
        hudRow2Y = hudRow1Y - hudVerticalMargin - font.getCapHeight();
        hudSectionWidth = WORLD_WIDTH / 3;
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
        playerShip = new PlayerShip(40, 6, WORLD_WIDTH / 2, WORLD_HEIGHT / 4,
                10, 10,
                0.4f, 4, 45, 0.5f,
                playerShipTextureRegion, playerShieldTextureRegion,
                playerLaserTextureRegion);

        enemyShipsList = new LinkedList<>();

    }

    //Initialise Texture regions
    private void setTextureRegions() {
        playerShipTextureRegion = textureAtlas.findRegion("playerShip");
        playerShieldTextureRegion = textureAtlas.findRegion("shield1");
        playerLaserTextureRegion = textureAtlas.findRegion("laserPlayer");

        enemyShipTextureRegion = textureAtlas.findRegion("enemyShip");
        enemyShipTextureRegion.flip(false, true);
        enemyShieldTextureRegion = textureAtlas.findRegion("shield2");
        enemyShieldTextureRegion.flip(false, true);
        enemyLaserTextureRegion = textureAtlas.findRegion("laserEnemy");
    }


    //Render the UI
    @Override
    public void render(float deltaTime) {
        spriteBatch.begin();

        //Scrolling background
        renderBackground(deltaTime);

        //Detect mouse and keyboard events
        detectInput(deltaTime);

        //Update player ship
        playerShip.update(deltaTime);

        spawnEnemyShip(deltaTime);

        ListIterator<EnemyShip> enemyShipListIterator = enemyShipsList.listIterator();
        while (enemyShipListIterator.hasNext()) {
            EnemyShip enemyShip = enemyShipListIterator.next();

            //Move enemies
            moveEnemy(enemyShip, deltaTime);

            //Update enemyShip
            enemyShip.update(deltaTime);

            //Display enemy ship
            enemyShip.draw(spriteBatch);

        }

        //Display player ship
        playerShip.draw(spriteBatch);

        //Display lasers
        createLasers(deltaTime);

        //Detect collisions between lasers and ships
        detectCollisions();

        //Display explosions
        renderExplosions(deltaTime);

        //Display HUD
        

        spriteBatch.end();
    }


    private void spawnEnemyShip(float deltaTime) {
        enemySpawnTimer += deltaTime;
        if (enemySpawnTimer > timeBetweenEnemySpawns) {
            enemyShipsList.add(new EnemyShip(35, 7,
                    SpaceShooterGame.getRandom().nextFloat() * (WORLD_WIDTH - 10) + 5,
                    WORLD_HEIGHT - 5,
                    10, 10,
                    0.3f, 5, 50, 0.8f,
                    enemyShipTextureRegion, enemyShieldTextureRegion,
                    enemyLaserTextureRegion));
            enemySpawnTimer -= timeBetweenEnemySpawns;
        }
    }

    //Method to move enemies
    private void moveEnemy(EnemyShip enemyShip, float deltaTime) {
        //Move positions limits
        float leftLimit = -enemyShip.getShipRectangle().x;
        float downLimit = (float) WORLD_HEIGHT / 2 - enemyShip.getShipRectangle().y;
        float rightLimit = WORLD_WIDTH - enemyShip.getShipRectangle().x - enemyShip.getShipRectangle().width;
        float upLimit = WORLD_HEIGHT - enemyShip.getShipRectangle().y - enemyShip.getShipRectangle().height;


        float xMove = enemyShip.getDirectionsVector().x * enemyShip.getMovementSpeed() * deltaTime;
        float yMove = enemyShip.getDirectionsVector().y * enemyShip.getMovementSpeed() * deltaTime;

        if (xMove > 0) {
            xMove = Math.min(xMove, rightLimit);
        } else {
            xMove = Math.max(xMove, leftLimit);
        }

        if (yMove > 0) {
            yMove = Math.min(yMove, upLimit);
        } else {
            yMove = Math.max(yMove, downLimit);
        }

        enemyShip.moveTo(xMove, yMove);
    }

    //Method to detect mouse and keyboard events
    private void detectInput(float deltaTime) {
        //Move positions limits
        float leftLimit = -playerShip.getShipRectangle().x;
        float downLimit = -playerShip.getShipRectangle().y;
        float rightLimit = WORLD_WIDTH - playerShip.getShipRectangle().x - playerShip.getShipRectangle().width;
        float upLimit = (float) WORLD_HEIGHT / 2 - playerShip.getShipRectangle().y - playerShip.getShipRectangle().height;

        //Keyboard input
        //Right key press
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) &&
                rightLimit > 0) {
            playerShip.moveTo(
                    Math.min(playerShip.getMovementSpeed() * deltaTime, rightLimit),
                    0f);
        }

        //Up key press
        if (Gdx.input.isKeyPressed(Input.Keys.UP) &&
                upLimit > 0) {
            playerShip.moveTo(
                    0f,
                    Math.min(playerShip.getMovementSpeed() * deltaTime, upLimit));
        }

        //Left key press
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) &&
                leftLimit < 0) {
            playerShip.moveTo(
                    Math.max(-playerShip.getMovementSpeed() * deltaTime, leftLimit),
                    0f);
        }

        //Down key press
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN) &&
                downLimit < 0) {
            playerShip.moveTo(
                    0f,
                    Math.max(-playerShip.getMovementSpeed() * deltaTime, downLimit));
        }

        //Touch input and mouse input
        if (Gdx.input.isTouched()) {
            //Get screen position
            float xTouchPixels = Gdx.input.getX();
            float yTouchPixels = Gdx.input.getY();

            //Convert to world units
            Vector2 touchCoords = new Vector2(xTouchPixels, yTouchPixels);
            touchCoords = viewport.unproject(touchCoords);

            //Calculate X and Y difference
            Vector2 playerShipCentre =
                    new Vector2(playerShip.getShipRectangle().x + playerShip.getShipRectangle().width / 2,
                            playerShip.getShipRectangle().y + playerShip.getShipRectangle().height / 2);

            float touchDistance = touchCoords.dst(playerShipCentre);
            if (touchDistance > MOVEMENT_THRESHOLD) {
                float xTouchDifference = touchCoords.x - playerShipCentre.x;
                float yTouchDifference = touchCoords.y - playerShipCentre.y;

                float xMove = xTouchDifference / touchDistance * playerShip.getMovementSpeed() * deltaTime;
                float yMove = yTouchDifference / touchDistance * playerShip.getMovementSpeed() * deltaTime;

                if (xMove > 0) {
                    xMove = Math.min(xMove, rightLimit);
                } else {
                    xMove = Math.max(xMove, leftLimit);
                }

                if (yMove > 0) {
                    yMove = Math.min(yMove, upLimit);
                } else {
                    yMove = Math.max(yMove, downLimit);
                }

                playerShip.moveTo(xMove, yMove);
            }
        }
    }

    //Method to detect collisions between lasers and ships
    private void detectCollisions() {
        //Check for intersections between lasers and the enemy ship
        ListIterator<Laser> laserListIterator = playerLasers.listIterator();

        while (laserListIterator.hasNext()) {
            Laser laser = laserListIterator.next();

            ListIterator<EnemyShip> enemyShipListIterator = enemyShipsList.listIterator();
            while (enemyShipListIterator.hasNext()) {
                EnemyShip enemyShip = enemyShipListIterator.next();

                if (enemyShip.intersects(laser.getBoundingBox())) {
                    if (enemyShip.hitAndCheckIfDestroyed(laser)) {
                        enemyShipListIterator.remove();
                        explosionsList.add(new Explosion(explosionTexture,
                                new Rectangle(enemyShip.getShipRectangle()),
                                0.7f));
                    }

                    laserListIterator.remove();
                    break;
                }
            }
        }

        //Check for intersections between lasers and the player ship
        laserListIterator = enemyLasers.listIterator();

        while (laserListIterator.hasNext()) {
            Laser laser = laserListIterator.next();

            if (playerShip.intersects(laser.getBoundingBox())) {

                if (playerShip.hitAndCheckIfDestroyed(laser)) {
                    explosionsList.add(new Explosion(explosionTexture,
                            new Rectangle(playerShip.getShipRectangle()),
                            1.6f));
                    playerShip.setShield(10);
                }

                laserListIterator.remove();
            }
        }
    }

    //Method to create new lasers
    private void createLasers(float deltaTime) {
        //Player lasers
        if (playerShip.canFireLaser()) {
            Laser[] lasers = playerShip.fireLaser();
            playerLasers.addAll(Arrays.asList(lasers));
        }

        //Enemy lasers
        ListIterator<EnemyShip> enemyShipListIterator = enemyShipsList.listIterator();
        while (enemyShipListIterator.hasNext()) {
            EnemyShip enemyShip = enemyShipListIterator.next();
            if (enemyShip.canFireLaser()) {
                Laser[] lasers = enemyShip.fireLaser();
                enemyLasers.addAll(Arrays.asList(lasers));
            }
        }

        //Draw lasers and Delete old lasers
        //Player lasers
        ListIterator<Laser> iterator = playerLasers.listIterator();

        while (iterator.hasNext()) {
            Laser laser = iterator.next();
            laser.draw(spriteBatch);
            laser.setYPosition(laser.getYPosition() + laser.getMovementSpeed() * deltaTime);
            if (laser.getYPosition() > WORLD_HEIGHT) {
                iterator.remove();
            }
        }

        //Enemy lasers
        iterator = enemyLasers.listIterator();

        while (iterator.hasNext()) {
            Laser laser = iterator.next();
            laser.draw(spriteBatch);
            laser.setYPosition(laser.getYPosition() - laser.getMovementSpeed() * deltaTime);
            if (laser.getYPosition() + laser.getHeight() < 0) {
                iterator.remove();
            }
        }
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

    //Method to render explosions
    private void renderExplosions(float deltaTime) {
        ListIterator<Explosion> explosionListIterator = explosionsList.listIterator();
        while (explosionListIterator.hasNext()) {
            Explosion explosion = explosionListIterator.next();
            explosion.update(deltaTime);

            if (explosion.isFinished()) {
                explosionListIterator.remove();
            } else {
                explosion.draw(spriteBatch);
            }
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
