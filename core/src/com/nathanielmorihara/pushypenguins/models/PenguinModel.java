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
import com.nathanielmorihara.pushypenguins.views.PenguinView;

/**
 * @author nathaniel.morihara
 */
public class PenguinModel {

  // TODO Play around with these values
  private static final float minScale = 1f;
  private static final float maxScale = 3f;
  private static final float minSpeedUnscaled = 25f;
  private static final float maxSpeedUnscaled = 75f;

  public float speed;
  public float width;
  public float height;
  // TODO Play around with these values
  public float density = 0.5f;
  public float friction = 0.4f;
  public float restitution = 0.6f;

  public Body body;

  // TODO Rename Beavers? Depends on if I decide on that. May not be worth thinking too much about
  // the concept for this prototype game
  public PenguinModel(World world, float scale, float x, float y) {
    BodyDef bodyDef = new BodyDef();
    bodyDef.type = BodyDef.BodyType.DynamicBody;
    bodyDef.position.set(x, y);
    body = world.createBody(bodyDef);

    // TODO Refactor, use scale correctly
    Random random = new Random();
    float randScale = random.nextFloat();

    // TODO Should this error if not set? or have a default?
    // TODO Change so the probabiliy isn't evenly distributed
    width = PenguinView.FRAME_WIDTH * scale * ((randScale * (maxScale - minScale)) + minScale);
    height = PenguinView.FRAME_HEIGHT * scale * ((randScale * (maxScale - minScale)) + minScale);

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
