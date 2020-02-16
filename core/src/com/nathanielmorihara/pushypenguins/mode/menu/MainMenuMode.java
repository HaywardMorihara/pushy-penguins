/**
 * Copyright (C) 2019 Urban Compass, Inc.
 */
package com.nathanielmorihara.pushypenguins.mode.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.nathanielmorihara.pushypenguins.mode.Mode;
import com.nathanielmorihara.pushypenguins.mode.game.GameMode;

/**
 * @author nathaniel.morihara
 */
public class MainMenuMode implements Mode {

  // TODO Make the menu nicer

  private final String TEXT_TITLE = "Pushy Penguins";
  private final float SCALE_TITLE = 3f;
  private final String TEXT_OPTION_SINGLE_PLAYER = "Press Enter to Start Single Player";
  private final String TEXT_OPTION_HIGH_SCORES = "Press 'H' to see High Scores";
  private final float SCALE_OPTION = 1f;

  private BitmapFont titleFont;
  private BitmapFont optionFont;

  private OrthographicCamera camera; // Controls the area of the map that you can see (?)
  private Viewport viewport; // Manages a Camera and determines how world coordinates are mapped to and from the screen

  private SpriteBatch batch;

  private Mode modeReplacement;


  public MainMenuMode() {
    titleFont = new BitmapFont();
    titleFont.setColor(Color.BLUE);
    titleFont.getData().setScale(SCALE_TITLE);

    optionFont = new BitmapFont();
    optionFont.setColor(Color.WHITE);
    optionFont.getData().setScale(SCALE_OPTION);

    camera = new OrthographicCamera();
    camera.setToOrtho(false);
    viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);

    batch = new SpriteBatch();
  }

  // TODO How to make sure dispose gets called?
  @Override
  public void dispose() {
    batch.dispose();
    titleFont.dispose();
    optionFont.dispose();
  }

  @Override
  public void resize(int width, int height) {
    this.viewport.update(width, height);
  }

  @Override
  public void update() {
    // TODO Menu selection with cursor
    if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
      modeReplacement = new GameMode();
    }
    if (Gdx.input.isKeyJustPressed(Input.Keys.H)) {
      modeReplacement = new HighScoreMenuMode();
    }
  }

  @Override
  public Mode changeMode() {
    if (modeReplacement != null) {
      this.dispose();
      return modeReplacement;
    }
    return null;
  }

  @Override
  public void draw() {
    Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Clear screen

    camera.update();
    batch.setProjectionMatrix(camera.combined);

    batch.begin();
    GlyphLayout titleLayout = new GlyphLayout(titleFont, TEXT_TITLE);
    titleFont.draw(
        batch,
        titleLayout,
        (camera.viewportWidth / 2) - (titleLayout.width / 2),
        camera.viewportHeight * 2 / 3);

    GlyphLayout singlePlayerOptionLayout = new GlyphLayout(optionFont, TEXT_OPTION_SINGLE_PLAYER);
    optionFont.draw(
        batch,
        singlePlayerOptionLayout,
        (camera.viewportWidth / 2) - (singlePlayerOptionLayout.width / 2),
        camera.viewportHeight / 3);

    GlyphLayout highScoreOptionLayout = new GlyphLayout(optionFont, TEXT_OPTION_HIGH_SCORES);
    optionFont.draw(
        batch,
        highScoreOptionLayout,
        (camera.viewportWidth / 2) - (singlePlayerOptionLayout.width / 2),
        camera.viewportHeight / 4);
    batch.end();
  }

  @Override
  public void pause() {

  }

  @Override
  public void resume() {

  }
}
