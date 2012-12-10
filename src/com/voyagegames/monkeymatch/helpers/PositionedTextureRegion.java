package com.voyagegames.monkeymatch.helpers;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class PositionedTextureRegion {
	
	public final TextureRegion textureRegion;
	public final float xOffset;
	public final float yOffset;
	
	public PositionedTextureRegion(final TextureRegion textureRegion, final float xOffset, final float yOffset) {
		this.textureRegion = textureRegion;
		this.xOffset = xOffset;
		this.yOffset = yOffset;
	}

}
