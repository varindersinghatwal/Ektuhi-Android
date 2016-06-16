package com.jeifbell.ektuhi;

public class ConnectDb {
	
	//private variables
	int _id;
	String _time;
	String _title;
	String _Content;
	String _cName;
	String _image;
	byte[] _bmap;
	
	// Empty constructor
	public ConnectDb(){
		
	}
	public ConnectDb(String cName){
		this._cName= cName;
		
	}
	//constructor
	public ConnectDb(int id){
		this._id= id;
		
	}
	
	//constructor
	public ConnectDb(String title, byte[] bmap){
		this._title = title;
		this._bmap=bmap;
	}
	public ConnectDb(String time, String title, String Content){
		this._time = time;
		this._title = title;
		this._Content = Content;
	}
	//constructor
	public ConnectDb(String time, String title, String Content,String image){
		this._time = time;
		this._title = title;
		this._Content = Content;
		this._image = image;
	}
	public ConnectDb(String name,String time, String title, String Content,String image){
		this._time = time;
		this._title = title;
		this._Content = Content;
		this._image = image;
		this._cName = name;
	}
		//constructor
	public ConnectDb(String name,String time, String title, String Content,String image,byte[] bmap){
		this._time = time;
		this._title = title;
		this._Content = Content;
		this._image = image;
		this._cName = name;
		this._bmap=bmap;
	}
	// constructor
	public ConnectDb(int id, String time, String Title, String Content){
		this._id = id;
		this._time = time;
		this._title = Title;
		this._Content = Content;
		
	}
		
	// getting ID
	public int getID(){
		return this._id;
	}
	
	// setting id
	public void setID(int id){
		this._id = id;
	}
	
	// getting Time
	public String getTime(){
		return this._time;
	}
	
	// setting Time
	public void setTime(String time){
		this._time = time;
	}
	//getting title
	public String getTitle(){
		return this._title;
	}
	
	// setting title
	public void setTitle(String title){
		this._title =title;
	}
	
	//getting Content
	public String getContent(){
		return this._Content;
	}
	
	// setting content
	public void setContent(String Content){
		this._Content =Content;
	}
	
	public String getName(){
		return this._cName;
	}
	
	// setting content
	public void setName(String cName){
		this._cName =cName;
	}
	public String getImage() {
		return this._image;
	}

	public void setImage(String image) {
		this._image = image;
	}
	public byte[] getBmap() {
		return this._bmap;
	}

	public void setBmap(byte[] bmap) {
		this._bmap = bmap;
	}
}
