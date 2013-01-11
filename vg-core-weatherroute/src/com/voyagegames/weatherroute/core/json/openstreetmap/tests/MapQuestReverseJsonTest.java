package com.voyagegames.weatherroute.core.json.openstreetmap.tests;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.voyagegames.weatherroute.core.json.JsonObject;
import com.voyagegames.weatherroute.core.json.JsonParser;
import com.voyagegames.weatherroute.core.json.openstreetmap.MapQuestReverseJson;
import com.voyagegames.weatherroute.core.tests.TestLogger;

public class MapQuestReverseJsonTest {
	
	private static final String TAG = MapQuestReverseJsonTest.class.getName();
	
	private final TestLogger log = new TestLogger();
	
	private JsonParser mParser;

	@Before
	public void setUp() throws Exception {
		log.logs.clear();
		mParser = new JsonParser(log);
	}
	
	@After
	public void tearDown() throws Exception {
		for (final String s : log.logs) {
			System.out.println(s);
		}
	}

	@Test
	public void testMapQuestReverseJson() {
		final JsonObject obj = mParser.parse(TEST_VALUE);

		assertTrue(obj != null);
		
		try {
			final MapQuestReverseJson mqrj = new MapQuestReverseJson(obj);
			
			assertTrue(mqrj.info != null);
			assertTrue(mqrj.options != null);
			assertTrue(mqrj.results != null);
		} catch (final Exception e) {
			log.log(TAG, e.toString(), e);
			log.log(TAG, obj.debug());
			fail("Unexpected exception");
		}
	}
	
	private static final String TEST_VALUE =
			"{ \"info\" : { \"copyright\" : { \"imageAltText\" : \"© 2012 MapQuest, Inc.\",\n" +
			"          \"imageUrl\" : \"http://api.mqcdn.com/res/mqlogo.gif\",\n" +
			"          \"text\" : \"© 2012 MapQuest, Inc.\"\n" +
			"        },\n" +
			"      \"messages\" : [  ],\n" +
			"      \"statuscode\" : 0\n" +
			"    },\n" +
			"  \"options\" : { \"ignoreLatLngInput\" : false,\n" +
			"      \"maxResults\" : -1,\n" +
			"      \"thumbMaps\" : true\n" +
			"    },\n" +
			"  \"results\" : [ { \"locations\" : [ { \"adminArea1\" : \"United States of America\",\n" +
			"              \"adminArea1Type\" : \"Country\",\n" +
			"              \"adminArea3\" : \"Oregon\",\n" +
			"              \"adminArea3Type\" : \"State\",\n" +
			"              \"adminArea4\" : \"Deschutes\",\n" +
			"              \"adminArea4Type\" : \"County\",\n" +
			"              \"adminArea5\" : \"Bend\",\n" +
			"              \"adminArea5Type\" : \"City\",\n" +
			"              \"displayLatLng\" : { \"lat\" : 44.049351999999999,\n" +
			"                  \"lng\" : -121.300927\n" +
			"                },\n" +
			"              \"dragPoint\" : false,\n" +
			"              \"geocodeQuality\" : \"ADDRESS\",\n" +
			"              \"geocodeQualityCode\" : \"L1AAA\",\n" +
			"              \"latLng\" : { \"lat\" : 44.049999999999997,\n" +
			"                  \"lng\" : -121.3\n" +
			"                },\n" +
			"              \"linkId\" : 0,\n" +
			"              \"mapUrl\" : \"http://open.mapquestapi.com/staticmap/v4/getmap?type=map&size=225,160&pois=purple-1,44.05,-121.3,0,0|¢er=44.05,-121.3&zoom=12&key=Kmjtd%7Cluu7n162n1%2C22%3Do5-h61wh&rand=735422036\",\n" +
			"              \"postalCode\" : \"97702\",\n" +
			"              \"sideOfStreet\" : \"N\",\n" +
			"              \"street\" : \"Southeast Railroad Street\",\n" +
			"              \"type\" : \"s\"\n" +
			"            } ],\n" +
			"        \"providedLocation\" : { \"latLng\" : { \"lat\" : 44.049999999999997,\n" +
			"                \"lng\" : -121.3\n" +
			"              },\n" +
			"            \"zoom\" : \"8\"\n" +
			"          }\n" +
			"      } ]\n" +
			"}";


}
