package com.nathanielmorihara.pushypenguins;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

public class Penguin {




  public Penguin(World world) {





    // Create a fixture definition to apply our shape to
    FixtureDef fixtureDef = new FixtureDef();
    fixtureDef.shape = circle;
    fixtureDef.density = 0.5f;
    fixtureDef.friction = 0.4f;
    fixtureDef.restitution = 0.6f; // Make it bounce a little bit

    // Create our fixture and attach it to the body
    Fixture fixture = body.createFixture(fixtureDef);

    // Remember to dispose of any shapes after you're done with them!
    // BodyDef and FixtureDef don't need disposing, but shapes do.
    circle.dispose();

    Random r = new Random();
    speed = 50f + (r.nextFloat() * 50f);
  }

  public void update(float time) {
    //body.setLinearVelocity(0,0);

    Vector2 pos = body.getPosition();
    body.setLinearVelocity(0, -speed);
    // body.applyLinearImpulse(0, -10f, pos.x, pos.y, true);
  }

  public void draw(SpriteBatch spriteBatch, float time) {
    // Get current frame of animation for the current time
    TextureRegion currentFrame = still;
    currentFrame = walkDownAnimation.getKeyFrame(time, true);
//    if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
//      currentFrame = walkUpAnimation.getKeyFrame(time, true);
//    }
//    if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
//      currentFrame = walkDownAnimation.getKeyFrame(time, true);
//    }
//    if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
//      currentFrame = walkLeftAnimation.getKeyFrame(time, true);
//    }
//    if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
//      currentFrame = walkRightAnimation.getKeyFrame(time, true);
//    }
    spriteBatch.draw(currentFrame, body.getPosition().x - scale/2, body.getPosition().y - scale/2, scale, scale); // Draw current frame at (50, 50)
  }
}
