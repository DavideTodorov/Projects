package com.game.spaceshooter.ships;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.game.spaceshooter.lasers.Laser;

public abstract class Ship {
    //Ship's characteristics
    protected float movementSpeed;
    protected int shield;

    //position and dimensions
    protected float xPosition;    //Lower-left corner
    protected float yPosition;    //Lower-left corner
    protected float width;
    protected float height;

    //graphics
    protected TextureRegion shipTextureRegion;
    protected TextureRegion shieldTextureRegion;
    protected TextureRegion laserTextureRegion;

    //Laser
    protected float laserWidth;
    protected float laserHeight;
    protected float laserMovementSpeed;
    protected float timeBetweenShots;
    protected float lastShotTimer;

    protected Ship(float movementSpeed, int shield,
                float xCentre, float yCentre,
                float width, float height,
                float laserWidth, float laserHeight, float laserMovementSpeed,
                float timeBetweenShots,
                TextureRegion shipTextureRegion, TextureRegion shieldTextureRegion,
                TextureRegion laserTextureRegion) {
        this.movementSpeed = movementSpeed;
        this.shield = shield;
        this.xPosition = xCentre - width / 2;
        this.yPosition = yCentre - height / 2;
        this.width = width;
        this.height = height;
        this.shipTextureRegion = shipTextureRegion;
        this.shieldTextureRegion = shieldTextureRegion;
        this.laserTextureRegion = laserTextureRegion;
        this.laserWidth = laserWidth;
        this.laserHeight = laserHeight;
        this.laserMovementSpeed = laserMovementSpeed;
        this.timeBetweenShots = timeBetweenShots;
        this.lastShotTimer = 0;
    }

    public void update(float deltaTime) {
        lastShotTimer += deltaTime;
    }

    public boolean canFireLaser() {
        return timeBetweenShots <= lastShotTimer;
    }

    public abstract Laser[] fireLaser();

    public void draw(SpriteBatch spriteBatch) {
        spriteBatch.draw(shipTextureRegion, xPosition, yPosition,
                width, height);

        if (shield > 0) {
            spriteBatch.draw(shieldTextureRegion, xPosition, yPosition,
                    width, height);
        }
    }
}
