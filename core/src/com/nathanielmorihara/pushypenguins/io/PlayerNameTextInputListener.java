/**
 * Copyright (C) 2020 Urban Compass, Inc.
 */
package com.nathanielmorihara.pushypenguins.io;

import com.badlogic.gdx.Input;

/**
 * @author nathaniel.morihara
 */
public class PlayerNameTextInputListener implements Input.TextInputListener {

  String playerName = "Unknown";

  @Override
  public void input(String text) {
    playerName = text;
  }

  @Override
  public void canceled() {

  }

  public String getPlayerName() {
    return playerName;
  }
}
