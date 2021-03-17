package com.game.spaceshooter.ships;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.game.spaceshooter.SpaceShooterGame;
import com.game.spaceshooter.lasers.Laser;

public class EnemyShip extends Ship {
    private Vector2 directionsVector;
    private float timeSinceLastDirectionChange;
    private float directionChangeFrequency;


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
        this.directionsVector = new Vector2(0, -1);
        this.timeSinceLastDirectionChange = 0;
        this.directionChangeFrequency = 0.75f;
    }

    public Vector2 getDirectionsVector() {
        return directionsVector;
    }

    private void randomizeDirectionVector() {
        double bearing = SpaceShooterGame.getRandom().nextDouble() * 6.283185; //0 to 2 * PI
        directionsVector.x = (float) Math.sin(bearing);
        directionsVector.y = (float) Math.cos(bearing);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        timeSinceLastDirectionChange += deltaTime;

        if (timeSinceLastDirectionChange > directionChangeFrequency) {
            randomizeDirectionVector();
            timeSinceLastDirectionChange -= directionChangeFrequency;
        }
    }

    @Override
    public Laser[] fireLaser() {
        Laser[] lasers = new Laser[2];

        //Left laser
        lasers[0] = new Laser(shipRectangle.x + shipRectangle.width * 0.18f,
                shipRectangle.y - laserHeight,
                laserWidth, laserHeight,
                laserMovementSpeed, laserTextureRegion);

        //Right laser
        lasers[1] = new Laser(shipRectangle.x + shipRectangle.width * 0.82f,
                shipRectangle.y - laserHeight,
                laserWidth, laserHeight,
                laserMovementSpeed, laserTextureRegion);

        lastShotTimer = 0;

        return lasers;
    }

    @Override
    public void draw(SpriteBatch spriteBatch) {
        spriteBatch.draw(shipTextureRegion, shipRectangle.x, shipRectangle.y,
                shipRectangle.width, shipRectangle.height);

        if (shield > 0) {
            spriteBatch.draw(shieldTextureRegion, shipRectangle.x, shipRectangle.y - 3,
                    shipRectangle.width, shipRectangle.height);
        }
    }
}
