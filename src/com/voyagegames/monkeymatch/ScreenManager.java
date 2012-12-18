package com.voyagegames.monkeymatch;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.FPSLogger;
import com.voyagegames.monkeymatch.screens.LevelCallback;
import com.voyagegames.monkeymatch.screens.LevelScreen;

public class ScreenManager extends Game implements LevelCallback {

    private FPSLogger mFPSLogger;
    private LevelScreen mScreen;
    private int mLevelCount;
    private int mTotalScore;

	@Override
	public void create() {
        mFPSLogger = new FPSLogger();
        levelComplete(0);
	}
 
    @Override
    public void render() {
        mFPSLogger.log();
        getScreen().render(Gdx.graphics.getDeltaTime());
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
		mLevelCount++;
        
        try {
        	if (mScreen != null) {
        		mScreen.dispose();
        		mScreen = null;
        	}
        	
        	switch (mLevelCount) {
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
        	case 8:
        		doEndGame();
        		return;
        	}
        	
            setScreen(mScreen);
    		Gdx.input.setInputProcessor(mScreen);
        } catch (final Exception e) {
        	System.out.println(e.toString());
        	e.printStackTrace();
        	dispose();
        }
	}
	
	private void doEndGame() {
		// TODO
	}

}
