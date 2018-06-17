package com.sahilmak.me.gymbuddy;

import android.graphics.Bitmap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Exercise {
    String name;
    Bitmap image = null;
    String category;
    List<String> targets;

    public void setName(String name) {
        this.name = name;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setTargets(ArrayList<String> targets) {
        this.targets = targets;
    }
}
