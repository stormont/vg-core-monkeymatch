package com.voyagegames.weatherroute.core.json.openstreetmap;

import com.voyagegames.weatherroute.core.json.JsonObject;
import com.voyagegames.weatherroute.core.json.JsonValueType;
import com.voyagegames.weatherroute.core.json.RequiredObject;
import com.voyagegames.weatherroute.core.json.StructuredJson;
import com.voyagegames.weatherroute.core.json.openstreetmap.Direction.DirectionValue;

public class MapQuestSignJson extends StructuredJson {
	
	private static final RequiredObject[] REQUIRED_OBJECTS = {
			new RequiredObject("direction", JsonValueType.NUMBER),
			new RequiredObject("extraText", JsonValueType.STRING),
			new RequiredObject("text", JsonValueType.STRING),
			new RequiredObject("type", JsonValueType.NUMBER)
		};
	
	public final String url;
	public final String extraText;
	public final String text;
	
	public final DirectionValue direction;
	public final int type;
	
	public MapQuestSignJson(final JsonObject obj) {
		super(obj, REQUIRED_OBJECTS);
		
		if (obj.values.containsKey("url")) {
			validate(obj, "url", JsonValueType.STRING);
			url = obj.values.get("url").stringValue();
		} else {
			url = null;
		}
		
		extraText = obj.values.get("extraText").stringValue();
		text = obj.values.get("text").stringValue();
		
		direction = Direction.map((int)obj.values.get("direction").numberValue());
		
		type = (int)obj.values.get("type").numberValue();
	}

}
