package com.voyagegames.monkeymatch.screens;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.voyagegames.monkeymatch.helpers.DynamicGridImage;
import com.voyagegames.monkeymatch.helpers.GridBox;
import com.voyagegames.monkeymatch.helpers.GridElement;
import com.voyagegames.monkeymatch.helpers.LevelLoader;
import com.voyagegames.monkeymatch.helpers.StaticGridImage;
import com.voyagegames.monkeymatch.helpers.Token;
import com.voyagegames.monkeymatch.helpers.TokenDrag;

public abstract class LevelScreen extends AbstractScreen implements InputProcessor {
	
	private static final float FADE_TIME = 0.25f;

	private Random mRandomGenerator = new Random();
	
    private final boolean[] mGridInUse;
    private final boolean[] mGridCollected;
    private final DynamicGridImage[] mGridImages;
    private final Texture[] mGrids;
    private final Texture[] mTokenTextures;
    private final Texture[] mGoldTokens;
	private final LevelLoader mLevel;
	private final int mGridElements;
	
	private final List<Token> mTokens = new ArrayList<Token>();
	private final List<Token> mTargets = new ArrayList<Token>();
	private final List<GridBox> mGridBoxes = new ArrayList<GridBox>();
	
	private float mScale;
	private float mTargetSpawnTime;
	private float mLastTargetTime;
    private float mElapsedTime;
    private TokenDrag mDrag;
    private Texture mBackground;
    private Texture mBorder;
    private Texture mGridBackground;
    private StaticGridImage mGridBackgroundImage;

	public LevelScreen(final String levelXML) throws Exception {
		super();
		
		mLevel = new LevelLoader(levelXML);
		mTargetSpawnTime = mLevel.spawnTime;
		mGridElements = mLevel.numRows * mLevel.numCols;
		mGridInUse = new boolean[mGridElements];
		mGridCollected = new boolean[mGridElements];
		mGridImages = new DynamicGridImage[mGridElements];
		mGrids = new Texture[mGridElements];
		mTokenTextures = new Texture[mLevel.tokens.size()];
		mGoldTokens = new Texture[mLevel.tokens.size()];
	}

	@Override
	public void show() {
        mElapsedTime = 0f;
        
        mBackground = new Texture("backgrounds/bkgd_tutorial.png");
        mBackground.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        
        mBorder = new Texture("backgrounds/bkgd_border.png");
        mBorder.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        
        mGridBackground = new Texture(mLevel.background);
        mGridBackground.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        
        for (int i = 0; i < mLevel.grids.size(); ++i) {
        	mGrids[i] = new Texture(mLevel.grids.get(i).asset);
        }
        
        for (final Texture t : mGrids) {
        	t.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        }

        for (int i = 0; i < mLevel.tokens.size(); ++i) {
        	mTokenTextures[i] = new Texture("tokens/" + mLevel.tokens.get(i));
        	mGoldTokens[i] = new Texture("tokens/gold" + mLevel.tokens.get(i));
        	mTokenTextures[i].setFilter(TextureFilter.Linear, TextureFilter.Linear);
        	mGoldTokens[i].setFilter(TextureFilter.Linear, TextureFilter.Linear);
        }
	}

	@Override
	public void render(final float delta) {
        update(delta);
    	Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
    	Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        super.renderStage(delta);
	}

