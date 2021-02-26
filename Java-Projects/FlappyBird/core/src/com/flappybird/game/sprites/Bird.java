package com.flappybird.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import org.w3c.dom.css.Rect;

public class Bird {
    private static final int GRAVITY = -15;
    private static final int MOVEMENT = 100;

    private Vector3 position;
    private Vector3 velocity;
    private Texture bird;
    private Rectangle birdBounds;

    //Constructor
    public Bird(int x, int y) {
        this.position = new Vector3(x, y, 0);
        this.velocity = new Vector3(0, 0, 0);
        this.bird = new Texture("bird.png");
        this.birdBounds = new Rectangle(x, y, bird.getWidth(), bird.getHeight());
    }

    //Method to update the bird's velocity and position
    public void update(float deltaTime) {
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

    public Texture getTexture() {
        return bird;
    }

    public Rectangle getBounds() {
        return birdBounds;
    }

    //Method to make the bird jump
    public void jump() {
        velocity.y = 250;
    }

    public void dispose(){
        bird.dispose();
    }
}
