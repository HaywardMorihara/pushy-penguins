/**
 * Copyright (C) 2019 Urban Compass, Inc.
 */
package com.nathanielmorihara.pushypenguins.mode.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.nathanielmorihara.pushypenguins.mode.Mode;
import com.nathanielmorihara.pushypenguins.mode.game.Game;

/**
 * @author nathaniel.morihara
 */
public class Menu implements Mode {

  // TODO Make the Menu nicer

  private SpriteBatch batch;
  private BitmapFont font;

  private String text = "Press Enter to Start";

  private Mode modeReplacement;

  @Override
  public void create() {
    batch = new SpriteBatch();
    font = new BitmapFont();
    font.setColor(Color.RED);

  }

  @Override
  public void dispose() {
    batch.dispose();
    font.dispose();
  }

  @Override
  public void update() {
    if (Gdx.input.isKeyPressed(Input.Keys.ENTER)) {
      text = "Starting game...";
      modeReplacement = new Game();
    }
  }

  @Override
  public void draw() {
    Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Clear screen

    batch.begin();
    font.draw(batch, text, 200, 200);
    batch.end();
  }

  @Override
  public void resize(int width, int height) {

  }

  @Override
  public void pause() {

  }

  @Override
  public void resume() {

  }

  @Override
  public Mode change() {
    if (modeReplacement != null) {
      modeReplacement.create();
      modeReplacement.resize(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
      return modeReplacement;
    }
    return null;
  }
}
