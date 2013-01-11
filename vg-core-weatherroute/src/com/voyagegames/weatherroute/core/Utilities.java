package com.voyagegames.weatherroute.core;

public class Utilities {
	
	public static double roundTo(final double value, final int places) {
	    if (places < 0) {
	    	throw new IllegalArgumentException("Decimal places specified was less than 0");
	    }

	    final long factor = (long) Math.pow(10, places);
	    final long tmp = Math.round(value * factor);
	    
	    return (double) tmp / factor;
	}

}
