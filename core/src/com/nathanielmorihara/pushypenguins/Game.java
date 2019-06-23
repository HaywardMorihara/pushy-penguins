package com.nathanielmorihara.pushypenguins;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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

public class Game extends ApplicationAdapter {

	private float time;
	private SpriteBatch spriteBatch;

	World world;

	Player player;
	// Penguin penguin;
	List<Penguin> penguins;
	float nextPenguinSpawn;

	Box2DDebugRenderer debugRenderer;
	Camera camera;

	// Method called once when the application is created.
	@Override
	public void create() {
		// Load Assets
		Player.load();
		Penguin.load();

		// Physics
		Box2D.init();
		world = new World(new Vector2(0, 0), true);

		// There is a Box2DDebugRenderer
		float width = 500; // 20 meters
		//You can calculate on your chosen width or height to maintain aspect ratio
		float height = 500;
		camera = new OrthographicCamera(width, height);
		//Now the center of the camera is on 0,0 in the game world. It's often more desired and practical to have it's bottom left corner start out on 0,0
		//All we need to do is translate it by half it's width and height since that is the offset from it's center point (and that is currently set to 0,0.
		camera.translate(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
		camera.update();
		debugRenderer = new Box2DDebugRenderer();

		// Game Start
		time = 0f;
		nextPenguinSpawn = 0f;
		// Instantiate a SpriteBatch for drawing and reset the elapsed animation
		// time to 0
		spriteBatch = new SpriteBatch();
		player = new Player(world);
		penguins = new ArrayList<Penguin>();
		// penguin = new Penguin(world);
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

	public void update() {
		time += Gdx.graphics.getDeltaTime(); // Accumulate elapsed animation time

		nextPenguinSpawn++;
		if (nextPenguinSpawn > 10) {
			nextPenguinSpawn = 0;
			System.out.println("making new one");
			Penguin p = new Penguin(world);
			penguins.add(p);
		}

		player.update(time);

		Iterator itr = penguins.iterator();
		while (itr.hasNext())
		{

			Penguin p = (Penguin) itr.next();
			if (p.body.getPosition().y < 0) {
				world.destroyBody(p.body);
				itr.remove();
			} else {
				p.update(time);
			}
		}


		// Should this be based on the time or something?
		// TODO Where should this be? Is this a good calculation? Saw some fancier stuff here https://github.com/libgdx/libgdx/wiki/Box2d
		world.step(1/60f, 6, 2);
	}

	public void draw() {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Clear screen

		spriteBatch.begin();
		spriteBatch.setProjectionMatrix(camera.combined);
		// TODO I feel like this is bad to do...I'm not sure why though
		player.draw(spriteBatch, time);
		for (Penguin p : penguins) {
			p.draw(spriteBatch, time);
		}
		spriteBatch.end();

		//Create a copy of camera projection matrix
		Matrix4 debugMatrix;
		debugMatrix=new Matrix4(camera.combined);

		//Scale it by 100 as our box physics bodies are scaled down by 100
		debugMatrix.scale(1f, 100f, 1f);

		debugRenderer.render(world, camera.combined);
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
		Player.dispose();
		Penguin.dispose();
		debugRenderer.dispose();
	}
}
