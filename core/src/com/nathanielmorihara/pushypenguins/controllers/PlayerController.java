package com.nathanielmorihara.pushypenguins.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.nathanielmorihara.pushypenguins.models.PlayerModel;

public class PlayerController {

  public void update(PlayerModel playerModel) {
    Vector2 pos = playerModel.body.getPosition();

    if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
      playerModel.body.applyLinearImpulse(0, PlayerModel.linearImpulse, pos.x, pos.y, true);
    }
    else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
      playerModel.body.applyLinearImpulse(0, -PlayerModel.linearImpulse, pos.x, pos.y, true);
    }
    else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
      playerModel.body.applyLinearImpulse(-PlayerModel.linearImpulse, 0, pos.x, pos.y, true);
    }
    else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
      playerModel.body.applyLinearImpulse(PlayerModel.linearImpulse, 0, pos.x, pos.y, true);
    } else {
      // But...wouldn't this prevent player from getting pushed? Meh, it actually seems okay.
      // May want to tweak it though
      // Also I know its weird so the user can still move in x if start pressing y
      playerModel.body.setLinearVelocity(0,0);
    }
  }
}
