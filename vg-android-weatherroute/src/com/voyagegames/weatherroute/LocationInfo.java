package com.voyagegames.weatherroute;

public class LocationInfo {
	
	public final String city;
	public final String county;
	public final String state;
	public final String countryCode;
	
	public LocationInfo(final String county, final String state, final String countryCode) {
		this.city = null;
		this.county = county;
		this.state = state;
		this.countryCode = countryCode;
	}
	
	public LocationInfo(final String city, final String county, final String state, final String countryCode) {
		this.city = city;
		this.county = county;
		this.state = state;
		this.countryCode = countryCode;
	}

}
