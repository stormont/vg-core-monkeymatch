package com.voyagegames.weatherroute.core.tests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.voyagegames.weatherroute.core.LatLong;

public class LatLongTest {

	@Test
	public void testLatLong() {
		final LatLong ll = new LatLong(5.0, -10.0);
		assertTrue(ll.latitude == 5.0);
		assertTrue(ll.longitude == -10.0);
	}

}
