package com.nathanielmorihara.pushypenguins.models;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

/**
 * @author nathaniel.morihara
 */
public class PlayerModel {

  // TODO Dynamically set these based on sprite?
  // TODO Adjust these values
  public static final float unscaledWidth = 14f;//28
  public static final float unscaledHeight = 19f; //38

  public static final float unscaledSpeed = 100f;//200
  public static final float density = 0.5f;
  public static final float friction = 0.4f;
  public static final float restitution = 0.6f;
  public static final float linearImpulse = 1f;

  public float width;
  public float height;
  public float speed;

  public Body body;

  // TODO Bug: Isn't being created anymore?
  // TODO Make use of scale
  public PlayerModel(World world, float scale, float x, float y) {
    BodyDef bodyDef = new BodyDef();
    bodyDef.type = BodyDef.BodyType.DynamicBody;
    bodyDef.position.set(x, y);
    body = world.createBody(bodyDef);

    width = unscaledWidth * scale;
    height = unscaledHeight * scale;
    speed = unscaledSpeed * scale;

    // Create a circle shape and set its radius to 6
    CircleShape circle = new CircleShape();
    circle.setRadius(width / 2);

    // Create a fixture definition to apply our shape to
    FixtureDef fixtureDef = new FixtureDef();
    fixtureDef.shape = circle;
    fixtureDef.density = density;
    fixtureDef.friction = friction;
    fixtureDef.restitution = restitution;

    body.createFixture(fixtureDef);

    // Remember to dispose of any shapes after you're done with them!
    // BodyDef and FixtureDef don't need disposing, but shapes do.
    circle.dispose();
  }
}
