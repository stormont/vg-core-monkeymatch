package com.voyagegames.monkeymatch.helpers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;

public class TextureManager {

    public final Texture[] digits;
    public final Texture[] grids;
    public final Texture[] tokens;
    public final Texture[] goldTokens;
    
    public Texture background;
    public Texture border;
    public Texture highlight;
    public Texture bonus;
    public Texture points;
    public Texture trophy;
    public Texture start;
    public Texture title;
    public Texture gridBackground;
    
    public TextureManager(final int maxGrids, final int maxTokens) {
    	digits = new Texture[10];
    	grids = new Texture[maxGrids];
    	tokens = new Texture[maxTokens];
    	goldTokens = new Texture[maxTokens];
    }
    
    public void initialize() {
        background = new Texture("backgrounds/bkgd_tutorial.png");
        background.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        
        border = new Texture("backgrounds/bkgd_border.png");
        border.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        
        highlight = new Texture("tokens/highlight.png");
        highlight.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        
        bonus = new Texture("tokens/banana.png");
        bonus.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        
        points = new Texture("tokens/bananas.png");
        points.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        
        trophy = new Texture("misc/trophy.png");
        trophy.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        
        start = new Texture("tokens/monkey.png");
        start.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        
        title = new Texture("misc/title.png");
        title.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        
        for (int i = 0; i < digits.length; ++i) {
        	digits[i] = new Texture("misc/digits" + i + ".png");
        	digits[i].setFilter(TextureFilter.Linear, TextureFilter.Linear);
        }
    }
    
    public void disposeDynamic() {
    	if (gridBackground != null) {
    		gridBackground.dispose();
    		gridBackground = null;
    	}
    	
    	for (int i = 0; i < grids.length; ++i) {
    		final Texture t = grids[i];
    		
    		if (t != null) {
    			t.dispose();
    			grids[i] = null;
    		}
    	}
    	
    	for (int i = 0; i < tokens.length; ++i) {
    		final Texture t = tokens[i];
    		
    		if (t != null) {
    			t.dispose();
    			tokens[i] = null;
    		}
    	}
    	
    	for (int i = 0; i < goldTokens.length; ++i) {
    		final Texture t = goldTokens[i];
    		
    		if (t != null) {
    			t.dispose();
    			goldTokens[i] = null;
    		}
    	}
    }
    
    public void disposeAll() {
    	disposeDynamic();
    	
		background.dispose();
		border.dispose();
		highlight.dispose();
		bonus.dispose();
		points.dispose();
		trophy.dispose();
		start.dispose();
		title.dispose();
		
		for (final Texture t : digits) {
			t.dispose();
		}
    }
    
}
