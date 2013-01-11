package com.voyagegames.weatherroute.core.json.openstreetmap;

import java.util.ArrayList;
import java.util.List;

import com.voyagegames.weatherroute.core.json.JsonObject;
import com.voyagegames.weatherroute.core.json.JsonValue;
import com.voyagegames.weatherroute.core.json.JsonValueType;
import com.voyagegames.weatherroute.core.json.RequiredObject;
import com.voyagegames.weatherroute.core.json.StructuredJson;

public class MapQuestReverseJson extends StructuredJson {
	
	private static final RequiredObject[] REQUIRED_OBJECTS = {
			new RequiredObject("info", JsonValueType.OBJECT),
			new RequiredObject("options", JsonValueType.OBJECT),
			new RequiredObject("results", JsonValueType.ARRAY)
		};
	
	public final MapQuestInfoJson info;
	public final MapQuestOptionsJson options;
	public final List<MapQuestResultJson> results;
	
	public MapQuestReverseJson(final JsonObject obj) {
		super(obj, REQUIRED_OBJECTS);
		
		info = new MapQuestInfoJson(obj.values.get("info").objectValue());
		options = new MapQuestOptionsJson(obj.values.get("options").objectValue());
		results = new ArrayList<MapQuestResultJson>();
		
		for (final JsonValue v : obj.values.get("results").arrayValue()) {
			results.add(new MapQuestResultJson(v.objectValue()));
		}
	}

}
