package com.voyagegames.weatherroute.core.json.tests;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.voyagegames.weatherroute.core.json.JsonObject;
import com.voyagegames.weatherroute.core.json.JsonParser;
import com.voyagegames.weatherroute.core.json.JsonString;
import com.voyagegames.weatherroute.core.json.JsonValue;
import com.voyagegames.weatherroute.core.json.JsonValueType;
import com.voyagegames.weatherroute.core.tests.TestLogger;

public class JsonParserTest {
	
	private static final String VALID_STRING =
			"\n" +
			"{\n" +
			"    \"firstName\": \"John\",\n" +
			"    \"lastName\": \"Smith\",\n" +
			"    \"age\": 25,\n" +
			"    \"address\": {\n" +
			"        \"streetAddress\": \"21 2nd \\\" Street\",\n" +
			"        \"city\": \"New York\",\n" +
			"        \"state\": \"NY\",\n" +
			"        \"postalCode\": 10021\n" +
			"    },\n" +
			"    \"phoneNumber\": [\n" +
			"        {\n" +
			"            \"type\": \"home\",\n" +
			"            \"number\": \"212 555-1234\"\n" +
			"        },\n" +
			"        {\n" +
			"            \"type\": \"fax\",\n" +
			"            \"number\": \"646 555-4567\"\n" +
			"        }\n" +
			"    ],\n" +
			"    \"a null\" : null,\n" +
			"    \"a true\" : true,\n" +
			"    \"a false\" : false  ,\n" +
			"    \"a decimal\" : 100.123456\n" +
			"}\n" +
			"";
	
	private static final String VALID_SUB_STRING =
			"{\n" +
			"    \"streetAddress\": \"21 2nd \\\" Street\",\n" +
			"    \"city\": \"New York\",\n" +
			"    \"state\": \"NY\",\n" +
			"    \"postalCode\": 10021\n" +
			"}";
	
	private static final String VALID_ARRAY_STRING =
			"\n" +
			"    [\n" +
			"        {\n" +
			"            \"type\": \"home\",\n" +
			"            \"number\": \"212 555-1234\"\n" +
			"        },\n" +
			"        {\n" +
			"            \"type\": \"fax\",\n" +
			"            \"number\": \"646 555-4567\"\n" +
			"        }\n" +
			"    ]\n" +
			"";
	
	private static final String MALFORMED_STRING = VALID_STRING.substring(0, VALID_STRING.length() / 2);
	
	private final TestLogger log = new TestLogger();
	
	private JsonParser mParser;

	@Before
	public void setUp() throws Exception {
		log.logs.clear();
		mParser = new JsonParser(log);
	}

	@Test
	public void testParseString_success() {
		final JsonObject obj = mParser.parse(VALID_STRING);

		assertTrue(log.logs.size() == 0);
		assertTrue(obj != null);
		assertTrue(obj.values.size() == 9);
		assertTrue(obj.values.containsKey("firstName"));
		assertTrue(obj.values.containsKey("lastName"));
		assertTrue(obj.values.containsKey("age"));
		assertTrue(obj.values.containsKey("address"));
		assertTrue(obj.values.containsKey("phoneNumber"));
		assertTrue(obj.values.containsKey("a null"));
		assertTrue(obj.values.containsKey("a true"));
		assertTrue(obj.values.containsKey("a false"));
		assertTrue(obj.values.containsKey("a decimal"));
	}

	@Test
	public void testParseString_successWithArray() {
		final JsonObject obj = mParser.parse(VALID_ARRAY_STRING);

		assertTrue(log.logs.size() == 0);
		assertTrue(obj != null);
		assertTrue(obj.values.size() == 1);
		
		final JsonValue v = obj.values.get(JsonParser.ARRAY_RESULT);
		
		assertTrue(v != null);
		assertTrue(v.type() == JsonValueType.ARRAY);
		assertTrue(v.arrayValue().size() == 2);
		assertTrue(v.arrayValue().get(0).type() == JsonValueType.OBJECT);
		assertTrue(v.arrayValue().get(1).type() == JsonValueType.OBJECT);
	}

	@Test
	public void testParseJsonString_success() {
		final JsonString str = new JsonString(VALID_SUB_STRING, 0, VALID_SUB_STRING.length());
		final JsonObject obj = mParser.parse(str);
		
		assertTrue(log.logs.size() == 0);
		assertTrue(obj != null);
		assertTrue(obj.values.size() == 4);
		assertTrue(obj.values.containsKey("streetAddress"));
		assertTrue(obj.values.containsKey("city"));
		assertTrue(obj.values.containsKey("state"));
		assertTrue(obj.values.containsKey("postalCode"));
	}

	@Test
	public void testParseString_malformed() {
		final JsonObject obj = mParser.parse(MALFORMED_STRING);

		assertTrue(log.logs.size() > 0);
		assertTrue(obj == null);
	}

}
