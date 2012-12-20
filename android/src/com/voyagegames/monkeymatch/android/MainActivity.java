package com.voyagegames.monkeymatch.android;

import java.io.IOException;
import java.io.InputStream;

import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.voyagegames.monkeymatch.IApplicationProvider;
import com.voyagegames.monkeymatch.ScreenManager;
import com.voyagegames.monkeymatch.StubDataProvider;

public class MainActivity extends AndroidApplication implements IApplicationProvider {
	
	private ScreenManager mManager;

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

        final boolean useOpenGLES2 = true;
        
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        mManager = new ScreenManager(this, new Logger(), new StubDataProvider());
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
	
}
