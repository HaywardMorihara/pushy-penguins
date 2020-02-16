/**
 * Copyright (C) 2020 Urban Compass, Inc.
 */
package com.nathanielmorihara.pushypenguins.mode.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.nathanielmorihara.pushypenguins.io.HighScoreFileHandler;
import com.nathanielmorihara.pushypenguins.json.Score;
import com.nathanielmorihara.pushypenguins.mode.Mode;

/**
 * @author nathaniel.morihara
 */
public class GameOverMenuMode implements Mode {

  private final String TEXT_TITLE = "Game Over";
  private final float SCALE_TITLE = 2f;

  private BitmapFont titleFont;

  private OrthographicCamera camera;
  private Viewport viewport;

  private SpriteBatch batch;

  private Mode modeReplacement;

  private int score;
  private boolean isNewHighScore;

  public GameOverMenuMode(float time) {
    // Text
    FreeTypeFontGenerator fontGenerator =
        new FreeTypeFontGenerator(Gdx.files.internal("pokemon_font.ttf"));

    FreeTypeFontGenerator.FreeTypeFontParameter titleFontParams =
        new FreeTypeFontGenerator.FreeTypeFontParameter();
    titleFontParams.size = Math.round(titleFontParams.size * SCALE_TITLE);
    titleFont = fontGenerator.generateFont(titleFontParams);

    fontGenerator.dispose(); // Don't dispose if doing incremental glyph generation.

    // Camera
    camera = new OrthographicCamera();
    camera.setToOrtho(false);
    viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);

    batch = new SpriteBatch();

    // Score
    score = Math.round(time);
    HighScoreFileHandler highScoreFileHandler = new HighScoreFileHandler();
    if (!highScoreFileHandler.existsHighScoreFile()) {
      highScoreFileHandler.initializeEmptyScores();
    }
    isNewHighScore = highScoreFileHandler.isNewHighScore(score);
    if (isNewHighScore) {
      // TODO Allow user to enter their name
      highScoreFileHandler.writeNewHighScore(new Score(score, "Nathaniel"));
    }
  }

  @Override
  public void dispose() {
    batch.dispose();
    titleFont.dispose();
  }

  @Override
  public void resize(int width, int height) {
    this.viewport.update(width, height);
  }

  @Override
  public void update() {
    // TODO Ask user to enter name if new high score
    if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
      modeReplacement = new MainMenuMode();
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
        camera.viewportHeight * 3 / 10);

    // TODO Temp text to indicate new high score, in context of other high scores

    // TODO Ask user to enter name if new high score

    batch.end();
  }

  @Override
  public void pause() {

  }

  @Override
  public void resume() {

  }
}
