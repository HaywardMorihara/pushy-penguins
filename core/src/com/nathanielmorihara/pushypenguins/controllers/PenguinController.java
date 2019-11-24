/**
 * Copyright (C) 2019 Urban Compass, Inc.
 */
package com.nathanielmorihara.pushypenguins.controllers;

import com.badlogic.gdx.math.Vector2;
import com.nathanielmorihara.pushypenguins.models.PenguinModel;

/**
 * @author nathaniel.morihara
 */
public class PenguinController {
  public void update(PenguinModel penguinModel) {
    penguinModel.body.setLinearVelocity(0, -penguinModel.speed);
  }
}
