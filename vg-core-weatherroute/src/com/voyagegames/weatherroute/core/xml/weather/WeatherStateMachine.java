package com.voyagegames.weatherroute.core.xml.weather;

import org.xml.sax.Attributes;

import com.voyagegames.weatherroute.core.IState;
import com.voyagegames.weatherroute.core.StateMachine;

public class WeatherStateMachine extends StateMachine {

	public WeatherStateMachine(final IState[] states, final IState initState) {
		super(states, initState);
	}

	public void transition(final String name, final boolean elementEnding, final Attributes attributes) {
		final WeatherBaseXmlState s = (WeatherBaseXmlState)this.state();
		
		if (!elementEnding) {
			mChain.add(name);
		} else {
			final int size = mChain.size();
			
			if (size > 0) {
				mChain.remove(mChain.size() - 1);
			}
			
			s.pushData();
			s.resetData();
		}

		s.setIncomingElement(new IncomingElement(name, elementEnding));
		
		super.transition();
		
		final WeatherBaseXmlState n = (WeatherBaseXmlState)this.state();
		
		if (n != s && n.desiresAttributes()) {
			n.setAttributes(attributes);
		}
	}
	
	public void accumulateCharacters(final char[] ch, final int start, final int length) {
		((WeatherBaseXmlState)this.state()).accumulateCharacters(ch, start, length);
	}

}
