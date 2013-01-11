package com.voyagegames.weatherroute.core.xml.weather.tests;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.ByteArrayInputStream;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.junit.Before;
import org.junit.Test;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import com.voyagegames.weatherroute.core.ILogger;
import com.voyagegames.weatherroute.core.xml.weather.EndState;
import com.voyagegames.weatherroute.core.xml.weather.WeatherParser;

public class WeatherParserTest {

	private final ILogger mLogger = new Logger();

	private Enumerator mEnumerator;
	private WeatherParser mParser;

	@Before
	public void setUp() throws Exception {
		mEnumerator = new Enumerator();
		mParser = new WeatherParser();
		mParser.initialize(mLogger, mEnumerator);
	}

	@Test
	public void testSuccessfulParse() {
		try {
		    final SAXParserFactory saxPF = SAXParserFactory.newInstance();
		    final SAXParser saxP = saxPF.newSAXParser();
		    final XMLReader xmlR = saxP.getXMLReader();

		    xmlR.setContentHandler(mParser);
		    xmlR.parse(new InputSource(new ByteArrayInputStream(VALID_STRING.getBytes("UTF-8"))));
		    
			if (!mParser.currentState().contentEquals(EndState.ELEMENT)) {
				mLogger.log("testSuccessfulParse", mParser.currentState());
			}

		    for (final String s : mParser.chain()) {
		    	mLogger.log("testSuccessfulParse", s + " ->");
		    }
		    
		    assertTrue(mEnumerator.completed());
		    assertTrue(mEnumerator.timeLayoutsCompleted());
		    assertTrue(mEnumerator.startTimes().size() == 140);
		    assertTrue(mEnumerator.iconLinks().size() == 40);
		    assertTrue(mEnumerator.nullCount() == 30);
		    assertTrue(mEnumerator.attributes().keySet().size() == 5);
		    assertTrue(mEnumerator.attributes().get("coverage").size() == 12);
		    assertTrue(mEnumerator.attributes().get("intensity").size() == 12);
		    assertTrue(mEnumerator.attributes().get("qualifier").size() == 12);
		    assertTrue(mEnumerator.attributes().get("weather-type").size() == 12);
		    assertTrue(mEnumerator.attributes().get("additive").size() == 2);
		} catch (final Exception e) {
			mLogger.log("testSuccessfulParse", e.getMessage(), e);
			fail("Parsing failed");
		}
	}

