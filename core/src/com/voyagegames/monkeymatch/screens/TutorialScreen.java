package com.voyagegames.monkeymatch.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.voyagegames.monkeymatch.helpers.AudioManager;
import com.voyagegames.monkeymatch.helpers.AudioManager.MusicSelection;
import com.voyagegames.monkeymatch.helpers.AudioManager.SoundSelection;
import com.voyagegames.monkeymatch.helpers.BundledTexture;
import com.voyagegames.monkeymatch.helpers.TextureManager;
import com.voyagegames.monkeymatch.helpers.Token;
import com.voyagegames.monkeymatch.helpers.TokenDrag;

public class TutorialScreen implements Screen, InputProcessor {

	private static final float TOKEN_SCALE = 0.6f;
	private static final float SCALE_SIZE = 1.2f;
	private static final float TIME_0 = 0f;
	private static final float TIME_1 = 0.25f;
	private static final float TIME_2 = 0.5f;
	private static final float TIME_3 = 0.75f;
	private static final float TIME_4 = 1f;

	private final Stage          mStage;
	private final LevelCallback  mCallback;
	private final TextureManager mTextures;
	private final AudioManager   mAudio;

    private TokenDrag mDrag;
    private Token     mToken;
    private Actor     mBackgroundActor;
	private Actor     mTokenActor;
	private Actor     mTargetActor;
	private boolean   mIsTouched;
	private float     mScale;
	
	public TutorialScreen(
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
    	mTextures.tokens[0] = new BundledTexture("tokens/monkey.png", 128, 128);
    	mTextures.goldTokens[0] = new BundledTexture("tokens/goldmonkey.png", 128, 128);
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

        mTokenActor = new Image(mTextures.tokens[0].region);
        mTargetActor = new Image(mTextures.goldTokens[0].region);
        
        final float tokenScale = TOKEN_SCALE * mScale;
        final float tokenCenterX = (background.getWidth() - (mTokenActor.getWidth() * tokenScale)) / 2f;
        final float tokenCenterY = (background.getHeight() - (mTargetActor.getHeight() * tokenScale)) / 2f;
        
        mTargetActor.setPosition(tokenCenterX, tokenCenterY);
        setupActor(mTargetActor, TIME_2, TIME_2, tokenScale);
        
        mTokenActor.setPosition(tokenCenterX, 0f);
        setupActor(mTokenActor, TIME_2, TIME_2, tokenScale);
        mTokenActor.setTouchable(Touchable.enabled);
        mTokenActor.addAction(Actions.scaleTo(SCALE_SIZE * tokenScale, SCALE_SIZE * tokenScale));
        addStartButtonActions();
        
        mToken = new Token(mTokenActor, 0, new Vector2(mTokenActor.getX(), mTokenActor.getY()), 0f, 0);
	}
	
	@Override
	public boolean touchDown(final int x, final int y, final int pointer, final int button) {
		final Actor a = mStage.hit(x, mStage.getHeight() - y, true);
		
		if (a != mTokenActor) {
			return false;
		}

		mTokenActor.clearActions();
		mDrag = new TokenDrag(mToken, x, y);
		mIsTouched = true;
		return true;
	}

	@Override
	public boolean touchDragged(final int x, final int y, final int pointer) {
		if (mDrag == null) {
			return false;
		}

		final float deltaX = x - mDrag.x();
		final float deltaY = mDrag.y() - y;
		final Actor a = mDrag.token.actor;

		a.setPosition(a.getX() + deltaX, a.getY() + deltaY);
		mDrag.update(x, y);
		return true;
	}

	@Override
	public boolean touchUp(final int x, final int y, final int pointer, final int button) {
		if (!mIsTouched) {
			return false;
		}

		final float deltaX = x - mDrag.x();
		final float deltaY = y - mDrag.y();
		final Token t = mDrag.token;

		t.actor.setPosition(t.actor.getX() + deltaX, t.actor.getY() + deltaY);
		
		final Actor target = getNearestHit(t);
		
		if (target == mTargetActor) {
			doVictory();
		} else {
			mTokenActor.setPosition(mDrag.token.initialPosition.x, mDrag.token.initialPosition.y);
			addStartButtonActions();
			mDrag = null;
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
	
	private void doVictory() {
		mTokenActor.clearActions();
		mTokenActor.addAction(Actions.removeActor());
		mTargetActor.clearActions();
		mTargetActor.addAction(Actions.removeActor());
		
        final Actor actor = new Image(mTextures.trophy.region);
        
        actor.setScale(mScale);
        actor.setPosition(
        		(mStage.getWidth() - (actor.getWidth() * mScale)) / 2f,
        		(mStage.getHeight() - (actor.getHeight() * mScale)) / 2f);
        actor.addAction(Actions.sequence(
        		Actions.fadeIn(TIME_2),
        		new Action() {

					@Override
					public boolean act(final float delta) {
						mAudio.playSound(SoundSelection.APPLAUSE);
						return true;
					}
        			
        		},
        		Actions.delay(TIME_4),
        		Actions.fadeOut(TIME_4),
        		new Action() {

					@Override
					public boolean act(final float delta) {
						mStage.clear();
						mCallback.levelComplete(0);
						return true;
					}
        			
        		},
        		Actions.removeActor()
        	));
		actor.getColor().a = 0f;
		actor.setTouchable(Touchable.disabled);
		mStage.addActor(actor);
	}
	
	private void addStartButtonActions() {
        final float tokenScale = TOKEN_SCALE * mScale;
        mTokenActor.addAction(Actions.forever(Actions.sequence(
        		Actions.scaleTo(tokenScale, tokenScale, TIME_3),
        		Actions.scaleTo(SCALE_SIZE * tokenScale, SCALE_SIZE * tokenScale, TIME_3)
        	)));
	}
	
	private float getDistSquared(final Actor a, final float tokenX, final float tokenY) {
		final float xDist = (a.getX() - tokenX);
		final float yDist = (a.getY() - tokenY);
		return (xDist * xDist) + (yDist * yDist);
	}
	
	private Actor getNearestHit(final Token token) {
		final float tokenWidth = token.actor.getWidth();
		final float tokenHeight = token.actor.getHeight();
		final float radius = tokenHeight < tokenWidth ? tokenHeight / 2f : tokenWidth / 2f;
		final float radiusSq = radius * radius;
		final float tokenX = token.actor.getX();
		final float tokenY = token.actor.getY();

		if (getDistSquared(mTargetActor, tokenX, tokenY) >= radiusSq) {
			return null;
		}
		
		return mTargetActor;
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

}
