package com.nathanielmorihara.pushypenguins;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.nathanielmorihara.pushypenguins.controllers.PenguinController;
import com.nathanielmorihara.pushypenguins.controllers.PlayerController;
import com.nathanielmorihara.pushypenguins.models.PenguinModel;
import com.nathanielmorihara.pushypenguins.models.PlayerModel;
import com.nathanielmorihara.pushypenguins.views.PenguinView;
import com.nathanielmorihara.pushypenguins.views.PlayerView;

public class Game extends ApplicationAdapter {

	// Example from https://www.gamefromscratch.com/post/2014/04/16/LibGDX-Tutorial-11-Tiled-Maps-Part-1-Simple-Orthogonal-Maps.aspx

	TiledMap tiledMap;
	Viewport viewport;
	OrthographicCamera camera;
	TiledMapRenderer tiledMapRenderer;

	float height;
	float width;
	float unitScale;

	@Override
	public void create () {

		tiledMap = new TmxMapLoader().load("BasicMap.tmx");

		MapProperties mapProperties = tiledMap.getProperties();
		Integer imageheight = (Integer) mapProperties.get("height");
		Integer imagewidth = (Integer) mapProperties.get("width");
		Integer tileheight = (Integer) mapProperties.get("tileheight");
		Integer tilewidth = (Integer) mapProperties.get("tilewidth");
		
		// TODO assert the tilewidth and tileheight are the same

		height = (float) imageheight;
		width = (float) imagewidth;
		unitScale = (float) 1 / tileheight;

		camera = new OrthographicCamera();
		camera.setToOrtho(false, imagewidth, imageheight);
		viewport = new FitViewport(imagewidth, imageheight, camera);

		tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap, unitScale);
	}

	@Override
	public void render () {
		// Clear the screen
		Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update();
		tiledMapRenderer.setView(camera);
		tiledMapRenderer.render();
	}

	@Override
	public void resize (int w, int h) {
		this.viewport.update(w, h);
	}
}
