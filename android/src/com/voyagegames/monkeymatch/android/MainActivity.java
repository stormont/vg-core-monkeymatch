package com.voyagegames.monkeymatch.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends Activity {
	
	private static final int REVIEW_ID = 1;
	
	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		final DataProvider db = new DataProvider(this);
		
		if (!db.hasGamePreviouslyLaunched()) {
			db.setGamePreviouslyLaunched();
			startActivity(new Intent(this, GameActivity.class));
			finish();
		} else if (!db.hasGameBeenReviewed()) {
			startActivityForResult(new Intent(this, ReviewActivity.class), REVIEW_ID);
		} else {
			startActivity(new Intent(this, GameActivity.class));
			finish();
		}
	}

	@Override
	protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
		startActivity(new Intent(this, GameActivity.class));
		finish();
	}
	
}
