package com.voyagegames.weatherroute.core.json;



public class JsonString {
	
	public final String json;
	public final int length;
	
	private int mNdx;
	
	public JsonString(final String json, final int curNdx, final int length) {
		if (curNdx < 0 || curNdx > length) {
			throw new IllegalArgumentException("curNdx must not be negative or greater than length");
		}
		
		if (length <= 0) {
			throw new IllegalArgumentException("length must be greater than 0");
		}
		
		this.json = json;
		this.length = length;
		this.mNdx = curNdx;
	}
	
	public int ndx() {
		return mNdx;
	}
	
	public char currentChar() {
		if (mNdx >= length) {
			throw new IllegalArgumentException("Index is out of bounds: " + mNdx);
		}
		
		return json.charAt(mNdx);
	}
	
	public void incrementNdx() {
		incrementNdx(1);
	}
	
	public void incrementNdx(final int count) {
		mNdx += count;
		
		if (mNdx > length) {
			mNdx = length;
		}
	}
	
	public void decrementNdx() {
		if (mNdx == 0) {
			return;
		}
		
		--mNdx;
	}
	
	public void skipWhitespace() {
		if (mNdx >= length) {
			return;
		}
		
		char c = currentChar();
		
		while (mNdx < length && Character.isWhitespace(c)) {
			incrementNdx();
			
			if (mNdx >= length) {
				break;
			}
			
			c = currentChar();
		}
	}
	
	public boolean end() {
		return (mNdx >= length);
	}
	
	public String substring(final int length) {
		return json.substring(mNdx, mNdx + length);
	}
	
}
