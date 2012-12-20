package com.voyagegames.monkeymatch.helpers;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class PositionedTextureRegion {
	
	public final TextureRegion textureRegion;
	public final float xOffset;
	public final float yOffset;
	
	private float mScale;
	
	public PositionedTextureRegion(final TextureRegion textureRegion, final float xOffset, final float yOffset) {
		this.textureRegion = textureRegion;
		this.xOffset = xOffset;
		this.yOffset = yOffset;
		this.mScale = 1f;
	}
	
	public float scale() {
		return mScale;
	}
	
	public void setScale(final float scale) {
		mScale = scale;
	}

}
