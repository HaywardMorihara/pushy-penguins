package com.nathanielmorihara.pushypenguins;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
import com.nathanielmorihara.pushypenguins.models.PenguinModel;
import com.nathanielmorihara.pushypenguins.models.PlayerModel;
import com.nathanielmorihara.pushypenguins.views.PenguinView;
import com.nathanielmorihara.pushypenguins.views.PlayerView;

public class Game extends ApplicationAdapter {

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

	public Game() {
		playerController = new PlayerController();
		penguinController = new PenguinController();

		playerView = new PlayerView();
		penguinView = new PenguinView();
	}

	@Override
	public void create() {
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

		// Game Start
		time = 0f;
		nextPenguinSpawn = 0f;
		playerStartX = mapWidth / 2;
		playerStartY = land.getRectangle().y + (PlayerView.FRAME_HEIGHT * unitScale);
		playerModel = new PlayerModel(world, unitScale, playerStartX, playerStartY);
		penguinModels = new ArrayList<PenguinModel>();

		// Instantiate a SpriteBatch for drawing and reset the elapsed animation
		spriteBatch = new SpriteBatch();
	}

	// This method is called every time the game screen is re-sized and the game is not in the paused state. It is also called once just after the create() method.
	// The parameters are the new width and height the screen has been resized to in pixels.
	@Override
	public void resize (int width, int height) {
		this.viewport.update(width, height);
	}

	// Method called by the game loop from the application every time rendering should be performed. Game logic updates are usually also performed in this method.
	@Override
	public void render() {
		update();
		draw();
	}

	// On Android this method is called when the Home button is pressed or an incoming call is received. On desktop this is called just before dispose() when exiting the application.
	// A good place to save the game state.
	@Override
	public void pause () {
	}

	// This method is only called on Android, when the application resumes from a paused state.
	@Override
	public void resume () {
	}

	// Called when the application is destroyed. It is preceded by a call to pause().
	@Override
	public void dispose() { // SpriteBatches and Textures must always be disposed
		PlayerView.dispose();
		PenguinView.dispose();
		debugRenderer.dispose();
	}

	private void update() {
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
			System.exit(0);
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
	}

	private void draw() {
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
