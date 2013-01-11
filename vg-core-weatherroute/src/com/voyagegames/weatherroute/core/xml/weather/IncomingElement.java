package com.voyagegames.weatherroute.core.xml.weather;



public class IncomingElement {
	
	public final String name;
	public final boolean ending;
	
	public IncomingElement(final String name, final boolean ending) {
		this.name = name;
		this.ending = ending;
	}
	
	protected boolean matchTransition(final String name, final boolean ending) {
		if (this.name.contentEquals(name) && this.ending == ending) {
			return true;
		}
		
		return false;
	}
	
}
