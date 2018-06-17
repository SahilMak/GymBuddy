package com.sahilmak.me.gymbuddy;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class AddExercise extends Fragment {
    Exercise exercise;
    ImageView picture;
    String[] pictureMenu;
    TextInputEditText exerciseName;
    Spinner category;
    Button targetsBtn;
    TextView targetsText;
    String[] targets;
    boolean[] checkedTargets;
    ArrayList<String> selectedTargets = new ArrayList<>();
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

        pictureMenu = getResources().getStringArray(R.array.photo_menu);
        picture = view.findViewById(R.id.exerciseImage);
        picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Add Photo");
                builder.setItems(pictureMenu, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (i == 0) {
                            if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, 42);
                            } else {
                                takePhoto();
                            }
                        } else if (i == 1) {
                            if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 43);
                            } else {
                                uploadPhoto();
                            }
                        } else if (i == 2) {
                            dialogInterface.dismiss();
                        }
                    }

                });
                builder.show();
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
                        // Display selected targets on screen
                        StringBuilder item = new StringBuilder();
                        for (String target : selectedTargets) {
                            item.append(target).append("\n");
                        }
                        targetsText.setText(item.toString());
                        // add targets to object
                        exercise.setTargets(selectedTargets);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.setNeutralButton("Reset", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Arrays.fill(checkedTargets, false);
                        selectedTargets.clear();
                        targetsText.setText("");
                        targetsBtn.performClick();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        createBtn = view.findViewById(R.id.createExercise);
        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (exerciseName.getText().toString().matches("")) {
                    exerciseName.setError("Field cannot be left blank");
                    return;
                }
                exercise.setName(exerciseName.getText().toString());
                exercise.setCategory(category.getSelectedItem().toString());
                // Connect to database
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("exercise");
                databaseReference.child(exercise.getName()).child("category").setValue(exercise.getCategory());
                databaseReference.child(exercise.getName()).child("targets").setValue(exercise.getTargets());
                // Connect to storage
                if (exercise.getImage() != null) {
                    StorageReference storageReference = FirebaseStorage.getInstance().getReference();
                    StorageReference exerciseReference = storageReference.child(exercise.getName() + ".jpg");
                    UploadTask uploadTask = exerciseReference.putBytes(exercise.getImage());
                    uploadTask.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            e.printStackTrace();
                        }
                    });
                }
                // Notify user
                Toast.makeText(getContext(), "Exercise successfully added!", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    private void takePhoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 0);
    }

    private void uploadPhoto() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select File"), 1);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (requestCode == 42) {
                takePhoto();
            } else if (requestCode == 43) {
                uploadPhoto();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 0) {
                // Compress image
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 90, byteArrayOutputStream);
                // Place in ImageView
                picture.setImageBitmap(bitmap);
                // Save image in object
                exercise.setImage(byteArrayOutputStream.toByteArray());
            } else if (requestCode == 1) {
                Bitmap bitmap = null;
                if (data != null) {
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), data.getData());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 90, byteArrayOutputStream);
                // Place in ImageView
                picture.setImageBitmap(bitmap);
                // Save image in object
                exercise.setImage(byteArrayOutputStream.toByteArray());
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }
}
