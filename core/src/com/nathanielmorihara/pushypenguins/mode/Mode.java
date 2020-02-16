/**
 * Copyright (C) 2019 Urban Compass, Inc.
 */
package com.nathanielmorihara.pushypenguins.mode;

/**
 * @author nathaniel.morihara
 */
public interface Mode {

  // TODO How to make sure dispose gets called/defined?
  void dispose();

  void resize(int width, int height);

  void update();

  // The next game mode to enter. If returns null, the game mode does not change
   // Called between `update()` and `draw()`
  Mode changeMode();

  void draw();

  void pause();

  void resume();
}
