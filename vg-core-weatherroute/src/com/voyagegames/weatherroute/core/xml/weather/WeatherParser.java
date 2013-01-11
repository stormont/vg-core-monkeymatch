package com.voyagegames.weatherroute.core.xml.weather;

import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.voyagegames.weatherroute.core.ILogger;
import com.voyagegames.weatherroute.core.IState;
import com.voyagegames.weatherroute.core.IXmlHandler;

public class WeatherParser extends DefaultHandler implements IXmlHandler<IWeatherEnumerator> {

	private WeatherStateMachine mSM;
	private IWeatherEnumerator mEnumerator;
	
	@Override
	public void initialize(final ILogger logger, final IWeatherEnumerator enumerator) {
		final InitState initState = new InitState(logger, enumerator);
		final IState[] states = new IState[] {
				initState,
				new DwmlXmlState(logger, enumerator),
				new HeadXmlState(logger, enumerator),
				new DataXmlState(logger, enumerator),
				new TimeLayoutXmlState(logger, enumerator),
				new StartValidTimeXmlState(logger, enumerator),
				new ParametersXmlState(logger, enumerator),
				new TemperatureXmlState(logger, enumerator),
				new HazardsXmlState(logger, enumerator),
				new WeatherXmlState(logger, enumerator),
				new ConditionsIconXmlState(logger, enumerator),
				new ValueXmlState(logger, enumerator),
				new VisibilityXmlState(logger, enumerator),
				new IconLinkXmlState(logger, enumerator),
				new WeatherConditionsXmlState(logger, enumerator),
				new EndState(logger, enumerator)
			};
		
		mSM = new WeatherStateMachine(states, initState);
		mEnumerator = enumerator;
	}
	
	@Override
	public IWeatherEnumerator generic() {
		return mEnumerator;
	}
	
	public String currentState() {
		return mSM.state().key();
	}
	
	public List<String> chain() {
		return mSM.chain();
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
	}

	@Override
	public void endDocument() throws SAXException {
		super.endDocument();
		
		if (mSM.state().key().contentEquals(EndState.ELEMENT)) {
			mEnumerator.signalComplete();
		}
	}

	@Override
	public void startElement(final String uri, final String name, final String qName, final Attributes atts) throws SAXException {
		super.startElement(uri, name, qName, atts);
		mSM.transition(qName, false, atts);
	}

	@Override
	public void endElement(final String uri, final String name, final String qName) throws SAXException {
		super.endElement(uri, name, qName);
		mSM.transition(qName, true, null);
	}

	@Override
	public void characters(final char[] ch, final int start, final int length) throws SAXException {
		mSM.accumulateCharacters(ch, start, length);
	}

}
