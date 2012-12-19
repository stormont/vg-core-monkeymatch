package com.voyagegames.monkeymatch;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;

public class MainEntry {
	
	public static void main(final String[] args) {
		new LwjglApplication(new ScreenManager(new StubDataProvider()), "Demo", 800, 600, true);
	}

}
