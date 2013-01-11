package com.voyagegames.weatherroute.core.json.tests;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.voyagegames.weatherroute.core.json.JsonString;

public class JsonStringTest {
	
	private static final String TEST_VALUE = "Test string";
	
	private JsonString mStr;

	@Before
	public void setUp() throws Exception {
		mStr = new JsonString(TEST_VALUE, 0, TEST_VALUE.length());
	}

	@Test
	public void testJsonString() {
		assertTrue(mStr.json == TEST_VALUE);
		assertTrue(mStr.length == TEST_VALUE.length());
		assertTrue(mStr.ndx() == 0);
	}

	@Test
	public void testNdx() {
		assertTrue(mStr.ndx() == 0);
		
		mStr.incrementNdx();
		assertTrue(mStr.ndx() == 1);
		
		mStr.incrementNdx(TEST_VALUE.length() + 10);
		assertTrue(mStr.ndx() == TEST_VALUE.length());
	}

	@Test
	public void testCurrentChar() {
		assertTrue(mStr.currentChar() == 'T');
	}

	@Test
	public void testIncrementNdx() {
		testNdx();
	}

	@Test
	public void testDecrementNdx() {
		assertTrue(mStr.ndx() == 0);

		mStr.incrementNdx();
		assertTrue(mStr.ndx() == 1);
		
		mStr.decrementNdx();
		assertTrue(mStr.ndx() == 0);
		
		mStr.decrementNdx();
		assertTrue(mStr.ndx() == 0);
	}

	@Test
	public void testSkipWhitespace() {
		mStr.incrementNdx(4);
		assertTrue(mStr.ndx() == 4);
		
		mStr.skipWhitespace();
		assertTrue(mStr.ndx() == 5);
	}

	@Test
	public void testEnd() {
		assertTrue(mStr.end() == false);
		
		mStr.incrementNdx(TEST_VALUE.length());
		assertTrue(mStr.end() == true);
	}

	@Test
	public void testSubstring() {
		assertTrue(mStr.substring(4).contentEquals("Test"));
	}

}
