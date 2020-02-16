/**
 * Copyright (C) 2019 Urban Compass, Inc.
 */
package com.nathanielmorihara.pushypenguins.mode.game;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.nathanielmorihara.pushypenguins.controllers.PenguinController;
import com.nathanielmorihara.pushypenguins.controllers.PlayerController;
import com.nathanielmorihara.pushypenguins.mode.Mode;
import com.nathanielmorihara.pushypenguins.mode.menu.MainMenu;
import com.nathanielmorihara.pushypenguins.mode.menu.PauseMenu;
import com.nathanielmorihara.pushypenguins.models.PenguinModel;
import com.nathanielmorihara.pushypenguins.models.PlayerModel;
import com.nathanielmorihara.pushypenguins.views.PenguinView;
import com.nathanielmorihara.pushypenguins.views.PlayerView;

/**
 * @author nathaniel.morihara
 */
public class Game implements Mode {

  // World
  private World world; // This just holds everything and manages physics. Nothing to do with the view
  private float time;
  private boolean isDebugMode = true;

  // Map
  private TiledMap tiledMap;
  private float unitScale; // 1 over tile height/width. When passed in, makes coordinates tile-based instead of pixel-based
  private float mapWidth;
  private float mapHeight;
  private RectangleMapObject land;

  // Camera
  private OrthographicCamera camera; // Controls the area of the map that you can see (?)
  private Viewport viewport; // Manages a Camera and determines how world coordinates are mapped to and from the screen
  private TiledMapRenderer tiledMapRenderer; // Does stuff to control the Camera...(how different from Viewport?)
  private Box2DDebugRenderer debugRenderer;

  // Text
  private BitmapFont timerFont;
  private final float TIMER_SCALE = 1f;
  private final float TIMER_POS_PER_WIDTH = 0.05f;
  private final float TIMER_POS_PER_HEIGHT = 0.05f;

  // Model
  private PlayerModel playerModel;
  private float playerStartX;
  private float playerStartY;

  private List<PenguinModel> penguinModels;
  private float nextPenguinSpawn;

  // Controller
  private PlayerController playerController;
  private PenguinController penguinController;

  // View
  private SpriteBatch spriteBatch;
  private PlayerView playerView;
  private PenguinView penguinView;

  // Mode
  private Mode modeReplacement;

  public Game() {
    playerController = new PlayerController();
    penguinController = new PenguinController();

    playerView = new PlayerView();
    penguinView = new PenguinView();

    // Load Assets
    PlayerView.load();
    PenguinView.load();

    // Physics
    Box2D.init();
    world = new World(new Vector2(0, 0), true);

    // Map
    tiledMap = new TmxMapLoader().load("BasicMap.tmx");
    MapProperties mapProperties = tiledMap.getProperties();
    this.mapHeight = (float) (Integer) mapProperties.get("height");
    this.mapWidth = (float) (Integer) mapProperties.get("width");
    Integer tileHeight = (Integer) mapProperties.get("tileheight");
    Integer tileWidth = (Integer) mapProperties.get("tilewidth");
    if (tileHeight != tileWidth) {
      throw new RuntimeException("Tile width and height should be the same");
    }
    unitScale = (float) 1 / tileHeight;

    // Get Land details
    // TODO Give this a name in tiled?
    int landIndex = 0;
    land = ((RectangleMapObject) tiledMap.getLayers().get("Land").getObjects().get(landIndex));
    // TODO better way of doing this?
    land.getRectangle().setX(land.getRectangle().getX() * unitScale);
    land.getRectangle().setY(land.getRectangle().getY() * unitScale);
    land.getRectangle().setWidth(land.getRectangle().getWidth() * unitScale);
    land.getRectangle().setHeight(land.getRectangle().getHeight() * unitScale);

    // Camera
    camera = new OrthographicCamera();
    camera.setToOrtho(false, mapWidth, mapHeight);
    viewport = new FitViewport(mapWidth, mapHeight, camera);
    tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap, unitScale);
    debugRenderer = new Box2DDebugRenderer();

    // Text
    FreeTypeFontGenerator gen =
        new FreeTypeFontGenerator(Gdx.files.internal("pokemon_font.ttf"));
    FreeTypeFontGenerator.FreeTypeFontParameter timerFontParams =
        new FreeTypeFontGenerator.FreeTypeFontParameter();
    timerFont = gen.generateFont(timerFontParams);
    timerFont.setUseIntegerPositions(false); // https://issue.life/questions/35945910
    timerFont.getData().setScale(unitScale * TIMER_SCALE);
    gen.dispose(); // Don't dispose if doing incremental glyph generation.

