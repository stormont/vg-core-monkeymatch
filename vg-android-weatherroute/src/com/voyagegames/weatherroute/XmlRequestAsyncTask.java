package com.voyagegames.weatherroute;

import java.net.URL;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import android.os.AsyncTask;
import android.util.Log;

import com.voyagegames.weatherroute.core.IHttpRequestCallback;

public class XmlRequestAsyncTask <T> extends AsyncTask<IUrlXmlHandler<T>, String, Boolean> {
	
	private static final String TAG = XmlRequestAsyncTask.class.getName();
	
	private final IHttpRequestCallback<Boolean> mCallback;
	
	public XmlRequestAsyncTask(final IHttpRequestCallback<Boolean> callback) {
		if (callback == null) {
			throw new IllegalArgumentException("callback is null");
		}
		
		mCallback = callback;
	}

	@Override
	protected Boolean doInBackground(final IUrlXmlHandler<T>... params) {
		if (params == null || params.length != 1) {
			throw new IllegalArgumentException("params are invalid");
		}

		try {
		    final SAXParserFactory saxPF = SAXParserFactory.newInstance();
		    final SAXParser saxP = saxPF.newSAXParser();
		    final XMLReader xmlR = saxP.getXMLReader();
		    final URL url = new URL(params[0].url());
		    final T handler = params[0].handler();
		    
		    xmlR.setContentHandler((DefaultHandler)handler);
		    xmlR.parse(new InputSource(url.openStream()));
		    return true;
		} catch (final Exception e) {
        	Log.e(TAG, e.getMessage(), e);
        	Log.e(TAG, "Using " + params[0].url());
            return false;
		}
	}

	@Override
	protected void onCancelled() {
		mCallback.onCancelled();
		super.onCancelled();
	}

	@Override
	protected void onPostExecute(final Boolean result) {
		mCallback.onPostExecute(result);
		super.onPostExecute(result);
	}

	@Override
	protected void onProgressUpdate(final String... values) {
		mCallback.onProgressUpdate(values);
		super.onProgressUpdate(values);
	}

}
