package com.voyagegames.monkeymatch.android;

import android.os.Bundle;
import android.view.WindowManager;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.voyagegames.monkeymatch.ScreenManager;
import com.voyagegames.monkeymatch.StubDataProvider;

public class MainActivity extends AndroidApplication {
	
	private ScreenManager mManager;

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

        final boolean useOpenGLES2 = true;
        
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        mManager = new ScreenManager(new StubDataProvider());
        initialize(mManager, useOpenGLES2);
	}

	@Override
	protected void onDestroy() {
		if (mManager != null) {
			mManager.dispose();
		}
		
		super.onDestroy();
	}
	
}
