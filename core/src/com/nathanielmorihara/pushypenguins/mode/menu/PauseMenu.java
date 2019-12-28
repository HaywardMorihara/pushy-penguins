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
public class PauseMenu implements Mode {

  private SpriteBatch batch;
  private BitmapFont font;
  private boolean shouldUnpause = false;
  private Game game;

  public PauseMenu(Game game) {
    this.game = game;

    batch = new SpriteBatch();
    font = new BitmapFont();
    font.setColor(Color.RED);
  }

  // TODO How to Make sure it gets called?
  @Override
  public void dispose() {
    batch.dispose();
    font.dispose();
  }

  @Override
  public void resize(int width, int height) {

  }

  @Override
  public void update() {
    if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
      shouldUnpause = true;
    }
  }

  @Override
  public Mode changeMode() {
    if (shouldUnpause) {
      this.dispose();
      return game;
    }
    return null;
  }

  @Override
  public void draw() {
    Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Clear screen

    batch.begin();
    font.draw(batch, "Paused", 200, 200);
    batch.end();
  }

  @Override
  public void pause() {

  }

  @Override
  public void resume() {

  }
}
