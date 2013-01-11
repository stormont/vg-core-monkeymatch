package com.voyagegames.weatherroute.core.json.openstreetmap;

import com.voyagegames.weatherroute.core.BoundingBox;
import com.voyagegames.weatherroute.core.json.JsonObject;
import com.voyagegames.weatherroute.core.json.JsonValueType;
import com.voyagegames.weatherroute.core.json.RequiredObject;
import com.voyagegames.weatherroute.core.json.StructuredJson;

public class MapQuestBoundingBoxJson extends StructuredJson {
	
	private static final RequiredObject[] REQUIRED_OBJECTS = {
			new RequiredObject("lr", JsonValueType.OBJECT),
			new RequiredObject("ul", JsonValueType.OBJECT),
		};
	
	public final BoundingBox boundingBox;
	
	public MapQuestBoundingBoxJson(final JsonObject obj) {
		super(obj, REQUIRED_OBJECTS);
		
		final MapQuestLatLongJson lr = new MapQuestLatLongJson(obj.values.get("lr").objectValue());
		final MapQuestLatLongJson ul = new MapQuestLatLongJson(obj.values.get("ul").objectValue());
		
		boundingBox = new BoundingBox(lr.value, ul.value);
	}

}
