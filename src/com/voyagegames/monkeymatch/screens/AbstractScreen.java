package com.voyagegames.monkeymatch.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;

public abstract class AbstractScreen implements Screen {
	
	protected final SpriteBatch mBatch;
	protected final Stage       mStage;
	
	public AbstractScreen() {
		mBatch = new SpriteBatch();
		mStage = new Stage(0, 0, true);
	}

	@Override
	public void dispose() {
		mBatch.dispose();
		mStage.dispose();
	}

	@Override
	public void resize(final int width, final int height) {
		mStage.setViewport(width, height, true);
	}
	
	public void renderStage(final float delta) {
        mStage.act(delta);
        mStage.draw();
	}

}
