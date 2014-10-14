package com.aad1.aad1_pro_emulator;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TheDatabase {

	public static final String KEY_ROWID = "_id";
	public static final String KEY_IP = "ip_address";
	public static final String KEY_PORT = "port";
	
	private static final String DATABASE_NAME = "ipDB";
	private static final String DATABASE_TABLE = "ipTable";
	private static final int DATABASE_VERSION = 1;
	
	private DbHelper ourHelper;
	private final Context ourContext;
	private SQLiteDatabase ourDatabase;
	
	private static class DbHelper extends SQLiteOpenHelper{

		public DbHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
			// TODO Auto-generated constructor stub
		}
		
		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			db.execSQL("CREATE TABLE " + DATABASE_TABLE + " (" +
					KEY_ROWID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
					KEY_IP + " TEXT NOT NULL, " +
					KEY_PORT + " TEXT NOT NULL);"
			);
		}
		
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
			onCreate(db);
		}
	}
	
	public TheDatabase(Context c) {
		ourContext = c;
	}
	
	public TheDatabase open() throws SQLException{
		ourHelper = new DbHelper(ourContext);
		ourDatabase = ourHelper.getWritableDatabase();
		return this;
	}
	
	public void close(){
		ourHelper.close();
	}
	
	public long createEntry(String ip2save, String port2save) {
		// TODO Auto-generated method stub
		ContentValues cv = new ContentValues();
		cv.put(KEY_IP, ip2save);
		cv.put(KEY_PORT, port2save);
		return ourDatabase.insert(DATABASE_TABLE, null, cv);
	}

	public String[] getIP() {
		// TODO Auto-generated method stub
		String [] columns = new String[]{KEY_ROWID, KEY_IP, KEY_PORT};
		Cursor c = ourDatabase.query(DATABASE_TABLE, columns, null, null, null, null, null);
		String result[] = {"","","","",""};
		
		int iIP = c.getColumnIndex(KEY_IP);
		int i=0;
		int k=4;
		
		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
			//result[i] = c.getString(iIP);
			i++;
		}
		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
			//result[i] = c.getString(iIP);
			if(i<=5 && i>0){
				result[k] = c.getString(iIP);
				k--;
			}
			i--;
		}
		return result;
	}

	public String[] getPORT() {
		// TODO Auto-generated method stub
		String [] columns = new String[]{KEY_ROWID, KEY_IP, KEY_PORT};
		Cursor c = ourDatabase.query(DATABASE_TABLE, columns, null, null, null, null, null);
		String result[] = {"","","","",""};
		
		int iPORT = c.getColumnIndex(KEY_PORT);
		int i=0;
		int k=4;
		
		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
			//result[i] = c.getString(iPORT);
			i++;
		}
		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
			//result[i] = c.getString(iPORT);
			if(i<=5 && i>0) {
				result[k] = c.getString(iPORT);
				k--;
			}
			i--;
		}

		return result;
	}
	
	public void deleteDataBase() {
		// TODO Auto-generated method stub
		
		ourContext.deleteDatabase(DATABASE_NAME);
	}

	public void deleteEntry(String ip2delet) {
		// TODO Auto-generated method stub
		String[] whereArgs = new String[] {ip2delet};
		ourDatabase.delete(DATABASE_TABLE, KEY_IP + "=?", whereArgs);
	}
}
