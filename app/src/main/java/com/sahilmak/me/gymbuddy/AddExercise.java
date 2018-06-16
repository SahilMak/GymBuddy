package com.sahilmak.me.gymbuddy;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class AddExercise extends Fragment {
    Exercise exercise;
    String path;
    ImageView picture;
    TextInputEditText exerciseName;
    Spinner category;
    Button targetsBtn;
    TextView targetsText;
    String[] targets;
    boolean[] checkedTargets;
    ArrayList selectedTargets = new ArrayList();
    FloatingActionButton createBtn;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_exercise, container, false);
        exercise = new Exercise();

        picture = view.findViewById(R.id.exerciseImage);
        picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Prompt user for image upload
                path = "";
                exercise.setImage(path);
            }
        });

        exerciseName = view.findViewById(R.id.exerciseNameText);

        category = view.findViewById(R.id.exerciseCategory);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.category_list, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category.setAdapter(adapter);

        targetsBtn = view.findViewById(R.id.targetsButton);
        targetsText = view.findViewById(R.id.targetsText);
        targets = getResources().getStringArray(R.array.targets_list);
        checkedTargets = new boolean[targets.length];
        targetsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Exercise Targets");
                builder.setMultiChoiceItems(targets, checkedTargets, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                        if (b) {
                            if (!selectedTargets.contains(targets[i])) {
                                selectedTargets.add(targets[i]);
                            } else {
                                selectedTargets.remove(targets[i]);
                            }
                        }
                    }
                });
                builder.setCancelable(false);
                builder.setPositiveButton("Select", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        exercise.setTargets((String[]) selectedTargets.toArray());
                    }
                });
            }
        });

        createBtn = view.findViewById(R.id.createExercise);
        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (exerciseName.getText().toString().matches("")) {
                    Toast.makeText(getContext(), "Please enter a name", Toast.LENGTH_SHORT).show();
                    return;
                }

                exercise.setName(exerciseName.getText().toString());
                exercise.setCategory(category.getSelectedItem().toString());
            }
        });

        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
    }
}
