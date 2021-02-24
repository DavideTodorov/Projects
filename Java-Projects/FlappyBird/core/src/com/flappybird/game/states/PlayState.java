package com.flappybird.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.flappybird.game.FlappyBird;
import com.flappybird.game.sprites.Bird;

public class PlayState extends State {
    private Bird bird;
    private Texture background;

    public PlayState(GameStateManager gameStateManager) {
        super(gameStateManager);
        this.bird = new Bird(50, 300);
        this.camera.setToOrtho(false, FlappyBird.WIDTH / 2,
                FlappyBird.HEIGHT / 2);
        this.background = new Texture("bg.png");
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
        spriteBatch.end();
    }

    @Override
    public void dispose() {

    }
}
