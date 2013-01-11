package com.voyagegames.weatherroute.core.xml.weather;

import java.util.ArrayList;
import java.util.List;

public class WeatherConditionCollection {
	
	public final List<WeatherCondition> conditions;
	
	public WeatherConditionCollection() {
		conditions = new ArrayList<WeatherCondition>();
	}
	
	public void add(final WeatherCondition wc) {
		conditions.add(wc);
	}
	
	public String describe() {
		if (conditions.size() < 1) {
			return null;
		}
		
		final StringBuilder sb = new StringBuilder();
		
		for (int i = 0; i < conditions.size(); ++i) {
			final WeatherCondition wc = conditions.get(i);
			
			if (i > 0) {
				sb.append(" and ");
				
				if (wc.coverage.contentEquals("chance") || wc.coverage.contentEquals("slight chance")) {
					sb.append("a ");
				}
			}
			
			sb.append(wc.describe());
		}
		
		final String basicResult = sb.toString();
		final String result = Character.toUpperCase(basicResult.charAt(0)) + basicResult.substring(1);
		
		return result;
	}

}
