package com.voyagegames.monkeymatch.helpers;

import com.badlogic.gdx.scenes.scene2d.Actor;

public class TokenDrag {
	
	public final Actor token;
	
	private float mX;
	private float mY;
	
	public TokenDrag(final Actor token, final float x, final float y) {
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
