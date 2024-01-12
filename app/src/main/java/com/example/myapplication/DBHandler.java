package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHandler extends SQLiteOpenHelper {

    private static final String DB_NAME = "gosling-bog";

    private static final int DB_VERSION = 1;

    private static final String TABLE_NAME = "main";

    private static final String ID_COL = "id";

    private static final String PHOTO_ID = "img_id";

    private static final String LIKES_COL = "Likes";

    private static final String DISLIKES_COL = "Dislikes";

    public DBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + PHOTO_ID + " INTEGER ,"
                + LIKES_COL + " INTEGER,"
                + DISLIKES_COL + " INTEGER)";

        db.execSQL(query);
    }

    // this method is use to add new course to our sqlite database.
    public void addNewRecord(Integer photoID, Integer Like, Integer Dislike) {
        try {
        // on below line we are creating a variable for
        // our sqlite database and calling writable method
        // as we are writing data in our database.
        SQLiteDatabase db = this.getWritableDatabase();

        // on below line we are creating a
        // variable for content values.
        ContentValues values = new ContentValues();

        // on below line we are passing all values
        // along with its key and value pair.
        values.put(PHOTO_ID, photoID);
        values.put(LIKES_COL, Like);
        values.put(DISLIKES_COL, Dislike);

        // after adding all values we are passing
        // content values to our table.
        db.insert(TABLE_NAME, null, values);

        // at last we are closing our
        // database after adding database.
        db.close();
        } catch (Exception e) {
            e.printStackTrace(); // Print the stack trace
            System.out.println("Exception: " + e.getMessage()); // Log the exception message
        }
    }

    public int getLikes(int photoID){
        try {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT SUM("+ LIKES_COL +") FROM " + TABLE_NAME + " WHERE " + PHOTO_ID + " = " + photoID;

        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        int likes = cursor.getInt(0);

        cursor.close();

        return likes;
        } catch (Exception e) {
            e.printStackTrace(); // Print the stack trace
            System.out.println("Exception: " + e.getMessage()); // Log the exception message
            return 0;
        }
    }

    public int getDislikes(int photoID){
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            String query = "SELECT SUM("+ DISLIKES_COL +") FROM " + TABLE_NAME + " WHERE " + PHOTO_ID + " = " + photoID;

            Cursor cursor = db.rawQuery(query, null);
            cursor.moveToFirst();
            int dislikes = cursor.getInt(0);

            cursor.close();

            return dislikes;
        } catch (Exception e) {
            e.printStackTrace(); // Print the stack trace
            System.out.println("Exception: " + e.getMessage()); // Log the exception message
            return 0;
        }
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // this method is called to check if the table exists already.
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}

