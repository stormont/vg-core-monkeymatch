package com.voyagegames.weatherroute.core.json;

import java.util.HashMap;
import java.util.Map;

public class JsonObject {

	public final Map<String, JsonValue> values = new HashMap<String, JsonValue>();
	
	public boolean validate(final String key, final JsonValueType type) {
		if (!values.containsKey(key) || values.get(key).type() != type) {
			return false;
		}
		
		return true;
	}
	
	public double parseQuotedDouble(final String s, final String key) {
		try {
			return Double.parseDouble(s.substring(0, s.length()));
		} catch (final NumberFormatException e) {
			throw new IllegalArgumentException(key + " is not a double-precision value");
		}
	}
	
	public int parseQuotedInt(final String s, final String key) {
		try {
			return Integer.parseInt(s.substring(0, s.length()));
		} catch (final NumberFormatException e) {
			throw new IllegalArgumentException(key + " is not an integer value");
		}
	}
	
	public String debug() {
		final StringBuilder builder = new StringBuilder();
		builder.append("Values:\n");
		
		for (final JsonValue v : values.values()) {
			builder.append(v.key + " (" + v.type().toString() + "): " + debugValue(v) + "\n");
		}
		
		return builder.toString();
	}
	
	private String debugValue(final JsonValue value) {
		switch (value.type()) {
		case NULL:
			return "null";
		case BOOLEAN:
			return String.valueOf(value.booleanValue());
		case STRING:
			return value.stringValue();
		case NUMBER:
			return String.valueOf(value.numberValue());
		case ARRAY:
			String s = "";
			
			for (final JsonValue v : value.arrayValue()) {
				s += debugValue(v) + ", ";
			}

			return s;
		case OBJECT:
			return value.objectValue().debug();
		}
		
		return null;
	}
	
}
