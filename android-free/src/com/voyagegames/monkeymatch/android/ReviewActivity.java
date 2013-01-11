package com.voyagegames.monkeymatch.android;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.voyagegames.monkeymatch.free.R;

public class ReviewActivity extends Activity {

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_review);
        
        new AlertDialog.Builder(this)
        		.setMessage(R.string.review_prompt)
        		.setPositiveButton(R.string.review_yes, new DialogInterface.OnClickListener() {
        			@Override
        			public void onClick(final DialogInterface dialog, final int id) {
        				final DataProvider db = new DataProvider(ReviewActivity.this);
        				db.setGameReviewed();
        				
        				startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("market://details?id=com.voyagegames.monkeymatch.free")));
        				finish();
        			}
        		})
        		.setNegativeButton(R.string.review_no, new DialogInterface.OnClickListener() {
        			@Override
        			public void onClick(final DialogInterface dialog, final int id) {
        				finish();
        			}
        		})
        		.setOnCancelListener(new DialogInterface.OnCancelListener() {
        			@Override
					public void onCancel(final DialogInterface dialog) {
        				finish();
					}
        		})
        		.create()
        		.show();
	}

}
