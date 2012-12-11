package com.voyagegames.monkeymatch;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.FPSLogger;
import com.voyagegames.monkeymatch.screens.Level01;
import com.voyagegames.monkeymatch.screens.LevelScreen;

public class ScreenManager extends Game {

    private FPSLogger mFPSLogger;
    private LevelScreen mScreen;

	@Override
	public void create() {
        mFPSLogger = new FPSLogger();
        
        try {
        	mScreen = new Level01();
            setScreen(mScreen);
        } catch (final Exception e) {
        	System.out.println(e.toString());
        	e.printStackTrace();
        	dispose();
        }
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

}
