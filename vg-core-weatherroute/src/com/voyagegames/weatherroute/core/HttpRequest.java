package com.voyagegames.weatherroute.core;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpRequest {
	
	public String request(final String urlString) throws MalformedURLException, IOException {
		final URL url = new URL(urlString);
		final HttpURLConnection conn = (HttpURLConnection)url.openConnection();
		
		try {
			final InputStream in = new BufferedInputStream(conn.getInputStream());
			
			try {
				final BufferedInputStream bin = new BufferedInputStream(in);
				
				try {
					final byte[] contents = new byte[1024];
					final StringBuilder builder = new StringBuilder();
	
					int bytesRead = 0;
	
					while ((bytesRead = bin.read(contents)) != -1) {
						builder.append(new String(contents, 0, bytesRead));               
					}
	
					return builder.toString();
				} finally {
					bin.close();
				}
			} finally {
				in.close();
			}
		} finally {
			conn.disconnect();
		}
	}

}
