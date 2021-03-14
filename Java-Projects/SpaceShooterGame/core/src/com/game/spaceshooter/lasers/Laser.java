package com.game.spaceshooter.lasers;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

import java.awt.*;

public class Laser {
    //position and dimensions

    private Rectangle laserRectangle;

    //Physical characteristics
    private float movementSpeed;

    //Graphics
    private TextureRegion textureRegion;

    public Laser(float xPosition, float yPosition,
                 float width, float height,
                 float movementSpeed, TextureRegion textureRegion) {
        this.laserRectangle = new Rectangle(xPosition, yPosition,
                width, height);

        this.movementSpeed = movementSpeed;
        this.textureRegion = textureRegion;
    }

    public void draw(SpriteBatch spriteBatch) {
        spriteBatch.draw(textureRegion, laserRectangle.x - laserRectangle.width / 2, laserRectangle.y,
                laserRectangle.width, laserRectangle.height);
    }


    public float getMovementSpeed() {
        return movementSpeed;
    }

    public void setMovementSpeed(float movementSpeed) {
        this.movementSpeed = movementSpeed;
    }

    public float getYPosition() {
        return laserRectangle.y;
    }

    public void setYPosition(float yPosition) {
        this.laserRectangle.y = yPosition;
    }

    public float getXPosition() {
        return laserRectangle.x;
    }

    public void setXPosition(float xPosition) {
        this.laserRectangle.x = xPosition;
    }

    public float getHeight() {
        return laserRectangle.height;
    }

    public Rectangle getBoundingBox() {
        return laserRectangle;
    }
}
