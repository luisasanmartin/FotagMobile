package com.example.luisasanmartin.fotagmobile;

import android.graphics.Bitmap;

/**
 * Created by Luisa on 2016-04-03.
 */
public class ImageItem {

    private Bitmap image;
    private String title;

    public ImageItem(Bitmap image, String title) {
        super();
        this.image = image;
        this.title = title;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
