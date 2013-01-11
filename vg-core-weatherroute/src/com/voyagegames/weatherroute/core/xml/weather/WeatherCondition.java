package com.voyagegames.weatherroute.core.xml.weather;

import java.util.Map;

public class WeatherCondition {
	
	public final WeatherConditionType type;
	public final String coverage;
	public final String intensity;
	public final String weatherType;
	
	public WeatherCondition(final Map<String, String> attributes) {
		if (attributes == null) {
			type = WeatherConditionType.SINGLE;
			coverage = "definitely";
			intensity = "none";
			weatherType = "sunny";
			return;
		}
		
		if (!(attributes.containsKey("coverage") && attributes.containsKey("intensity") && attributes.containsKey("weather-type"))) {
			throw new IllegalArgumentException("Weather descriptor missing required attributes");
		}
		
		if (attributes.containsKey("additive")) {
			type = WeatherConditionType.ADDITIVE;
		} else {
			type = WeatherConditionType.SINGLE;
		}
		
		coverage = attributes.get("coverage");
		intensity = attributes.get("intensity");
		weatherType = attributes.get("weather-type");
	}
	
	public String describe() {
		String coverageDesc = coverage;
		
		if (coverage.contentEquals("definitely")) {
			coverageDesc = "";
		} else {
			final int covDescLen = coverageDesc.length();
			
			if (
					covDescLen >= 6 &&
					coverageDesc.charAt(coverageDesc.length() - 1) != 'y' &&
					coverageDesc.substring(coverageDesc.length() - 6).contentEquals("chance")) {
				coverageDesc += " of";
			}
			
			coverageDesc += " ";
		}
		
		if (intensity.contentEquals("none")) {
			return (coverageDesc + weatherType);
		} else {
			return (coverageDesc + intensity + " " + weatherType);
		}
	}

}
