package com.nathanielmorihara.pushypenguins;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

public class Player {

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

//  private float x;
//  private float y;

  Body body;

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

  public Player(World world) {
//    x = 0;
//    y = 0;

    // First we create a body definition
    BodyDef bodyDef = new BodyDef();
    // We set our body to dynamic, for something like ground which doesn't move we would set it to StaticBody
    bodyDef.type = BodyDef.BodyType.DynamicBody;
    // Set our body's starting position in the world
    bodyDef.position.set(0, 0);

    // Create our body in the world using our body definition
    body = world.createBody(bodyDef);

    // Create a circle shape and set its radius to 6
    CircleShape circle = new CircleShape();
    circle.setRadius(10f);

    // Create a fixture definition to apply our shape to
    FixtureDef fixtureDef = new FixtureDef();
    fixtureDef.shape = circle;
    fixtureDef.density = 0.5f;
    fixtureDef.friction = 0.4f;
    fixtureDef.restitution = 0.6f; // Make it bounce a little bit

    // Create our fixture and attach it to the body
    Fixture fixture = body.createFixture(fixtureDef);

    // Remember to dispose of any shapes after you're done with them!
    // BodyDef and FixtureDef don't need disposing, but shapes do.
    circle.dispose();
  }

//  Vector2 vel = this.player.body.getLinearVelocity();
//  Vector2 pos = this.player.body.getPosition();
//
//  // apply left impulse, but only if max velocity is not reached yet
//if (Gdx.input.isKeyPressed(Keys.A) && vel.x > -MAX_VELOCITY) {
//    this.player.body.applyLinearImpulse(-0.80f, 0, pos.x, pos.y, true);
//  }
//
//  // apply right impulse, but only if max velocity is not reached yet
//if (Gdx.input.isKeyPressed(Keys.D) && vel.x < MAX_VELOCITY) {
//    this.player.body.applyLinearImpulse(0.80f, 0, pos.x, pos.y, true);
//  }

  public void update(float time) {
    Vector2 pos = body.getPosition();

    if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
      body.applyLinearImpulse(0, 20000f, pos.x, pos.y, true);
    }
    else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
      body.applyLinearImpulse(0, -20000f, pos.x, pos.y, true);
    }
    else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
      body.applyLinearImpulse(-20000f, 0, pos.x, pos.y, true);
    }
    else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
      body.applyLinearImpulse(20000f, 0, pos.x, pos.y, true);
    }
    else {
      // But...wouldn't this prevent player from getting pushed? Meh, it actually seems okay. May want to tweak it though
      /// Also I know its weird so the user can still move in x if start pressing y
      body.setLinearVelocity(0,0);
    }
  }

  public void draw(SpriteBatch spriteBatch, float time) {
    // Get current frame of animation for the current time
    TextureRegion currentFrame = still;
    if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
      currentFrame = walkUpAnimation.getKeyFrame(time, true);
    }
    if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
      currentFrame = walkDownAnimation.getKeyFrame(time, true);
    }
    if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
      currentFrame = walkLeftAnimation.getKeyFrame(time, true);
    }
    if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
      currentFrame = walkRightAnimation.getKeyFrame(time, true);
    }
    spriteBatch.draw(currentFrame, body.getPosition().x - 10f, body.getPosition().y -10f, 20f, 20f); // Draw current frame at (50, 50)
  }
}
