package com.voyagegames.monkeymatch;

public class StubLogger implements ILogger {

	@Override
	public void log(final String msg) {
		System.out.println(msg);
	}

	@Override
	public void log(final Exception e) {
		System.out.println(e.toString());
		e.printStackTrace();
	}

}
