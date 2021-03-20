package com.game.spaceshooter.ships;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.game.spaceshooter.lasers.Laser;
import com.badlogic.gdx.math.Rectangle;


import java.awt.*;

public abstract class Ship {
    //Ship's characteristics
    protected float movementSpeed;
    protected int shield;

    //position and dimensions
    protected Rectangle shipRectangle;

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
        this.shipRectangle = new Rectangle(xCentre - width / 2, yCentre - height / 2,
                width, height);
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

    public boolean intersects(Rectangle laserRectangle) {
        return shipRectangle.overlaps(laserRectangle);
    }

    public Rectangle getShipRectangle() {
        return shipRectangle;
    }

    public float getMovementSpeed() {
        return movementSpeed;
    }

    public boolean hitAndCheckIfDestroyed(Laser laser) {
        if (shield > 0) {
            shield--;
            return false;
        }

        return true;
    }

    public void moveTo(float xChange, float yChange) {
        shipRectangle.setPosition(shipRectangle.x + xChange, shipRectangle.y + yChange);
    }

    public abstract void draw(SpriteBatch spriteBatch);
}
