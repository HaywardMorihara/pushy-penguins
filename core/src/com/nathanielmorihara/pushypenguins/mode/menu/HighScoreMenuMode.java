/**
 * Copyright (C) 2020 Urban Compass, Inc.
 */
package com.nathanielmorihara.pushypenguins.mode.menu;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.nathanielmorihara.pushypenguins.json.Score;
import com.nathanielmorihara.pushypenguins.mode.Mode;

/**
 * @author nathaniel.morihara
 */
public class HighScoreMenuMode implements Mode {

  private final String TEXT_TITLE = "High Scores:";
  private final float SCALE_TITLE = 2f;
  private final float SCALE_SCORES = 1f;

  private BitmapFont titleFont;
  private BitmapFont scoreFont;

  private OrthographicCamera camera;
  private Viewport viewport;

  private SpriteBatch batch;

  private Mode modeReplacement;

  public HighScoreMenuMode() {
    // Text
    FreeTypeFontGenerator fontGenerator =
        new FreeTypeFontGenerator(Gdx.files.internal("pokemon_font.ttf"));

    FreeTypeFontGenerator.FreeTypeFontParameter titleFontParams =
        new FreeTypeFontGenerator.FreeTypeFontParameter();
    titleFontParams.size = Math.round(titleFontParams.size * SCALE_TITLE);
    titleFont = fontGenerator.generateFont(titleFontParams);

    FreeTypeFontGenerator.FreeTypeFontParameter scoreFontParams =
        new FreeTypeFontGenerator.FreeTypeFontParameter();
    scoreFontParams.size = Math.round(scoreFontParams.size * SCALE_SCORES);
    scoreFont = fontGenerator.generateFont(scoreFontParams);

    fontGenerator.dispose(); // Don't dispose if doing incremental glyph generation.

    // Camera
    camera = new OrthographicCamera();
    camera.setToOrtho(false);
    viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);

    batch = new SpriteBatch();

    // TODO Make high score file if it does not exist
  }

  @Override
  public void dispose() {
    batch.dispose();
    titleFont.dispose();
    scoreFont.dispose();
  }

  @Override
  public void resize(int width, int height) {
    this.viewport.update(width, height);
  }

  @Override
  public void update() {
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
        camera.viewportHeight * 9 / 10);

    Json json = new Json();
    List<JsonValue> list = json.fromJson(List.class, Gdx.files.internal("data/high_scores.json"));
    int scoreNumber = 1;
    for (JsonValue v : list) {
      Score score = json.readValue(Score.class, v);
      GlyphLayout scoreLayout = new GlyphLayout(
          scoreFont,
          String.format("%s: %s (%s)", scoreNumber, score.getScore(), score.getOwner())); // TODO Why is this unhappy?
      scoreFont.draw(
          batch,
          scoreLayout,
          (camera.viewportWidth / 2) - (scoreLayout.width / 2),
          camera.viewportHeight  * (8f / 10f) * ((11 - scoreNumber) / 10f));
      scoreNumber += 1;
    }

    batch.end();
  }

  @Override
  public void pause() {

  }

  @Override
  public void resume() {

  }
}
