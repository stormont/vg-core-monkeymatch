package com.voyagegames.weatherroute.core.json.tests;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.voyagegames.weatherroute.core.json.JsonObject;
import com.voyagegames.weatherroute.core.json.JsonValue;
import com.voyagegames.weatherroute.core.json.JsonValueType;

public class JsonValueTest {
	
	private static final String TEST_KEY = "test"; 
	
	private JsonValue mValue;

	@Before
	public void setUp() throws Exception {
		mValue = new JsonValue(TEST_KEY);
	}

	@Test
	public void testJsonValue() {
		assertTrue(mValue.key == TEST_KEY);
		assertTrue(mValue.type() == JsonValueType.NOT_SET);
	}

	@Test
	public void testType() {
		assertTrue(mValue.type() == JsonValueType.NOT_SET);
	}

	@Test
	public void testBooleanValue() {
		assertTrue(mValue.type() == JsonValueType.NOT_SET);
		
		mValue.setBoolean(true);
		
		assertTrue(mValue.type() == JsonValueType.BOOLEAN);
		assertTrue(mValue.booleanValue() == true);
	}

	@Test
	public void testStringValue() {
		assertTrue(mValue.type() == JsonValueType.NOT_SET);
		
		mValue.setString("value");
		
		assertTrue(mValue.type() == JsonValueType.STRING);
		assertTrue(mValue.stringValue() == "value");
	}

	@Test
	public void testNumberValue() {
		assertTrue(mValue.type() == JsonValueType.NOT_SET);
		
		mValue.setNumber(100.0);
		
		assertTrue(mValue.type() == JsonValueType.NUMBER);
		assertTrue(mValue.numberValue() == 100.0);
	}

	@Test
	public void testArrayValue() {
		final List<JsonValue> values = new ArrayList<JsonValue>();
		
		values.add(new JsonValue("val1"));
		values.add(new JsonValue("val2"));
		values.add(new JsonValue("val3"));
		
		assertTrue(mValue.type() == JsonValueType.NOT_SET);
		
		mValue.setArray(values);
		
		assertTrue(mValue.type() == JsonValueType.ARRAY);
		assertTrue(mValue.arrayValue() == values);
	}

	@Test
	public void testObjectValue() {
		final JsonObject obj = new JsonObject();
		
		assertTrue(mValue.type() == JsonValueType.NOT_SET);
		
		mValue.setObject(obj);
		
		assertTrue(mValue.type() == JsonValueType.OBJECT);
		assertTrue(mValue.objectValue() == obj);
	}

	@Test
	public void testSetNull() {
		assertTrue(mValue.type() == JsonValueType.NOT_SET);
		
		mValue.setNull();
		
		assertTrue(mValue.type() == JsonValueType.NULL);
	}

	@Test
	public void testSetBoolean() {
		testBooleanValue();
	}

	@Test
	public void testSetString() {
		testStringValue();
	}

	@Test
	public void testSetNumber() {
		testStringValue();
	}

	@Test
	public void testSetArray() {
		testArrayValue();
	}

	@Test
	public void testSetObject() {
		testObjectValue();
	}

}
