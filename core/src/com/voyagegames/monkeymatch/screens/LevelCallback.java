package com.voyagegames.monkeymatch.screens;

public interface LevelCallback {
	
	public void levelComplete(int score);
	public void log(String msg);
	public void exitSignaled();

}
