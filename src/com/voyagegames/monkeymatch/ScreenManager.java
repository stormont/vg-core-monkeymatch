package com.voyagegames.monkeymatch;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.FPSLogger;
import com.voyagegames.monkeymatch.screens.EndGameScreen;
import com.voyagegames.monkeymatch.screens.LevelCallback;
import com.voyagegames.monkeymatch.screens.LevelScreen;

public class ScreenManager extends Game implements LevelCallback {
	
	private static final int MAX_LEVELS = 8;

	private final IDataProvider mDataProvider;
    private final FPSLogger mFPSLogger;
    
    private Screen mScreen;
    private int mLevelCount;
    private int mTotalScore;
    
    public ScreenManager(final IDataProvider dataProvider) {
    	super();
    	mDataProvider = dataProvider;
        mFPSLogger = new FPSLogger();
    }

	@Override
	public void create() {
        mLevelCount = MAX_LEVELS - 1;
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
    		
        	if (mScreen != null) {
        		mScreen.pause();
        		mScreen.dispose();
        	}
        	
        	switch (mLevelCount) {
        	case 0:
        		mScreen = new EndGameScreen(this, mTotalScore, mDataProvider.personalBest());
        		break;
        	case 1:
            	mScreen = new LevelScreen("assets/levels/level01.xml", mTotalScore, this);
        		break;
        	case 2:
            	mScreen = new LevelScreen("assets/levels/level02.xml", mTotalScore, this);
        		break;
        	case 3:
            	mScreen = new LevelScreen("assets/levels/level03.xml", mTotalScore, this);
        		break;
        	case 4:
            	mScreen = new LevelScreen("assets/levels/level04.xml", mTotalScore, this);
        		break;
        	case 5:
            	mScreen = new LevelScreen("assets/levels/level05.xml", mTotalScore, this);
        		break;
        	case 6:
            	mScreen = new LevelScreen("assets/levels/level06.xml", mTotalScore, this);
        		break;
        	case 7:
            	mScreen = new LevelScreen("assets/levels/level07.xml", mTotalScore, this);
        		break;
        	}
        	
            setScreen(mScreen);
    		Gdx.input.setInputProcessor((InputProcessor)mScreen);
        } catch (final Exception e) {
        	System.out.println(e.toString());
        	e.printStackTrace();
        	dispose();
        }
	}

}