    // Main Start
    time = 0f;
    nextPenguinSpawn = 0f;
    playerStartX = mapWidth / 2;
    playerStartY = land.getRectangle().y + (PlayerView.FRAME_HEIGHT * unitScale);
    playerModel = new PlayerModel(world, unitScale, playerStartX, playerStartY);
    penguinModels = new ArrayList<PenguinModel>();

    // Instantiate a SpriteBatch for drawing and reset the elapsed animation
    spriteBatch = new SpriteBatch();
  }

  // TODO How to make sure gets called?
  @Override
  public void dispose() { // SpriteBatches and Textures must always be disposed
    world.dispose();

    tiledMap.dispose();

    debugRenderer.dispose();

    spriteBatch.dispose();
    PlayerView.dispose();
    PenguinView.dispose();
  }

  @Override
  public void resize (int width, int height) {
    this.viewport.update(width, height);
  }

  @Override
  public void pause () {
  }

  @Override
  public void resume () {
  }

  @Override
  public void update() {
    // TODO Put back and Abstract if do fullscreen again
    //		if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
    //			// TODO Why is this unhappy?
    //			System.exit(0);
    //		}

    time += Gdx.graphics.getDeltaTime(); // Accumulate elapsed animation time

    // TODO Move this meta-player logic?
    if (!isBodyOnLand(playerModel.body, land)) {
      // TODO Game Over Screen
      // TODO Can't destroy now or else Box2D unhappy that no bodies...
      // world.destroyBody(playerModel.body);
      // System.exit(0);
      modeReplacement = new MainMenu();
      this.dispose();
      return;
    }
    playerController.update(playerModel);

    // TODO Penguin Logic; should be refactored and moved out of here. A general penguin controller?
    nextPenguinSpawn++;
    // TODO Don't hardcode here
    if (nextPenguinSpawn > 7) {
      nextPenguinSpawn = 0;
      Random random = new Random();
      // TODO Calculate the land values elsewhere to simplify things
      // TODO Account for player width
      // TODO Make a "Land" class? Meh, I don't think it has anything except location right now. Unless wanted some collision detection or something
      float landLeftX = land.getRectangle().getX();
      float landWidth = land.getRectangle().getWidth();
      float px = landLeftX + (random.nextFloat() * landWidth);
      float py = mapHeight;
      PenguinModel p = new PenguinModel(world, unitScale, px, py);
      penguinModels.add(p);
    }
    Iterator itr = penguinModels.iterator();
    while (itr.hasNext()) {
      PenguinModel penguinModel = (PenguinModel) itr.next();
      if (!isBodyOnLand(penguinModel.body, land)) {
        world.destroyBody(penguinModel.body);
        itr.remove();
      } else {
        penguinController.update(penguinModel);
      }
    }

    // TODO Where should this be? Is this a good calculation? Saw some fancier stuff here https://github.com/libgdx/libgdx/wiki/Box2d
    world.step(1/60f, 6, 2);

    if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
      modeReplacement = new PauseMenu(this);
    }
  }

  @Override
  public Mode changeMode() {
    if (modeReplacement != null) {
      Mode retVal = modeReplacement;
      modeReplacement = null;
      return retVal;
    }
    return null;
  }

  @Override
  public void draw() {
    Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Clear screen

    camera.update();
    tiledMapRenderer.setView(camera);
    tiledMapRenderer.render();

    spriteBatch.begin();
    spriteBatch.setProjectionMatrix(camera.combined);

    playerView.draw(spriteBatch, time, playerModel);

    for (PenguinModel p : penguinModels) {
      penguinView.draw(spriteBatch, time, p);
    }

    timerFont.draw(
      spriteBatch,
        "Time:  " + String.valueOf((int) time),
      mapWidth * TIMER_POS_PER_WIDTH,
      mapHeight * TIMER_POS_PER_HEIGHT
    );

    spriteBatch.end();

    if (isDebugMode) {
      //Create a copy of camera projection matrix
      Matrix4 debugMatrix;
      debugMatrix=new Matrix4(camera.combined);
      //Scale it by 100 as our box physics bodies are scaled down by 100
      debugMatrix.scale(1f, 100f, 1f);
      debugRenderer.render(world, camera.combined);
    }
  }

  private boolean isBodyOnLand(Body b, RectangleMapObject land) {
    boolean isRightOfLeftEdge = b.getPosition().x > land.getRectangle().getX();
    boolean isLeftOfRightEdge = b.getPosition().x < (land.getRectangle().getX()
        + land.getRectangle().getWidth());
    boolean isAboveBottomEdge = b.getPosition().y > land.getRectangle().getY();
    return isRightOfLeftEdge && isLeftOfRightEdge && isAboveBottomEdge;
  }

}
