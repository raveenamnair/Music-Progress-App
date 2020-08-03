package com.raveena.progressapp.models;

import androidx.annotation.NonNull;

public class NotesDBModel {

    public int notesID;
    public int musicID;
    public String note;

    public NotesDBModel(int notesID, int musicID, String note) {
        this.notesID = notesID;
        this.musicID = musicID;
        this.note = note;
    }

    public int getNotesID() {
        return notesID;
    }

    public void setNotesID(int notesID) {
        this.notesID = notesID;
    }

    public int getMusicID() {
        return musicID;
    }

    public void setMusicID(int musicID) {
        this.musicID = musicID;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @NonNull
    @Override
    public String toString() {
        return getNote();
    }
}
