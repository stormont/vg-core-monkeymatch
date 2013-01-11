package com.voyagegames.weatherroute.core;


public interface IXmlHandler <T> {
	
	public void initialize(ILogger logger, T enumerator);
	public T generic();

}
