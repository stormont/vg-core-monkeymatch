package com.voyagegames.monkeymatch.helpers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class DynamicGridImage {
	
	public final Image image;
	
	public DynamicGridImage(final Texture texture, final float gridX, final float gridY, final float xOffset, final float yOffset) {
        image = new Image(new TextureRegion(texture, 0, 0, texture.getWidth(), texture.getHeight()));
        image.setPosition(gridX + xOffset, gridY + yOffset);
	}

}
