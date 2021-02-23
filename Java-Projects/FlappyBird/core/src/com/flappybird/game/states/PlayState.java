package com.flappybird.game.states;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.flappybird.game.FlappyBird;

public class PlayState extends State {
    private Texture bird;

    public PlayState(GameStateManager gameStateManager) {
        super(gameStateManager);
        this.bird = new Texture("bird.png");
        this.camera.setToOrtho(false, FlappyBird.WIDTH / 2,
                FlappyBird.HEIGHT / 2);
    }

    @Override
    protected void handleInput() {

    }

    @Override
    public void update(float deltaTime) {

    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        spriteBatch.setProjectionMatrix(camera.combined);
        spriteBatch.begin();
        spriteBatch.draw(bird, 50, 150);
        spriteBatch.end();
    }

    @Override
    public void dispose() {

    }
}
