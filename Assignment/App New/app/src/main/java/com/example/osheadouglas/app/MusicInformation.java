package com.example.osheadouglas.app;

import android.graphics.Bitmap;

/**
 * Created by osheadouglas on 23/11/2016.
 */

public class MusicInformation {

    private int id;
    private String musicDescription;
    private String imageUrl;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMusicDescription() {
        return musicDescription;
    }

    public void setMusicDescription(String musicDescription) {
        this.musicDescription = musicDescription;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrlText) {
        this.imageUrl = imageUrlText;
    }


}
