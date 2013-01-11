package com.voyagegames.monkeymatch.android;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.voyagegames.monkeymatch.IDataProvider;

public class DataProvider implements IDataProvider {
	
	private final SQLiteScoreHelper mScoreHelper;
	private final SQLiteReviewHelper mReviewHelper;
	private final SQLiteFirstLaunchHelper mFirstLaunchHelper;
	
	private SQLiteDatabase mDB;
	
	public DataProvider(final Context context) {
		mScoreHelper = new SQLiteScoreHelper(context);
		mReviewHelper = new SQLiteReviewHelper(context);
		mFirstLaunchHelper = new SQLiteFirstLaunchHelper(context);
	}
	
	public boolean hasGamePreviouslyLaunched() {
		mDB = mFirstLaunchHelper.getWritableDatabase();
		
		final List<Integer> entries = select(SQLiteFirstLaunchHelper.TABLE_NAME, SQLiteFirstLaunchHelper.KEY_VALUE);
		
		if (entries.size() == 0) {
			return false;
		}
		
		return true;
	}
	
	public boolean hasGameBeenReviewed() {
		mDB = mReviewHelper.getWritableDatabase();
		
		final List<Integer> entries = select(SQLiteReviewHelper.TABLE_NAME, SQLiteReviewHelper.KEY_VALUE);
		
		if (entries.size() == 0) {
			return false;
		}
		
		return true;
	}
	
	public void setGamePreviouslyLaunched() {
		final ContentValues values = new ContentValues();
		values.put(SQLiteFirstLaunchHelper.KEY_VALUE, 0);
		mDB = mFirstLaunchHelper.getWritableDatabase();
		mDB.insert(SQLiteFirstLaunchHelper.TABLE_NAME, null, values);
	}
	
	public void setGameReviewed() {
		final ContentValues values = new ContentValues();
		values.put(SQLiteReviewHelper.KEY_VALUE, 0);
		mDB = mReviewHelper.getWritableDatabase();
		mDB.insert(SQLiteReviewHelper.TABLE_NAME, null, values);
	}

	@Override
	public int personalBest() {
		mDB = mScoreHelper.getWritableDatabase();
		
		final List<Integer> scores = select(SQLiteScoreHelper.TABLE_NAME, SQLiteScoreHelper.KEY_VALUE);
		
		if (scores.size() == 0) {
			return 0;
		}
		
		return scores.get(0);
	}

	@Override
	public void setPersonalBest(final int score) {
		final ContentValues values = new ContentValues();
		values.put(SQLiteScoreHelper.KEY_VALUE, score);
		mDB = mScoreHelper.getWritableDatabase();
		mDB.insert(SQLiteScoreHelper.TABLE_NAME, null, values);
	}
	
	private List<Integer> select(final String table, final String key) {
		final String[] columns = new String[] { key };
		
		final List<Integer> results = new ArrayList<Integer>();
		
		Cursor cursor = null;
		
		try {
			cursor = mDB.query(
					true,
					table,
					columns,
					null,
					null,
					null,
					null,
					key + " DESC",
					null);
			
			if (cursor != null) {
				if (cursor.moveToFirst()) {
					results.add(cursor.getInt(cursor.getColumnIndex(key)));
				}
				
				while (cursor.moveToNext()) {
					results.add(cursor.getInt(cursor.getColumnIndex(key)));
				}
			}
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
		
		return results;
	}

}
