package com.voyagegames.weatherroute;

public interface IResultHandler {
	
	public void notifySuccess(int identifier);
	public void notifyFailure(int identifier);

}
