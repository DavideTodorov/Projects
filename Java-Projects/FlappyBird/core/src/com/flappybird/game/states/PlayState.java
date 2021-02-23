package com.flappybird.game.states;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.flappybird.game.FlappyBird;
import com.flappybird.game.sprites.Bird;

public class PlayState extends State {
    private Bird bird;

    public PlayState(GameStateManager gameStateManager) {
        super(gameStateManager);
        this.bird = new Bird(50, 300);
        this.camera.setToOrtho(false, FlappyBird.WIDTH / 2,
                FlappyBird.HEIGHT / 2);
    }

    @Override
    protected void handleInput() {

    }

    @Override
    public void update(float deltaTime) {
        handleInput();
        bird.update(deltaTime);
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        spriteBatch.setProjectionMatrix(camera.combined);
        spriteBatch.begin();
        spriteBatch.draw(bird.getTexture(), bird.getPosition().x,
                bird.getPosition().y);
        spriteBatch.end();
    }

    @Override
    public void dispose() {

    }
}
