package com.jeifbell.ektuhi;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseFav extends SQLiteOpenHelper {

	// All Static variables
	// Database Version
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_FAV = "favouriteManager";
	// Contacts table name
	private static final String TABLE_Category = "category";
	private static final String TABLE_Fav = "favorites";
	// Contacts Table Columns names
	private static final String KEY_cName = "category_name";
	private static final String KEY_CA_ID = "CA_id";
	private static final String KEY_C_ID = "C_id";
	private static final String KEY_Title = "title";
	private static final String KEY_Time = "time";
	private static final String KEY_Content = "Content";
	private static final String KEY_IMAGE = "image";
	private static final String KEY_BMAP = "bmap";
	
	SharedPreferences sharedpreferences;
	
	public DatabaseFav(Context context) {
		super(context, DATABASE_FAV, null, DATABASE_VERSION);
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {

		String CREATE_CATEGORY_TABLE = "CREATE TABLE " + TABLE_Category + "("
		         + KEY_CA_ID + " INTEGER PRIMARY KEY," + KEY_cName + " TEXT" + ")";
				
		
		db.execSQL(CREATE_CATEGORY_TABLE);
		
		String CREATE_CITY_TABLE = "CREATE TABLE " + TABLE_Fav + "(" + KEY_C_ID + " INTEGER PRIMARY KEY," + KEY_Time + " TEXT," + KEY_Title + " Text," + KEY_Content + " Text," + KEY_IMAGE + " TEXT," +KEY_BMAP + " BLOB"+ ")";
				
		db.execSQL(CREATE_CITY_TABLE);
		
	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_Category);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_Fav);
		// Create tables again
		onCreate(db);
	}

	/**
	 ** All CRUD(Create, Read, Update, Delete) Operations
	 **/

	// Adding new contact
	void addContact(String Table_Name, ConnectDb connect) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		if(Table_Name.equals("category")){
			values.put(KEY_cName, connect.getName());
		}else{
			values.put(KEY_Time, connect.getTime());
			values.put(KEY_Title, connect.getTitle());
			values.put(KEY_Content, connect.getContent());
			values.put(KEY_IMAGE, connect.getImage());
			values.put(KEY_BMAP, connect.getBmap());
		}
		// Inserting Row
		db.insert(Table_Name, null, values);
		db.close(); // Closing database connection
	}

	// Getting single contact
	public ConnectDb getContact(String Table_Name, String name) {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor;
		if(Table_Name.equals(TABLE_Fav)){
			cursor = db.query(Table_Name, new String[] { KEY_C_ID, KEY_Time, 
					KEY_Title, KEY_Content,KEY_IMAGE,KEY_BMAP}, KEY_Title + "=?",
					new String[] { name }, null, null, null, null);
		}
		else{
			cursor = db.query(Table_Name, new String[] { KEY_CA_ID, KEY_cName}, KEY_CA_ID + "=?",
					new String[] { name }, null, null, null, null);
		}
		//String[] columns = new String[] {KEY_ID, KEY_NAME, KEY_PH_NO, KEY_Title,KEY_Cost, KEY_Content};
		if (cursor != null)
			cursor.moveToFirst();

		ConnectDb connect = new ConnectDb();
		connect.setID(Integer.parseInt(cursor.getString(0)));
		connect.setTime(cursor.getString(1));
		connect.setTitle(cursor.getString(2));
		connect.setContent(cursor.getString(3));
		connect.setImage(cursor.getString(4));
		connect.setBmap(cursor.getBlob(5));
		// return connect
		return connect;
	}
	
	// Getting All Contacts
	public List<ConnectDb> getAllContacts(String Table_Name) {
		List<ConnectDb> connectList = new ArrayList<ConnectDb>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + Table_Name;
		  
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				ConnectDb connect = new ConnectDb();
				if(Table_Name.equals("category")){
					connect.setID(Integer.parseInt(cursor.getString(0)));
					connect.setName(cursor.getString(1));
				}else{
					connect.setID(Integer.parseInt(cursor.getString(0)));
					connect.setTime(cursor.getString(1));
					connect.setTitle(cursor.getString(2));
					connect.setContent(cursor.getString(3));
					connect.setImage(cursor.getString(4));
					connect.setBmap(cursor.getBlob(5));
				}
				// Adding contact to list
				connectList.add(connect);
			} while (cursor.moveToNext());
		}

		// return contact list
		return connectList;
	}

	// Updating single contact
	public int updateContact(String Table_Name, ConnectDb contact) {
		SQLiteDatabase db = this.getWritableDatabase();
		
		ContentValues values = new ContentValues();
		values.put(KEY_Title, contact.getTitle());
		values.put(KEY_BMAP, contact.getBmap());

		// updating row
		return db.update(Table_Name, values, KEY_Title + " = ?",
				new String[] {contact.getTitle()});
	}

	// Deleting single contact
	public void deleteContact(String Table_Name, ConnectDb contact) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(Table_Name, KEY_Title + " = ?",
				new String[] { contact.getTitle()});
		db.close();
	}

	// Getting contacts Count
	public int getContactsCount(String Table_Name) {
		String countQuery = "SELECT  * FROM " + Table_Name;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		cursor.close();
		// return count
		return cursor.getCount();
	}
}
