package com.voyagegames.monkeymatch.android;

import android.util.Log;

import com.voyagegames.monkeymatch.ILogger;

public class Logger implements ILogger {

	@Override
	public void log(final String msg) {
		Log.e("MonkeyMatch", msg);
	}

	@Override
	public void log(final Exception e) {
		Log.e("MonkeyMatch", e.toString(), e);
	}

}
