package com.voyagegames.weatherroute;

import java.io.IOException;
import java.net.MalformedURLException;

import com.voyagegames.weatherroute.core.HttpRequest;
import com.voyagegames.weatherroute.core.IHttpRequestCallback;

import android.os.AsyncTask;
import android.util.Log;

public class HttpRequestAsyncTask extends AsyncTask<String, String, String> {
	
	private static final String TAG = HttpRequestAsyncTask.class.getName();
	
	private final IHttpRequestCallback<String> mCallback;
	
	public HttpRequestAsyncTask(final IHttpRequestCallback<String> callback) {
		if (callback == null) {
			throw new IllegalArgumentException("callback is null");
		}
		
		mCallback = callback;
	}

	@Override
	protected String doInBackground(final String... params) {
		if (params == null || params.length != 1) {
			throw new IllegalArgumentException("params are invalid");
		}
		
		try {
			return (new HttpRequest().request(params[0]));
		} catch (final MalformedURLException e) {
        	Log.e(TAG, e.toString(), e);
		} catch (final IOException e) {
        	Log.e(TAG, e.toString(), e);
		}
        
        return null;
	}

	@Override
	protected void onCancelled() {
		mCallback.onCancelled();
		super.onCancelled();
	}

	@Override
	protected void onPostExecute(final String result) {
		mCallback.onPostExecute(result);
		super.onPostExecute(result);
	}

	@Override
	protected void onProgressUpdate(final String... values) {
		mCallback.onProgressUpdate(values);
		super.onProgressUpdate(values);
	}

}
