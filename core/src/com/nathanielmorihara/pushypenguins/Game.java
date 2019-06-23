package com.nathanielmorihara.pushypenguins;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.nathanielmorihara.pushypenguins.controllers.PenguinController;
import com.nathanielmorihara.pushypenguins.controllers.PlayerController;
import com.nathanielmorihara.pushypenguins.models.PenguinModel;
import com.nathanielmorihara.pushypenguins.models.PlayerModel;
import com.nathanielmorihara.pushypenguins.views.PenguinView;
import com.nathanielmorihara.pushypenguins.views.PlayerView;

public class Game extends ApplicationAdapter {

	private World world;

	private float time;

	private Box2DDebugRenderer debugRenderer;
	private Camera camera;
	private float cameraWidth = 500f;
	private float cameraHeight = 500f;

	private SpriteBatch spriteBatch;

	private PlayerModel playerModel;
	private float playerStartX = 50f;
	private float playerStartY = 50f;

	private List<PenguinModel> penguinModels;
	private float nextPenguinSpawn;

	private PlayerController playerController;
	private PenguinController penguinController;

	private PlayerView playerView;
	private PenguinView penguinView;

	public Game() {
		playerController = new PlayerController();
		penguinController = new PenguinController();

		playerView = new PlayerView();
		penguinView = new PenguinView();
	}

	// Method called once when the application is created.
	@Override
	public void create() {
		// Load Assets
		PlayerView.load();
		PenguinView.load();

		// Physics
		Box2D.init();
		world = new World(new Vector2(0, 0), true);

		// Camera
		camera = new OrthographicCamera(this.cameraWidth, this.cameraHeight);
		//Now the center of the camera is on 0,0 in the game world. It's often more desired and practical to have it's bottom left corner start out on 0,0
		//All we need to do is translate it by half it's width and height since that is the offset from it's center point (and that is currently set to 0,0.
		camera.translate(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
		camera.update();
		debugRenderer = new Box2DDebugRenderer();

		// Game Start
		time = 0f;
		nextPenguinSpawn = 0f;
		playerModel = new PlayerModel(world, playerStartX, playerStartY);
		penguinModels = new ArrayList<PenguinModel>();

		// Instantiate a SpriteBatch for drawing and reset the elapsed animation
		spriteBatch = new SpriteBatch();
	}

	// This method is called every time the game screen is re-sized and the game is not in the paused state. It is also called once just after the create() method.
	// The parameters are the new width and height the screen has been resized to in pixels.
	@Override
	public void resize (int width, int height) {
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
		time += Gdx.graphics.getDeltaTime(); // Accumulate elapsed animation time

		playerController.update(playerModel);

		nextPenguinSpawn++;
		if (nextPenguinSpawn > 10) {
			nextPenguinSpawn = 0;
			Random random = new Random();
			float px = random.nextFloat() * cameraWidth;
			float py = cameraHeight;
			PenguinModel p = new PenguinModel(world, px, py);
			penguinModels.add(p);
		}
		Iterator itr = penguinModels.iterator();
		while (itr.hasNext()) {
			PenguinModel penguinModel = (PenguinModel) itr.next();
			if (penguinModel.body.getPosition().y < 0) {
				world.destroyBody(penguinModel.body);
				itr.remove();
			} else {
				penguinController.update(penguinModel);
			}
		}

		// Should this be based on the time or something?
		// TODO Where should this be? Is this a good calculation? Saw some fancier stuff here https://github.com/libgdx/libgdx/wiki/Box2d
		world.step(1/60f, 6, 2);
	}

	private void draw() {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Clear screen

		spriteBatch.begin();
		spriteBatch.setProjectionMatrix(camera.combined);

		playerView.draw(spriteBatch, time, playerModel);

		for (PenguinModel p : penguinModels) {
			penguinView.draw(spriteBatch, time, p);
		}

		spriteBatch.end();

		//Create a copy of camera projection matrix
		Matrix4 debugMatrix;
		debugMatrix=new Matrix4(camera.combined);
		//Scale it by 100 as our box physics bodies are scaled down by 100
		debugMatrix.scale(1f, 100f, 1f);
		debugRenderer.render(world, camera.combined);
	}
}
