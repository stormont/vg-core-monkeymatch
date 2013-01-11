package com.voyagegames.weatherroute.core.json.openstreetmap.tests;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.voyagegames.weatherroute.core.json.JsonObject;
import com.voyagegames.weatherroute.core.json.JsonParser;
import com.voyagegames.weatherroute.core.json.openstreetmap.MapQuestSearchJson;
import com.voyagegames.weatherroute.core.tests.TestLogger;

public class MapQuestSearchJsonTest {
	
	private static final String TAG = MapQuestSearchJsonTest.class.getName();
	
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
	public void testMapQuestDirectionsJson() {
		final JsonObject obj = mParser.parse(TEST_VALUE);

		assertTrue(obj != null);
		
		try {
			final MapQuestSearchJson mqdj = new MapQuestSearchJson(obj);
			
			assertTrue(mqdj.info != null);
			assertTrue(mqdj.route != null);
		} catch (final Exception e) {
			log.log(TAG, e.toString(), e);
			log.log(TAG, obj.debug());
			fail("Unexpected exception");
		}
	}
	
	private static final String TEST_VALUE =
			"\n" +
			"{ \"info\" : { \"copyright\" : { \"imageAltText\" : \"© 2012 MapQuest, Inc.\",\n" +
			"          \"imageUrl\" : \"http://api.mqcdn.com/res/mqlogo.gif\",\n" +
			"          \"text\" : \"© 2012 MapQuest, Inc.\"\n" +
			"        },\n" +
			"      \"messages\" : [  ],\n" +
			"      \"statuscode\" : 0\n" +
			"    },\n" +
			"  \"route\" : { \"boundingBox\" : { \"lr\" : { \"lat\" : 39.529170000000001,\n" +
			"              \"lng\" : -119.813987\n" +
			"            },\n" +
			"          \"ul\" : { \"lat\" : 44.058174000000001,\n" +
			"              \"lng\" : -121.79605100000001\n" +
			"            }\n" +
			"        },\n" +
			"      \"computedWaypoints\" : [  ],\n" +
			"      \"distance\" : 388.541,\n" +
			"      \"formattedTime\" : \"07:32:55\",\n" +
			"      \"fuelUsed\" : 16.329504,\n" +
			"      \"hasCountryCross\" : false,\n" +
			"      \"hasFerry\" : false,\n" +
			"      \"hasHighway\" : true,\n" +
			"      \"hasSeasonalClosure\" : false,\n" +
			"      \"hasTollRoad\" : false,\n" +
			"      \"hasUnpaved\" : false,\n" +
			"      \"legs\" : [ { \"destIndex\" : 10,\n" +
			"            \"destNarrative\" : \"Proceed to NORTHWEST WALL STREET.\",\n" +
			"            \"distance\" : 388.541,\n" +
			"            \"formattedTime\" : \"07:32:55\",\n" +
			"            \"hasCountryCross\" : false,\n" +
			"            \"hasFerry\" : false,\n" +
			"            \"hasHighway\" : true,\n" +
			"            \"hasSeasonalClosure\" : false,\n" +
			"            \"hasTollRoad\" : false,\n" +
			"            \"hasUnpaved\" : false,\n" +
			"            \"index\" : 0,\n" +
			"            \"maneuvers\" : [ { \"attributes\" : 0,\n" +
			"                  \"direction\" : 1,\n" +
			"                  \"directionName\" : \"North\",\n" +
			"                  \"distance\" : 4.1559999999999997,\n" +
			"                  \"formattedTime\" : \"00:06:22\",\n" +
			"                  \"iconUrl\" : \"http://content.mapquest.com/mqsite/turnsigns/icon-dirs-start_sm.gif\",\n" +
			"                  \"index\" : 0,\n" +
			"                  \"linkIds\" : [  ],\n" +
			"                  \"maneuverNotes\" : [  ],\n" +
			"                  \"mapUrl\" : \"http://open.mapquestapi.com/staticmap/v4/getmap?type=map&size=225,160&pois=purple-1,39.52917,-119.813987,0,0|purple-2,39.583614,-119.82994799999999,0,0|¢er=39.556392,-119.8219675&zoom=7&key=Kmjtd%7Cluu7n162n1%2C22%3Do5-h61wh&rand=1848485700&session=50e4b73f-0293-0014-02b7-7f1f-002655800362\",\n" +
			"                  \"narrative\" : \"Start out going north on US-395 Business/N Virginia St toward E 4th St.\",\n" +
			"                  \"signs\" : [ { \"direction\" : 0,\n" +
			"                        \"extraText\" : \"\",\n" +
			"                        \"text\" : \"395:BUS\",\n" +
			"                        \"type\" : 2,\n" +
			"                        \"url\" : \"http://icons.mqcdn.com/icons/roadsign.gif?s=rs&t=RS00002BW_SM&n=395:BUS\"\n" +
			"                      } ],\n" +
			"                  \"startPoint\" : { \"lat\" : 39.529170000000001,\n" +
			"                      \"lng\" : -119.813987\n" +
			"                    },\n" +
			"                  \"streets\" : [ \"US-395 Business\",\n" +
			"                      \"N Virginia St\"\n" +
			"                    ],\n" +
			"                  \"time\" : 382,\n" +
			"                  \"transportMode\" : \"AUTO\",\n" +
			"                  \"turnType\" : 0\n" +
			"                },\n" +
			"                { \"attributes\" : 0,\n" +
			"                  \"direction\" : 3,\n" +
			"                  \"directionName\" : \"Northeast\",\n" +
			"                  \"distance\" : 0.27900000000000003,\n" +
			"                  \"formattedTime\" : \"00:00:25\",\n" +
			"                  \"iconUrl\" : \"http://content.mapquest.com/mqsite/turnsigns/rs_ramp_sm.gif\",\n" +
			"                  \"index\" : 1,\n" +
			"                  \"linkIds\" : [  ],\n" +
			"                  \"maneuverNotes\" : [  ],\n" +
			"                  \"mapUrl\" : \"http://open.mapquestapi.com/staticmap/v4/getmap?type=map&size=225,160&pois=purple-2,39.583614,-119.82994799999999,0,0|purple-3,39.587032,-119.827247,0,0|¢er=39.585323,-119.8285975&zoom=11&key=Kmjtd%7Cluu7n162n1%2C22%3Do5-h61wh&rand=1848485700&session=50e4b73f-0293-0014-02b7-7f1f-002655800362\",\n" +
			"                  \"narrative\" : \"Take the US-395 Business ramp.\",\n" +
			"                  \"signs\" : [  ],\n" +
			"                  \"startPoint\" : { \"lat\" : 39.583613999999997,\n" +
			"                      \"lng\" : -119.829948\n" +
			"                    },\n" +
			"                  \"streets\" : [  ],\n" +
			"                  \"time\" : 25,\n" +
			"                  \"transportMode\" : \"AUTO\",\n" +
			"                  \"turnType\" : 12\n" +
			"                },\n" +
			"                { \"attributes\" : 128,\n" +
			"                  \"direction\" : 2,\n" +
			"                  \"directionName\" : \"Northwest\",\n" +
			"                  \"distance\" : 75.855000000000004,\n" +
			"                  \"formattedTime\" : \"01:17:24\",\n" +
			"                  \"iconUrl\" : \"http://content.mapquest.com/mqsite/turnsigns/rs_merge_left_sm.gif\",\n" +
			"                  \"index\" : 2,\n" +
			"                  \"linkIds\" : [  ],\n" +
			"                  \"maneuverNotes\" : [  ],\n" +
			"                  \"mapUrl\" : \"http://open.mapquestapi.com/staticmap/v4/getmap?type=map&size=225,160&pois=purple-3,39.587032,-119.827247,0,0|purple-4,40.380545999999995,-120.591865,0,0|¢er=39.983789,-120.20955599999999&zoom=4&key=Kmjtd%7Cluu7n162n1%2C22%3Do5-h61wh&rand=1848485700&session=50e4b73f-0293-0014-02b7-7f1f-002655800362\",\n" +
			"                  \"narrative\" : \"Merge onto US-395 via the ramp on the left (Crossing into California).\",\n" +
			"                  \"signs\" : [ { \"direction\" : 0,\n" +
			"                        \"extraText\" : \"\",\n" +
			"                        \"text\" : \"395\",\n" +
			"                        \"type\" : 2,\n" +
			"                        \"url\" : \"http://icons.mqcdn.com/icons/roadsign.gif?s=rs&t=RS00002BW_SM&n=395\"\n" +
			"                      } ],\n" +
			"                  \"startPoint\" : { \"lat\" : 39.587032000000001,\n" +
			"                      \"lng\" : -119.827247\n" +
			"                    },\n" +
			"                  \"streets\" : [ \"US-395\" ],\n" +
			"                  \"time\" : 4644,\n" +
			"                  \"transportMode\" : \"AUTO\",\n" +
			"                  \"turnType\" : 11\n" +
			"                },\n" +
			"                { \"attributes\" : 128,\n" +
			"                  \"direction\" : 2,\n" +
			"                  \"directionName\" : \"Northwest\",\n" +
			"                  \"distance\" : 4.0359999999999996,\n" +
			"                  \"formattedTime\" : \"00:04:48\",\n" +
			"                  \"iconUrl\" : \"http://content.mapquest.com/mqsite/turnsigns/rs_straight_sm.gif\",\n" +
			"                  \"index\" : 3,\n" +
			"                  \"linkIds\" : [  ],\n" +
			"                  \"maneuverNotes\" : [  ],\n" +
			"                  \"mapUrl\" : \"http://open.mapquestapi.com/staticmap/v4/getmap?type=map&size=225,160&pois=purple-4,40.380545999999995,-120.591865,0,0|purple-5,40.415493,-120.64892499999999,0,0|¢er=40.3980195,-120.620395&zoom=8&key=Kmjtd%7Cluu7n162n1%2C22%3Do5-h61wh&rand=1848485700&session=50e4b73f-0293-0014-02b7-7f1f-002655800362\",\n" +
			"                  \"narrative\" : \"Stay straight to go onto CA-36.\",\n" +
			"                  \"signs\" : [ { \"direction\" : 0,\n" +
			"                        \"extraText\" : \"\",\n" +
			"                        \"text\" : \"36\",\n" +
			"                        \"type\" : 504\n" +
			"                      } ],\n" +
			"                  \"startPoint\" : { \"lat\" : 40.380546000000002,\n" +
			"                      \"lng\" : -120.591865\n" +
			"                    },\n" +
			"                  \"streets\" : [ \"CA-36\" ],\n" +
			"                  \"time\" : 288,\n" +
			"                  \"transportMode\" : \"AUTO\",\n" +
			"                  \"turnType\" : 0\n" +
			"                },\n" +
			"                { \"attributes\" : 0,\n" +
			"                  \"direction\" : 2,\n" +
			"                  \"directionName\" : \"Northwest\",\n" +
			"                  \"distance\" : 66.843000000000004,\n" +
			"                  \"formattedTime\" : \"01:22:53\",\n" +
			"                  \"iconUrl\" : \"http://content.mapquest.com/mqsite/turnsigns/rs_right_sm.gif\",\n" +
			"                  \"index\" : 4,\n" +
			"                  \"linkIds\" : [  ],\n" +
			"                  \"maneuverNotes\" : [  ],\n" +
			"                  \"mapUrl\" : \"http://open.mapquestapi.com/staticmap/v4/getmap?type=map&size=225,160&pois=purple-5,40.415493,-120.64892499999999,0,0|purple-6,41.18745,-120.945121,0,0|¢er=40.8014715,-120.797023&zoom=4&key=Kmjtd%7Cluu7n162n1%2C22%3Do5-h61wh&rand=1848485700&session=50e4b73f-0293-0014-02b7-7f1f-002655800362\",\n" +
			"                  \"narrative\" : \"Turn right onto CA-139/Ash St. Continue to follow CA-139.\",\n" +
			"                  \"signs\" : [ { \"direction\" : 0,\n" +
			"                        \"extraText\" : \"\",\n" +
			"                        \"text\" : \"139\",\n" +
			"                        \"type\" : 504\n" +
			"                      } ],\n" +
			"                  \"startPoint\" : { \"lat\" : 40.415492999999998,\n" +
			"                      \"lng\" : -120.64892500000001\n" +
			"                    },\n" +
			"                  \"streets\" : [ \"CA-139\" ],\n" +
			"                  \"time\" : 4973,\n" +
			"                  \"transportMode\" : \"AUTO\",\n" +
			"                  \"turnType\" : 2\n" +
			"                },\n" +
			"                { \"attributes\" : 0,\n" +
			"                  \"direction\" : 3,\n" +
			"                  \"directionName\" : \"Northeast\",\n" +
			"                  \"distance\" : 21.456,\n" +
			"                  \"formattedTime\" : \"00:26:30\",\n" +
			"                  \"iconUrl\" : \"http://content.mapquest.com/mqsite/turnsigns/rs_slight_right_sm.gif\",\n" +
			"                  \"index\" : 5,\n" +
			"                  \"linkIds\" : [  ],\n" +
			"                  \"maneuverNotes\" : [  ],\n" +
			"                  \"mapUrl\" : \"http://open.mapquestapi.com/staticmap/v4/getmap?type=map&size=225,160&pois=purple-6,41.18745,-120.945121,0,0|purple-7,41.437961,-120.87863899999999,0,0|¢er=41.3127055,-120.91188&zoom=5&key=Kmjtd%7Cluu7n162n1%2C22%3Do5-h61wh&rand=1848485700&session=50e4b73f-0293-0014-02b7-7f1f-002655800362\",\n" +
			"                  \"narrative\" : \"Turn slight right onto SR 139/SR 299.\",\n" +
			"                  \"signs\" : [ { \"direction\" : 0,\n" +
			"                        \"extraText\" : \"\",\n" +
			"                        \"text\" : \"139\",\n" +
			"                        \"type\" : 3,\n" +
			"                        \"url\" : \"http://icons.mqcdn.com/icons/roadsign.gif?s=rs&t=RS00003BW_SM&n=139\"\n" +
			"                      },\n" +
			"                      { \"direction\" : 0,\n" +
			"                        \"extraText\" : \"\",\n" +
			"                        \"text\" : \"299\",\n" +
			"                        \"type\" : 3,\n" +
			"                        \"url\" : \"http://icons.mqcdn.com/icons/roadsign.gif?s=rs&t=RS00003BW_SM&n=299\"\n" +
			"                      }\n" +
			"                    ],\n" +
			"                  \"startPoint\" : { \"lat\" : 41.187449999999998,\n" +
			"                      \"lng\" : -120.945121\n" +
			"                    },\n" +
			"                  \"streets\" : [ \"SR 139\",\n" +
			"                      \"SR 299\"\n" +
			"                    ],\n" +
			"                  \"time\" : 1590,\n" +
			"                  \"transportMode\" : \"AUTO\",\n" +
			"                  \"turnType\" : 1\n" +
			"                },\n" +
			"                { \"attributes\" : 0,\n" +
			"                  \"direction\" : 2,\n" +
			"                  \"directionName\" : \"Northwest\",\n" +
			"                  \"distance\" : 55.043999999999997,\n" +
			"                  \"formattedTime\" : \"01:07:24\",\n" +
			"                  \"iconUrl\" : \"http://content.mapquest.com/mqsite/turnsigns/rs_left_sm.gif\",\n" +
			"                  \"index\" : 6,\n" +
			"                  \"linkIds\" : [  ],\n" +
			"                  \"maneuverNotes\" : [  ],\n" +
			"                  \"mapUrl\" : \"http://open.mapquestapi.com/staticmap/v4/getmap?type=map&size=225,160&pois=purple-7,41.437961,-120.87863899999999,0,0|purple-8,41.998279,-121.51948499999999,0,0|¢er=41.71812,-121.199062&zoom=4&key=Kmjtd%7Cluu7n162n1%2C22%3Do5-h61wh&rand=1848485700&session=50e4b73f-0293-0014-02b7-7f1f-002655800362\",\n" +
			"                  \"narrative\" : \"Turn left onto CA-139 (Crossing into Oregon).\",\n" +
			"                  \"signs\" : [ { \"direction\" : 0,\n" +
			"                        \"extraText\" : \"\",\n" +
			"                        \"text\" : \"139\",\n" +
			"                        \"type\" : 504\n" +
			"                      } ],\n" +
			"                  \"startPoint\" : { \"lat\" : 41.437961000000001,\n" +
			"                      \"lng\" : -120.87863900000001\n" +
			"                    },\n" +
			"                  \"streets\" : [ \"CA-139\" ],\n" +
			"                  \"time\" : 4044,\n" +
			"                  \"transportMode\" : \"AUTO\",\n" +
			"                  \"turnType\" : 6\n" +
			"                },\n" +
			"                { \"attributes\" : 0,\n" +
			"                  \"direction\" : 2,\n" +
			"                  \"directionName\" : \"Northwest\",\n" +
			"                  \"distance\" : 21.215,\n" +
			"                  \"formattedTime\" : \"00:27:15\",\n" +
			"                  \"iconUrl\" : \"http://content.mapquest.com/mqsite/turnsigns/rs_straight_sm.gif\",\n" +
			"                  \"index\" : 7,\n" +
			"                  \"linkIds\" : [  ],\n" +
			"                  \"maneuverNotes\" : [  ],\n" +
			"                  \"mapUrl\" : \"http://open.mapquestapi.com/staticmap/v4/getmap?type=map&size=225,160&pois=purple-8,41.998279,-121.51948499999999,0,0|purple-9,42.206508,-121.736747,0,0|¢er=42.1023935,-121.62811599999999&zoom=5&key=Kmjtd%7Cluu7n162n1%2C22%3Do5-h61wh&rand=1848485700&session=50e4b73f-0293-0014-02b7-7f1f-002655800362\",\n" +
			"                  \"narrative\" : \"CA-139 becomes OR-39.\",\n" +
			"                  \"signs\" : [ { \"direction\" : 0,\n" +
			"                        \"extraText\" : \"\",\n" +
			"                        \"text\" : \"39\",\n" +
			"                        \"type\" : 536\n" +
			"                      } ],\n" +
			"                  \"startPoint\" : { \"lat\" : 41.998278999999997,\n" +
			"                      \"lng\" : -121.519485\n" +
			"                    },\n" +
			"                  \"streets\" : [ \"OR-39\" ],\n" +
			"                  \"time\" : 1635,\n" +
			"                  \"transportMode\" : \"AUTO\",\n" +
			"                  \"turnType\" : 0\n" +
			"                },\n" +
			"                { \"attributes\" : 128,\n" +
			"                  \"direction\" : 2,\n" +
			"                  \"directionName\" : \"Northwest\",\n" +
			"                  \"distance\" : 4.5679999999999996,\n" +
			"                  \"formattedTime\" : \"00:05:30\",\n" +
			"                  \"iconUrl\" : \"http://content.mapquest.com/mqsite/turnsigns/rs_right_sm.gif\",\n" +
			"                  \"index\" : 8,\n" +
			"                  \"linkIds\" : [  ],\n" +
			"                  \"maneuverNotes\" : [  ],\n" +
			"                  \"mapUrl\" : \"http://open.mapquestapi.com/staticmap/v4/getmap?type=map&size=225,160&pois=purple-9,42.206508,-121.736747,0,0|purple-10,42.253009,-121.79605099999999,0,0|¢er=42.2297585,-121.76639899999999&zoom=8&key=Kmjtd%7Cluu7n162n1%2C22%3Do5-h61wh&rand=1848485700&session=50e4b73f-0293-0014-02b7-7f1f-002655800362\",\n" +
			"                  \"narrative\" : \"Turn right onto OR-39/Eastside Byp. Continue to follow OR-39.\",\n" +
			"                  \"signs\" : [ { \"direction\" : 0,\n" +
			"                        \"extraText\" : \"\",\n" +
			"                        \"text\" : \"39\",\n" +
			"                        \"type\" : 536\n" +
			"                      } ],\n" +
			"                  \"startPoint\" : { \"lat\" : 42.206507999999999,\n" +
			"                      \"lng\" : -121.73674699999999\n" +
			"                    },\n" +
			"                  \"streets\" : [ \"OR-39\" ],\n" +
			"                  \"time\" : 330,\n" +
			"                  \"transportMode\" : \"AUTO\",\n" +
			"                  \"turnType\" : 2\n" +
			"                },\n" +
			"                { \"attributes\" : 128,\n" +
			"                  \"direction\" : 1,\n" +
			"                  \"directionName\" : \"North\",\n" +
			"                  \"distance\" : 133.905,\n" +
			"                  \"formattedTime\" : \"02:31:27\",\n" +
			"                  \"iconUrl\" : \"http://content.mapquest.com/mqsite/turnsigns/rs_straight_sm.gif\",\n" +
			"                  \"index\" : 9,\n" +
			"                  \"linkIds\" : [  ],\n" +
			"                  \"maneuverNotes\" : [  ],\n" +
			"                  \"mapUrl\" : \"http://open.mapquestapi.com/staticmap/v4/getmap?type=map&size=225,160&pois=purple-10,42.253009,-121.79605099999999,0,0|purple-11,44.052654,-121.307075,0,0|¢er=43.1528315,-121.55156299999999&zoom=3&key=Kmjtd%7Cluu7n162n1%2C22%3Do5-h61wh&rand=1848485700&session=50e4b73f-0293-0014-02b7-7f1f-002655800362\",\n" +
			"                  \"narrative\" : \"OR-39 becomes US-97.\",\n" +
			"                  \"signs\" : [ { \"direction\" : 0,\n" +
			"                        \"extraText\" : \"\",\n" +
			"                        \"text\" : \"97\",\n" +
			"                        \"type\" : 2,\n" +
			"                        \"url\" : \"http://icons.mqcdn.com/icons/roadsign.gif?s=rs&t=RS00002BW_SM&n=97\"\n" +
			"                      } ],\n" +
			"                  \"startPoint\" : { \"lat\" : 42.253008999999999,\n" +
			"                      \"lng\" : -121.79605100000001\n" +
			"                    },\n" +
			"                  \"streets\" : [ \"US-97\" ],\n" +
			"                  \"time\" : 9087,\n" +
			"                  \"transportMode\" : \"AUTO\",\n" +
			"                  \"turnType\" : 0\n" +
			"                },\n" +
			"                { \"attributes\" : 0,\n" +
			"                  \"direction\" : 4,\n" +
			"                  \"directionName\" : \"South\",\n" +
			"                  \"distance\" : 0.183,\n" +
			"                  \"formattedTime\" : \"00:00:22\",\n" +
			"                  \"iconUrl\" : \"http://content.mapquest.com/mqsite/turnsigns/rs_gr_exitright_sm.gif\",\n" +
			"                  \"index\" : 10,\n" +
			"                  \"linkIds\" : [  ],\n" +
			"                  \"maneuverNotes\" : [  ],\n" +
			"                  \"mapUrl\" : \"http://open.mapquestapi.com/staticmap/v4/getmap?type=map&size=225,160&pois=purple-11,44.052654,-121.307075,0,0|purple-12,44.051550999999996,-121.30613699999999,0,0|¢er=44.0521025,-121.30660599999999&zoom=13&key=Kmjtd%7Cluu7n162n1%2C22%3Do5-h61wh&rand=1848485700&session=50e4b73f-0293-0014-02b7-7f1f-002655800362\",\n" +
			"                  \"narrative\" : \"Take the exit.\",\n" +
			"                  \"signs\" : [  ],\n" +
			"                  \"startPoint\" : { \"lat\" : 44.052653999999997,\n" +
			"                      \"lng\" : -121.307075\n" +
			"                    },\n" +
			"                  \"streets\" : [  ],\n" +
			"                  \"time\" : 22,\n" +
			"                  \"transportMode\" : \"AUTO\",\n" +
			"                  \"turnType\" : 14\n" +
			"                },\n" +
			"                { \"attributes\" : 0,\n" +
			"                  \"direction\" : 7,\n" +
			"                  \"directionName\" : \"West\",\n" +
			"                  \"distance\" : 0.52400000000000002,\n" +
			"                  \"formattedTime\" : \"00:01:01\",\n" +
			"                  \"iconUrl\" : \"http://content.mapquest.com/mqsite/turnsigns/rs_right_sm.gif\",\n" +
			"                  \"index\" : 11,\n" +
			"                  \"linkIds\" : [  ],\n" +
			"                  \"maneuverNotes\" : [  ],\n" +
			"                  \"mapUrl\" : \"http://open.mapquestapi.com/staticmap/v4/getmap?type=map&size=225,160&pois=purple-12,44.051550999999996,-121.30613699999999,0,0|purple-13,44.052322,-121.316474,0,0|¢er=44.0519365,-121.3113055&zoom=11&key=Kmjtd%7Cluu7n162n1%2C22%3Do5-h61wh&rand=1848485700&session=50e4b73f-0293-0014-02b7-7f1f-002655800362\",\n" +
			"                  \"narrative\" : \"Turn right onto OR-372/SE Scott St. Continue to follow OR-372.\",\n" +
			"                  \"signs\" : [ { \"direction\" : 0,\n" +
			"                        \"extraText\" : \"\",\n" +
			"                        \"text\" : \"372\",\n" +
			"                        \"type\" : 536\n" +
			"                      } ],\n" +
			"                  \"startPoint\" : { \"lat\" : 44.051551000000003,\n" +
			"                      \"lng\" : -121.30613700000001\n" +
			"                    },\n" +
			"                  \"streets\" : [ \"OR-372\" ],\n" +
			"                  \"time\" : 61,\n" +
			"                  \"transportMode\" : \"AUTO\",\n" +
			"                  \"turnType\" : 2\n" +
			"                },\n" +
			"                { \"attributes\" : 0,\n" +
			"                  \"direction\" : 1,\n" +
			"                  \"directionName\" : \"North\",\n" +
			"                  \"distance\" : 0.39500000000000002,\n" +
			"                  \"formattedTime\" : \"00:01:17\",\n" +
			"                  \"iconUrl\" : \"http://content.mapquest.com/mqsite/turnsigns/rs_right_sm.gif\",\n" +
			"                  \"index\" : 12,\n" +
			"                  \"linkIds\" : [  ],\n" +
			"                  \"maneuverNotes\" : [  ],\n" +
			"                  \"mapUrl\" : \"http://open.mapquestapi.com/staticmap/v4/getmap?type=map&size=225,160&pois=purple-13,44.052322,-121.316474,0,0|purple-14,44.05743,-121.31401,0,0|¢er=44.05487599999999,-121.315242&zoom=11&key=Kmjtd%7Cluu7n162n1%2C22%3Do5-h61wh&rand=1848485700&session=50e4b73f-0293-0014-02b7-7f1f-002655800362\",\n" +
			"                  \"narrative\" : \"Turn right onto NW Bond St.\",\n" +
			"                  \"signs\" : [  ],\n" +
			"                  \"startPoint\" : { \"lat\" : 44.052321999999997,\n" +
			"                      \"lng\" : -121.316474\n" +
			"                    },\n" +
			"                  \"streets\" : [ \"NW Bond St\" ],\n" +
			"                  \"time\" : 77,\n" +
			"                  \"transportMode\" : \"AUTO\",\n" +
			"                  \"turnType\" : 2\n" +
			"                },\n" +
			"                { \"attributes\" : 0,\n" +
			"                  \"direction\" : 2,\n" +
			"                  \"directionName\" : \"Northwest\",\n" +
			"                  \"distance\" : 0.082000000000000003,\n" +
			"                  \"formattedTime\" : \"00:00:17\",\n" +
			"                  \"iconUrl\" : \"http://content.mapquest.com/mqsite/turnsigns/rs_left_sm.gif\",\n" +
			"                  \"index\" : 13,\n" +
			"                  \"linkIds\" : [  ],\n" +
			"                  \"maneuverNotes\" : [  ],\n" +
			"                  \"mapUrl\" : \"http://open.mapquestapi.com/staticmap/v4/getmap?type=map&size=225,160&pois=purple-14,44.05743,-121.31401,0,0|purple-15,44.058174,-121.31530699999999,0,0|¢er=44.057801999999995,-121.3146585&zoom=14&key=Kmjtd%7Cluu7n162n1%2C22%3Do5-h61wh&rand=1848485700&session=50e4b73f-0293-0014-02b7-7f1f-002655800362\",\n" +
			"                  \"narrative\" : \"Turn left onto NW Franklin Ave.\",\n" +
			"                  \"signs\" : [  ],\n" +
			"                  \"startPoint\" : { \"lat\" : 44.057429999999997,\n" +
			"                      \"lng\" : -121.31401\n" +
			"                    },\n" +
			"                  \"streets\" : [ \"NW Franklin Ave\" ],\n" +
			"                  \"time\" : 17,\n" +
			"                  \"transportMode\" : \"AUTO\",\n" +
			"                  \"turnType\" : 6\n" +
			"                },\n" +
			"                { \"attributes\" : 0,\n" +
			"                  \"direction\" : 0,\n" +
			"                  \"directionName\" : \"\",\n" +
			"                  \"distance\" : 0,\n" +
			"                  \"formattedTime\" : \"00:00:00\",\n" +
			"                  \"iconUrl\" : \"http://content.mapquest.com/mqsite/turnsigns/icon-dirs-end_sm.gif\",\n" +
			"                  \"index\" : 14,\n" +
			"                  \"linkIds\" : [  ],\n" +
			"                  \"maneuverNotes\" : [  ],\n" +
			"                  \"narrative\" : \"NORTHWEST WALL STREET.\",\n" +
			"                  \"signs\" : [  ],\n" +
			"                  \"startPoint\" : { \"lat\" : 44.058174000000001,\n" +
			"                      \"lng\" : -121.315307\n" +
			"                    },\n" +
			"                  \"streets\" : [  ],\n" +
			"                  \"time\" : 0,\n" +
			"                  \"transportMode\" : \"AUTO\",\n" +
			"                  \"turnType\" : -1\n" +
			"                }\n" +
			"              ],\n" +
			"            \"origIndex\" : -1,\n" +
			"            \"origNarrative\" : \"\",\n" +
			"            \"roadGradeStrategy\" : [ [  ] ],\n" +
			"            \"time\" : 27175\n" +
			"          } ],\n" +
			"      \"locationSequence\" : [ 0,\n" +
			"          1\n" +
			"        ],\n" +
			"      \"locations\" : [ { \"adminArea1\" : \"United States of America\",\n" +
			"            \"adminArea1Type\" : \"Country\",\n" +
			"            \"adminArea3\" : \"Nevada\",\n" +
			"            \"adminArea3Type\" : \"State\",\n" +
			"            \"adminArea4\" : \"Washoe\",\n" +
			"            \"adminArea4Type\" : \"County\",\n" +
			"            \"adminArea5\" : \"Reno\",\n" +
			"            \"adminArea5Type\" : \"City\",\n" +
			"            \"displayLatLng\" : { \"lat\" : 39.529269999999997,\n" +
			"                \"lng\" : -119.81367400000001\n" +
			"              },\n" +
			"            \"dragPoint\" : false,\n" +
			"            \"geocodeQuality\" : \"ADDRESS\",\n" +
			"            \"geocodeQualityCode\" : \"L1AAA\",\n" +
			"            \"latLng\" : { \"lat\" : 39.529269999999997,\n" +
			"                \"lng\" : -119.81367400000001\n" +
			"              },\n" +
			"            \"linkId\" : 30318705,\n" +
			"            \"postalCode\" : \"89501\",\n" +
			"            \"sideOfStreet\" : \"N\",\n" +
			"            \"street\" : \"North Virginia Street\",\n" +
			"            \"type\" : \"s\"\n" +
			"          },\n" +
			"          { \"adminArea1\" : \"United States of America\",\n" +
			"            \"adminArea1Type\" : \"Country\",\n" +
			"            \"adminArea3\" : \"Oregon\",\n" +
			"            \"adminArea3Type\" : \"State\",\n" +
			"            \"adminArea4\" : \"Deschutes\",\n" +
			"            \"adminArea4Type\" : \"County\",\n" +
			"            \"adminArea5\" : \"Bend\",\n" +
			"            \"adminArea5Type\" : \"City\",\n" +
			"            \"displayLatLng\" : { \"lat\" : 44.058169999999997,\n" +
			"                \"lng\" : -121.315307\n" +
			"              },\n" +
			"            \"dragPoint\" : false,\n" +
			"            \"geocodeQuality\" : \"ADDRESS\",\n" +
			"            \"geocodeQualityCode\" : \"L1AAA\",\n" +
			"            \"latLng\" : { \"lat\" : 44.058171999999999,\n" +
			"                \"lng\" : -121.315309\n" +
			"              },\n" +
			"            \"linkId\" : 381913,\n" +
			"            \"postalCode\" : \"97701\",\n" +
			"            \"sideOfStreet\" : \"N\",\n" +
			"            \"street\" : \"Northwest Wall Street\",\n" +
			"            \"type\" : \"s\"\n" +
			"          }\n" +
			"        ],\n" +
			"      \"options\" : { \"arteryWeights\" : [  ],\n" +
			"          \"avoidTimedConditions\" : false,\n" +
			"          \"avoidTripIds\" : [  ],\n" +
			"          \"countryBoundaryDisplay\" : true,\n" +
			"          \"cyclingRoadFactor\" : 1,\n" +
			"          \"destinationManeuverDisplay\" : true,\n" +
			"          \"drivingStyle\" : 2,\n" +
			"          \"enhancedNarrative\" : false,\n" +
			"          \"filterZoneFactor\" : -1,\n" +
			"          \"generalize\" : 50000,\n" +
			"          \"highwayEfficiency\" : 22,\n" +
			"          \"locale\" : \"en_US\",\n" +
			"          \"maneuverPenalty\" : -1,\n" +
			"          \"manmaps\" : \"true\",\n" +
			"          \"maxLinkId\" : 0,\n" +
			"          \"maxWalkingDistance\" : -1,\n" +
			"          \"mustAvoidLinkIds\" : [  ],\n" +
			"          \"narrativeType\" : \"text\",\n" +
			"          \"projection\" : \"Equirectangular\",\n" +
			"          \"returnLinkDirections\" : false,\n" +
			"          \"routeNumber\" : 0,\n" +
			"          \"routeType\" : \"FASTEST\",\n" +
			"          \"shapeFormat\" : \"raw\",\n" +
			"          \"sideOfStreetDisplay\" : true,\n" +
			"          \"stateBoundaryDisplay\" : true,\n" +
			"          \"timeType\" : 1,\n" +
			"          \"transferPenalty\" : -1,\n" +
			"          \"tryAvoidLinkIds\" : [  ],\n" +
			"          \"unit\" : \"M\",\n" +
			"          \"urbanAvoidFactor\" : -1,\n" +
			"          \"useTraffic\" : false,\n" +
			"          \"walkingSpeed\" : -1\n" +
			"        },\n" +
			"      \"realTime\" : -1,\n" +
			"      \"sessionId\" : \"50e4b73f-0293-0014-02b7-7f1f-002655800362\",\n" +
			"      \"shape\" : { \"legIndexes\" : [ 0,\n" +
			"              28\n" +
			"            ],\n" +
			"          \"maneuverIndexes\" : [ 0,\n" +
			"              2,\n" +
			"              4,\n" +
			"              6,\n" +
			"              8,\n" +
			"              10,\n" +
			"              12,\n" +
			"              14,\n" +
			"              16,\n" +
			"              18,\n" +
			"              20,\n" +
			"              22,\n" +
			"              24,\n" +
			"              26,\n" +
			"              28\n" +
			"            ],\n" +
			"          \"shapePoints\" : [ 39.529170000000001,\n" +
			"              -119.813987,\n" +
			"              39.583613999999997,\n" +
			"              -119.829948,\n" +
			"              39.583613999999997,\n" +
			"              -119.829948,\n" +
			"              39.587032000000001,\n" +
			"              -119.827247,\n" +
			"              39.587032000000001,\n" +
			"              -119.827247,\n" +
			"              40.380546000000002,\n" +
			"              -120.591865,\n" +
			"              40.380546000000002,\n" +
			"              -120.591865,\n" +
			"              40.415492999999998,\n" +
			"              -120.64892500000001,\n" +
			"              40.415492999999998,\n" +
			"              -120.64892500000001,\n" +
			"              41.187449999999998,\n" +
			"              -120.945121,\n" +
			"              41.187449999999998,\n" +
			"              -120.945121,\n" +
			"              41.437961000000001,\n" +
			"              -120.87863900000001,\n" +
			"              41.437961000000001,\n" +
			"              -120.87863900000001,\n" +
			"              41.998278999999997,\n" +
			"              -121.519485,\n" +
			"              41.998278999999997,\n" +
			"              -121.519485,\n" +
			"              42.206507999999999,\n" +
			"              -121.73674699999999,\n" +
			"              42.206507999999999,\n" +
			"              -121.73674699999999,\n" +
			"              42.253008999999999,\n" +
			"              -121.79605100000001,\n" +
			"              42.253008999999999,\n" +
			"              -121.79605100000001,\n" +
			"              44.052653999999997,\n" +
			"              -121.307075,\n" +
			"              44.052653999999997,\n" +
			"              -121.307075,\n" +
			"              44.051551000000003,\n" +
			"              -121.30613700000001,\n" +
			"              44.051551000000003,\n" +
			"              -121.30613700000001,\n" +
			"              44.052321999999997,\n" +
			"              -121.316474,\n" +
			"              44.052321999999997,\n" +
			"              -121.316474,\n" +
			"              44.057429999999997,\n" +
			"              -121.31401,\n" +
			"              44.057429999999997,\n" +
			"              -121.31401,\n" +
			"              44.058174000000001,\n" +
			"              -121.315307\n" +
			"            ]\n" +
			"        },\n" +
			"      \"time\" : 27175\n" +
			"    }\n" +
			"}\n" +
			"";

}