	@Override
	public void resize(final int width, final int height) {
		super.resize(width, height);
		
        mStage.clear();
        mTokens.clear();
        mTargets.clear();
        
        for (int i = 0; i < mGridElements; ++i) {
        	mGridInUse[i] = false;
        	mGridCollected[i] = false;
        }
        
        mGridBackgroundImage = new StaticGridImage(mGridBackground, width, height);
        mScale = width < height ?
        		((float)width) / ((float)mBackground.getWidth()) :
        		((float)height) / ((float)mBackground.getHeight());

        final float gridX = (width - (mGridBackgroundImage.image.getWidth() * mScale)) / 2f;
        final float gridY = (height - (mGridBackgroundImage.image.getHeight() * mScale)) / 2f;
        final StaticGridImage background = new StaticGridImage(mBackground, width, height);
        
        background.image.setPosition(0f, 0f);
        background.image.setWidth(width);
        background.image.setHeight(height);
        setupImage(background.image, 0f, 0.25f, 1f);
        
        mGridBackgroundImage.image.setPosition(gridX, gridY);
        setupImage(mGridBackgroundImage.image, 0f, 0.25f, mScale);

        for (int i = 0; i < mGridElements; ++i) {
        	mGridImages[i] = new DynamicGridImage(
    				mGrids[i],
    				gridX,
    				gridY,
    				mLevel.grids.get(i).x * mScale,
    				mLevel.grids.get(i).y * mScale);
            setupImage(mGridImages[i].image, 1.25f, 0.5f, mScale);
        }

        final StaticGridImage gridBorder = new StaticGridImage(mBorder, width, height);
        final float borderX = ((float)(mBorder.getWidth() - mGridBackground.getWidth())) / 2f;
        final float borderY = ((float)(mBorder.getHeight() - mGridBackground.getHeight())) / 2f;
        
        gridBorder.image.setPosition(gridX - (borderX * mScale), gridY - (borderY * mScale));
        setupImage(gridBorder.image, 0f, 0.25f, mScale);

        float totalTokenWidth = 0f;
        
        for (final Texture t : mTokenTextures) {
        	totalTokenWidth += t.getWidth() * mScale;
        }

        float offset = (((float)width) - totalTokenWidth) / 2f;
        
        for (int i = 0; i < mTokenTextures.length; ++i) {
        	final Texture t = mTokenTextures[i];
            final Image image = new Image(new TextureRegion(t, 0, 0, t.getWidth(), t.getHeight()));
            final float imageWidth = image.getWidth() * mScale;
            final Vector2 initialPosition = new Vector2(offset + (imageWidth / 4f), 0f);
            
            image.setPosition(initialPosition.x, initialPosition.y);
            setupImage(image, 0.5f, 0.5f, mLevel.tokenScale * mScale);
            image.setTouchable(Touchable.enabled);

            mTokens.add(new Token(image, i, initialPosition, 0f));
            offset += imageWidth;
        }
	}

	@Override
	public void dispose() {
		mBorder.dispose();
		mBackground.dispose();
		mGridBackground.dispose();
		
		for (final Texture t : mGrids) {
			t.dispose();
		}
		
		for (final Texture t : mTokenTextures) {
			t.dispose();
		}
		
		for (final Texture t : mGoldTokens) {
			t.dispose();
		}
		
		super.dispose();
	}

	@Override
	public boolean touchDown(final int x, final int y, final int pointer, final int button) {
		if (mDrag != null) {
			return false;
		}

		final Actor a = mStage.hit(x, mStage.getHeight() - y, true);
		
		if (a == null) {
			return false;
		}
		
		for (final Token t : mTokens) {
			if (t.actor != a) {
				continue;
			}
			
			mDrag = new TokenDrag(t, x, y);
			return true;
		}
		
		return false;
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
		if (mDrag == null) {
			return false;
		}

		final float deltaX = x - mDrag.x();
		final float deltaY = y - mDrag.y();
		final Token t = mDrag.token;

		t.actor.setPosition(t.actor.getX() + deltaX, t.actor.getY() + deltaY);
		testTokenHit(t);
		resetToken(t);
		mDrag = null;
		return true;
	}
	
	private void setupImage(final Image image, final float delay, final float fadeIn, final float scale) {
        image.getColor().a = 0f;
        image.setTouchable(Touchable.disabled);
        image.addAction(Actions.sequence(
        		Actions.delay(delay),
        		Actions.fadeIn(fadeIn)
        	));
        image.setScale(scale);
        mStage.addActor(image);
	}
	
	private void resetToken(final Token token) {
		token.actor.clearActions();
		token.actor.addAction(Actions.sequence(
				Actions.touchable(Touchable.disabled),
				Actions.fadeOut(FADE_TIME),
				Actions.moveTo(token.initialPosition.x, token.initialPosition.y),
				Actions.fadeIn(FADE_TIME),
				Actions.touchable(Touchable.enabled)
			));
	}
	
