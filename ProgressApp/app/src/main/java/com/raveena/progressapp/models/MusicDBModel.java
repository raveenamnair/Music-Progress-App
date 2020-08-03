package com.raveena.progressapp.models;


public class MusicDBModel {

    public int musicID;
    public String description;
    public String fullProjectPath;
    public String projectName;
    public String dateAdded;
    public boolean isComplete;
    public boolean isFavorite;

    // Constructor - only needs projectName. Everything updated later
    public MusicDBModel(String projectName) {
        this.projectName = projectName;

        // default values (may change)
        musicID = -1;
        description = "";
        fullProjectPath = "";
        dateAdded = "";
        isComplete = false;
        isFavorite = false;
    }

    public MusicDBModel(int id, String fullProjectPath, String projectName, String dateAdded,
                        String description, boolean isComplete, boolean isFavorite) {
        this.musicID = id;
        this.description = description;
        this.fullProjectPath = fullProjectPath;
        this.projectName = projectName;
        this.dateAdded = dateAdded;
        this.isComplete = isComplete;
        this.isFavorite = isFavorite;
    }

    public int getMusicID() {
        return musicID;
    }

    public void setMusicID(int musicID) {
        this.musicID = musicID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFullProjectPath() {
        return fullProjectPath;
    }

    public void setFullProjectPath(String fullProjectPath) {
        this.fullProjectPath = fullProjectPath;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }

    public boolean isComplete() {
        return isComplete;
    }

    public void setComplete(boolean complete) {
        isComplete = complete;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    @Override
    public String toString() {
        return "Testing " + projectName + dateAdded;
    }
}
