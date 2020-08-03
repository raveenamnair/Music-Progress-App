package com.raveena.progressapp.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.raveena.progressapp.models.FilesDBModel;
import com.raveena.progressapp.models.NotesDBModel;

import java.util.ArrayList;
import java.util.List;

public class FilesDatabaseHandler extends SQLiteOpenHelper {

    public static final String TABLE_NAME = "FILES_TABLE";
    public static final String COLUMN_FILE_ID = "FILES_ID";
    public static final String COLUMN_MUSIC_ID = "MUSIC_ID";
    public static final String COLUMN_FILE_PATH = "FILE_PATH";


    public FilesDatabaseHandler(@Nullable Context context) {
        super(context, "files.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (" + COLUMN_FILE_ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_MUSIC_ID + " INT, "
                + COLUMN_FILE_PATH + " TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean addFileModel(FilesDBModel filesDBModel) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_MUSIC_ID, filesDBModel.getMusicID());
        cv.put(COLUMN_FILE_PATH, filesDBModel.getFilePath());

        long insert = db.insert(TABLE_NAME, null, cv);
        if (insert == -1) {
            return false;
        }
        else {
            return true;
        }
    }

    public List<FilesDBModel> getFullDBList() {
        List<FilesDBModel> returnList = new ArrayList<>();

        // Get data from database
        String queryString = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase(); // You don't need writableDatabase
        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()) {
            // loop through the cursor (result set) and create new Customer objects
            // Put them into return list
            do {
                int fileID = cursor.getInt(0);
                int musicID = cursor.getInt(1);
                String filePath = cursor.getString(2);

                FilesDBModel filesDBModel = new FilesDBModel(fileID, musicID, filePath);
                returnList.add(filesDBModel);

            } while(cursor.moveToNext());   // Proceed through database one at a time
        }
        else {
            // failure. do not add anything to the list
        }

        cursor.close(); // IMPORTANT
        db.close(); // IMPORTANT

        return returnList;
    }
}
