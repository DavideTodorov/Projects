package com.flappybird.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class Bird {
    private static final int GRAVITY = -15;
    private static final int MOVEMENT = 100;

    private Vector3 position;
    private Vector3 velocity;
    private Rectangle birdBounds;
    private Texture texture;
    private Animation birdAnimation;
    private Sound flapSound;

    //Constructor
    public Bird(int x, int y) {
        this.position = new Vector3(x, y, 0);
        this.velocity = new Vector3(0, 0, 0);

        this.texture = new Texture("birdanimation.png");
        this.birdAnimation = new Animation(new TextureRegion(texture), 3, 0.5f);

        this.birdBounds = new Rectangle(x, y, texture.getWidth() / 3, texture.getHeight());
        this.flapSound = Gdx.audio.newSound(Gdx.files.internal("sfx_wing.ogg"));
    }

    //Method to update the bird's velocity and position
    public void update(float deltaTime) {
        birdAnimation.update(deltaTime);

        if (position.y > 0) {
            velocity.add(0, GRAVITY, 0);
        }

        velocity.scl(deltaTime);
        position.add(MOVEMENT * deltaTime, velocity.y, 0);

        if (position.y < 0) {
            position.y = 0;
        }

        velocity.scl(1 / deltaTime);
        birdBounds.setPosition(position.x, position.y);
    }

    //Getters
    public Vector3 getPosition() {
        return position;
    }

    public TextureRegion getTexture() {
        return birdAnimation.getFrame();
    }

    public Rectangle getBounds() {
        return birdBounds;
    }

    //Method to make the bird jump
    public void jump() {
        velocity.y = 250;
        flapSound.play(0.2f);
    }

    public void dispose() {
        texture.dispose();
        flapSound.dispose();
    }
}
