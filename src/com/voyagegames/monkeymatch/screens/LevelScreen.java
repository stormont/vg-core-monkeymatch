package com.voyagegames.monkeymatch.screens;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.delay;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.voyagegames.monkeymatch.helpers.BackgroundImage;
import com.voyagegames.monkeymatch.helpers.DynamicGridImage;
import com.voyagegames.monkeymatch.helpers.GridElement;
import com.voyagegames.monkeymatch.helpers.LevelLoader;
import com.voyagegames.monkeymatch.helpers.StaticGridImage;

public abstract class LevelScreen extends AbstractScreen {

    private final Texture[] mGrids;
    private final Texture[] mTokens;
    private final Texture[] mGoldTokens;
	private final LevelLoader mLevel;
	private final int mGridElements; 
	
    private OrthographicCamera mCamera;
    private float mElapsedTime;
    
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
    	Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
    	Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    	
        mElapsedTime += delta;
        
        super.renderBackground(delta, mCamera);
        super.renderStage(delta);
        super.renderForeground(delta, mCamera);
	}

	@Override
	public void resize(final int width, final int height) {
		super.resize(width, height);
        super.clearBackgrounds();
        super.addBackground(new BackgroundImage(mBackground).region);
		
        mCamera = new OrthographicCamera(width, height);
        mStage.clear();

        final StaticGridImage gridBackground = new StaticGridImage(mGridBackground, width, height);
        setupImage(gridBackground.image, 0f, 0.25f);
        
        final float gridX = gridBackground.image.getX();
        final float gridY = gridBackground.image.getY();
        
        final DynamicGridImage[] gridImages = new DynamicGridImage[mGridElements];

        for (int i = 0; i < mGridElements; ++i) {
    		gridImages[i] = new DynamicGridImage(
    				mGrids[i],
    				gridX,
    				gridY,
    				mLevel.grids.get(i).x,
    				mLevel.grids.get(i).y);
            setupImage(gridImages[i].image, 1.25f, 0.5f);
        }

        final StaticGridImage gridBorder = new StaticGridImage(mBorder, width, height);
        setupImage(gridBorder.image, 0f, 0.25f);

        float offset = 0f;
        
        for (final Texture t : mTokens) {
            final Image image = new Image(new TextureRegion(t, 0, 0, t.getWidth(), t.getHeight()));
            
            image.setScale(mLevel.tokenScale);
            image.setPosition(offset, 0f);
            setupImage(image, 0.5f, 0.5f);
            
            offset += image.getWidth();
        }

        
        // TODO
    	final Texture t = mGoldTokens[0];
        for (int i = 0; i < mGridElements; ++i) {
            final Image image = new Image(new TextureRegion(t, 0, 0, t.getWidth(), t.getHeight()));
            final GridElement e = mLevel.grids.get(i);
            
            image.setScale(mLevel.tokenScale);
            image.setPosition(e.x + mLevel.tokenX + gridX, e.y + mLevel.tokenY + gridY);
            setupImage(image, 2f, 0.5f);
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
	
	private void setupImage(final Image image, final float delay, final float fadeIn) {
        image.getColor().a = 0f;
        image.addAction(sequence( delay(delay), fadeIn(fadeIn) ));
        mStage.addActor(image);
	}

}
