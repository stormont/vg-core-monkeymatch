package com.voyagegames.monkeymatch.screens;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.delay;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.voyagegames.monkeymatch.helpers.PositionedTextureRegion;
import com.voyagegames.monkeymatch.helpers.RenderEntity;
import com.voyagegames.monkeymatch.helpers.RenderGroup;

public class Level01 extends AbstractScreen {

    private final List<RenderGroup> mRenderGroups;
    private final float mTextureScale;
    
    private OrthographicCamera mCamera;
    private RenderEntity mEntity;
    private float mElapsedTime;
    
    private Texture mBackground;
    private Texture mBorder;
    private Texture mGrid;
    private Texture mMonkey;

	public Level01() {
		super();
		
		mRenderGroups = new ArrayList<RenderGroup>();
		mTextureScale = 0.5f;
	}
	
	public void setRenderEntity(final RenderEntity entity) {
		mEntity = entity;
	}
	
	public void addRenderGroup(final RenderGroup renderGroup) {
		mRenderGroups.add(renderGroup);
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
        
        mBorder = new Texture("bkgd_border.png");
        mBorder.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        
        mGrid = new Texture("bkgd_africa.png");
        mGrid.setFilter(TextureFilter.Linear, TextureFilter.Linear);
	}

	@Override
	public void render(final float delta) {
    	Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
    	Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    	
        mElapsedTime += delta;
        
        super.renderBackground(delta, mCamera);
        super.renderStage(delta);
		
		for (final RenderGroup rg : mRenderGroups) {
			rg.render(delta, mCamera);
		}
        
        super.renderForeground(delta, mCamera);
	}

	@Override
	public void resize(final int width, final int height) {
		super.resize(width, height);
		
        mCamera = new OrthographicCamera(width, height);
        
        final TextureRegion background = new TextureRegion(mBackground);
        final PositionedTextureRegion backgroundRegion = new PositionedTextureRegion(
        		background,
        		-background.getRegionWidth() / 2f,
        		-background.getRegionHeight() / 2f);
        
        super.clearBackgrounds();
        super.addBackground(backgroundRegion);

        mStage.clear();

        final int gridWidth = mGrid.getWidth();
        final int gridHeight = mGrid.getHeight();
        final Image gridImage = new Image(new TextureRegion(mGrid, 0, 0, gridWidth, gridHeight));

        gridImage.getColor().a = 0f;
        gridImage.setPosition((width - gridWidth) / 2f, (height - gridHeight) / 2f);
        gridImage.addAction(sequence( fadeIn(0.25f) ));
        mStage.addActor(gridImage);

        final int borderWidth = mBorder.getWidth();
        final int borderHeight = mBorder.getHeight();
        final Image borderImage = new Image(new TextureRegion(mBorder, 0, 0, borderWidth, borderHeight));

        borderImage.getColor().a = 0f;
        borderImage.setPosition((width - borderWidth) / 2f, (height - borderHeight) / 2f);
        borderImage.addAction(sequence( fadeIn(0.25f) ));
        mStage.addActor(borderImage);

        final Image monkeyImage = new Image(new TextureRegion(mMonkey, 0, 0, mMonkey.getWidth(), mMonkey.getHeight()));

        monkeyImage.setScale(mTextureScale);
        monkeyImage.getColor().a = 0f;
        monkeyImage.addAction(sequence( delay(1f), fadeIn(0.5f) ));
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
		for (final RenderGroup rg : mRenderGroups) {
			rg.shader.dispose();
		}
		
		if (mEntity != null && mEntity.mesh != null) {
			mEntity.mesh.dispose();
		}
		
		mBackground.dispose();
		
		super.dispose();
	}

}
