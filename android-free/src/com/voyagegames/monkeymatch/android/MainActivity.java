package com.voyagegames.monkeymatch.android;

import java.io.IOException;
import java.io.InputStream;

import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.voyagegames.monkeymatch.IApplicationProvider;
import com.voyagegames.monkeymatch.ScreenManager;

public class MainActivity extends AndroidApplication implements IApplicationProvider {
	
	private ScreenManager mManager;

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

        final boolean useOpenGLES2 = true;
        
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        mManager = new ScreenManager(this, new Logger(), new DataProvider(this));
        initialize(mManager, useOpenGLES2);
	}

	@Override
	protected void onDestroy() {
		if (mManager != null) {
			mManager.dispose();
		}
		
		super.onDestroy();
	}

	@Override
	public InputStream openAsset(final String path) {
		try {
			return getAssets().open(path);
		} catch (final IOException e) {
			Log.e("MainActivity", e.toString(), e);
			return null;
		} 
	}

	@Override
	public Music openMusic(final String path) {
		return Gdx.audio.newMusic(Gdx.files.internal(path));
	}

	@Override
	public Sound openSound(final String path) {
		return Gdx.audio.newSound(Gdx.files.internal(path));
	}
	
}
