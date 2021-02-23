package com.flappybird.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.flappybird.game.FlappyBird;

public class MenuState extends State {
    private Texture background;
    private Texture playButton;

    public MenuState(GameStateManager gameStateManager) {
        super(gameStateManager);
        this.background = new Texture("bg.png");
        this.playButton = new Texture("playbtn.png");
    }

    @Override
    protected void handleInput() {
        if (Gdx.input.justTouched()) {
            gameStateManager.set(new PlayState(gameStateManager));
            dispose();
        }
    }

    @Override
    public void update(float deltaTime) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        spriteBatch.begin();
        spriteBatch.draw(background, 0, 0, FlappyBird.WIDTH, FlappyBird.HEIGHT);
        spriteBatch.draw(playButton, (FlappyBird.WIDTH / 2) - (playButton.getWidth() / 2),
                (FlappyBird.HEIGHT / 2));
        spriteBatch.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        playButton.dispose();
    }
}
