package com.voyagegames.monkeymatch;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.files.FileHandle;

public class MainEntry implements IApplicationProvider {
	
	public LwjglApplication app;
	
	public static void main(final String[] args) {
		final MainEntry m = new MainEntry();
		final ScreenManager mgr = new ScreenManager(m, new StubLogger(), new StubDataProvider());
		final LwjglApplication app = new LwjglApplication(mgr, "Demo", 800, 600, true);
		
		mgr.setConfig(new ConfigData(true));
		m.app = app;
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

	@Override
	public void finish() {
		app.stop();
		app.exit();
	}

	@Override
	public Music openMusic(final String path) {
		return Gdx.audio.newMusic(new FileHandle("assets/" + path));
	}

	@Override
	public Sound openSound(final String path) {
		return Gdx.audio.newSound(new FileHandle("assets/" + path));
	}

	@Override
	public void launchWebsite() {
		System.out.println("launchWebsite()");
	}

	@Override
	public void launchFullVersion() {
		System.out.println("launchFullVersion()");
	}

	@Override
	public void launchReview() {
		System.out.println("launchReview()");
	}

}
