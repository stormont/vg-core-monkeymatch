package com.voyagegames.monkeymatch;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.FPSLogger;
import com.voyagegames.monkeymatch.helpers.AudioManager;
import com.voyagegames.monkeymatch.helpers.TextureManager;
import com.voyagegames.monkeymatch.helpers.AudioManager.MusicSelection;
import com.voyagegames.monkeymatch.screens.EndGameScreen;
import com.voyagegames.monkeymatch.screens.LevelCallback;
import com.voyagegames.monkeymatch.screens.LevelScreen;

public class ScreenManager extends Game implements LevelCallback {
	
	private static final int MAX_LEVELS = 8;

	private final IApplicationProvider mApp;
	private final ILogger mLogger;
	private final IDataProvider mDataProvider;
    private final FPSLogger mFPSLogger;
    private final TextureManager mTextures;
    private final AudioManager mAudio;
    
    private Screen mScreen;
    private int mLevelCount;
    private int mTotalScore;
    
    public ScreenManager(final IApplicationProvider app, final ILogger logger, final IDataProvider dataProvider) {
    	super();
    	mApp = app;
    	mLogger = logger;
    	mDataProvider = dataProvider;
        mFPSLogger = new FPSLogger();
        mTextures = new TextureManager(49, 5);
        mAudio = new AudioManager(app);
    }

	@Override
	public void create() {
        mLevelCount = MAX_LEVELS - 1;
        mTextures.initialize();
        mAudio.initialize();
        levelComplete(0);
	}
 
    @Override
    public void render() {
		final Screen screen = getScreen();
		
		if (screen != null) {
			mFPSLogger.log();
			screen.render(Gdx.graphics.getDeltaTime());
		}
    }

	@Override
	public void dispose() {
		final Screen screen = getScreen();
		
		if (screen != null) {
			screen.dispose();
		}
		
    	mTextures.disposeAll();
    	mAudio.dispose();
	}

	@Override
	public void pause() {
		final Screen screen = getScreen();
		
		if (screen != null) {
			screen.pause();
		}
		
	}

	@Override
	public void resize(final int width, final int height) {
		final Screen screen = getScreen();
		
		if (screen != null) {
			screen.resize(width, height);
		}
		
	}

	@Override
	public void resume() {
		final Screen screen = getScreen();
		
		if (screen != null) {
			screen.resume();
		}
	}

	@Override
	public void levelComplete(final int score) {
		mTotalScore = score;
		mLevelCount = (mLevelCount + 1) % MAX_LEVELS;
		
		if (mDataProvider.personalBest() < mTotalScore) {
			mDataProvider.setPersonalBest(mTotalScore);
		}
        
        try {
    		Gdx.input.setInputProcessor(null);
    		Gdx.input.setCatchBackKey(false);
    		
        	if (mScreen != null) {
        		mScreen.pause();
        		mScreen.dispose();
        		mTextures.disposeDynamic();
        	}
        	
        	switch (mLevelCount) {
        	case 0:
        		if (mTotalScore > 0) {
        			mAudio.stopMusic();
        			mAudio.playMusic(MusicSelection.WIN);
        		}
        		
        		mScreen = new EndGameScreen(mTotalScore, mDataProvider.personalBest(), this, mTextures, mAudio);
        		break;
        	case 1:
            	mScreen = new LevelScreen(mApp.openAsset("levels/level01.xml"), mTotalScore, this, mTextures, mAudio);
        		break;
        	case 2:
            	mScreen = new LevelScreen(mApp.openAsset("levels/level02.xml"), mTotalScore, this, mTextures, mAudio);
        		break;
        	case 3:
            	mScreen = new LevelScreen(mApp.openAsset("levels/level03.xml"), mTotalScore, this, mTextures, mAudio);
        		break;
        	case 4:
            	mScreen = new LevelScreen(mApp.openAsset("levels/level04.xml"), mTotalScore, this, mTextures, mAudio);
        		break;
        	case 5:
            	mScreen = new LevelScreen(mApp.openAsset("levels/level05.xml"), mTotalScore, this, mTextures, mAudio);
        		break;
        	case 6:
            	mScreen = new LevelScreen(mApp.openAsset("levels/level06.xml"), mTotalScore, this, mTextures, mAudio);
        		break;
        	case 7:
            	mScreen = new LevelScreen(mApp.openAsset("levels/level07.xml"), mTotalScore, this, mTextures, mAudio);
        		break;
        	}
        	
            setScreen(mScreen);
    		Gdx.input.setInputProcessor((InputProcessor)mScreen);
    		Gdx.input.setCatchBackKey(true);
        } catch (final Exception e) {
        	mLogger.log(e);
        	dispose();
        	mApp.finish();
        }
	}

	@Override
	public void log(final String msg) {
		mLogger.log(msg);
	}
	
	@Override
	public void exitSignaled() {
		if (mLevelCount == 0) {
			dispose();
			mApp.finish();
			return;
		}
		
		mLevelCount = MAX_LEVELS - 1;
		levelComplete(mTotalScore);
	}

}
