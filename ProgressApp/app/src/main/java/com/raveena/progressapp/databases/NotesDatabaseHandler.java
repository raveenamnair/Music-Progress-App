package com.raveena.progressapp.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.raveena.progressapp.models.MusicDBModel;
import com.raveena.progressapp.models.NotesDBModel;

import java.util.ArrayList;
import java.util.List;

public class NotesDatabaseHandler extends SQLiteOpenHelper {

    public static final String TABLE_NAME = "NOTES_TABLE";
    public static final String COLUMN_NOTES_ID = "NOTES_ID";
    public static final String COLUMN_MUSIC_ID = "MUSIC_ID";
    public static final String COLUMN_NOTE = "NOTE";


    public NotesDatabaseHandler(@Nullable Context context) {
        super(context, "notes.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (" + COLUMN_NOTES_ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_MUSIC_ID + " INT, "
                + COLUMN_NOTE + " TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean addNoteModel(NotesDBModel notesDBModel) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();

        // Don't need to put notes_id because that auto increments
        cv.put(COLUMN_MUSIC_ID, notesDBModel.getMusicID());
        cv.put(COLUMN_NOTE, notesDBModel.getNote());

        long insert = db.insert(TABLE_NAME, null, cv);
        if (insert == -1) {
            return false;
        }
        else {
            return true;
        }

    }

    public List<NotesDBModel> getFullDBList() {
        List<NotesDBModel> returnList = new ArrayList<>();

        // Get data from database
        String queryString = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase(); // You don't need writableDatabase
        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()) {
            // loop through the cursor (result set) and create new Customer objects
            // Put them into return list
            do {
                int noteId = cursor.getInt(0);
                int musicID = cursor.getInt(1);
                String note = cursor.getString(2);

                NotesDBModel notesDBModel = new NotesDBModel(noteId, musicID, note);
                returnList.add(notesDBModel);

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
