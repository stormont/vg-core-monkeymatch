package com.voyagegames.weatherroute.core.xml.weather;

import java.util.List;


public class WeatherSnapshot {
	
	public final String startTime;
	public final String iconLink;
	public final int temperature;
	public final WeatherConditionCollection conditions;
	
	public WeatherSnapshot(final String startTime, final String iconLink, final int temperature, final List<WeatherCondition> conditions) {
		this.startTime = startTime;
		this.iconLink = iconLink;
		this.temperature = temperature;
		
		this.conditions = new WeatherConditionCollection();
		
		for (final WeatherCondition wc : conditions) {
			this.conditions.add(wc);
		}
	}

}
