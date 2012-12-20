package com.voyagegames.monkeymatch;

public class StubDataProvider implements IDataProvider {
	
	private int mPersonalBest;

	@Override
	public int personalBest() {
		return mPersonalBest;
	}

	@Override
	public void setPersonalBest(final int score) {
		mPersonalBest = score;
		System.out.println("New personal best: " + score);
	}
	
}
