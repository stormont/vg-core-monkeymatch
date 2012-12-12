package com.voyagegames.monkeymatch.screens;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.delay;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.removeActor;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.voyagegames.monkeymatch.helpers.DynamicGridImage;
import com.voyagegames.monkeymatch.helpers.GridElement;
import com.voyagegames.monkeymatch.helpers.LevelLoader;
import com.voyagegames.monkeymatch.helpers.StaticGridImage;
import com.voyagegames.monkeymatch.helpers.TokenDrag;

public abstract class LevelScreen extends AbstractScreen implements InputProcessor {

    private final Texture[] mGrids;
    private final Texture[] mTokens;
    private final Texture[] mGoldTokens;
	private final LevelLoader mLevel;
	private final int mGridElements;
	private final List<Actor> mTargets = new ArrayList<Actor>();
	
    private float mElapsedTime;
    private TokenDrag mDrag;
    
    private Texture mBackground;
    private Texture mBorder;
    private Texture mGridBackground;

	public LevelScreen(final String levelXML) throws Exception {
		super();
		
		mLevel = new LevelLoader(levelXML);
		mGridElements = mLevel.numRows * mLevel.numCols;
		mGrids = new Texture[mGridElements];
		mTokens = new Texture[mLevel.tokens.size()];
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
        	mTokens[i] = new Texture("tokens/" + mLevel.tokens.get(i));
        	mGoldTokens[i] = new Texture("tokens/gold" + mLevel.tokens.get(i));
        	mTokens[i].setFilter(TextureFilter.Linear, TextureFilter.Linear);
        	mGoldTokens[i].setFilter(TextureFilter.Linear, TextureFilter.Linear);
        }
	}

	@Override
	public void render(final float delta) {
        mElapsedTime += delta;
    	Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
    	Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        super.renderStage(delta);
	}

	@Override
	public void resize(final int width, final int height) {
		super.resize(width, height);
		
        mStage.clear();
        mTargets.clear();
        
        final StaticGridImage background = new StaticGridImage(mBackground, width, height);
        final float scale = width < height ?
        		((float)width) / ((float)mBackground.getWidth()) :
        		((float)height) / ((float)mBackground.getHeight());
        
        background.image.setPosition(0f, 0f);
        background.image.setWidth(width);
        background.image.setHeight(height);
        setupImage(background.image, 0f, 0.25f, 1f);

        final StaticGridImage gridBackground = new StaticGridImage(mGridBackground, width, height);
        final float gridX = (width - (gridBackground.image.getWidth() * scale)) / 2f;
        final float gridY = (height - (gridBackground.image.getHeight() * scale)) / 2f;
        
        gridBackground.image.setPosition(gridX, gridY);
        setupImage(gridBackground.image, 0f, 0.25f, scale);
        
        final DynamicGridImage[] gridImages = new DynamicGridImage[mGridElements];

        for (int i = 0; i < mGridElements; ++i) {
    		gridImages[i] = new DynamicGridImage(
    				mGrids[i],
    				gridX,
    				gridY,
    				mLevel.grids.get(i).x * scale,
    				mLevel.grids.get(i).y * scale);
            setupImage(gridImages[i].image, 1.25f, 0.5f, scale);
        }

        final StaticGridImage gridBorder = new StaticGridImage(mBorder, width, height);
        final float borderX = ((float)(mBorder.getWidth() - mGridBackground.getWidth())) / 2f;
        final float borderY = ((float)(mBorder.getHeight() - mGridBackground.getHeight())) / 2f;
        
        gridBorder.image.setPosition(gridX - (borderX * scale), gridY - (borderY * scale));
        setupImage(gridBorder.image, 0f, 0.25f, scale);

        float offset = 0f;
        
        for (final Texture t : mTokens) {
            final Image image = new Image(new TextureRegion(t, 0, 0, t.getWidth(), t.getHeight()));
            
            image.setPosition(offset * scale, 0f);
            setupImage(image, 0.5f, 0.5f, mLevel.tokenScale * scale);
            image.setTouchable(Touchable.enabled);
            
            offset += image.getWidth();
        }

        
        // TODO
    	final Texture t = mGoldTokens[0];
        for (int i = 0; i < mGridElements; ++i) {
            final Image image = new Image(new TextureRegion(t, 0, 0, t.getWidth(), t.getHeight()));
            final GridElement e = mLevel.grids.get(i);
            
            image.setPosition(((e.x + mLevel.tokenX) * scale) + gridX, ((e.y + mLevel.tokenY) * scale) + gridY);
            setupImage(image, 2f, 0.5f, mLevel.tokenScale * scale);
            mTargets.add(image);
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
		
		for (final Texture t : mTokens) {
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
		
		mDrag = new TokenDrag(a, x, y);
		return true;
	}

	@Override
	public boolean touchDragged(final int x, final int y, final int pointer) {
		if (mDrag == null) {
			return false;
		}

		final float deltaX = x - mDrag.x();
		final float deltaY = mDrag.y() - y;
		final Actor a = mDrag.token;

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
		final Actor a = mDrag.token;

		a.setPosition(a.getX() + deltaX, a.getY() + deltaY);
		testTokenHit(a);
		mDrag = null;
		return true;
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
	
	private void setupImage(final Image image, final float delay, final float fadeIn, final float scale) {
        image.getColor().a = 0f;
        image.addAction(sequence( delay(delay), fadeIn(fadeIn) ));
        image.setTouchable(Touchable.disabled);
        image.setScale(scale);
        mStage.addActor(image);
	}
	
	private float getDistSquared(final Actor a, final float tokenX, final float tokenY) {
		final float xDist = (a.getX() - tokenX);
		final float yDist = (a.getY() - tokenY);
		
		return (xDist * xDist) + (yDist * yDist);
	}
	
	private void testTokenHit(final Actor token) {
		final List<Actor> targets = new ArrayList<Actor>();
		final float tokenWidth = token.getWidth();
		final float tokenHeight = token.getHeight();
		final float radius = tokenHeight < tokenWidth ? tokenHeight / 2f : tokenWidth / 2f;
		final float radiusSq = (radius * radius) * 0.25f;
		final float tokenX = token.getX();
		final float tokenY = token.getY();
		
		for (final Actor a : mStage.getActors()) {
			if (!mTargets.contains(a)) {
				continue;
			}
			
			if (getDistSquared(a, tokenX, tokenY) < radiusSq) {
				targets.add(a);
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
		closestTarget.addAction( removeActor() );
		mTargets.remove(closestTarget);
	}

}
