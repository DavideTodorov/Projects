package com.game.spaceshooter.lasers;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Laser {
    //position and dimensions
    private float xPosition;    //Bottom centre
    private float yPosition;    //Bottom centre
    private float width;
    private float height;

    //Physical characteristics
    private float movementSpeed;

    //Graphics
    private TextureRegion textureRegion;

    public Laser(float xPosition, float yPosition,
                 float width, float height,
                 float movementSpeed, TextureRegion textureRegion) {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.width = width;
        this.height = height;
        this.movementSpeed = movementSpeed;
        this.textureRegion = textureRegion;
    }

    public void draw(SpriteBatch spriteBatch) {
        spriteBatch.draw(textureRegion, xPosition - width / 2, yPosition,
                width, height);
    }


    public float getMovementSpeed() {
        return movementSpeed;
    }

    public void setMovementSpeed(float movementSpeed) {
        this.movementSpeed = movementSpeed;
    }

    public float getYPosition() {
        return yPosition;
    }

    public void setYPosition(float yPosition) {
        this.yPosition = yPosition;
    }

    public float getXPosition() {
        return xPosition;
    }

    public void setXPosition(float xPosition) {
        this.xPosition = xPosition;
    }

    public float getHeight() {
        return height;
    }
}
