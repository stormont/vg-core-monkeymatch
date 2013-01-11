package com.voyagegames.weatherroute.core.json;


public class RequiredObject {
	
	public final String key;
	public final JsonValueType type;
	
	public RequiredObject(final String key, final JsonValueType type) {
		this.key = key;
		this.type = type;
	}

}
