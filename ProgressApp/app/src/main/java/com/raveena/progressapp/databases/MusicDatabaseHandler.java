package com.raveena.progressapp.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.raveena.progressapp.models.MusicDBModel;

import java.util.ArrayList;
import java.util.List;

public class MusicDatabaseHandler extends SQLiteOpenHelper {
    // Making constants that will be used throughout program
    public static final String TABLE_NAME = "MUSIC_FILES";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_DESCRIPTION = "DESCRIPTION";
    public static final String COLUMN_FULL_PATH = "FULL_PATH";
    public static final String COLUMN_PROJECT_NAME = "PROJECT_NAME";
    public static final String COLUMN_DATE_ADDED = "DATE_ADDED";
    public static final String COLUMN_COMPLETION_STATUS = "COMPLETION_STATUS";
    public static final String COLUMN_FAVORITE_STATUS = "FAVORITE_STATUS";

    public MusicDatabaseHandler(@Nullable Context context) {
        super(context, "music.db", null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (" + COLUMN_ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_FULL_PATH + " TEXT, "
                + COLUMN_PROJECT_NAME + " TEXT, " + COLUMN_DATE_ADDED + " TEXT, "
                + COLUMN_DESCRIPTION + " TEXT, " + COLUMN_COMPLETION_STATUS + " BOOL, "
                + COLUMN_FAVORITE_STATUS + " BOOL)";

        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean addMusicToDBWithEverything(MusicDBModel musicItem) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        // Adding values into database
        cv.put(COLUMN_DESCRIPTION, musicItem.getDescription());
        cv.put(COLUMN_FULL_PATH, musicItem.getFullProjectPath());
        cv.put(COLUMN_PROJECT_NAME, musicItem.getProjectName());
        cv.put(COLUMN_DATE_ADDED, musicItem.getDateAdded());
        cv.put(COLUMN_COMPLETION_STATUS, musicItem.isComplete());
        cv.put(COLUMN_FAVORITE_STATUS, musicItem.isFavorite());

        long insert = db.insert(TABLE_NAME, null, cv);
        if (insert == -1) {
            return false;
        }
        else {
            return true;
        }
    }

    public boolean addMusicToDBWithProjectName(MusicDBModel musicItem) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        // Add project name into database
        cv.put(COLUMN_DESCRIPTION, musicItem.getDescription());
        cv.put(COLUMN_PROJECT_NAME, musicItem.getProjectName());
        cv.put(COLUMN_DATE_ADDED, musicItem.getDateAdded());


        long insert = db.insert(TABLE_NAME, null, cv);
        if (insert == -1) {
            return false;
        }
        else {
            return true;
        }
    }

    public List<MusicDBModel> getFullDBList() {
        List<MusicDBModel> returnList = new ArrayList<>();

        // Get data from database
        String queryString = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase(); // You don't need writableDatabase
        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()) {
            // loop through the cursor (result set) and create new Customer objects
            // Put them into return list
            do {
                int musicID = cursor.getInt(0);
                String fullPath = cursor.getString(1);
                String projectName = cursor.getString(2);
                String dateAdded = cursor.getString(3);
                String description = cursor.getString(4);
                boolean isComplete = cursor.getInt(5) == 1 ? true: false;
                boolean isFavorite = cursor.getInt(6) == 1 ? true: false; // ternary operation

                MusicDBModel newFoodItem = new MusicDBModel(musicID, fullPath, projectName,
                        dateAdded, description, isComplete, isFavorite);

                returnList.add(newFoodItem);

            } while(cursor.moveToNext());   // Proceed through database one at a time
        }
        else {
            // failure. do not add anything to the list
        }

        cursor.close(); // IMPORTANT
        db.close(); // IMPORTANT

        return returnList;
    }

    public MusicDBModel getOneRow(String givenProjectName) {
        SQLiteDatabase db = getReadableDatabase();
        String queryString = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_PROJECT_NAME + " = " + givenProjectName;
        Cursor cursor = db.rawQuery(queryString, null);

        MusicDBModel newMusicDB = null;
        if (cursor.moveToFirst()) {
            // loop through the cursor (result set) and create new Customer objects
            // Put them into return list
            do {
                int musicID = cursor.getInt(0);
                String fullPath = cursor.getString(1);
                String projectName = cursor.getString(2);
                String dateAdded = cursor.getString(3);
                String description = cursor.getString(4);
                boolean isComplete = cursor.getInt(5) == 1 ? true: false;
                boolean isFavorite = cursor.getInt(6) == 1 ? true: false; // ternary operation

                newMusicDB = new MusicDBModel(musicID, fullPath, projectName,
                        dateAdded, description, isComplete, isFavorite);

            } while(cursor.moveToNext());   // Proceed through database one at a time
        }
        else {
            // failure. do not add anything to the list
        }

        cursor.close(); // IMPORTANT
        db.close(); // IMPORTANT

        return newMusicDB;

    }
}
