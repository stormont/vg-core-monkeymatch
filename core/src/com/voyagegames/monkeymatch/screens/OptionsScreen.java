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
import com.voyagegames.monkeymatch.helpers.AudioManager;
import com.voyagegames.monkeymatch.helpers.AudioManager.MusicSelection;
import com.voyagegames.monkeymatch.helpers.BundledTexture;
import com.voyagegames.monkeymatch.helpers.TextureManager;

public class OptionsScreen implements Screen, InputProcessor {

	private static final float SCALE_SIZE = 1.2f;
	private static final float LOGO_SCALE = 0.75f;
	private static final float BORDER_WIDTH = 10f;
	private static final float TIME_0 = 0f;
	private static final float TIME_1 = 0.25f;
	private static final float TIME_3 = 0.75f;
	private static final float TIME_4 = 1f;

	private final Stage          mStage;
	private final LevelCallback  mCallback;
	private final TextureManager mTextures;
	private final AudioManager   mAudio;
    
    private Actor mBackgroundActor;
	private Actor mButtonActor;
	private boolean mIsTouched;
	
	public OptionsScreen(
			final LevelCallback callback,
			final TextureManager textures,
			final AudioManager audio) {
		mStage = new Stage(0, 0, true);
		mCallback = callback;
		mTextures = textures;
		mAudio = audio;
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
        
        final float scale = width < height ?
        		((float)width) / ((float)mTextures.background.getWidth()) :
        		((float)height) / ((float)mTextures.background.getHeight());

        final float gridX = (width - (mBackgroundActor.getWidth() * scale)) / 2f;
        final float gridY = (height - (mBackgroundActor.getHeight() * scale)) / 2f;
        final Actor background = new Image(mTextures.background.region);
        
        background.setPosition(0f, 0f);
        background.setWidth(width);
        background.setHeight(height);
        setupActor(background, TIME_0, TIME_1, 1f);
        
        mBackgroundActor.setPosition(gridX, gridY);
        setupActor(mBackgroundActor, TIME_0, TIME_1, scale);

        final Actor gridBorder = new Image(mTextures.border.region);
        final float borderX = ((float)(mTextures.border.getWidth() - mTextures.gridBackground.getWidth())) / 2f;
        final float borderY = ((float)(mTextures.border.getHeight() - mTextures.gridBackground.getHeight())) / 2f;
        
        gridBorder.setPosition(gridX - (borderX * scale), gridY - (borderY * scale));
        setupActor(gridBorder, TIME_0, TIME_1, scale);
        
        final Actor title = new Image(mTextures.title.region);
        title.setPosition(
        		(width - (title.getWidth() * scale)) / 2f,
        		(height - (title.getHeight() * scale)));
        setupActor(title, TIME_0, TIME_1, scale);

        final float logoScale = scale * LOGO_SCALE;
        final Actor logoActor = new Image(mTextures.logo.region);
        logoActor.setPosition(0f, 0f);
        setupActor(logoActor, TIME_0, TIME_1, logoScale);
        
        final Actor monkeyActor = new Image(mTextures.start.region);
        monkeyActor.setPosition(
        		gridBorder.getX() + BORDER_WIDTH,
        		gridBorder.getY() - BORDER_WIDTH + gridBorder.getHeight() - (monkeyActor.getHeight() * logoScale));
        setupActor(monkeyActor, TIME_0, TIME_1, logoScale);
        
        final Actor voyageGamesActor = new Image(mTextures.voyageGames.region);
        voyageGamesActor.setPosition(
        		monkeyActor.getX() + (monkeyActor.getWidth() * logoScale),
        		monkeyActor.getY() + (voyageGamesActor.getHeight() * logoScale / 4f));
        setupActor(voyageGamesActor, TIME_0, TIME_1, logoScale);
        
        final Actor musicActor = new Image(mTextures.music.region);
        musicActor.setPosition(
        		gridBorder.getX() + BORDER_WIDTH,
        		monkeyActor.getY() - (musicActor.getHeight() * logoScale));
        setupActor(musicActor, TIME_0, TIME_1, logoScale);
        
        final Actor soundJayActor = new Image(mTextures.soundJay.region);
        soundJayActor.setPosition(
        		musicActor.getX() + (musicActor.getWidth() * logoScale),
        		musicActor.getY() + (soundJayActor.getHeight() * logoScale / 4f));
        setupActor(soundJayActor, TIME_0, TIME_1, logoScale);
        
        mButtonActor = new Image(mTextures.start.region);
        mButtonActor.setPosition((width - mButtonActor.getWidth() * scale) / 2f, gridBorder.getY());
        mButtonActor.setOrigin(mButtonActor.getWidth() / 2f, mButtonActor.getHeight() / 2f);
        setupActor(mButtonActor, TIME_4, TIME_1, scale);
        mButtonActor.addAction(Actions.touchable(Touchable.enabled));
        mButtonActor.addAction(Actions.scaleTo(SCALE_SIZE, SCALE_SIZE));
        addStartButtonActions();
        
	}
	
	@Override
	public boolean touchDown(final int x, final int y, final int pointer, final int button) {
		final Actor a = mStage.hit(x, mStage.getHeight() - y, true);
		
		if (a != mButtonActor) {
			return false;
		}

		mIsTouched = true;
		mButtonActor.clearActions();
		return true;
	}

	@Override
	public boolean touchUp(final int x, final int y, final int pointer, final int button) {
		if (!mIsTouched) {
			return false;
		}

		final Actor a = mStage.hit(x, mStage.getHeight() - y, true);
		
		if (a == mButtonActor) {
			mCallback.levelComplete(0);
			return true;
		}

		mIsTouched = false;
		mButtonActor.clearActions();
		addStartButtonActions();
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
	
	private void addStartButtonActions() {
        mButtonActor.addAction(Actions.forever(Actions.sequence(
        		Actions.scaleTo(1f, 1f, TIME_3),
        		Actions.scaleTo(SCALE_SIZE, SCALE_SIZE, TIME_3)
        	)));
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
