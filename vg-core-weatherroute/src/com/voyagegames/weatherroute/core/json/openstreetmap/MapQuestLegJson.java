package com.voyagegames.weatherroute.core.json.openstreetmap;

import java.util.ArrayList;
import java.util.List;

import com.voyagegames.weatherroute.core.json.JsonObject;
import com.voyagegames.weatherroute.core.json.JsonValue;
import com.voyagegames.weatherroute.core.json.JsonValueType;
import com.voyagegames.weatherroute.core.json.RequiredObject;
import com.voyagegames.weatherroute.core.json.StructuredJson;

public class MapQuestLegJson extends StructuredJson {
	
	private static final RequiredObject[] REQUIRED_OBJECTS = {
			new RequiredObject("destIndex", JsonValueType.NUMBER),
			new RequiredObject("destNarrative", JsonValueType.STRING),
			new RequiredObject("distance", JsonValueType.NUMBER),
			new RequiredObject("formattedTime", JsonValueType.STRING),
			new RequiredObject("hasCountryCross", JsonValueType.BOOLEAN),
			new RequiredObject("hasFerry", JsonValueType.BOOLEAN),
			new RequiredObject("hasHighway", JsonValueType.BOOLEAN),
			new RequiredObject("hasSeasonalClosure", JsonValueType.BOOLEAN),
			new RequiredObject("hasTollRoad", JsonValueType.BOOLEAN),
			new RequiredObject("hasUnpaved", JsonValueType.BOOLEAN),
			new RequiredObject("index", JsonValueType.NUMBER),
			new RequiredObject("maneuvers", JsonValueType.ARRAY),
			new RequiredObject("origIndex", JsonValueType.NUMBER),
			new RequiredObject("origNarrative", JsonValueType.STRING),
			new RequiredObject("roadGradeStrategy", JsonValueType.ARRAY),
			new RequiredObject("time", JsonValueType.NUMBER)
		};

	public final double distance;
	
	public final int destIndex;
	public final int index;
	public final int origIndex;
	public final int time;
	
	public final boolean hasCountryCross;
	public final boolean hasFerry;
	public final boolean hasHighway;
	public final boolean hasSeasonalClosure;
	public final boolean hasTollRoad;
	public final boolean hasUnpaved;
	
	public final String destNarrative;
	public final String formattedTime;
	public final String origNarrative;
	
	public final List<MapQuestManeuverJson> maneuvers;
	public final List<JsonValue> roadGradeStrategy;
	
	public MapQuestLegJson(final JsonObject obj) {
		super(obj, REQUIRED_OBJECTS);
		
		distance = obj.values.get("distance").numberValue();
		
		destIndex = (int)obj.values.get("destIndex").numberValue();
		index = (int)obj.values.get("index").numberValue();
		origIndex = (int)obj.values.get("origIndex").numberValue();
		time = (int)obj.values.get("time").numberValue();
		
		hasCountryCross = obj.values.get("hasCountryCross").booleanValue();
		hasFerry = obj.values.get("hasFerry").booleanValue();
		hasHighway = obj.values.get("hasHighway").booleanValue();
		hasSeasonalClosure = obj.values.get("hasSeasonalClosure").booleanValue();
		hasTollRoad = obj.values.get("hasTollRoad").booleanValue();
		hasUnpaved = obj.values.get("hasUnpaved").booleanValue();
		
		destNarrative = obj.values.get("destNarrative").stringValue();
		formattedTime = obj.values.get("formattedTime").stringValue();
		origNarrative = obj.values.get("origNarrative").stringValue();
		
		maneuvers = new ArrayList<MapQuestManeuverJson>();
		
		for (final JsonValue v : obj.values.get("maneuvers").arrayValue()) {
			maneuvers.add(new MapQuestManeuverJson(v.objectValue()));
		}
		
		roadGradeStrategy = obj.values.get("roadGradeStrategy").arrayValue();
	}
	
}
