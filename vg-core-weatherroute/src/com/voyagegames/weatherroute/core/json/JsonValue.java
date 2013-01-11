package com.voyagegames.weatherroute.core.json;

import java.util.ArrayList;
import java.util.List;

public class JsonValue {
	
	public final String key;

	private JsonValueType   mType;
	private boolean         mBoolean;
	private String          mString;
	private double          mNumber;
	private JsonObject      mObject;
	private List<JsonValue> mArray;
	
	public JsonValue(final String key) {
		this.key = key;
		mType = JsonValueType.NOT_SET;
		mArray = new ArrayList<JsonValue>();
	}
	
	public JsonValueType type() {
		return mType;
	}
	
	public boolean booleanValue() {
		if (mType != JsonValueType.BOOLEAN) {
			throw new IllegalArgumentException("JSON value is not a boolean");
		}
		
		return mBoolean;
	}
	
	public String stringValue() {
		if (mType != JsonValueType.STRING) {
			throw new IllegalArgumentException("JSON value is not a string");
		}
		
		return mString;
	}
	
	public double numberValue() {
		if (mType != JsonValueType.NUMBER) {
			throw new IllegalArgumentException("JSON value is not a number");
		}
		
		return mNumber;
	}
	
	public List<JsonValue> arrayValue() {
		if (mType != JsonValueType.ARRAY) {
			throw new IllegalArgumentException("JSON value is not an array");
		}
		
		return mArray;
	}
	
	public JsonObject objectValue() {
		if (mType != JsonValueType.OBJECT) {
			throw new IllegalArgumentException("JSON value is not an object");
		}
		
		return mObject;
	}
	
	public void setNull() {
		mType = JsonValueType.NULL;
	}
	
	public void setBoolean(final boolean value) {
		mType = JsonValueType.BOOLEAN;
		mBoolean = value;
	}
	
	public void setString(final String value) {
		mType = JsonValueType.STRING;
		mString = value;
	}
	
	public void setNumber(final double value) {
		mType = JsonValueType.NUMBER;
		mNumber = value;
	}
	
	public void setArray(final List<JsonValue> value) {
		mType = JsonValueType.ARRAY;
		mArray = value;
	}
	
	public void setObject(final JsonObject value) {
		mType = JsonValueType.OBJECT;
		mObject = value;
	}
	
}
