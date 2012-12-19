package com.voyagegames.monkeymatch.screens;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.voyagegames.monkeymatch.helpers.StaticGridImage;

public class EndGameScreen implements Screen, InputProcessor {

	private static final float TROPHY_SCALE = 0.5f;
	private static final float ROTATE_ANGLE =30f;
	private static final float TIME_0 = 0f;
	private static final float TIME_1 = 0.25f;
	private static final float TIME_2 = 0.5f;
	private static final float TIME_3 = 0.75f;
	private static final float TIME_4 = 1f;

	private final Stage         mStage;
	private final LevelCallback mCallback;
	private final int           mScore;
	private final int           mPersonalBest;
    private final Texture[]     mDigits;
    
    private Texture mBackground;
    private Texture mBorder;
    private Texture mGridBackground;
    private Texture mMonkey;
    private Texture mBananas;
    private Texture mTrophy;
    
    private StaticGridImage mBackgroundActor;
	private Actor mButtonActor;
	
	public EndGameScreen(final LevelCallback callback, final int score, final int personalBest) {
		mStage = new Stage(0, 0, true);
		mCallback = callback;
		mScore = score;
		mPersonalBest = personalBest;
		mDigits = new Texture[10];
	}

	@Override
	public void dispose() {
		mStage.dispose();
		mBorder.dispose();
		mBackground.dispose();
		mGridBackground.dispose();
		
		for (final Texture t : mDigits) {
			t.dispose();
		}
	}

	@Override
	public void render(final float delta) {
		synchronized (mCallback) {
	    	Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
	    	Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	        mStage.act(delta);
	        mStage.draw();
		}
	}

	@Override
	public void show() {
        mBackground = new Texture("backgrounds/bkgd_tutorial.png");
        mBackground.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        
        mBorder = new Texture("backgrounds/bkgd_border.png");
        mBorder.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        
        mGridBackground = new Texture("backgrounds/bkgd_endgame.png");
        mGridBackground.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        
        mMonkey = new Texture("tokens/monkey.png");
        mMonkey.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        
        mBananas = new Texture("tokens/bananas.png");
        mBananas.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        
        mTrophy = new Texture("misc/trophy.png");
        mTrophy.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        
        for (int i = 0; i < mDigits.length; ++i) {
        	mDigits[i] = new Texture("misc/digits" + i + ".png");
        	mDigits[i].setFilter(TextureFilter.Linear, TextureFilter.Linear);
        }
	}

	@Override
	public void resize(final int width, final int height) {
		mStage.setViewport(width, height, true);
        mStage.clear();
        mBackgroundActor = new StaticGridImage(mGridBackground, width, height);
        
        final float scale = width < height ?
        		((float)width) / ((float)mBackground.getWidth()) :
        		((float)height) / ((float)mBackground.getHeight());

        final float gridX = (width - (mBackgroundActor.image.getWidth() * scale)) / 2f;
        final float gridY = (height - (mBackgroundActor.image.getHeight() * scale)) / 2f;
        final StaticGridImage background = new StaticGridImage(mBackground, width, height);
        
        background.image.setPosition(0f, 0f);
        background.image.setWidth(width);
        background.image.setHeight(height);
        setupActor(background.image, TIME_0, TIME_1, 1f);
        
        mBackgroundActor.image.setPosition(gridX, gridY);
        setupActor(mBackgroundActor.image, TIME_0, TIME_1, scale);

        final StaticGridImage gridBorder = new StaticGridImage(mBorder, width, height);
        final float borderX = ((float)(mBorder.getWidth() - mGridBackground.getWidth())) / 2f;
        final float borderY = ((float)(mBorder.getHeight() - mGridBackground.getHeight())) / 2f;
        
        gridBorder.image.setPosition(gridX - (borderX * scale), gridY - (borderY * scale));
        setupActor(gridBorder.image, TIME_0, TIME_1, scale);
        
        setPersonalBestScore(scale);
        setCurrentScore(scale);
        
        mButtonActor = new Image(new TextureRegion(mMonkey));
        
        final float buttonWidth = mButtonActor.getWidth() * scale;
        final float buttonHeight = mButtonActor.getHeight() * scale;
        
        mButtonActor.setPosition((width - buttonWidth) / 2f, gridBorder.image.getY());
        mButtonActor.setOrigin(buttonWidth / 2f, buttonHeight / 2f);
        setupActor(mButtonActor, TIME_4, TIME_1, scale);
        mButtonActor.addAction(Actions.rotateTo(ROTATE_ANGLE));
        mButtonActor.addAction(Actions.forever(Actions.sequence(
        		Actions.rotateTo(-ROTATE_ANGLE, TIME_3),
        		Actions.rotateTo(ROTATE_ANGLE, TIME_3)
        	)));
	}
	
