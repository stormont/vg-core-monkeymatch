package com.voyagegames.weatherroute.core;

public interface IHttpRequestCallback <T> {
	
	public void run();
	public void onCancelled();
	public void onProgressUpdate(String... values);
	public void onPostExecute(T result);

}
