package com.voyagegames.weatherroute.core.json.openstreetmap;

import com.voyagegames.weatherroute.core.json.JsonObject;
import com.voyagegames.weatherroute.core.json.JsonValueType;
import com.voyagegames.weatherroute.core.json.RequiredObject;
import com.voyagegames.weatherroute.core.json.StructuredJson;

public class MapQuestLocationJson extends StructuredJson {
	
	public enum LocationType {
		STOP,
		VIA
	}
	
	public enum SideOfStreet {
		LEFT,
		RIGHT,
		NONE
	}
	
	private static final RequiredObject[] REQUIRED_OBJECTS = {
			new RequiredObject("adminArea1", JsonValueType.STRING),
			new RequiredObject("adminArea1Type", JsonValueType.STRING),
			new RequiredObject("adminArea3", JsonValueType.STRING),
			new RequiredObject("adminArea3Type", JsonValueType.STRING),
			new RequiredObject("adminArea4", JsonValueType.STRING),
			new RequiredObject("adminArea4Type", JsonValueType.STRING),
			new RequiredObject("adminArea5", JsonValueType.STRING),
			new RequiredObject("adminArea5Type", JsonValueType.STRING),
			new RequiredObject("displayLatLng", JsonValueType.OBJECT),
			new RequiredObject("dragPoint", JsonValueType.BOOLEAN),
			new RequiredObject("geocodeQuality", JsonValueType.STRING),
			new RequiredObject("geocodeQualityCode", JsonValueType.STRING),
			new RequiredObject("latLng", JsonValueType.OBJECT),
			new RequiredObject("linkId", JsonValueType.NUMBER),
			new RequiredObject("postalCode", JsonValueType.STRING),
			new RequiredObject("sideOfStreet", JsonValueType.STRING),
			new RequiredObject("street", JsonValueType.STRING),
			new RequiredObject("type", JsonValueType.STRING)
		};
	
	public final String adminArea1;
	public final String adminArea1Type;
	public final String adminArea3;
	public final String adminArea3Type;
	public final String adminArea4;
	public final String adminArea4Type;
	public final String adminArea5;
	public final String adminArea5Type;
	public final String geocodeQuality;
	public final String geocodeQualityCode;
	public final String postalCode;
	public final String street;
	
	public final LocationType type;
	public final SideOfStreet sideOfStreet;

	public final MapQuestLatLongJson displayLatLng;
	public final MapQuestLatLongJson latLng;
	
	public final boolean dragPoint;
	
	public final int linkId;

	public MapQuestLocationJson(final JsonObject obj) {
		super(obj, REQUIRED_OBJECTS);
		
		final String t = obj.values.get("type").stringValue();
		
		if (t.contentEquals("s")) {
			type = LocationType.STOP;
		} else if (t.contentEquals("v")) {
			type = LocationType.VIA;
		} else {
			throw new IllegalArgumentException("Unexpected location type: " + t);
		}
		
		final String s = obj.values.get("sideOfStreet").stringValue();
		
		if (s.contentEquals("L")) {
			sideOfStreet = SideOfStreet.LEFT;
		} else if (s.contentEquals("R")) {
			sideOfStreet = SideOfStreet.RIGHT;
		} else if (s.contentEquals("N")) {
			sideOfStreet = SideOfStreet.NONE;
		} else {
			throw new IllegalArgumentException("Unexpected location side of street: " + s);
		}
		
		adminArea1 = obj.values.get("adminArea1").stringValue();
		adminArea1Type = obj.values.get("adminArea1Type").stringValue();
		adminArea3 = obj.values.get("adminArea3").stringValue();
		adminArea3Type = obj.values.get("adminArea3Type").stringValue();
		adminArea4 = obj.values.get("adminArea4").stringValue();
		adminArea4Type = obj.values.get("adminArea4Type").stringValue();
		adminArea5 = obj.values.get("adminArea5").stringValue();
		adminArea5Type = obj.values.get("adminArea5Type").stringValue();
		geocodeQuality = obj.values.get("geocodeQuality").stringValue();
		geocodeQualityCode = obj.values.get("geocodeQualityCode").stringValue();
		postalCode = obj.values.get("postalCode").stringValue();
		street = obj.values.get("street").stringValue();

		displayLatLng = new MapQuestLatLongJson(obj.values.get("displayLatLng").objectValue());
		latLng = new MapQuestLatLongJson(obj.values.get("latLng").objectValue());
		
		dragPoint = obj.values.get("dragPoint").booleanValue();
		
		linkId = (int)obj.values.get("linkId").numberValue();
	}
	
	public String city() {
		return adminArea5;
	}
	
	public String county() {
		return adminArea4;
	}
	
	public String state() {
		return adminArea3;
	}
	
	public String country() {
		return adminArea1;
	}
	
}
