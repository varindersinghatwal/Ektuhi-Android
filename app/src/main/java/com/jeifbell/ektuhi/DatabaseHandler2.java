package com.jeifbell.ektuhi;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler2 extends SQLiteOpenHelper {

	// All Static variables
	// Database Version
	private static final int DATABASE_VERSION = 1;
	// Database Name
	private static final String DATABASE_NAME = "contactsManager";
	// Contacts table name
	private static final String TABLE_Category = "category";
	private static final String TABLE_Post = "post";
	
	// Contacts Table Columns names
	private static final String KEY_cName = "category_name";
	private static final String KEY_CA_ID = "CA_id";
	private static final String KEY_C_ID = "C_id";
	private static final String KEY_Title = "title";
	private static final String KEY_Time = "time";
	private static final String KEY_Content = "Content";
	private static final String KEY_IMAGE = "image";
	private static final String KEY_BMAP = "bmap";
	
	public DatabaseHandler2(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {

		String CREATE_CATEGORY_TABLE = "CREATE TABLE " + TABLE_Category + "("
		         + KEY_CA_ID + " INTEGER PRIMARY KEY," + KEY_cName + " TEXT" + ")";
				
		
		
		
				 
		//String CREATE_CATEGORY_TABLE = "CREATE TABLE " + TABLE_Category + "(" + KEY_CA_ID + " INTEGER PRIMARY KEY," + KEY_cName + " TEXT" + ")";
		
		db.execSQL(CREATE_CATEGORY_TABLE);
		
		String CREATE_CITY_TABLE = "CREATE TABLE " + TABLE_Post + "(" + KEY_C_ID + " INTEGER PRIMARY KEY," + KEY_cName + " TEXT," + KEY_Time + " TEXT," + KEY_Title + " Text," + KEY_Content + " Text," + KEY_IMAGE + " TEXT,"+ KEY_BMAP + " BLOB"+")";
				
		db.execSQL(CREATE_CITY_TABLE);
		
		
		
		
	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_Category);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_Post);
		// Create tables again
		onCreate(db);
	}

	/**
	 * All CRUD(Create, Read, Update, Delete) Operations
	 */

	// Adding new contact
	void addContact(String Table_Name, ConnectDb connect) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		if(Table_Name.equals("category")){
			values.put(KEY_cName, connect.getName());
		}else{
			values.put(KEY_cName, connect.getName());
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
		if(Table_Name.equals(TABLE_Post)){
			cursor = db.query(Table_Name, new String[] { KEY_C_ID, KEY_cName, KEY_Time, 
					KEY_Title, KEY_Content,KEY_IMAGE, KEY_BMAP }, KEY_Title + "=?",
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
		connect.setName(cursor.getString(1));
		connect.setTime(cursor.getString(2));
		connect.setTitle(cursor.getString(3));
		connect.setContent(cursor.getString(4));
		connect.setImage(cursor.getString(5));
		connect.setBmap(cursor.getBlob(6));
		// return connect
		return connect;
	}
	
	// Getting All Contacts
	public List<ConnectDb> getAllContacts(String Table_Name, String name) {
		List<ConnectDb> connectList = new ArrayList<ConnectDb>();
		// Select All Query
		Cursor cursor;
		SQLiteDatabase db = this.getWritableDatabase();
		if(Table_Name.equals("category")){
			cursor = db.rawQuery("SELECT * FROM category",  null);
		}
		else{
			cursor = db.rawQuery("SELECT * FROM post WHERE TRIM(category_name) = '"+name.trim()+"'",  null);
		}		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				ConnectDb connect = new ConnectDb();
				if(Table_Name.equals("category")){
					connect.setID(Integer.parseInt(cursor.getString(0)));
					connect.setName(cursor.getString(1));
				}else{
					connect.setID(Integer.parseInt(cursor.getString(0)));
					connect.setName(cursor.getString(1));
					connect.setTime(cursor.getString(2));
					connect.setTitle(cursor.getString(3));
					connect.setContent(cursor.getString(4));
					connect.setImage(cursor.getString(5));
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
		db.delete(Table_Name, KEY_C_ID + " = ?",
				new String[] { String.valueOf(contact.getID())});
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
