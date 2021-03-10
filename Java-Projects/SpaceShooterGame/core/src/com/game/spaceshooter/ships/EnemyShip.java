package com.game.spaceshooter.ships;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.game.spaceshooter.lasers.Laser;

public class EnemyShip extends Ship {

    public EnemyShip(float movementSpeed, int shield,
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
        lasers[0] = new Laser(xPosition + width * 0.18f, yPosition - laserHeight,
                laserWidth, laserHeight,
                laserMovementSpeed, laserTextureRegion);

        //Right laser
        lasers[1] = new Laser(xPosition + width * 0.82f, yPosition - laserHeight,
                laserWidth, laserHeight,
                laserMovementSpeed, laserTextureRegion);

        lastShotTimer = 0;

        return lasers;
    }

    @Override
    public void draw(SpriteBatch spriteBatch) {
        spriteBatch.draw(shipTextureRegion, xPosition, yPosition,
                width, height);

        if (shield > 0) {
            spriteBatch.draw(shieldTextureRegion, xPosition, yPosition - 3,
                    width, height);
        }
    }
}
