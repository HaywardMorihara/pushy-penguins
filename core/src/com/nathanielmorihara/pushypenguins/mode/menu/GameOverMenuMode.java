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
import com.nathanielmorihara.pushypenguins.io.PlayerNameTextInputListener;
import com.nathanielmorihara.pushypenguins.json.Score;
import com.nathanielmorihara.pushypenguins.mode.Mode;

/**
 * @author nathaniel.morihara
 */
public class GameOverMenuMode implements Mode {

  private final String TEXT_TITLE = "Game Over";
  private final float SCALE_TITLE = 2f;
  private final String HIGH_SCORE_INPUT_TITLE = "New High Score!";
  private final String HIGH_SCORE_INPUT_HINT = "Please enter your name";
  private final String TEXT_EXIT_INSTRUCTIONS = "Press Enter to Return to the Main Menu";
  private final float SCALE_EXIT_INSTRUCTIONS = 1f;

  private BitmapFont titleFont;
  private BitmapFont exitInstructionsFont;

  private OrthographicCamera camera;
  private Viewport viewport;

  private SpriteBatch batch;

  private Mode modeReplacement;

  private int score;
  private boolean isNewHighScore;

  private HighScoreFileHandler highScoreFileHandler;
  private PlayerNameTextInputListener playerNameTextInputListener;

  public GameOverMenuMode(float time) {
    // Text
    FreeTypeFontGenerator fontGenerator =
        new FreeTypeFontGenerator(Gdx.files.internal("pokemon_font.ttf"));

    FreeTypeFontGenerator.FreeTypeFontParameter titleFontParams =
        new FreeTypeFontGenerator.FreeTypeFontParameter();
    titleFontParams.size = Math.round(titleFontParams.size * SCALE_TITLE);
    titleFont = fontGenerator.generateFont(titleFontParams);

    FreeTypeFontGenerator.FreeTypeFontParameter exitInsturctionsFontParams =
        new FreeTypeFontGenerator.FreeTypeFontParameter();
    exitInsturctionsFontParams.size = Math.round(exitInsturctionsFontParams.size * SCALE_EXIT_INSTRUCTIONS);
    exitInstructionsFont = fontGenerator.generateFont(exitInsturctionsFontParams);

    fontGenerator.dispose(); // Don't dispose if doing incremental glyph generation.

    // Camera
    camera = new OrthographicCamera();
    camera.setToOrtho(false);
    viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);

    batch = new SpriteBatch();

    // Score
    score = Math.round(time);
    this.highScoreFileHandler = new HighScoreFileHandler();
    if (!this.highScoreFileHandler.existsHighScoreFile()) {
      this.highScoreFileHandler.initializeEmptyScores();
    }
    isNewHighScore = this.highScoreFileHandler.isNewHighScore(score);
    if (isNewHighScore) {
      // TODO Doing the 'TextInputListener' now because it's much less work and works w Android
      // and Desktop...but would really like an in-game interface for it
      this.playerNameTextInputListener = new PlayerNameTextInputListener();
      Gdx.input.getTextInput(
          this.playerNameTextInputListener,
          HIGH_SCORE_INPUT_TITLE,
          "",
          HIGH_SCORE_INPUT_HINT);
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
    // TODO Check that the dialog box has rendered
    if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
      // TODO null means it's not a new high score...should probably be explicit here
      if (playerNameTextInputListener != null) {
        String playerName = playerNameTextInputListener.getPlayerName();
        this.highScoreFileHandler.writeNewHighScore(new Score(score, playerName));
      }
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
        camera.viewportHeight * 7 / 10);

    // TODO Temp text to indicate new high score, in context of other high scores

    GlyphLayout exitInstructionsLayout = new GlyphLayout(exitInstructionsFont, TEXT_EXIT_INSTRUCTIONS);
    exitInstructionsFont.draw(
        batch,
        exitInstructionsLayout,
        (camera.viewportWidth / 2) - (exitInstructionsLayout.width / 2),
        camera.viewportHeight * 3 / 10);

    batch.end();
  }

  @Override
  public void pause() {

  }

  @Override
  public void resume() {

  }
}
