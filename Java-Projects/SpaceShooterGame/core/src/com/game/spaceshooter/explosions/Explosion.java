package com.game.spaceshooter.explosions;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class Explosion {
    private Animation<TextureRegion> explosionAnimation;
    private float explosionTimer;
    private Rectangle explosionRectangle;


    public Explosion(Texture texture, Rectangle explosionRectangle, float totalAnimationTime) {
        this.explosionRectangle = explosionRectangle;
        createExplosionAnimation(texture, totalAnimationTime);
        explosionTimer = 0;
    }

    //Method to create explosionAnimation
    private void createExplosionAnimation(Texture texture, float totalAnimationTime) {
        //Split texture
        TextureRegion[][] textureRegion2D = TextureRegion.split(texture,
                64, 64);

        //Convert to 1D
        TextureRegion[] textureRegion1D = new TextureRegion[16];
        int index = 0;
        for (int i = 0; i < textureRegion2D.length; i++) {
            for (int j = 0; j < textureRegion2D[i].length; j++) {
                textureRegion1D[index] = textureRegion2D[i][j];
                index++;
            }
        }

        explosionAnimation = new Animation<TextureRegion>(totalAnimationTime / 16,
                textureRegion1D);
    }

    public void update(float deltaTime) {
        explosionTimer += deltaTime;
    }

    public void draw(SpriteBatch spriteBatch) {
        spriteBatch.draw(explosionAnimation.getKeyFrame(explosionTimer),
                explosionRectangle.x, explosionRectangle.y,
                explosionRectangle.width, explosionRectangle.height);
    }

    public boolean isFinished() {
        return explosionAnimation.isAnimationFinished(explosionTimer);
    }
}
