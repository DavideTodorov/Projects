package com.game.spaceshooter.ships;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.game.spaceshooter.lasers.Laser;

public class PlayerShip extends Ship {

    public PlayerShip(float movementSpeed, int shield,
                      float xCentre, float yCentre,
                      float width, float height,
                      float laserWidth, float laserHeight,
                      float laserMovementSpeed, float timeBetweenShots,
                      TextureRegion shipTextureRegion, TextureRegion shieldTextureRegion,
                      TextureRegion laserTextureRegion) {
        super(movementSpeed, shield, xCentre, yCentre,
                width, height, laserWidth, laserHeight,
                laserMovementSpeed, timeBetweenShots,
                shipTextureRegion, shieldTextureRegion,
                laserTextureRegion);
    }

    @Override
    public Laser[] fireLaser() {
        Laser[] lasers = new Laser[2];

        //Left laser
        lasers[0] = new Laser(shipRectangle.x + shipRectangle.width * 0.07f,
                shipRectangle.y + shipRectangle.height * 0.45f,
                laserWidth, laserHeight,
                laserMovementSpeed, laserTextureRegion);

        //Right laser
        lasers[1] = new Laser(shipRectangle.x + shipRectangle.width * 0.93f,
                shipRectangle.y + shipRectangle.height * 0.45f,
                laserWidth, laserHeight,
                laserMovementSpeed, laserTextureRegion);

        lastShotTimer = 0;

        return lasers;
    }

    public void setShield(int shield) {
        this.shield = shield;
    }

    @Override
    public void draw(SpriteBatch spriteBatch) {
        spriteBatch.draw(shipTextureRegion, shipRectangle.x, shipRectangle.y,
                shipRectangle.width, shipRectangle.height);

        if (shield > 0) {
            spriteBatch.draw(shieldTextureRegion, shipRectangle.x, shipRectangle.y + 2,
                    shipRectangle.width, shipRectangle.height);
        }
    }


}
