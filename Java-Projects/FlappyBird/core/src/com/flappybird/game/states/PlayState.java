package com.flappybird.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.flappybird.game.FlappyBird;
import com.flappybird.game.sprites.Bird;
import com.flappybird.game.sprites.Tube;

public class PlayState extends State {
    private static final int TUBE_SPACING = 125;
    private static final int TUBE_COUNT = 4;
    private static final int GROUND_Y_OFFSET = -50;

    private Array<Tube> tubes;
    private Bird bird;
    private Texture background;
    private Texture ground;
    private Vector2 groundPosition1;
    private Vector2 groundPosition2;


    public PlayState(GameStateManager gameStateManager) {
        super(gameStateManager);
        this.bird = new Bird(50, 300);
        this.camera.setToOrtho(false, FlappyBird.WIDTH / 2,
                FlappyBird.HEIGHT / 2);
        this.background = new Texture("bg.png");

        this.tubes = createTubes();

        this.ground = new Texture("ground.png");
        this.groundPosition1 = new Vector2(
                camera.position.x - camera.viewportWidth / 2,
                GROUND_Y_OFFSET);

        this.groundPosition2 = new Vector2(
                (camera.position.x - camera.viewportWidth / 2) +
                        ground.getWidth(),
                GROUND_Y_OFFSET);
    }

    //Method to create tubes array
    private Array<Tube> createTubes() {
        Array<Tube> tubes = new Array<>();

        for (int i = 0; i < TUBE_COUNT; i++) {
            tubes.add(new Tube(i * (TUBE_SPACING + Tube.TUBE_WIDTH)));
        }

        return tubes;
    }

    @Override
    protected void handleInput() {
        if (Gdx.input.justTouched()) {
            bird.jump();
        }
    }

    //Method to update the UI
    @Override
    public void update(float deltaTime) {
        handleInput();
        updateGround();

        bird.update(deltaTime);
        camera.position.x = bird.getPosition().x + 80;

        //Reposition tubes
        for (int i = 0; i < TUBE_COUNT; i++) {
            Tube tube = tubes.get(i);

            if (camera.position.x - (camera.viewportWidth / 2) >
                    tube.getPosTopTube().x + tube.getTopTube().getWidth()) {

                tube.repositionTube(tube.getPosTopTube().x +
                        ((Tube.TUBE_WIDTH + TUBE_SPACING) * TUBE_COUNT));
            }

            //Kill the bird if it touches the tube
            if (tube.collides(bird.getBounds())) {
                gameStateManager.set(new PlayState(gameStateManager));
            }
        }

        //Kill the bird if it touches the ground
        if (bird.getPosition().y <= ground.getHeight() + GROUND_Y_OFFSET) {
            gameStateManager.set(new PlayState(gameStateManager));
        }

        camera.update();
    }

    //Method to update the ground position based on the viewport
    private void updateGround() {
        if (camera.position.x - (camera.viewportWidth / 2) >
                groundPosition1.x + ground.getWidth()) {
            groundPosition1.add(ground.getWidth() * 2, 0);
        }

        if (camera.position.x - (camera.viewportWidth / 2) >
                groundPosition2.x + ground.getWidth()) {
            groundPosition2.add(ground.getWidth() * 2, 0);
        }
    }

    //Method to draw textures on the screen
    @Override
    public void render(SpriteBatch spriteBatch) {
        spriteBatch.setProjectionMatrix(camera.combined);
        spriteBatch.begin();

        //Draw the background
        spriteBatch.draw(background,
                camera.position.x - (camera.viewportWidth / 2),
                0);

        //Draw the bird
        spriteBatch.draw(bird.getTexture(), bird.getPosition().x,
                bird.getPosition().y);

        //Draw tubes
        for (Tube tube : tubes) {
            spriteBatch.draw(tube.getBottomTube(), tube.getPosBottomTube().x,
                    tube.getPosBottomTube().y);

            spriteBatch.draw(tube.getTopTube(), tube.getPosTopTube().x, tube
                    .getPosTopTube().y);
        }

        //Draw ground
        spriteBatch.draw(ground, groundPosition1.x, groundPosition1.y);
        spriteBatch.draw(ground, groundPosition2.x, groundPosition2.y);

        spriteBatch.end();
    }

    //Method to dispose resources
    @Override
    public void dispose() {
        background.dispose();
        bird.dispose();
        ground.dispose();
        for (Tube tube : tubes) {
            tube.dispose();
        }

        System.out.println("Play State disposed!");
    }
}
