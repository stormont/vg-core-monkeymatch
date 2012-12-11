package com.voyagegames.monkeymatch.helpers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class BackgroundImage {
	
	public final PositionedTextureRegion region;
	
	public BackgroundImage(final Texture texture) {
        final TextureRegion background = new TextureRegion(texture);
        region = new PositionedTextureRegion(background, -background.getRegionWidth() / 2f, -background.getRegionHeight() / 2f);
	}

}
