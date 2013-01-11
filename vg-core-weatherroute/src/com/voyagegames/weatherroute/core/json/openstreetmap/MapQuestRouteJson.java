package com.voyagegames.weatherroute.core.json.openstreetmap;

import java.util.ArrayList;
import java.util.List;

import com.voyagegames.weatherroute.core.json.JsonObject;
import com.voyagegames.weatherroute.core.json.JsonValue;
import com.voyagegames.weatherroute.core.json.JsonValueType;
import com.voyagegames.weatherroute.core.json.RequiredObject;
import com.voyagegames.weatherroute.core.json.StructuredJson;

public class MapQuestRouteJson extends StructuredJson {
	
	private static final RequiredObject[] REQUIRED_OBJECTS = {
			new RequiredObject("boundingBox", JsonValueType.OBJECT),
			new RequiredObject("computedWaypoints", JsonValueType.ARRAY),
			new RequiredObject("distance", JsonValueType.NUMBER),
			new RequiredObject("formattedTime", JsonValueType.STRING),
			new RequiredObject("fuelUsed", JsonValueType.NUMBER),
			new RequiredObject("hasCountryCross", JsonValueType.BOOLEAN),
			new RequiredObject("hasFerry", JsonValueType.BOOLEAN),
			new RequiredObject("hasHighway", JsonValueType.BOOLEAN),
			new RequiredObject("hasSeasonalClosure", JsonValueType.BOOLEAN),
			new RequiredObject("hasTollRoad", JsonValueType.BOOLEAN),
			new RequiredObject("hasUnpaved", JsonValueType.BOOLEAN),
			new RequiredObject("legs", JsonValueType.ARRAY),
			new RequiredObject("locations", JsonValueType.ARRAY),
			new RequiredObject("locationSequence", JsonValueType.ARRAY),
			new RequiredObject("options", JsonValueType.OBJECT),
			new RequiredObject("realTime", JsonValueType.NUMBER),
			new RequiredObject("sessionId", JsonValueType.STRING),
			new RequiredObject("shape", JsonValueType.OBJECT),
			new RequiredObject("time", JsonValueType.NUMBER)
		};
	
	public final MapQuestBoundingBoxJson boundingBox;
	
	// TODO How is this defined?
	public final List<JsonValue> computedWaypoints;
	public final List<MapQuestLegJson> legs;
	public final List<MapQuestLocationJson> locations;
	public final List<Integer> locationSequence;
	
	public final double distance;
	public final double fuelUsed;
	public final int realTime;
	public final int time;
	
	public final String formattedTime;
	public final String sessionId;
	
	public final JsonObject options;
	public final JsonObject shape;
	
	public MapQuestRouteJson(final JsonObject obj) {
		super(obj, REQUIRED_OBJECTS);
		
		final List<JsonValue> locationSeq = obj.values.get("locationSequence").arrayValue();
		
		for (final JsonValue v : locationSeq) {
			if (v.type() != JsonValueType.NUMBER) {
				throw new IllegalArgumentException("locationSequence value is not a number");
			}
		}
		
		boundingBox = new MapQuestBoundingBoxJson(obj.values.get("boundingBox").objectValue());

		computedWaypoints = obj.values.get("computedWaypoints").arrayValue();
		
		legs = new ArrayList<MapQuestLegJson>();
		locations = new ArrayList<MapQuestLocationJson>();
		
		for (final JsonValue v : obj.values.get("legs").arrayValue()) {
			legs.add(new MapQuestLegJson(v.objectValue()));
		}
		
		for (final JsonValue v : obj.values.get("locations").arrayValue()) {
			locations.add(new MapQuestLocationJson(v.objectValue()));
		}
		
		locationSequence = new ArrayList<Integer>();

		for (final JsonValue v : locationSeq) {
			locationSequence.add((int)v.numberValue());
		}
		
		distance = obj.values.get("distance").numberValue();
		fuelUsed = obj.values.get("fuelUsed").numberValue();
		realTime = (int)obj.values.get("realTime").numberValue();
		time = (int)obj.values.get("time").numberValue();
		
		formattedTime = obj.values.get("formattedTime").stringValue();
		sessionId = obj.values.get("sessionId").stringValue();
		
		options = obj.values.get("options").objectValue();
		shape = obj.values.get("shape").objectValue();
	}

}
