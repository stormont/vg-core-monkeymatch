package com.voyagegames.monkeymatch.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.voyagegames.monkeymatch.ConfigData;
import com.voyagegames.monkeymatch.helpers.AudioManager;
import com.voyagegames.monkeymatch.helpers.AudioManager.MusicSelection;
import com.voyagegames.monkeymatch.helpers.BundledTexture;
import com.voyagegames.monkeymatch.helpers.TextureManager;

public class OptionsScreen implements Screen, InputProcessor {

	private static final float LOGO_SCALE = 0.75f;
	private static final float WEBSITE_OFFSET_Y = 140f;
	private static final float TIME_0 = 0f;
	private static final float TIME_1 = 0.25f;

	private final Stage          mStage;
	private final LevelCallback  mCallback;
	private final TextureManager mTextures;
	private final AudioManager   mAudio;
	private final ConfigData     mConfig;
    
    private Actor   mBackgroundActor;
	private Actor   mWebsiteActor;
	private Actor   mFullVersionActor;
	private boolean mIsTouched;
	private float   mScale;
	
	public OptionsScreen(
			final LevelCallback callback,
			final TextureManager textures,
			final AudioManager audio,
			final ConfigData config) {
		mStage = new Stage(0, 0, true);
		mCallback = callback;
		mTextures = textures;
		mAudio = audio;
		mConfig = config;
	}

	@Override
	public void dispose() {
		try {
			mStage.dispose();
		} catch (final IllegalArgumentException e) {
			// no-op; Android libgdx apparently handles this as managed objects 
		}
	}

	@Override
	public void render(final float delta) {
		synchronized (mCallback) {
			if (mAudio.enabled() && !mAudio.isMusicPlaying()) {
				mAudio.playMusic(MusicSelection.JUNGLE);
			}
			
	    	Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
	    	Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	        mStage.act(delta);
	        mStage.draw();
		}
	}

	@Override
	public void show() {
		mTextures.gridBackground = new BundledTexture("backgrounds/bkgd_endgame.png", 584, 440);
	}

	@Override
	public void resize(final int width, final int height) {
		mStage.setViewport(width, height, true);
        mStage.clear();
        mBackgroundActor = new Image(mTextures.gridBackground.region);
        mBackgroundActor.setPosition(
        		(width - (mTextures.gridBackground.getWidth()) / 2f),
        		(height - (mTextures.gridBackground.getHeight()) / 2f));
        mScale = width < height ?
        		((float)width) / ((float)mTextures.background.getWidth()) :
        		((float)height) / ((float)mTextures.background.getHeight());

        final float gridX = (width - (mBackgroundActor.getWidth() * mScale)) / 2f;
        final float gridY = (height - (mBackgroundActor.getHeight() * mScale)) / 2f;
        final Actor background = new Image(mTextures.background.region);
        
        background.setPosition(0f, 0f);
        background.setWidth(width);
        background.setHeight(height);
        setupActor(background, TIME_0, TIME_1, 1f);
        
        mBackgroundActor.setPosition(gridX, gridY);
        setupActor(mBackgroundActor, TIME_0, TIME_1, mScale);

        final Actor gridBorder = new Image(mTextures.border.region);
        final float borderX = ((float)(mTextures.border.getWidth() - mTextures.gridBackground.getWidth())) / 2f;
        final float borderY = ((float)(mTextures.border.getHeight() - mTextures.gridBackground.getHeight())) / 2f;
        
        gridBorder.setPosition(gridX - (borderX * mScale), gridY - (borderY * mScale));
        setupActor(gridBorder, TIME_0, TIME_1, mScale);
        
        final Actor title = new Image(mTextures.title.region);
        title.setPosition(
        		(width - (title.getWidth() * mScale)) / 2f,
        		(height - (title.getHeight() * mScale)));
        setupActor(title, TIME_0, TIME_1, mScale);

        final float logoScale = mScale * LOGO_SCALE;
        final Actor logoActor = new Image(mTextures.logo.region);
        logoActor.setPosition(0f, 0f);
        setupActor(logoActor, TIME_0, TIME_1, logoScale);

        mFullVersionActor = new Image(mTextures.optionsFullVersion.region);
        mWebsiteActor = new Image(mTextures.optionsWebsite.region);
        
        final Actor textActor = new Image(mTextures.optionsText.region);
        final Actor textAdsActor = new Image(mTextures.optionsTextAds.region);
        final float totalHeight = (mBackgroundActor.getHeight() * mScale) -
        		((textActor.getHeight() + textAdsActor.getHeight() + mFullVersionActor.getHeight()) * logoScale);
        final float offsetX = ((gridBorder.getWidth() * mScale) - (textActor.getWidth() * logoScale)) / 2f;
        
        textActor.setPosition(
        		mBackgroundActor.getX() + offsetX,
        		mBackgroundActor.getY() + (totalHeight / 2f) + ((textAdsActor.getHeight() + mFullVersionActor.getHeight()) * logoScale));
        setupActor(textActor, TIME_0, TIME_1, logoScale);
        
        mWebsiteActor.setPosition(
        		textActor.getX(),
        		textActor.getY() + ((textActor.getHeight() - WEBSITE_OFFSET_Y - mWebsiteActor.getHeight()) * logoScale));
        setupActor(mWebsiteActor, TIME_0, TIME_1, logoScale);
        mWebsiteActor.setTouchable(Touchable.enabled);
        
        if (mConfig.showFullVersion) {
            textAdsActor.setPosition(
            		mBackgroundActor.getX() + offsetX,
            		mBackgroundActor.getY() + (totalHeight / 2f) + (mFullVersionActor.getHeight() * logoScale));
            setupActor(textAdsActor, TIME_0, TIME_1, logoScale);
            
	        mFullVersionActor.setPosition(
	        		textActor.getX(),
	        		textActor.getY() - ((textAdsActor.getHeight() + mFullVersionActor.getHeight()) * logoScale));
	        setupActor(mFullVersionActor, TIME_0, TIME_1, logoScale);
	        mFullVersionActor.setTouchable(Touchable.enabled);
        }
	}
	
	@Override
	public boolean touchDown(final int x, final int y, final int pointer, final int button) {
		final Actor a = mStage.hit(x, mStage.getHeight() - y, true);
		
		if (a != mWebsiteActor && a != mFullVersionActor) {
			return false;
		}

		mIsTouched = true;
		return true;
	}

	@Override
	public boolean touchUp(final int x, final int y, final int pointer, final int button) {
		if (!mIsTouched) {
			return false;
		}

		final Actor a = mStage.hit(x, mStage.getHeight() - y, true);
		
		if (a == mWebsiteActor) {
			mCallback.launchWebsite();
		} else if (a == mFullVersionActor) {
			mCallback.launchFullVersion();
		}

		mIsTouched = false;
		return true;
	}

	@Override
	public boolean keyDown(final int keyCode) {
        if (keyCode == Keys.BACK) {
    		synchronized (mCallback) {
    			hide();
    		}
    		
        	mCallback.exitSignaled();
         }
        
		return false;
	}
	
	private void setupActor(final Actor actor, final float delay, final float fadeIn, final float scale) {
		actor.getColor().a = 0f;
		actor.setTouchable(Touchable.disabled);
		actor.addAction(Actions.sequence(
        		Actions.delay(delay),
        		Actions.fadeIn(fadeIn)
        	));
		actor.setScale(scale);
        mStage.addActor(actor);
	}
	
	//
	// Just a bunch of unused required implementation stubs below...
	//

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean keyTyped(final char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(final int keyCode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(final int x, final int y) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(final int amount) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(final int x, final int y, final int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

}
