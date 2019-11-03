/**
 * Copyright (C) 2019 Urban Compass, Inc.
 */
package com.nathanielmorihara.pushypenguins.models;

import java.util.Random;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

/**
 * @author nathaniel.morihara
 */
public class PenguinModel {

  // TODO Dynamically set these based on sprite?
  private static final float minWidthUnscaled = 24f;
  private static final float maxWidthUnscaled = 60f;
  private static final float minHeightUnscaled = 32f;
  private static final float maxHeightUnscaled = 80f;

  private static final float minSpeedUnscaled = 50f;
  private static final float maxSpeedUnscaled = 150f;

  public float speed;
  public float width;
  public float height;
  public float density = 0.5f;
  public float friction = 0.4f;
  public float restitution = 0.6f;

  public Body body;

  // TODO Rename Beavers? Depends on if I decide on that. May not be worth thinking too much about
  // the concept for this prototype game
  public PenguinModel(World world, float scale, float x, float y) {
    // TODO why aren't collisions working?
    BodyDef bodyDef = new BodyDef();
    bodyDef.type = BodyDef.BodyType.KinematicBody; // TODO What does this mean? Is this what I want?
    bodyDef.position.set(x, y);

    body = world.createBody(bodyDef);

    // TODO Refactor, use scale correctly
    Random random = new Random();
    float randScale = random.nextFloat();
    width = minWidthUnscaled * scale + randScale * scale * (maxWidthUnscaled - minWidthUnscaled);
    height = minHeightUnscaled * scale + randScale * scale * (maxHeightUnscaled - minHeightUnscaled);

    CircleShape circle = new CircleShape();
    circle.setRadius(width / 2);

    FixtureDef fixtureDef = new FixtureDef();
    fixtureDef.shape = circle;
    fixtureDef.density = density;
    fixtureDef.friction = friction;
    fixtureDef.restitution = restitution; // Make it bounce a little bit

    body.createFixture(fixtureDef);

    // Remember to dispose of any shapes after you're done with them!
    // BodyDef and FixtureDef don't need disposing, but shapes do.
    circle.dispose();

    Random r = new Random();
    speed = minSpeedUnscaled * scale + (r.nextFloat() * maxSpeedUnscaled * scale);
  }
}
