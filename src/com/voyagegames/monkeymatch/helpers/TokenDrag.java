package com.voyagegames.monkeymatch.helpers;


public class TokenDrag {
	
	public final Token token;
	
	private float mX;
	private float mY;
	
	public TokenDrag(final Token token, final float x, final float y) {
		this.token = token;
		this.mX = x;
		this.mY = y;
	}
	
	public void update(final float x, final float y) {
		this.mX = x;
		this.mY = y;
	}
	
	public float x() {
		return mX;
	}
	
	public float y() {
		return mY;
	}

}
