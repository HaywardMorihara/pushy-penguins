package com.nathanielmorihara.pushypenguins.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.nathanielmorihara.pushypenguins.Game;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		// TODO Figure out how to implement this, but so that it doesn't seem so hacky
		// (difficult to exit) and it freaks out when you try to debug
		// config.fullscreen = true;
		new LwjglApplication(new Game(), config);
	}
}
