package com.gmail.jpk.stu.starcluster.desktop;

import java.awt.Dimension;
import java.awt.Toolkit;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.gmail.jpk.stu.starcluster.Starcluster;

public class DesktopLauncher {
	public static void main (String[] arg) {
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Starcluster v0.0.1";
		config.height = 480;
		config.width = 800;
		config.resizable = false;
		new LwjglApplication(new Starcluster(config.height, config.width), config);
	}
}
