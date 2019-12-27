/**
 * Copyright (C) 2019 Urban Compass, Inc.
 */
package com.nathanielmorihara.pushypenguins.mode;

/**
 * @author nathaniel.morihara
 */
public interface Mode {

  void create();

  void update();

  void draw();

  void resize(int width, int height);

  void pause();

  void resume();

  void dispose();

  Mode change();
}
