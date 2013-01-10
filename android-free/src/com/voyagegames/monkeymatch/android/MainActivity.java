package com.voyagegames.monkeymatch.android;

import java.io.IOException;
import java.io.InputStream;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.google.ads.Ad;
import com.google.ads.AdListener;
import com.google.ads.AdRequest;
import com.google.ads.AdRequest.ErrorCode;
import com.google.ads.AdSize;
import com.google.ads.AdView;
import com.voyagegames.monkeymatch.ConfigData;
import com.voyagegames.monkeymatch.IApplicationProvider;
import com.voyagegames.monkeymatch.ScreenManager;

public class MainActivity extends AndroidApplication implements IApplicationProvider {
	
	private static final String ADVERTISER_ID = "a150dbe97fe2db8";
	private static final int AD_VIEW_ID = 1;
	private static final int GAME_VIEW_ID = 2;
	
	private ScreenManager mManager;

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

        final boolean useOpenGLES2 = true;

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                        WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);

        mManager = new ScreenManager(this, new Logger(), new DataProvider(this));
        mManager.setConfig(new ConfigData(true));

        final RelativeLayout layout = new RelativeLayout(this);
        final View gameView = initializeForView(mManager, useOpenGLES2);
        final AdView adView = new AdView(this, AdSize.BANNER, ADVERTISER_ID);
        final RelativeLayout.LayoutParams adParams = new RelativeLayout.LayoutParams(
        		RelativeLayout.LayoutParams.WRAP_CONTENT, 
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        final RelativeLayout.LayoutParams surfaceParams = new RelativeLayout.LayoutParams(
        		RelativeLayout.LayoutParams.WRAP_CONTENT, 
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        
        adView.setId(AD_VIEW_ID);
        gameView.setId(GAME_VIEW_ID);
        adParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        adParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        surfaceParams.addRule(RelativeLayout.BELOW, adView.getId());

        adView.loadAd(new AdRequest());
        adView.setAdListener(new AdListener() {

			@Override
			public void onFailedToReceiveAd(final Ad ad, final ErrorCode errorCode) {
				Log.w("AdListener", "Failed to receive ad: " + errorCode);
			}

			@Override
			public void onDismissScreen(final Ad ad) {
				// no-op
			}

			@Override
			public void onLeaveApplication(final Ad ad) {
				// no-op
			}

			@Override
			public void onPresentScreen(final Ad ad) {
				// no-op
			}

			@Override
			public void onReceiveAd(final Ad ad) {
				// no-op
			}
        	
        });
        
        layout.addView(adView, adParams);
        layout.addView(gameView, surfaceParams);
        setContentView(layout);
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

	@Override
	public void launchWebsite() {
		final String url = "http://www.voyagegames.com";
		final Intent intent = new Intent(Intent.ACTION_VIEW);
		
		intent.setData(Uri.parse(url));
		startActivity(intent);
	}

	@Override
	public void launchFullVersion() {
		startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("market://details?id=com.voyagegames.monkeymatch")));
	}

	@Override
	public void launchReview() {
		startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("market://details?id=com.voyagegames.monkeymatch.free")));
	}
	
}
