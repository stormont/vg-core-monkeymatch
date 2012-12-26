package com.voyagegames.monkeymatch.android;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.voyagegames.monkeymatch.IDataProvider;

public class DataProvider implements IDataProvider {
	
	private final SQLiteScoreHelper mHelper;
	private final SQLiteDatabase mDB;
	
	public DataProvider(final Context context) {
		mHelper = new SQLiteScoreHelper(context);
		mDB = mHelper.getWritableDatabase();
	}

	@Override
	public int personalBest() {
		final List<Integer> scores = select();
		
		if (scores.size() == 0) {
			return 0;
		}
		
		return scores.get(0);
	}

	@Override
	public void setPersonalBest(final int score) {
		final ContentValues values = new ContentValues();
		values.put(SQLiteScoreHelper.KEY_VALUE, score);
		mDB.insert(SQLiteScoreHelper.TABLE_NAME, null, values);
	}
	
	private List<Integer> select() {
		final String[] columns = new String[] {
				SQLiteScoreHelper.KEY_VALUE
			};
		
		final List<Integer> results = new ArrayList<Integer>();
		
		Cursor cursor = null;
		
		try {
			cursor = mDB.query(
					true,
					SQLiteScoreHelper.TABLE_NAME,
					columns,
					null,
					null,
					null,
					null,
					SQLiteScoreHelper.KEY_VALUE + " DESC",
					null);
			
			if (cursor != null) {
				if (cursor.moveToFirst()) {
					results.add(cursor.getInt(cursor.getColumnIndex(SQLiteScoreHelper.KEY_VALUE)));
				}
				
				while (cursor.moveToNext()) {
					results.add(cursor.getInt(cursor.getColumnIndex(SQLiteScoreHelper.KEY_VALUE)));
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
