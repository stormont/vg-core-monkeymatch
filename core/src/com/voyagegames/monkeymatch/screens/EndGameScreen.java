package com.voyagegames.monkeymatch.screens;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.voyagegames.monkeymatch.helpers.AudioManager;
import com.voyagegames.monkeymatch.helpers.BundledTexture;
import com.voyagegames.monkeymatch.helpers.TextureManager;
import com.voyagegames.monkeymatch.helpers.AudioManager.MusicSelection;

public class EndGameScreen implements Screen, InputProcessor {

	private static final float TROPHY_SCALE = 0.5f;
	private static final float SCALE_SIZE = 1.2f;
	private static final float LOGO_SCALE = 0.75f;
	private static final float TIME_0 = 0f;
	private static final float TIME_1 = 0.25f;
	private static final float TIME_3 = 0.75f;
	private static final float TIME_4 = 1f;

	private final Stage          mStage;
	private final LevelCallback  mCallback;
	private final int            mScore;
	private final int            mPersonalBest;
	private final TextureManager mTextures;
	private final AudioManager   mAudio;
    
    private Actor   mBackgroundActor;
	private Actor   mButtonActor;
	private Actor   mOptionsActor;
	private boolean mIsTouched;
	private float   mScale;
	
	public EndGameScreen(
			final int score,
			final int personalBest,
			final LevelCallback callback,
			final TextureManager textures,
			final AudioManager audio) {
		mStage = new Stage(0, 0, true);
		mCallback = callback;
		mScore = score;
		mPersonalBest = personalBest;
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
        
        setPersonalBestScore(mScale);
        setCurrentScore(mScale);
        
        final Actor title = new Image(mTextures.title.region);
        title.setPosition(
        		(width - (title.getWidth() * mScale)) / 2f,
        		(height - (title.getHeight() * mScale)));
        setupActor(title, TIME_0, TIME_1, mScale);
        
        final Actor logoActor = new Image(mTextures.logo.region);
        logoActor.setPosition(0f, 0f);
        setupActor(logoActor, TIME_0, TIME_1, mScale * LOGO_SCALE);
        
        final float optionsScale = mScale * LOGO_SCALE;
        mOptionsActor = new Image(mTextures.options.region);
        mOptionsActor.setPosition(width - (mOptionsActor.getWidth() * optionsScale), 0f);
        mOptionsActor.addAction(Actions.touchable(Touchable.enabled));
        setupActor(mOptionsActor, TIME_0, TIME_1, optionsScale);
        
        mButtonActor = new Image(mTextures.start.region);
        mButtonActor.setPosition(
        		gridBorder.getX() + (gridBorder.getWidth() * mScale / 2f) - (mButtonActor.getWidth() * mScale) / 2f,
        		gridBorder.getY());
        mButtonActor.setOrigin(mButtonActor.getWidth() * mScale / 2f, mButtonActor.getHeight() * mScale / 2f);
        setupActor(mButtonActor, TIME_4, TIME_1, mScale);
        mButtonActor.addAction(Actions.touchable(Touchable.enabled));
        mButtonActor.addAction(Actions.scaleTo(SCALE_SIZE, SCALE_SIZE));
        addStartButtonActions();
	}
	
	@Override
	public boolean touchDown(final int x, final int y, final int pointer, final int button) {
		final Actor a = mStage.hit(x, mStage.getHeight() - y, true);
		
		if (a != mButtonActor && a != mOptionsActor) {
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
		} else if (a == mOptionsActor) {
			mCallback.showOptions();
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
        		Actions.scaleTo(mScale, mScale, TIME_3),
        		Actions.scaleTo(SCALE_SIZE * mScale, SCALE_SIZE * mScale, TIME_3)
        	)));
	}
	
	private void setPersonalBestScore(final float scale) {
		if (mPersonalBest <= 0) {
			return;
		}
		
        final String scoreStr = String.valueOf(mPersonalBest);
        final int length = scoreStr.length();
        
        final float trophyScale = scale * TROPHY_SCALE;
        final Actor leftTrophy = new Image(mTextures.trophy.region);
        final Actor rightTrophy = new Image(mTextures.trophy.region);
        final float backgroundX = mBackgroundActor.getX();
        final float backgroundY = mBackgroundActor.getY();
        final float backgroundHeight = mBackgroundActor.getHeight() * scale;
        
        final List<Actor> digits = new ArrayList<Actor>();
        final float trophyWidth = (leftTrophy.getWidth() + rightTrophy.getWidth()) * trophyScale;
        final float trophyHeight = leftTrophy.getHeight() * trophyScale;
        float width = trophyWidth;
        
        for (int i = 0; i < length; ++i) {
        	final int v = Integer.parseInt(scoreStr.substring(i, i + 1));
        	final Actor a = new Image(mTextures.digits[v].region);

        	width += (a.getWidth() * trophyScale);
        	digits.add(a);
        }
        
        final float boxWidth = mBackgroundActor.getWidth() * scale;
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
        final Actor bananas = new Image(mTextures.points.region);
        final float backgroundX = mBackgroundActor.getX();
        final float backgroundY = mBackgroundActor.getY();
        final float backgroundHeight = mBackgroundActor.getHeight() * scale;
        
        final List<Actor> digits = new ArrayList<Actor>();
        final float bananasWidth = bananas.getWidth() * trophyScale;
        final float bananasHeight = bananas.getHeight() * trophyScale;
        float width = bananasWidth;
        
        for (int i = 0; i < length; ++i) {
        	final int v = Integer.parseInt(scoreStr.substring(i, i + 1));
        	final Actor a = new Image(mTextures.digits[v].region);

        	width += (a.getWidth() * trophyScale);
        	digits.add(a);
        }
        
        final float boxWidth = mBackgroundActor.getWidth() * scale;
        final float offsetY = backgroundY + ((backgroundHeight - bananas.getHeight() * trophyScale) / 2f);
        final float digitOffsetY = offsetY + (bananasHeight / 2f) - (digits.get(0).getHeight() * trophyScale / 2f);
        float offsetX = backgroundX + ((boxWidth - width) / 2f);
        
        bananas.setPosition(offsetX, offsetY);
        setupActor(bananas, TIME_3, TIME_1, trophyScale);
        offsetX += bananasWidth;
        
        for (final Actor a : digits) {
        	a.setPosition(offsetX, digitOffsetY);
            setupActor(a, TIME_3, TIME_1, trophyScale);
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
