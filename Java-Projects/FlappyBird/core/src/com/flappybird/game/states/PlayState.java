package com.flappybird.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.flappybird.game.FlappyBird;
import com.flappybird.game.sprites.Bird;
import com.flappybird.game.sprites.Tube;

public class PlayState extends State {
    private static final int TUBE_SPACING = 125;
    private static final int TUBE_COUNT = 4;

    private Array<Tube> tubes;

    private Bird bird;
    private Texture background;

    public PlayState(GameStateManager gameStateManager) {
        super(gameStateManager);
        this.bird = new Bird(50, 300);
        this.camera.setToOrtho(false, FlappyBird.WIDTH / 2,
                FlappyBird.HEIGHT / 2);
        this.background = new Texture("bg.png");

        this.tubes = createTubes();
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

    @Override
    public void update(float deltaTime) {
        handleInput();
        bird.update(deltaTime);
        camera.position.x = bird.getPosition().x + 80;

        for (int i = 0; i < TUBE_COUNT; i++) {
            Tube tube = tubes.get(i);
            
            if (camera.position.x - (camera.viewportWidth / 2) >
                    tube.getPosTopTube().x + tube.getTopTube().getWidth()) {

                tube.repositionTube(tube.getPosTopTube().x +
                        ((Tube.TUBE_WIDTH + TUBE_SPACING) * TUBE_COUNT));
            }

            if (tube.collides(bird.getBounds())) {
                gameStateManager.set(new PlayState(gameStateManager));
            }
        }

        camera.update();
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
        spriteBatch.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        bird.dispose();
        for (Tube tube : tubes) {
            tube.dispose();
        }

        System.out.println("Play State disposed!");
    }
}
