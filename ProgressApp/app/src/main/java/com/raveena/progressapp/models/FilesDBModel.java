package com.raveena.progressapp.models;

public class FilesDBModel {

    public int fileID;
    public int musicID;
    public String filePath;

    public FilesDBModel(int fileID, int musicID, String filePath) {
        this.fileID = fileID;
        this.musicID = musicID;
        this.filePath = filePath;
    }

    public int getFileID() {
        return fileID;
    }

    public void setFileID(int fileID) {
        this.fileID = fileID;
    }

    public int getMusicID() {
        return musicID;
    }

    public void setMusicID(int musicID) {
        this.musicID = musicID;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
