/**
 * Copyright (C) 2019 Urban Compass, Inc.
 */
package com.nathanielmorihara.pushypenguins.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * @author nathaniel.morihara
 */
public class PenguinView {

  // Constant rows and columns of the sprite sheet
  private static final int FRAME_COLS = 3, FRAME_ROWS = 3;
  private static Texture walkSheet;
  private static TextureRegion[] walkDown;
  private static TextureRegion[] walkUp;
  private static TextureRegion[] walkLeft;
  private static TextureRegion[] walkRight;
  // Must declare frame type (TextureRegion)
  private static TextureRegion still;
  private static Animation<TextureRegion> walkDownAnimation;
  private static Animation<TextureRegion> walkUpAnimation;
  private static Animation<TextureRegion> walkLeftAnimation;
  private static Animation<TextureRegion> walkRightAnimation;



  public static void load() {
    // Load the sprite sheet as a Texture
    walkSheet = new Texture(Gdx.files.internal("Trainer.png"));

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

    walkUp = new TextureRegion[4];
    walkUp[0] = tmp[1][1];
    walkUp[1] = tmp[1][0];
    walkUp[2] = tmp[1][1];
    walkUp[3] = tmp[1][2];
    walkUpAnimation = new Animation<TextureRegion>(0.1f, walkUp);

    walkLeft = new TextureRegion[4];
    walkLeft[0] = tmp[2][1];
    walkLeft[1] = tmp[2][0];
    walkLeft[2] = tmp[2][1];
    walkLeft[3] = tmp[2][2];
    walkLeftAnimation = new Animation<TextureRegion>(0.1f, walkLeft);

    walkRight = new TextureRegion[4];
    int tileWidth = walkSheet.getWidth() / FRAME_COLS;
    int tileHeight = walkSheet.getHeight() / FRAME_ROWS;
    walkRight[0] = new TextureRegion(walkSheet, tileWidth * 1, tileHeight * 2, tileWidth, tileHeight);
    walkRight[1] = new TextureRegion(walkSheet, tileWidth * 2, tileHeight * 2, tileWidth, tileHeight);
    walkRight[2] = new TextureRegion(walkSheet, tileWidth * 1, tileHeight* 2, tileWidth, tileHeight);
    walkRight[3] = new TextureRegion(walkSheet, tileWidth * 0, tileHeight* 2, tileWidth, tileHeight);
    // Needed to make copies for flipping
    walkRight[0].flip(true, false);
    walkRight[1].flip(true, false);
    walkRight[2].flip(true, false);
    walkRight[3].flip(true, false);
    walkRightAnimation = new Animation<TextureRegion>(0.1f, walkRight);
  }

  public static void dispose() { // SpriteBatches and Textures must always be disposed
    walkSheet.dispose();
  }
}
