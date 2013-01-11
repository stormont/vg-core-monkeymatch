package com.voyagegames.weatherroute.core.json.openstreetmap;

public class Direction {

	public enum DirectionValue {
		NONE,
		NORTH,
		NORTHWEST,
		NORTHEAST,
		SOUTH,
		SOUTHEAST,
		SOUTHWEST,
		WEST,
		EAST
	};

	public static DirectionValue map(final int direction) {
		switch (direction) {
		case 0:
			return DirectionValue.NONE;
		case 1:
			return DirectionValue.NORTH;
		case 2:
			return DirectionValue.NORTHWEST;
		case 3:
			return DirectionValue.NORTHEAST;
		case 4:
			return DirectionValue.SOUTH;
		case 5:
			return DirectionValue.SOUTHEAST;
		case 6:
			return DirectionValue.SOUTHWEST;
		case 7:
			return DirectionValue.WEST;
		case 8:
			return DirectionValue.EAST;
		default:
			throw new IllegalArgumentException("Unknown direction: " + direction);
		}
	}

}
