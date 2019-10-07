/**
 * Copyright (C) 2019 Urban Compass, Inc.
 */
package com.nathanielmorihara.pushypenguins.models;

import java.util.Random;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

/**
 * @author nathaniel.morihara
 */
public class PenguinModel {

  // TODO Make these based on spirte
  private static final float minWidth = 1f; //24
  private static final float maxWidth = 1f; //60
  private static final float minHeight = 2f; //32
  private static final float maxHeight = 2f; //80

  private static final float minSpeed = 1f; //50
  private static final float maxSpeed = 3f; //150

  public float speed;
  public float width;
  public float height;
  public float density = 0.5f;
  public float friction = 0.4f;
  public float restitution = 0.6f;

  public Body body;

  // TODO Use scale
  public PenguinModel(World world, float scale, float x, float y) {
    // First we create a body definition
    BodyDef bodyDef = new BodyDef();
    // We set our body to dynamic, for something like ground which doesn't move we would set it to StaticBody
    bodyDef.type = BodyDef.BodyType.KinematicBody;
    bodyDef.position.set(x, y);

    // Create our body in the world using our body definition
    body = world.createBody(bodyDef);

    Random random = new Random();
    float randScale = random.nextFloat();
    width = minWidth + randScale * (maxWidth - minWidth);
    height = minHeight + randScale * (maxHeight - minHeight);

    // Create a circle shape and set its radius to 6
    CircleShape circle = new CircleShape();
    circle.setRadius(width/2);

    // Create a fixture definition to apply our shape to
    FixtureDef fixtureDef = new FixtureDef();
    fixtureDef.shape = circle;
    fixtureDef.density = density;
    fixtureDef.friction = friction;
    fixtureDef.restitution = restitution; // Make it bounce a little bit

    // Create our fixture and attach it to the body
    Fixture fixture = body.createFixture(fixtureDef);

    // Remember to dispose of any shapes after you're done with them!
    // BodyDef and FixtureDef don't need disposing, but shapes do.
    circle.dispose();

    Random r = new Random();
    speed = minSpeed + (r.nextFloat() * maxSpeed);
  }
}
