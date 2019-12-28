/**
 * Copyright (C) 2019 Urban Compass, Inc.
 */
package com.nathanielmorihara.pushypenguins.mode;

/**
 * @author nathaniel.morihara
 */
public interface Mode {

  void dispose();

  void resize(int width, int height);

  void update();

  Mode changeMode();

  void draw();

  void pause();

  void resume();
}
