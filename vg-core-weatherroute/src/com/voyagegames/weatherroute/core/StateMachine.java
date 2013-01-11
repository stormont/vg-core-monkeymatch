package com.voyagegames.weatherroute.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StateMachine {
	
	protected final List<String> mChain;
	
	private final Map<String, IState> mStates;
	
	private IState mCurState;
	
	public StateMachine(final IState[] states, final IState initState) {
		mChain = new ArrayList<String>();
		mStates = new HashMap<String, IState>();
		mCurState = initState;
		
		for (final IState s : states) {
			if (mStates.containsKey(s.key())) {
				throw new IllegalArgumentException("Duplicate state defintion for " + s.key());
			}
			
			mStates.put(s.key(), s);
		}
	}
	
	public IState state() {
		return mCurState;
	}
	
	public List<String> chain() {
		return mChain;
	}
	
	public void transition() {
		if (!mCurState.canTransition()) {
			return;
		}
		
		final String key = mCurState.performTransition();

		if (!mStates.containsKey(key)) {
			throw new IllegalArgumentException("Undefined transition state " + key + " from " + mCurState.key());
		}
		
		mCurState = mStates.get(key);
	}

}
