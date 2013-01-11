package com.voyagegames.weatherroute.core;

public interface IState {
	
	public String key();
	public boolean canTransition();
	public String performTransition();

}
