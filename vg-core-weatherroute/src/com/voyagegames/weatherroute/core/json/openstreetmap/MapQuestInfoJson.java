package com.voyagegames.weatherroute.core.json.openstreetmap;

import java.util.ArrayList;
import java.util.List;

import com.voyagegames.weatherroute.core.json.JsonObject;
import com.voyagegames.weatherroute.core.json.JsonValue;
import com.voyagegames.weatherroute.core.json.JsonValueType;
import com.voyagegames.weatherroute.core.json.RequiredObject;
import com.voyagegames.weatherroute.core.json.StructuredJson;

public class MapQuestInfoJson extends StructuredJson {
	
	private static final RequiredObject[] REQUIRED_OBJECTS = {
			new RequiredObject("copyright", JsonValueType.OBJECT),
			new RequiredObject("messages", JsonValueType.ARRAY),
			new RequiredObject("statuscode", JsonValueType.NUMBER)
		};
	
	public final MapQuestCopyrightJson copyright;
	public final List<String> messages;
	public final int statuscode;
	
	public MapQuestInfoJson(final JsonObject obj) {
		super(obj, REQUIRED_OBJECTS);
		
		final List<JsonValue> values = obj.values.get("messages").arrayValue();
		
		for (final JsonValue v : values) {
			if (v.type() != JsonValueType.STRING) {
				throw new IllegalArgumentException("Message value is not a string");
			}
		}
		
		messages = new ArrayList<String>();

		for (final JsonValue v : values) {
			messages.add(v.stringValue());
		}
		
		copyright = new MapQuestCopyrightJson(obj.values.get("copyright").objectValue());
		statuscode = (int)obj.values.get("statuscode").numberValue();
	}

}
