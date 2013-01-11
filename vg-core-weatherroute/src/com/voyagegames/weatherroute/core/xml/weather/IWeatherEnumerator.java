package com.voyagegames.weatherroute.core.xml.weather;

import java.util.Map;

public interface IWeatherEnumerator {
	
	public void addStartValidTime(String time);
	public void addValue(String value, Map<String, String> attributes);
	public void addIconLink(String link);
	
	public void signalTimeLayoutsCompleted();
	public void signalComplete();

	public boolean temperatureEnumerating();
	public boolean weatherConditionEnumerating();
	
	public void setTemperatureEnumerating(boolean enumerating);
	public void setWeatherConditionEnumerating(boolean enumerating);

}
