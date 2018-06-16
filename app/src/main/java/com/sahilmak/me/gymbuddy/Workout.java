package com.sahilmak.me.gymbuddy;

import java.time.LocalDateTime;

class Workout extends Exercise {
    private double weight = 0.0;
    private int reps = 0;
    private double speed = 0.0;
    private LocalDateTime lastUsed;

    public Workout() {
        this.lastUsed = LocalDateTime.now();
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }
}
