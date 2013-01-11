package com.voyagegames.weatherroute.core;


public class BoundingBox {
	
	public final LatLong lowerRight;
	public final LatLong upperLeft;
	
	public BoundingBox(final LatLong lowerRight, final LatLong upperLeft) {
		this.lowerRight = lowerRight;
		this.upperLeft = upperLeft;
	}

}
