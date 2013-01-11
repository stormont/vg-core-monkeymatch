package com.voyagegames.weatherroute.core.tests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.voyagegames.weatherroute.core.Utilities;

public class UtilitiesTest {

	@Test
	public void testRoundTo() {
		final double value = 100.123456;
		
		assertTrue(Utilities.roundTo(value, 6) == 100.123456);
		assertTrue(Utilities.roundTo(value, 7) == 100.123456);
		assertTrue(Utilities.roundTo(value, 5) == 100.12346);
		assertTrue(Utilities.roundTo(value, 3) == 100.123);
		assertTrue(Utilities.roundTo(value, 0) == 100.0);
	}

}
