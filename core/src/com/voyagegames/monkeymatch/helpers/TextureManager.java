package com.voyagegames.monkeymatch.helpers;


public class TextureManager {

    public final BundledTexture[] digits;
    public final BundledTexture[] grids;
    public final BundledTexture[] tokens;
    public final BundledTexture[] goldTokens;
    
    public BundledTexture background;
    public BundledTexture border;
    public BundledTexture highlight;
    public BundledTexture bonus;
    public BundledTexture points;
    public BundledTexture trophy;
    public BundledTexture start;
    public BundledTexture title;
    public BundledTexture logo;
    public BundledTexture options;
    public BundledTexture gridBackground;
    
    public TextureManager(final int maxGrids, final int maxTokens) {
    	digits = new BundledTexture[10];
    	grids = new BundledTexture[maxGrids];
    	tokens = new BundledTexture[maxTokens];
    	goldTokens = new BundledTexture[maxTokens];
    }
    
    public void initialize() {
        background = new BundledTexture("backgrounds/bkgd_tutorial.png", 800, 600);
        border = new BundledTexture("backgrounds/bkgd_border.png", 600, 450);
        highlight = new BundledTexture("tokens/highlight.png", 128, 128);
        bonus = new BundledTexture("tokens/banana.png", 128, 128);
        points = new BundledTexture("tokens/bananas.png", 128, 128);
        trophy = new BundledTexture("misc/trophy.png", 256, 256);
        start = new BundledTexture("tokens/monkey.png", 128, 128);
        title = new BundledTexture("misc/title.png", 468, 75);
        logo = new BundledTexture("misc/logo.png", 128, 128);
        options = new BundledTexture("misc/gear.png", 128, 128);

        digits[0] = new BundledTexture("misc/digits0.png", 96, 120);
        digits[1] = new BundledTexture("misc/digits1.png", 66, 120);
        digits[2] = new BundledTexture("misc/digits2.png", 84, 120);
        digits[3] = new BundledTexture("misc/digits3.png", 84, 120);
        digits[4] = new BundledTexture("misc/digits4.png", 97, 120);
        digits[5] = new BundledTexture("misc/digits5.png", 90, 120);
        digits[6] = new BundledTexture("misc/digits6.png", 88, 120);
        digits[7] = new BundledTexture("misc/digits7.png", 98, 120);
        digits[8] = new BundledTexture("misc/digits8.png", 88, 120);
        digits[9] = new BundledTexture("misc/digits9.png", 91, 120);
    }
    
    public void disposeDynamic() {
    	if (gridBackground != null) {
    		gridBackground.texture.dispose();
    		gridBackground = null;
    	}
    	
    	for (int i = 0; i < grids.length; ++i) {
    		final BundledTexture t = grids[i];
    		
    		if (t != null) {
    			t.texture.dispose();
    			grids[i] = null;
    		}
    	}
    	
    	for (int i = 0; i < tokens.length; ++i) {
    		final BundledTexture t = tokens[i];
    		
    		if (t != null) {
    			t.texture.dispose();
    			tokens[i] = null;
    		}
    	}
    	
    	for (int i = 0; i < goldTokens.length; ++i) {
    		final BundledTexture t = goldTokens[i];
    		
    		if (t != null) {
    			t.texture.dispose();
    			goldTokens[i] = null;
    		}
    	}
    }
    
    public void disposeAll() {
    	disposeDynamic();
    	
		background.texture.dispose();
		border.texture.dispose();
		highlight.texture.dispose();
		bonus.texture.dispose();
		points.texture.dispose();
		trophy.texture.dispose();
		start.texture.dispose();
		title.texture.dispose();
		logo.texture.dispose();
		options.texture.dispose();
		
		for (final BundledTexture t : digits) {
			t.texture.dispose();
		}
    }
    
}