	@Override
	public boolean touchDown(final int x, final int y, final int pointer, final int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(final int x, final int y, final int pointer, final int button) {
		// TODO Auto-generated method stub
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
	
	private void setPersonalBestScore(final float scale) {
		if (mPersonalBest <= 0) {
			return;
		}
		
        final String scoreStr = String.valueOf(mPersonalBest);
        final int length = scoreStr.length();
        
        final float trophyScale = scale * TROPHY_SCALE;
        final TextureRegion region = new TextureRegion(mTrophy);
        final Actor leftTrophy = new Image(region);
        final Actor rightTrophy = new Image(region);
        final Actor background = mBackgroundActor.image;
        final float backgroundX = background.getX();
        final float backgroundY = background.getY();
        final float backgroundHeight = background.getHeight() * scale;
        
        final List<Actor> digits = new ArrayList<Actor>();
        final float trophyWidth = (leftTrophy.getWidth() + rightTrophy.getWidth()) * trophyScale;
        final float trophyHeight = leftTrophy.getHeight() * trophyScale;
        float width = trophyWidth;
        
        for (int i = 0; i < length; ++i) {
        	final int v = Integer.parseInt(scoreStr.substring(i, i + 1));
        	final Actor a = new Image(new TextureRegion(mDigits[v]));

        	width += (a.getWidth() * trophyScale);
        	digits.add(a);
        }
        
        final float boxWidth = background.getWidth() * scale;
        final float offsetY = backgroundY + (backgroundHeight - trophyHeight);
        final float digitOffsetY = offsetY + (trophyHeight / 2f) - (digits.get(0).getHeight() * trophyScale / 2f);
        float offsetX = backgroundX + ((boxWidth - width) / 2f);
        
        leftTrophy.setPosition(offsetX, offsetY);
        setupActor(leftTrophy, TIME_1, TIME_1, trophyScale);
        offsetX += leftTrophy.getWidth() * trophyScale;
        
        for (final Actor a : digits) {
        	a.setPosition(offsetX, digitOffsetY);
            setupActor(a, TIME_1, TIME_1, trophyScale);
        	offsetX += (a.getWidth() * trophyScale);
        }
        
        rightTrophy.setPosition(offsetX, offsetY);
        setupActor(rightTrophy, TIME_1, TIME_1, trophyScale);
        offsetX += rightTrophy.getWidth() * trophyScale;
	}
	
	private void setCurrentScore(final float scale) {
		if (mScore <= 0) {
			return;
		}
		
        final String scoreStr = String.valueOf(mScore);
        final int length = scoreStr.length();
        
        final float trophyScale = scale * TROPHY_SCALE;
        final TextureRegion region = new TextureRegion(mBananas);
        final Actor bananas = new Image(region);
        final Actor background = mBackgroundActor.image;
        final float backgroundX = background.getX();
        final float backgroundY = background.getY();
        final float backgroundHeight = background.getHeight() * scale;
        
        final List<Actor> digits = new ArrayList<Actor>();
        final float bananasWidth = bananas.getWidth() * trophyScale;
        final float bananasHeight = bananas.getHeight() * trophyScale;
        float width = bananasWidth;
        
        for (int i = 0; i < length; ++i) {
        	final int v = Integer.parseInt(scoreStr.substring(i, i + 1));
        	final Actor a = new Image(new TextureRegion(mDigits[v]));

        	width += (a.getWidth() * trophyScale);
        	digits.add(a);
        }
        
        final float boxWidth = background.getWidth() * scale;
        final float offsetY = backgroundY + ((backgroundHeight - bananas.getHeight() * trophyScale) / 2f);
        final float digitOffsetY = offsetY + (bananasHeight / 2f) - (digits.get(0).getHeight() * trophyScale / 2f);
        float offsetX = backgroundX + ((boxWidth - width) / 2f);
        
        bananas.setPosition(offsetX, offsetY);
        setupActor(bananas, TIME_2, TIME_1, trophyScale);
        offsetX += bananasWidth;
        
        for (final Actor a : digits) {
        	a.setPosition(offsetX, digitOffsetY);
            setupActor(a, TIME_2, TIME_1, trophyScale);
        	offsetX += (a.getWidth() * trophyScale);
        }
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
	public boolean keyDown(final int keyCode) {
		// TODO Auto-generated method stub
		return false;
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
