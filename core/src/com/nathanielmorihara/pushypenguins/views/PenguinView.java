/**
 * Copyright (C) 2019 Urban Compass, Inc.
 */
package com.nathanielmorihara.pushypenguins.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.nathanielmorihara.pushypenguins.models.PenguinModel;

/**
 * @author nathaniel.morihara
 */
public class PenguinView {

  // TODO Should these have a default?
  public static float FRAME_WIDTH, FRAME_HEIGHT;

  // Constant rows and columns of the sprite sheet
  private static final int FRAME_COLS = 3, FRAME_ROWS = 1;
  private static Texture walkSheet;
  private static TextureRegion[] walkDown;
  // Must declare frame type (TextureRegion)
  private static TextureRegion still;
  private static Animation<TextureRegion> walkDownAnimation;

  public static void load() {
    // Load the sprite sheet as a Texture
    walkSheet = new Texture(Gdx.files.internal("BeaverSpritesheet.png"));

    FRAME_WIDTH = walkSheet.getWidth() / FRAME_COLS;
    FRAME_HEIGHT = walkSheet.getHeight() / FRAME_ROWS;

    // Use the split utility method to create a 2D array of TextureRegions. This is
    // possible because this sprite sheet contains frames of equal size and they are
    // all aligned.
    TextureRegion[][] tmp = TextureRegion.split(walkSheet,
        walkSheet.getWidth() / FRAME_COLS,
        walkSheet.getHeight() / FRAME_ROWS);

    still = tmp[0][1];

    walkDown = new TextureRegion[4];
    walkDown[0] = tmp[0][1];
    walkDown[1] = tmp[0][0];
    walkDown[2] = tmp[0][1];
    walkDown[3] = tmp[0][2];
    // Initialize the Animation with the frame interval and array of frames
    walkDownAnimation = new Animation<TextureRegion>(0.1f, walkDown);
  }

  public static void dispose() { // SpriteBatches and Textures must always be disposed
    walkSheet.dispose();
  }

  public void draw(SpriteBatch spriteBatch, float time, PenguinModel penguinModel) {
    // Get current frame of animation for the current time
    TextureRegion currentFrame = still;
    currentFrame = walkDownAnimation.getKeyFrame(time, true);
    spriteBatch.draw(currentFrame,
        penguinModel.body.getPosition().x - penguinModel.width/2,
        penguinModel.body.getPosition().y - penguinModel.width/2,
        penguinModel.width,
        penguinModel.height);
  }
}
