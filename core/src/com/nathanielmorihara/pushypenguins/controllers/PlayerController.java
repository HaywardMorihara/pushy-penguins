package com.nathanielmorihara.pushypenguins.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.nathanielmorihara.pushypenguins.models.PlayerModel;

public class PlayerController {

  public void update(PlayerModel playerModel) {
    // TODO Refactor
    if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
      playerModel.body.setLinearVelocity(0, playerModel.speed);
    }
    else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
      playerModel.body.setLinearVelocity(0, -playerModel.speed);
    }
    else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
      playerModel.body.setLinearVelocity(-playerModel.speed, 0);
    }
    else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
      playerModel.body.setLinearVelocity(playerModel.speed, 0);
    } else {
      playerModel.body.setLinearVelocity(0,0);
    }
  }
}
