package com.sahilmak.me.gymbuddy;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

class Exercise {
    private String name;
    private Bitmap image = null;
    private String category;
    String[] categoryList = {"Arms", "Back", "Chest", "Legs"};
    private String[] targets;
    String[] targetsList = {"Abdominals", "Biceps", "Biking", "Calves", "Deltoids", "Gluteals",
            "Hamstrings", "Hip Abductors", "Hip Adductors", "Latissimus Dorsi", "Obliques",
            "Pectorals", "Quadriceps", "Rhomboids", "Running", "Spinal Erectors", "Teretes",
            "Trapezius", "Tricep"};
    private String note = "";

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

    public void setNote(String note) {
        this.note = note;
    }
}
