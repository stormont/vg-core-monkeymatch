package com.voyagegames.monkeymatch.helpers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class BundledTexture {
	
	public final Texture texture;
	public final TextureRegion region;
	
	public BundledTexture(final String texturePath, final int width, final int height) {
		this.texture = new Texture(texturePath);
        this.texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		this.region = new TextureRegion(texture, 0, 0, width, height);
	}
	
	public BundledTexture(final String texturePath, final int width, final int height, final int offsetX, final int offsetY) {
		this.texture = new Texture(texturePath);
        this.texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		this.region = new TextureRegion(texture, offsetX, offsetY, width, height);
	}
	
	public float getWidth() {
		return region.getRegionWidth();
	}
	
	public float getHeight() {
		return region.getRegionHeight();
	}

}
