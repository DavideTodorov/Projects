package com.game.spaceshooter.ships;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Ship {
    //Ship's characteristics
    private float movementSpeed;
    private int shield;

    //position and dimensions
    private float xPosition;    //Lower-left corner
    private float yPosition;    //Lower-left corner
    private float width;
    private float height;

    //graphics
    private TextureRegion shipTexture;
    private TextureRegion shieldTexture;

    public Ship(float movementSpeed, int shield,
                float xCentre, float yCentre,
                float width, float height,
                TextureRegion shipTexture, TextureRegion shieldTexture) {
        this.movementSpeed = movementSpeed;
        this.shield = shield;
        this.xPosition = xCentre - width / 2;
        this.yPosition = yCentre - height / 2;
        this.width = width;
        this.height = height;
        this.shipTexture = shipTexture;
        this.shieldTexture = shieldTexture;
    }

    public void draw(SpriteBatch spriteBatch) {
        spriteBatch.draw(shipTexture, xPosition, yPosition,
                width, height);

        if (shield > 0) {
            spriteBatch.draw(shieldTexture, xPosition, yPosition,
                    width, height);
        }
    }
}
