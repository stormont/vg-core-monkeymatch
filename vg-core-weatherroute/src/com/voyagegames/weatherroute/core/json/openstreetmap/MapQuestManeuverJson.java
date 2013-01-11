package com.voyagegames.weatherroute.core.json.openstreetmap;

import java.util.ArrayList;
import java.util.List;

import com.voyagegames.weatherroute.core.json.JsonObject;
import com.voyagegames.weatherroute.core.json.JsonValue;
import com.voyagegames.weatherroute.core.json.JsonValueType;
import com.voyagegames.weatherroute.core.json.RequiredObject;
import com.voyagegames.weatherroute.core.json.StructuredJson;
import com.voyagegames.weatherroute.core.json.openstreetmap.Direction.DirectionValue;

public class MapQuestManeuverJson extends StructuredJson {
	
	private static final RequiredObject[] REQUIRED_OBJECTS = {
			new RequiredObject("attributes", JsonValueType.NUMBER),
			new RequiredObject("direction", JsonValueType.NUMBER),
			new RequiredObject("directionName", JsonValueType.STRING),
			new RequiredObject("distance", JsonValueType.NUMBER),
			new RequiredObject("formattedTime", JsonValueType.STRING),
			new RequiredObject("iconUrl", JsonValueType.STRING),
			new RequiredObject("index", JsonValueType.NUMBER),
			new RequiredObject("linkIds", JsonValueType.ARRAY),
			new RequiredObject("maneuverNotes", JsonValueType.ARRAY),
			new RequiredObject("narrative", JsonValueType.STRING),
			new RequiredObject("signs", JsonValueType.ARRAY),
			new RequiredObject("startPoint", JsonValueType.OBJECT),
			new RequiredObject("streets", JsonValueType.ARRAY),
			new RequiredObject("time", JsonValueType.NUMBER),
			new RequiredObject("transportMode", JsonValueType.STRING),
			new RequiredObject("turnType", JsonValueType.NUMBER)
		};

	public final String directionName;
	public final String formattedTime;
	public final String iconUrl;
	public final String mapUrl;
	public final String narrative;
	public final String transportMode;
	
	public final int attributes;
	public final int index;
	public final int time;
	public final int turnType;
	
	public final DirectionValue direction;
	
	public final List<Integer> linkIds;
	public final List<String> maneuverNotes;
	public final List<String> streets;
	public final List<MapQuestSignJson> signs;
	
	public final double distance;
	
	public final MapQuestLatLongJson startPoint;
	
	public MapQuestManeuverJson(final JsonObject obj) {
		super(obj, REQUIRED_OBJECTS);
		
		if (obj.values.containsKey("mapUrl")) {
			validate(obj, "mapUrl", JsonValueType.STRING);
			mapUrl = obj.values.get("mapUrl").stringValue();
		} else {
			mapUrl = null;
		}
		
		directionName = obj.values.get("directionName").stringValue();
		formattedTime = obj.values.get("formattedTime").stringValue();
		iconUrl = obj.values.get("iconUrl").stringValue();
		narrative = obj.values.get("narrative").stringValue();
		transportMode = obj.values.get("transportMode").stringValue();
		
		attributes = (int)obj.values.get("attributes").numberValue();
		index = (int)obj.values.get("index").numberValue();
		time = (int)obj.values.get("time").numberValue();
		turnType = (int)obj.values.get("turnType").numberValue();
		
		direction = Direction.map((int)obj.values.get("direction").numberValue());

		linkIds = mapIntList(obj.values.get("linkIds").arrayValue());
		maneuverNotes = mapStringList(obj.values.get("maneuverNotes").arrayValue());
		streets = mapStringList(obj.values.get("streets").arrayValue());
		
		signs = new ArrayList<MapQuestSignJson>();
		
		for (final JsonValue v : obj.values.get("signs").arrayValue()) {
			if (v.type() != JsonValueType.OBJECT) {
				throw new IllegalArgumentException(v.key + " is not an OBJECT value");
			}
			
			signs.add(new MapQuestSignJson(v.objectValue()));
		}
		
		distance = obj.values.get("distance").numberValue();
		
		startPoint = new MapQuestLatLongJson(obj.values.get("startPoint").objectValue());
	}

}
