package com.nathanielmorihara.pushypenguins;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.nathanielmorihara.pushypenguins.mode.Mode;
import com.nathanielmorihara.pushypenguins.mode.menu.MainMenu;

public class Main extends ApplicationAdapter {

	private Mode mode;

	@Override
	public void create() {
		mode = new MainMenu();
	}

	// This method is called every time the mode screen is re-sized and the mode is not in the paused state. It is also called once just after the create() method.
	// The parameters are the new width and height the screen has been resized to in pixels.
	@Override
	public void resize (int width, int height) {
		mode.resize(width, height);
	}

	// Method called by the mode loop from the application every time rendering should be performed. Main logic updates are usually also performed in this method.
	@Override
	public void render() {
		mode.update();

		Mode changeMode = mode.changeMode();
		if (changeMode != null) {
			mode = changeMode;
			mode.resize(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
		}

		mode.draw();
	}

	// On Android this method is called when the Home button is pressed or an incoming call is received. On desktop this is called just before dispose() when exiting the application.
	// A good place to save the mode state.
	@Override
	public void pause () {
		mode.pause();
	}

	// This method is only called on Android, when the application resumes from a paused state.
	@Override
	public void resume () {
		mode.resume();
	}

	// Called when the application is destroyed. It is preceded by a call to pause().
	@Override
	public void dispose() {
		mode.dispose();
	}
}
