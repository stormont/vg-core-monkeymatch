package com.voyagegames.monkeymatch;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;

public class MainEntry implements IApplicationProvider {
	
	public static void main(final String[] args) {
		new LwjglApplication(new ScreenManager(new MainEntry(), new StubLogger(), new StubDataProvider()), "Demo", 800, 600, true);
	}

	@Override
	public void finish() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public InputStream openAsset(final String path) {
		try {
			return new FileInputStream(new File("assets/" + path));
		} catch (final FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}

}
