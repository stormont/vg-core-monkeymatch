package com.voyagegames.weatherroute;

import java.io.IOException;
import java.io.InputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

public class BitmapRequestAsyncTask extends AsyncTask<String, Void, Bitmap> {
	
	private static final String TAG = BitmapRequestAsyncTask.class.getName();
	
	private final ImageView mView;
	
	public BitmapRequestAsyncTask(final ImageView view) {
		mView = view;
	}

	@Override
	protected Bitmap doInBackground(final String... params) {
		if (params == null || params.length != 1) {
			throw new IllegalArgumentException("params are invalid");
		}
		
        final String urldisplay = params[0];
        
        InputStream in = null;
        
        try {
            in = new java.net.URL(urldisplay).openStream();
            
            final Bitmap bmp = BitmapFactory.decodeStream(in);
            
            if (bmp == null) {
            	return null;
            }
    		
            // The returned bitmaps are quite small...
            final int size = bmp.getHeight() * 2;
            
            return Bitmap.createScaledBitmap(bmp, size, size, true);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
        } finally {
        	if (in != null) {
        		try {
					in.close();
				} catch (final IOException e) {
					// no-op
				}
        	}
        }
        
        return null;
    }

	@Override
    protected void onPostExecute(final Bitmap result) {
		if (result == null) {
			return;
		}
		
		mView.setImageBitmap(result);
    }
    
}
