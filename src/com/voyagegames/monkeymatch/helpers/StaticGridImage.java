package com.voyagegames.monkeymatch.helpers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class StaticGridImage {
	
	public final Image image;
	
	public StaticGridImage(final Texture texture, final int viewportWidth, final int viewportHeight) {
        final int width = texture.getWidth();
        final int height = texture.getHeight();
        
        image = new Image(new TextureRegion(texture, 0, 0, width, height));
        image.setPosition((viewportWidth - width) / 2f, (viewportHeight - height) / 2f);
	}

}