	private void spawnTarget(final int gridIndex, final int tokenIndex) {
		if (
				gridIndex  < 0 || gridIndex  > mGridImages.length ||
				tokenIndex < 0 || tokenIndex > mGoldTokens.length) {
			return;
		}
		
    	final Texture t = mGoldTokens[tokenIndex];
        final float gridX = (mStage.getWidth() - (mGridBackgroundImage.image.getWidth() * mScale)) / 2f;
        final float gridY = (mStage.getHeight() - (mGridBackgroundImage.image.getHeight() * mScale)) / 2f;
        final Image image = new Image(new TextureRegion(t, 0, 0, t.getWidth(), t.getHeight()));
        final GridElement e = mLevel.grids.get(gridIndex);
        
        image.setPosition(((e.x + mLevel.tokenX) * mScale) + gridX, ((e.y + mLevel.tokenY) * mScale) + gridY);
        setupImage(image, 2f, 0.5f, mLevel.tokenScale * mScale);
        mTargets.add(new Token(image, tokenIndex, null, 0f));
        mGridBoxes.add(new GridBox(gridIndex, image, mGridImages[gridIndex].image));
	}
	
	private void update(final float delta) {
        mElapsedTime += delta;
		checkForNewSpawn();
		
        int countCollected = 0;
        
        for (int i = 0; i < mGridCollected.length; ++i) {
        	if (mGridCollected[i] == true) {
        		++countCollected;
        	}
        }
        
        if (countCollected == mGridElements) {
        	// TODO Trigger victory
        	return;
        }
	}
	
	private void checkForNewSpawn() {
        if (mLastTargetTime > 0 && (mElapsedTime - mLastTargetTime) < mTargetSpawnTime) {
        	return;
        }
        
        int countInUse = 0;
        
        for (countInUse = 0; countInUse < mGridInUse.length; ++countInUse) {
        	if (mGridInUse[countInUse] == false) {
        		break;
        	}
        }
        
        if (countInUse == mGridElements) {
        	return;
        }
    
    	final int tokenIndex = mRandomGenerator.nextInt(this.mGoldTokens.length);
    	int gridIndex = 0;
    	
    	do {
    		gridIndex = mRandomGenerator.nextInt(mGridElements);
    	} while (mGridInUse[gridIndex] == true || mGridCollected[gridIndex] == true);
    	
    	spawnTarget(gridIndex, tokenIndex);
    	mGridInUse[gridIndex] = true;
    	mLastTargetTime = mElapsedTime;
	}
	
	private float getDistSquared(final Actor a, final float tokenX, final float tokenY) {
		final float xDist = (a.getX() - tokenX);
		final float yDist = (a.getY() - tokenY);
		return (xDist * xDist) + (yDist * yDist);
	}
	
	private void testTokenHit(final Token token) {
		final List<Actor> targets = new ArrayList<Actor>();
		final float tokenWidth = token.actor.getWidth();
		final float tokenHeight = token.actor.getHeight();
		final float radius = tokenHeight < tokenWidth ? tokenHeight / 2f : tokenWidth / 2f;
		final float radiusSq = (radius * radius) * 0.25f;
		final float tokenX = token.actor.getX();
		final float tokenY = token.actor.getY();
		
		for (final Actor a : mStage.getActors()) {
			for (final Token t : mTargets) {
				if (t.actor != a || t.type != token.type) {
					continue;
				}
				
				if (getDistSquared(a, tokenX, tokenY) < radiusSq) {
					targets.add(a);
				}
			}
		}
		
		if (targets.size() == 0) {
			return;
		}
		
		float closestDist = Float.MAX_VALUE;
		Actor closestTarget = null;
		
		for (final Actor a : targets) {
			final float distSq = getDistSquared(a, tokenX, tokenY);
			
			if (distSq < closestDist) {
				closestDist = distSq;
				closestTarget = a;
			}
		}

		closestTarget.clearActions();
		closestTarget.addAction( Actions.removeActor() );
		mTargets.remove(closestTarget);
		
		for (final GridBox b : mGridBoxes) {
			if (b.target != closestTarget) {
				continue;
			}
		
			b.box.clearActions();
			b.box.addAction( Actions.removeActor() );
			
			mGridBoxes.remove(b);
			mGridCollected[b.offset] = true; 
			break;
		}
	}

}