	private static final String VALID_STRING =
			"<?xml version=\"1.0\"?>\n" +
			"<dwml version=\"1.0\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:noNamespaceSchemaLocation=\"http://graphical.weather.gov/xml/DWMLgen/schema/DWML.xsd\">\n" +
			"  <head>\n" +
			"    <product srsName=\"WGS 1984\" concise-name=\"time-series\" operational-mode=\"official\">\n" +
			"      <title>NOAA's National Weather Service Forecast Data</title>\n" +
			"      <field>meteorological</field>\n" +
			"      <category>forecast</category>\n" +
			"      <creation-date refresh-frequency=\"PT1H\">2013-01-04T00:30:04Z</creation-date>\n" +
			"    </product>\n" +
			"    <source>\n" +
			"      <more-information>http://graphical.weather.gov/xml/</more-information>\n" +
			"      <production-center>Meteorological Development Laboratory<sub-center>Product Generation Branch</sub-center></production-center>\n" +
			"      <disclaimer>http://www.nws.noaa.gov/disclaimer.html</disclaimer>\n" +
			"      <credit>http://www.weather.gov/</credit>\n" +
			"      <credit-logo>http://www.weather.gov/images/xml_logo.gif</credit-logo>\n" +
			"      <feedback>http://www.weather.gov/feedback.php</feedback>\n" +
			"    </source>\n" +
			"  </head>\n" +
			"  <data>\n" +
			"    <location>\n" +
			"      <location-key>point1</location-key>\n" +
			"      <point latitude=\"39.53\" longitude=\"-119.81\"/>\n" +
			"    </location>\n" +
			"    <moreWeatherInformation applicable-location=\"point1\">http://forecast.weather.gov/MapClick.php?textField1=39.53&amp;textField2=-119.81</moreWeatherInformation>\n" +
			"    <time-layout time-coordinate=\"local\" summarization=\"none\">\n" +
			"      <layout-key>k-p3h-n40-1</layout-key>\n" +
			"      <start-valid-time>2013-01-03T19:00:00-08:00</start-valid-time>\n" +
			"      <start-valid-time>2013-01-03T22:00:00-08:00</start-valid-time>\n" +
			"      <start-valid-time>2013-01-04T01:00:00-08:00</start-valid-time>\n" +
			"      <start-valid-time>2013-01-04T04:00:00-08:00</start-valid-time>\n" +
			"      <start-valid-time>2013-01-04T07:00:00-08:00</start-valid-time>\n" +
			"      <start-valid-time>2013-01-04T10:00:00-08:00</start-valid-time>\n" +
			"      <start-valid-time>2013-01-04T13:00:00-08:00</start-valid-time>\n" +
			"      <start-valid-time>2013-01-04T16:00:00-08:00</start-valid-time>\n" +
			"      <start-valid-time>2013-01-04T19:00:00-08:00</start-valid-time>\n" +
			"      <start-valid-time>2013-01-04T22:00:00-08:00</start-valid-time>\n" +
			"      <start-valid-time>2013-01-05T01:00:00-08:00</start-valid-time>\n" +
			"      <start-valid-time>2013-01-05T04:00:00-08:00</start-valid-time>\n" +
			"      <start-valid-time>2013-01-05T07:00:00-08:00</start-valid-time>\n" +
			"      <start-valid-time>2013-01-05T10:00:00-08:00</start-valid-time>\n" +
			"      <start-valid-time>2013-01-05T13:00:00-08:00</start-valid-time>\n" +
			"      <start-valid-time>2013-01-05T16:00:00-08:00</start-valid-time>\n" +
			"      <start-valid-time>2013-01-05T19:00:00-08:00</start-valid-time>\n" +
			"      <start-valid-time>2013-01-05T22:00:00-08:00</start-valid-time>\n" +
			"      <start-valid-time>2013-01-06T01:00:00-08:00</start-valid-time>\n" +
			"      <start-valid-time>2013-01-06T04:00:00-08:00</start-valid-time>\n" +
			"      <start-valid-time>2013-01-06T07:00:00-08:00</start-valid-time>\n" +
			"      <start-valid-time>2013-01-06T10:00:00-08:00</start-valid-time>\n" +
			"      <start-valid-time>2013-01-06T13:00:00-08:00</start-valid-time>\n" +
			"      <start-valid-time>2013-01-06T16:00:00-08:00</start-valid-time>\n" +
			"      <start-valid-time>2013-01-06T22:00:00-08:00</start-valid-time>\n" +
			"      <start-valid-time>2013-01-07T04:00:00-08:00</start-valid-time>\n" +
			"      <start-valid-time>2013-01-07T10:00:00-08:00</start-valid-time>\n" +
			"      <start-valid-time>2013-01-07T16:00:00-08:00</start-valid-time>\n" +
			"      <start-valid-time>2013-01-07T22:00:00-08:00</start-valid-time>\n" +
			"      <start-valid-time>2013-01-08T04:00:00-08:00</start-valid-time>\n" +
			"      <start-valid-time>2013-01-08T10:00:00-08:00</start-valid-time>\n" +
			"      <start-valid-time>2013-01-08T16:00:00-08:00</start-valid-time>\n" +
			"      <start-valid-time>2013-01-08T22:00:00-08:00</start-valid-time>\n" +
			"      <start-valid-time>2013-01-09T04:00:00-08:00</start-valid-time>\n" +
			"      <start-valid-time>2013-01-09T10:00:00-08:00</start-valid-time>\n" +
			"      <start-valid-time>2013-01-09T16:00:00-08:00</start-valid-time>\n" +
			"      <start-valid-time>2013-01-09T22:00:00-08:00</start-valid-time>\n" +
			"      <start-valid-time>2013-01-10T04:00:00-08:00</start-valid-time>\n" +
			"      <start-valid-time>2013-01-10T10:00:00-08:00</start-valid-time>\n" +
			"      <start-valid-time>2013-01-10T16:00:00-08:00</start-valid-time>\n" +
			"    </time-layout>\n" +
			"    <time-layout time-coordinate=\"local\" summarization=\"none\">\n" +
			"      <layout-key>k-p1h-n100-2</layout-key>\n" +
			"      <start-valid-time>2013-01-03T17:00:00-08:00</start-valid-time>\n" +
			"      <start-valid-time>2013-01-03T18:00:00-08:00</start-valid-time>\n" +
			"      <start-valid-time>2013-01-03T19:00:00-08:00</start-valid-time>\n" +
			"      <start-valid-time>2013-01-03T20:00:00-08:00</start-valid-time>\n" +
			"      <start-valid-time>2013-01-03T21:00:00-08:00</start-valid-time>\n" +
			"      <start-valid-time>2013-01-03T22:00:00-08:00</start-valid-time>\n" +
			"      <start-valid-time>2013-01-03T23:00:00-08:00</start-valid-time>\n" +
			"      <start-valid-time>2013-01-04T00:00:00-08:00</start-valid-time>\n" +
			"      <start-valid-time>2013-01-04T01:00:00-08:00</start-valid-time>\n" +
			"      <start-valid-time>2013-01-04T02:00:00-08:00</start-valid-time>\n" +
			"      <start-valid-time>2013-01-04T03:00:00-08:00</start-valid-time>\n" +
			"      <start-valid-time>2013-01-04T04:00:00-08:00</start-valid-time>\n" +
			"      <start-valid-time>2013-01-04T05:00:00-08:00</start-valid-time>\n" +
			"      <start-valid-time>2013-01-04T06:00:00-08:00</start-valid-time>\n" +
			"      <start-valid-time>2013-01-04T07:00:00-08:00</start-valid-time>\n" +
			"      <start-valid-time>2013-01-04T08:00:00-08:00</start-valid-time>\n" +
			"      <start-valid-time>2013-01-04T09:00:00-08:00</start-valid-time>\n" +
			"      <start-valid-time>2013-01-04T10:00:00-08:00</start-valid-time>\n" +
			"      <start-valid-time>2013-01-04T11:00:00-08:00</start-valid-time>\n" +
			"      <start-valid-time>2013-01-04T12:00:00-08:00</start-valid-time>\n" +
			"      <start-valid-time>2013-01-04T13:00:00-08:00</start-valid-time>\n" +
			"      <start-valid-time>2013-01-04T14:00:00-08:00</start-valid-time>\n" +
			"      <start-valid-time>2013-01-04T15:00:00-08:00</start-valid-time>\n" +
			"      <start-valid-time>2013-01-04T16:00:00-08:00</start-valid-time>\n" +
			"      <start-valid-time>2013-01-04T17:00:00-08:00</start-valid-time>\n" +
			"      <start-valid-time>2013-01-04T18:00:00-08:00</start-valid-time>\n" +
			"      <start-valid-time>2013-01-04T19:00:00-08:00</start-valid-time>\n" +
			"      <start-valid-time>2013-01-04T20:00:00-08:00</start-valid-time>\n" +
			"      <start-valid-time>2013-01-04T21:00:00-08:00</start-valid-time>\n" +
			"      <start-valid-time>2013-01-04T22:00:00-08:00</start-valid-time>\n" +
			"      <start-valid-time>2013-01-04T23:00:00-08:00</start-valid-time>\n" +
			"      <start-valid-time>2013-01-05T00:00:00-08:00</start-valid-time>\n" +
			"      <start-valid-time>2013-01-05T01:00:00-08:00</start-valid-time>\n" +
			"      <start-valid-time>2013-01-05T02:00:00-08:00</start-valid-time>\n" +
			"      <start-valid-time>2013-01-05T03:00:00-08:00</start-valid-time>\n" +
			"      <start-valid-time>2013-01-05T04:00:00-08:00</start-valid-time>\n" +
			"      <start-valid-time>2013-01-05T05:00:00-08:00</start-valid-time>\n" +
			"      <start-valid-time>2013-01-05T06:00:00-08:00</start-valid-time>\n" +
			"      <start-valid-time>2013-01-05T07:00:00-08:00</start-valid-time>\n" +
			"      <start-valid-time>2013-01-05T08:00:00-08:00</start-valid-time>\n" +
			"      <start-valid-time>2013-01-05T09:00:00-08:00</start-valid-time>\n" +
			"      <start-valid-time>2013-01-05T10:00:00-08:00</start-valid-time>\n" +
			"      <start-valid-time>2013-01-05T11:00:00-08:00</start-valid-time>\n" +
			"      <start-valid-time>2013-01-05T12:00:00-08:00</start-valid-time>\n" +
			"      <start-valid-time>2013-01-05T13:00:00-08:00</start-valid-time>\n" +
			"      <start-valid-time>2013-01-05T14:00:00-08:00</start-valid-time>\n" +
			"      <start-valid-time>2013-01-05T15:00:00-08:00</start-valid-time>\n" +
			"      <start-valid-time>2013-01-05T16:00:00-08:00</start-valid-time>\n" +
			"      <start-valid-time>2013-01-05T17:00:00-08:00</start-valid-time>\n" +
			"      <start-valid-time>2013-01-05T18:00:00-08:00</start-valid-time>\n" +
			"      <start-valid-time>2013-01-05T19:00:00-08:00</start-valid-time>\n" +
			"      <start-valid-time>2013-01-05T20:00:00-08:00</start-valid-time>\n" +
			"      <start-valid-time>2013-01-05T21:00:00-08:00</start-valid-time>\n" +
			"      <start-valid-time>2013-01-05T22:00:00-08:00</start-valid-time>\n" +
			"      <start-valid-time>2013-01-05T23:00:00-08:00</start-valid-time>\n" +
			"      <start-valid-time>2013-01-06T00:00:00-08:00</start-valid-time>\n" +
			"      <start-valid-time>2013-01-06T01:00:00-08:00</start-valid-time>\n" +
			"      <start-valid-time>2013-01-06T02:00:00-08:00</start-valid-time>\n" +
			"      <start-valid-time>2013-01-06T03:00:00-08:00</start-valid-time>\n" +
			"      <start-valid-time>2013-01-06T04:00:00-08:00</start-valid-time>\n" +
			"      <start-valid-time>2013-01-06T05:00:00-08:00</start-valid-time>\n" +
			"      <start-valid-time>2013-01-06T06:00:00-08:00</start-valid-time>\n" +
			"      <start-valid-time>2013-01-06T07:00:00-08:00</start-valid-time>\n" +
			"      <start-valid-time>2013-01-06T08:00:00-08:00</start-valid-time>\n" +
			"      <start-valid-time>2013-01-06T09:00:00-08:00</start-valid-time>\n" +
			"      <start-valid-time>2013-01-06T10:00:00-08:00</start-valid-time>\n" +
			"      <start-valid-time>2013-01-06T11:00:00-08:00</start-valid-time>\n" +
			"      <start-valid-time>2013-01-06T12:00:00-08:00</start-valid-time>\n" +
			"      <start-valid-time>2013-01-06T13:00:00-08:00</start-valid-time>\n" +
			"      <start-valid-time>2013-01-06T14:00:00-08:00</start-valid-time>\n" +
			"      <start-valid-time>2013-01-06T15:00:00-08:00</start-valid-time>\n" +
			"      <start-valid-time>2013-01-06T16:00:00-08:00</start-valid-time>\n" +
			"      <start-valid-time>2013-01-06T17:00:00-08:00</start-valid-time>\n" +
			"      <start-valid-time>2013-01-06T18:00:00-08:00</start-valid-time>\n" +
			"      <start-valid-time>2013-01-06T19:00:00-08:00</start-valid-time>\n" +
			"      <start-valid-time>2013-01-06T20:00:00-08:00</start-valid-time>\n" +
			"      <start-valid-time>2013-01-06T21:00:00-08:00</start-valid-time>\n" +
			"      <start-valid-time>2013-01-06T22:00:00-08:00</start-valid-time>\n" +
			"      <start-valid-time>2013-01-06T23:00:00-08:00</start-valid-time>\n" +
			"      <start-valid-time>2013-01-07T00:00:00-08:00</start-valid-time>\n" +
			"      <start-valid-time>2013-01-07T01:00:00-08:00</start-valid-time>\n" +
			"      <start-valid-time>2013-01-07T02:00:00-08:00</start-valid-time>\n" +
			"      <start-valid-time>2013-01-07T03:00:00-08:00</start-valid-time>\n" +
			"      <start-valid-time>2013-01-07T04:00:00-08:00</start-valid-time>\n" +
			"      <start-valid-time>2013-01-07T05:00:00-08:00</start-valid-time>\n" +
			"      <start-valid-time>2013-01-07T06:00:00-08:00</start-valid-time>\n" +
			"      <start-valid-time>2013-01-07T07:00:00-08:00</start-valid-time>\n" +
			"      <start-valid-time>2013-01-07T08:00:00-08:00</start-valid-time>\n" +
			"      <start-valid-time>2013-01-07T09:00:00-08:00</start-valid-time>\n" +
			"      <start-valid-time>2013-01-07T10:00:00-08:00</start-valid-time>\n" +
			"      <start-valid-time>2013-01-07T11:00:00-08:00</start-valid-time>\n" +
			"      <start-valid-time>2013-01-07T12:00:00-08:00</start-valid-time>\n" +
			"      <start-valid-time>2013-01-07T13:00:00-08:00</start-valid-time>\n" +
			"      <start-valid-time>2013-01-07T14:00:00-08:00</start-valid-time>\n" +
			"      <start-valid-time>2013-01-07T15:00:00-08:00</start-valid-time>\n" +
			"      <start-valid-time>2013-01-07T16:00:00-08:00</start-valid-time>\n" +
			"      <start-valid-time>2013-01-07T22:00:00-08:00</start-valid-time>\n" +
			"      <start-valid-time>2013-01-08T04:00:00-08:00</start-valid-time>\n" +
			"      <start-valid-time>2013-01-08T10:00:00-08:00</start-valid-time>\n" +
			"      <start-valid-time>2013-01-08T16:00:00-08:00</start-valid-time>\n" +
			"    </time-layout>\n" +
			"    <parameters applicable-location=\"point1\">\n" +
			"      <temperature type=\"hourly\" units=\"Fahrenheit\" time-layout=\"k-p3h-n40-1\">\n" +
			"        <name>Temperature</name>\n" +
			"        <value>22</value>\n" +
			"        <value>17</value>\n" +
			"        <value>13</value>\n" +
			"        <value>11</value>\n" +
			"        <value>11</value>\n" +
			"        <value>21</value>\n" +
			"        <value>28</value>\n" +
			"        <value>28</value>\n" +
			"        <value>23</value>\n" +
			"        <value>19</value>\n" +
			"        <value>16</value>\n" +
			"        <value>14</value>\n" +
			"        <value>14</value>\n" +
			"        <value>23</value>\n" +
			"        <value>29</value>\n" +
			"        <value>29</value>\n" +
			"        <value>25</value>\n" +
			"        <value>22</value>\n" +
			"        <value>20</value>\n" +
			"        <value>19</value>\n" +
			"        <value>19</value>\n" +
			"        <value>29</value>\n" +
			"        <value>37</value>\n" +
			"        <value>37</value>\n" +
			"        <value>26</value>\n" +
			"        <value>20</value>\n" +
			"        <value>29</value>\n" +
			"        <value>35</value>\n" +
			"        <value>25</value>\n" +
			"        <value>20</value>\n" +
			"        <value>31</value>\n" +
			"        <value>39</value>\n" +
			"        <value>31</value>\n" +
			"        <value>27</value>\n" +
			"        <value>39</value>\n" +
			"        <value>47</value>\n" +
			"        <value>28</value>\n" +
			"        <value>19</value>\n" +
			"        <value>26</value>\n" +
			"        <value>31</value>\n" +
			"      </temperature>\n" +
			"      <hazards time-layout=\"k-p1h-n100-2\">\n" +
			"        <name>Watches, Warnings, and Advisories</name>\n" +
			"        <hazard-conditions/>\n" +
			"        <hazard-conditions/>\n" +
			"        <hazard-conditions/>\n" +
			"        <hazard-conditions/>\n" +
			"        <hazard-conditions/>\n" +
			"        <hazard-conditions/>\n" +
			"        <hazard-conditions/>\n" +
			"        <hazard-conditions/>\n" +
			"        <hazard-conditions/>\n" +
			"        <hazard-conditions/>\n" +
			"        <hazard-conditions/>\n" +
			"        <hazard-conditions/>\n" +
			"        <hazard-conditions/>\n" +
			"        <hazard-conditions/>\n" +
			"        <hazard-conditions/>\n" +
			"        <hazard-conditions/>\n" +
			"        <hazard-conditions/>\n" +
			"        <hazard-conditions/>\n" +
			"        <hazard-conditions/>\n" +
			"        <hazard-conditions/>\n" +
			"        <hazard-conditions/>\n" +
			"        <hazard-conditions/>\n" +
			"        <hazard-conditions/>\n" +
			"        <hazard-conditions/>\n" +
			"        <hazard-conditions/>\n" +
			"        <hazard-conditions/>\n" +
			"        <hazard-conditions/>\n" +
			"        <hazard-conditions/>\n" +
			"        <hazard-conditions/>\n" +
			"        <hazard-conditions/>\n" +
			"        <hazard-conditions/>\n" +
			"        <hazard-conditions/>\n" +
			"        <hazard-conditions/>\n" +
			"        <hazard-conditions/>\n" +
			"        <hazard-conditions/>\n" +
			"        <hazard-conditions/>\n" +
			"        <hazard-conditions/>\n" +
			"        <hazard-conditions/>\n" +
			"        <hazard-conditions/>\n" +
			"        <hazard-conditions/>\n" +
			"        <hazard-conditions/>\n" +
			"        <hazard-conditions/>\n" +
			"        <hazard-conditions/>\n" +
			"        <hazard-conditions/>\n" +
			"        <hazard-conditions/>\n" +
			"        <hazard-conditions/>\n" +
			"        <hazard-conditions/>\n" +
			"        <hazard-conditions/>\n" +
			"        <hazard-conditions/>\n" +
			"        <hazard-conditions/>\n" +
			"        <hazard-conditions/>\n" +
			"        <hazard-conditions/>\n" +
			"        <hazard-conditions/>\n" +
			"        <hazard-conditions/>\n" +
			"        <hazard-conditions/>\n" +
			"        <hazard-conditions/>\n" +
			"        <hazard-conditions/>\n" +
			"        <hazard-conditions/>\n" +
			"        <hazard-conditions/>\n" +
			"        <hazard-conditions/>\n" +
			"        <hazard-conditions/>\n" +
			"        <hazard-conditions/>\n" +
			"        <hazard-conditions/>\n" +
			"        <hazard-conditions/>\n" +
			"        <hazard-conditions/>\n" +
			"        <hazard-conditions/>\n" +
			"        <hazard-conditions/>\n" +
			"        <hazard-conditions/>\n" +
			"        <hazard-conditions/>\n" +
			"        <hazard-conditions/>\n" +
			"        <hazard-conditions/>\n" +
			"        <hazard-conditions/>\n" +
			"        <hazard-conditions/>\n" +
			"        <hazard-conditions/>\n" +
			"        <hazard-conditions/>\n" +
			"        <hazard-conditions/>\n" +
			"        <hazard-conditions/>\n" +
			"        <hazard-conditions/>\n" +
			"        <hazard-conditions/>\n" +
			"        <hazard-conditions/>\n" +
			"        <hazard-conditions/>\n" +
			"        <hazard-conditions/>\n" +
			"        <hazard-conditions/>\n" +
			"        <hazard-conditions/>\n" +
			"        <hazard-conditions/>\n" +
			"        <hazard-conditions/>\n" +
			"        <hazard-conditions/>\n" +
			"        <hazard-conditions/>\n" +
			"        <hazard-conditions/>\n" +
			"        <hazard-conditions/>\n" +
			"        <hazard-conditions/>\n" +
			"        <hazard-conditions/>\n" +
			"        <hazard-conditions/>\n" +
			"        <hazard-conditions/>\n" +
			"        <hazard-conditions/>\n" +
			"        <hazard-conditions/>\n" +
			"        <hazard-conditions/>\n" +
			"        <hazard-conditions/>\n" +
			"        <hazard-conditions/>\n" +
			"        <hazard-conditions/>\n" +
			"      </hazards>\n" +
			"      <weather time-layout=\"k-p3h-n40-1\">\n" +
			"        <name>Weather Type, Coverage, and Intensity</name>\n" +
			"        <weather-conditions/>\n" +
			"        <weather-conditions/>\n" +
			"        <weather-conditions/>\n" +
			"        <weather-conditions/>\n" +
			"        <weather-conditions/>\n" +
			"        <weather-conditions/>\n" +
			"        <weather-conditions/>\n" +
			"        <weather-conditions/>\n" +
			"        <weather-conditions/>\n" +
			"        <weather-conditions/>\n" +
			"        <weather-conditions/>\n" +
			"        <weather-conditions/>\n" +
			"        <weather-conditions/>\n" +
			"        <weather-conditions/>\n" +
			"        <weather-conditions/>\n" +
			"        <weather-conditions/>\n" +
			"        <weather-conditions/>\n" +
			"        <weather-conditions/>\n" +
			"        <weather-conditions/>\n" +
			"        <weather-conditions>\n" +
			"          <value coverage=\"slight chance\" intensity=\"light\" weather-type=\"snow\" qualifier=\"none\">\n" +
			"            <visibility xsi:nil=\"true\"/>\n" +
			"          </value>\n" +
			"        </weather-conditions>\n" +
			"        <weather-conditions>\n" +
			"          <value coverage=\"slight chance\" intensity=\"light\" weather-type=\"snow\" qualifier=\"none\">\n" +
			"            <visibility xsi:nil=\"true\"/>\n" +
			"          </value>\n" +
			"        </weather-conditions>\n" +
			"        <weather-conditions>\n" +
			"          <value coverage=\"slight chance\" intensity=\"light\" weather-type=\"snow\" qualifier=\"none\">\n" +
			"            <visibility xsi:nil=\"true\"/>\n" +
			"          </value>\n" +
			"        </weather-conditions>\n" +
			"        <weather-conditions>\n" +
			"          <value coverage=\"slight chance\" intensity=\"light\" weather-type=\"snow\" qualifier=\"none\">\n" +
			"            <visibility xsi:nil=\"true\"/>\n" +
			"          </value>\n" +
			"        </weather-conditions>\n" +
			"        <weather-conditions>\n" +
			"          <value coverage=\"slight chance\" intensity=\"light\" weather-type=\"rain\" qualifier=\"none\">\n" +
			"            <visibility xsi:nil=\"true\"/>\n" +
			"          </value>\n" +
			"          <value coverage=\"slight chance\" intensity=\"light\" additive=\"and\" weather-type=\"snow\" qualifier=\"none\">\n" +
			"            <visibility xsi:nil=\"true\"/>\n" +
			"          </value>\n" +
			"        </weather-conditions>\n" +
			"        <weather-conditions>\n" +
			"          <value coverage=\"slight chance\" intensity=\"light\" weather-type=\"rain\" qualifier=\"none\">\n" +
			"            <visibility xsi:nil=\"true\"/>\n" +
			"          </value>\n" +
			"          <value coverage=\"slight chance\" intensity=\"light\" additive=\"and\" weather-type=\"snow\" qualifier=\"none\">\n" +
			"            <visibility xsi:nil=\"true\"/>\n" +
			"          </value>\n" +
			"        </weather-conditions>\n" +
			"        <weather-conditions/>\n" +
			"        <weather-conditions/>\n" +
			"        <weather-conditions/>\n" +
			"        <weather-conditions/>\n" +
			"        <weather-conditions/>\n" +
			"        <weather-conditions/>\n" +
			"        <weather-conditions/>\n" +
			"        <weather-conditions/>\n" +
			"        <weather-conditions/>\n" +
			"        <weather-conditions/>\n" +
			"        <weather-conditions>\n" +
			"          <value coverage=\"slight chance\" intensity=\"light\" weather-type=\"snow\" qualifier=\"none\">\n" +
			"            <visibility xsi:nil=\"true\"/>\n" +
			"          </value>\n" +
			"        </weather-conditions>\n" +
			"        <weather-conditions>\n" +
			"          <value coverage=\"slight chance\" intensity=\"light\" weather-type=\"snow\" qualifier=\"none\">\n" +
			"            <visibility xsi:nil=\"true\"/>\n" +
			"          </value>\n" +
			"        </weather-conditions>\n" +
			"        <weather-conditions>\n" +
			"          <value coverage=\"slight chance\" intensity=\"light\" weather-type=\"snow showers\" qualifier=\"none\">\n" +
			"            <visibility xsi:nil=\"true\"/>\n" +
			"          </value>\n" +
			"        </weather-conditions>\n" +
			"        <weather-conditions>\n" +
			"          <value coverage=\"slight chance\" intensity=\"light\" weather-type=\"snow showers\" qualifier=\"none\">\n" +
			"            <visibility xsi:nil=\"true\"/>\n" +
			"          </value>\n" +
			"        </weather-conditions>\n" +
			"        <weather-conditions/>\n" +
			"      </weather>\n" +
			"      <conditions-icon type=\"forecast-NWS\" time-layout=\"k-p3h-n40-1\">\n" +
			"        <name>Conditions Icons</name>\n" +
			"        <icon-link>http://forecast.weather.gov/images/wtf/nfew.jpg</icon-link>\n" +
			"        <icon-link>http://forecast.weather.gov/images/wtf/nfew.jpg</icon-link>\n" +
			"        <icon-link>http://forecast.weather.gov/images/wtf/nfew.jpg</icon-link>\n" +
			"        <icon-link>http://forecast.weather.gov/images/wtf/nsct.jpg</icon-link>\n" +
			"        <icon-link>http://forecast.weather.gov/images/wtf/nsct.jpg</icon-link>\n" +
			"        <icon-link>http://forecast.weather.gov/images/wtf/sct.jpg</icon-link>\n" +
			"        <icon-link>http://forecast.weather.gov/images/wtf/sct.jpg</icon-link>\n" +
			"        <icon-link>http://forecast.weather.gov/images/wtf/sct.jpg</icon-link>\n" +
			"        <icon-link>http://forecast.weather.gov/images/wtf/nsct.jpg</icon-link>\n" +
			"        <icon-link>http://forecast.weather.gov/images/wtf/nsct.jpg</icon-link>\n" +
			"        <icon-link>http://forecast.weather.gov/images/wtf/nsct.jpg</icon-link>\n" +
			"        <icon-link>http://forecast.weather.gov/images/wtf/nsct.jpg</icon-link>\n" +
			"        <icon-link>http://forecast.weather.gov/images/wtf/nsct.jpg</icon-link>\n" +
			"        <icon-link>http://forecast.weather.gov/images/wtf/sct.jpg</icon-link>\n" +
			"        <icon-link>http://forecast.weather.gov/images/wtf/sct.jpg</icon-link>\n" +
			"        <icon-link>http://forecast.weather.gov/images/wtf/bkn.jpg</icon-link>\n" +
			"        <icon-link>http://forecast.weather.gov/images/wtf/nbkn.jpg</icon-link>\n" +
			"        <icon-link>http://forecast.weather.gov/images/wtf/nbkn.jpg</icon-link>\n" +
			"        <icon-link>http://forecast.weather.gov/images/wtf/nbkn.jpg</icon-link>\n" +
			"        <icon-link>http://forecast.weather.gov/images/wtf/nsn.jpg</icon-link>\n" +
			"        <icon-link>http://forecast.weather.gov/images/wtf/nsn20.jpg</icon-link>\n" +
			"        <icon-link>http://forecast.weather.gov/images/wtf/sn20.jpg</icon-link>\n" +
			"        <icon-link>http://forecast.weather.gov/images/wtf/sn20.jpg</icon-link>\n" +
			"        <icon-link>http://forecast.weather.gov/images/wtf/rasn20.jpg</icon-link>\n" +
			"        <icon-link>http://forecast.weather.gov/images/wtf/nrasn20.jpg</icon-link>\n" +
			"        <icon-link>http://forecast.weather.gov/images/wtf/nsct.jpg</icon-link>\n" +
			"        <icon-link>http://forecast.weather.gov/images/wtf/sct.jpg</icon-link>\n" +
			"        <icon-link>http://forecast.weather.gov/images/wtf/sct.jpg</icon-link>\n" +
			"        <icon-link>http://forecast.weather.gov/images/wtf/nsct.jpg</icon-link>\n" +
			"        <icon-link>http://forecast.weather.gov/images/wtf/nbkn.jpg</icon-link>\n" +
			"        <icon-link>http://forecast.weather.gov/images/wtf/bkn.jpg</icon-link>\n" +
			"        <icon-link>http://forecast.weather.gov/images/wtf/sct.jpg</icon-link>\n" +
			"        <icon-link>http://forecast.weather.gov/images/wtf/nsct.jpg</icon-link>\n" +
			"        <icon-link>http://forecast.weather.gov/images/wtf/nbkn.jpg</icon-link>\n" +
			"        <icon-link>http://forecast.weather.gov/images/wtf/bkn.jpg</icon-link>\n" +
			"        <icon-link>http://forecast.weather.gov/images/wtf/sn10.jpg</icon-link>\n" +
			"        <icon-link>http://forecast.weather.gov/images/wtf/nsn20.jpg</icon-link>\n" +
			"        <icon-link>http://forecast.weather.gov/images/wtf/nsn20.jpg</icon-link>\n" +
			"        <icon-link>http://forecast.weather.gov/images/wtf/sn20.jpg</icon-link>\n" +
			"        <icon-link>http://forecast.weather.gov/images/wtf/sct.jpg</icon-link>\n" +
			"      </conditions-icon>\n" +
			"    </parameters>\n" +
			"  </data>\n" +
			"</dwml>";

}
