package com.voyagegames.weatherroute.core.json;

import java.util.ArrayList;
import java.util.List;


public abstract class StructuredJson {
	
	public StructuredJson(final JsonObject obj, final RequiredObject[] reqObjects) {
		if (obj == null) {
			throw new IllegalArgumentException("JsonObject is null");
		}
		
		for (final RequiredObject o : reqObjects) {
			validate(obj, o.key, o.type);
		}
	}
	
	protected void validate(final JsonObject obj, final String key, final JsonValueType type) {
		if (!obj.validate(key, type)) {
			if (obj.values.containsKey(key)) {
				throw new IllegalArgumentException(key + " definition is not a " + type.toString() + "; found a " + obj.values.get(key).type().toString());
			} else {
				throw new IllegalArgumentException(key + " definition is missing");
			}
		}
	}
	
	protected List<Double> mapDoubleList(final List<JsonValue> values) {
		final List<Double> result = new ArrayList<Double>();
		
		for (final JsonValue v : values) {
			if (v.type() != JsonValueType.NUMBER) {
				throw new IllegalArgumentException(v.key + " is not a NUMBER value: " + v.type().toString());
			}
			
			result.add(v.numberValue());
		}
		
		return result;
	}
	
	protected List<Integer> mapIntList(final List<JsonValue> values) {
		final List<Integer> result = new ArrayList<Integer>();
		
		for (final JsonValue v : values) {
			if (v.type() != JsonValueType.NUMBER) {
				throw new IllegalArgumentException(v.key + " is not a NUMBER value: " + v.type().toString());
			}
			
			result.add((int)v.numberValue());
		}
		
		return result;
	}
	
	protected List<String> mapStringList(final List<JsonValue> values) {
		final List<String> result = new ArrayList<String>();
		
		for (final JsonValue v : values) {
			if (v.type() != JsonValueType.STRING) {
				throw new IllegalArgumentException(v.key + " is not a STRING value: " + v.type().toString());
			}
			
			result.add(v.stringValue());
		}
		
		return result;
	}

}
