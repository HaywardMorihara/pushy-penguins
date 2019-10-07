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

  public static final float width = 1f; //28 // TODO Should be based on sprite
  public static final float height = 1f; //38 // TODO Should be based on sprite
  public static final float density = 0.5f;
  public static final float friction = 0.4f;
  public static final float restitution = 0.6f;
  public static final float linearImpulse = 20000f;

  public Body body;

  // TODO Make use of scale
  public PlayerModel(World world, float scale, float x, float y) {
    // First we create a body definition
    BodyDef bodyDef = new BodyDef();
    // We set our body to dynamic, for something like ground which doesn't move we would set it to StaticBody
    bodyDef.type = BodyDef.BodyType.DynamicBody;
    // Set our body's starting position in the world
    bodyDef.position.set(x, y);
    // Create our body in the world using our body definition
    body = world.createBody(bodyDef);

    // Create a circle shape and set its radius to 6
    CircleShape circle = new CircleShape();
    circle.setRadius(width / 2);

    // Create a fixture definition to apply our shape to
    FixtureDef fixtureDef = new FixtureDef();
    fixtureDef.shape = circle;
    fixtureDef.density = density;
    fixtureDef.friction = friction;
    fixtureDef.restitution = restitution;

    // Create our fixture and attach it to the body
    Fixture fixture = body.createFixture(fixtureDef);

    // Remember to dispose of any shapes after you're done with them!
    // BodyDef and FixtureDef don't need disposing, but shapes do.
    circle.dispose();
  }
}
