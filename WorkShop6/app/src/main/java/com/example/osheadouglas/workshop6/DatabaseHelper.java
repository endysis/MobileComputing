package com.example.osheadouglas.workshop6;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.R.attr.name;

/**
 * Created by osheadouglas on 15/11/2016.
 */

public class DatabaseHelper extends SQLiteOpenHelper { // Inherrits from the SQLOpenhelperclass

    public static final String DATABASE_NAME = "riff.db"; // Name of the database
    public static final String TABLE_NAME = "riff_table"; // Name of the database table
    public static final String COL_1 = "ID"; // Name of the database table
    public static final String COL_2 = "NAME"; // Name of the database tablee
    public static final String COL_3  = "MOOD"; // Name of the database table
    public static final String COL_4  = "LOCATION"; // Name of the database table


    public DatabaseHelper(Context context){
        super(context,DATABASE_NAME,null,1); // Calls the constructor of the parent class
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        // Creates a table
        db.execSQL("create table " + TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT,NAME TEXT,MOOD TEXT,LOCATION TEXT)");
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) { // Only called if database file exists but the stored version number is lower than requested in constructor.
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME); // I suppose it dosent enable a copy of a given table.
        onCreate(db);
    }


    public boolean insertData(String riffName, String userMood, String riffLoc){
        SQLiteDatabase db = this.getWritableDatabase(); // Creates the database and table
        ContentValues contentValues  = new ContentValues();
        contentValues.put(COL_2,riffName);
        contentValues.put(COL_3,userMood);
        contentValues.put(COL_4,riffLoc);

        long result = db.insert(TABLE_NAME,null,contentValues);
        if(result == -1){
            return false;
        } else {
            return true;
        }
    }


    public Cursor getAllData(){ // Cursor provides a random read-write access to the result set
        SQLiteDatabase db = this.getWritableDatabase(); // Creates the database and table
        Cursor result = db.rawQuery("select * from " + TABLE_NAME,null);
        return result;
    }





    // Updating Data
    public boolean updateData(String id, String riffName, String riffMood,String riffLocation){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues(); // DB values
        contentValues.put(COL_1,id); // Adds the values
        contentValues.put(COL_2,riffName);
        contentValues.put(COL_3,riffMood);
        contentValues.put(COL_4,riffLocation);

        db.update(TABLE_NAME,contentValues,"id = ?",new String[] { id }); // Updates the table with the content values
        return true; // If the data has updated
    }



    public Integer deleteData (String id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME,"ID = ?",new String[] {id}); // Return number of deleted data
    }



}















