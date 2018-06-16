package com.sahilmak.me.gymbuddy;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

class Exercise {
    private String name;
    private Bitmap image;
    private String category;
    private String[] targets;

    public void setName(String name) {
        this.name = name;
    }

    public void setImage(String pathName) {
        this.image = BitmapFactory.decodeFile(pathName);
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setTargets(String[] targets) {
        this.targets = targets;
    }
}
