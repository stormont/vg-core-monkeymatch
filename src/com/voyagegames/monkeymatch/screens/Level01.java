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
import com.voyagegames.monkeymatch.helpers.StaticGridImage;

public class Level01 extends AbstractScreen {

    private final float mTextureScale;
    private final int mNumRows = 4;
    private final int mNumCols = 4;
    private final Texture[] mGrids = new Texture[mNumRows * mNumCols];
    
    private OrthographicCamera mCamera;
    private float mElapsedTime;
    
    private Texture mBackground;
    private Texture mBorder;
    private Texture mGridBackground;
    private Texture mMonkey;

	public Level01() {
		super();
		
		mTextureScale = 0.5f;
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
        mElapsedTime = 0f;
        
        mBackground = new Texture("bkgd_tutorial.png");
        mBackground.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        
        mMonkey = new Texture("monkey.png");
        mMonkey.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        
        mBorder = new Texture("backgrounds/bkgd_border.png");
        mBorder.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        
        mGridBackground = new Texture("backgrounds/africa/bkgd_africa.png");
        mGridBackground.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        
        mGrids[0]  = new Texture("backgrounds/africa/bkgd_africa_1_1.png");
        mGrids[1]  = new Texture("backgrounds/africa/bkgd_africa_1_2.png");
        mGrids[2]  = new Texture("backgrounds/africa/bkgd_africa_1_3.png");
        mGrids[3]  = new Texture("backgrounds/africa/bkgd_africa_1_4.png");
        mGrids[4]  = new Texture("backgrounds/africa/bkgd_africa_2_1.png");
        mGrids[5]  = new Texture("backgrounds/africa/bkgd_africa_2_2.png");
        mGrids[6]  = new Texture("backgrounds/africa/bkgd_africa_2_3.png");
        mGrids[7]  = new Texture("backgrounds/africa/bkgd_africa_2_4.png");
        mGrids[8]  = new Texture("backgrounds/africa/bkgd_africa_3_1.png");
        mGrids[9]  = new Texture("backgrounds/africa/bkgd_africa_3_2.png");
        mGrids[10] = new Texture("backgrounds/africa/bkgd_africa_3_3.png");
        mGrids[11] = new Texture("backgrounds/africa/bkgd_africa_3_4.png");
        mGrids[12] = new Texture("backgrounds/africa/bkgd_africa_4_1.png");
        mGrids[13] = new Texture("backgrounds/africa/bkgd_africa_4_2.png");
        mGrids[14] = new Texture("backgrounds/africa/bkgd_africa_4_3.png");
        mGrids[15] = new Texture("backgrounds/africa/bkgd_africa_4_4.png");
        
        for (final Texture t : mGrids) {
        	t.setFilter(TextureFilter.Linear, TextureFilter.Linear);
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
		
        mCamera = new OrthographicCamera(width, height);
        
        super.clearBackgrounds();
        super.addBackground(new BackgroundImage(mBackground).region);

        mStage.clear();

        final StaticGridImage gridBackground = new StaticGridImage(mGridBackground, width, height);
        gridBackground.image.getColor().a = 0f;
        gridBackground.image.addAction(sequence( fadeIn(0.25f) ));
        mStage.addActor(gridBackground.image);
        
        final float gridX = gridBackground.image.getX();
        final float gridY = gridBackground.image.getY();
        
        final DynamicGridImage[] gridImages = new DynamicGridImage[mNumRows * mNumCols];
        final int gridImageWidth = mGrids[0].getWidth();
        final int gridImageHeight = mGrids[0].getHeight();

        for (int row = 0; row < mNumRows; ++row) {
        	for (int col = 0; col < mNumCols; ++col) {
        		final int offset = (row * 4) + col;
        		gridImages[offset] = new DynamicGridImage(
        				mGrids[offset],
        				gridX,
        				gridY,
        				col * gridImageWidth,
        				(mNumRows - 1 - row) * gridImageHeight);
        	}
        }
        
        for (int i = 0; i < gridImages.length; ++i) {
        	final Image image = gridImages[i].image;
        	image.getColor().a = 0f;
        	image.addAction(sequence( delay(1.25f), fadeIn(0.5f) ));
            mStage.addActor(image);
        }

        final StaticGridImage gridBorder = new StaticGridImage(mBorder, width, height);
        gridBorder.image.getColor().a = 0f;
        gridBorder.image.addAction(sequence( fadeIn(0.25f) ));
        mStage.addActor(gridBorder.image);

        final Image monkeyImage = new Image(new TextureRegion(mMonkey, 0, 0, mMonkey.getWidth(), mMonkey.getHeight()));
        monkeyImage.setScale(mTextureScale);
        monkeyImage.getColor().a = 0f;
        monkeyImage.addAction(sequence( delay(0.5f), fadeIn(0.5f) ));
        mStage.addActor(monkeyImage);
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
	public void dispose() {
		mBackground.dispose();
		mMonkey.dispose();
		mBorder.dispose();
		mGridBackground.dispose();
		
		for (final Texture t : mGrids) {
			t.dispose();
		}
		
		super.dispose();
	}

}
