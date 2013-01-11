package com.voyagegames.weatherroute.core;

public interface IController {

	public void notifyCompletion();
	public void notifyCompletion(int identifier);
	public void notifyFailure();
	public void notifyFailure(int identifier);

}
