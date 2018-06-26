package com.sahilmak.me.gymbuddy;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class LogWorkout extends Fragment {
    TextView name;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.log_workout, container, false);

        name = view.findViewById(R.id.log_name);
        Bundle args = getArguments();
        name.setText(args.getString("name"));

        // Inflate the layout for this fragment
        return view;
    }
}
