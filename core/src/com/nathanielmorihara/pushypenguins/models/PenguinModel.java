/**
 * Copyright (C) 2019 Urban Compass, Inc.
 */
package com.nathanielmorihara.pushypenguins.models;

import java.util.Random;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.World;

/**
 * @author nathaniel.morihara
 */
public class PenguinModel {

  static final float minWidth = 30f;
  static final float maxWidth = 80f;
  static final float minHeight = 30f;
  static final float maxHeight = 80f;

  static final float minSpeed = 50f;
  static final float maxSpeed = 100f;

  public float speed;
  public float width;
  public float height;

  Body body;

  public PenguinModel(World world, float x, float y) {
    // First we create a body definition
    BodyDef bodyDef = new BodyDef();
    // We set our body to dynamic, for something like ground which doesn't move we would set it to StaticBody
    bodyDef.type = BodyDef.BodyType.KinematicBody;
    bodyDef.position.set(x, y);

    // Create our body in the world using our body definition
    body = world.createBody(bodyDef);

    Random random = new Random();
    width = minWidth + random.nextFloat() * (maxWidth - minWidth);
    height = minHeight + random.nextFloat() * (maxHeight - minHeight);

    // Create a circle shape and set its radius to 6
    CircleShape circle = new CircleShape();
    circle.setRadius(width/2);
  }
}
