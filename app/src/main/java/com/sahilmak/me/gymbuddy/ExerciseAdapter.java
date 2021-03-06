package com.sahilmak.me.gymbuddy;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.ExerciseViewHolder> {
    private Context context;
    private List<Exercise> exerciseList;

    public ExerciseAdapter(Context context, List<Exercise> exerciseList) {
        this.context = context;
        this.exerciseList = exerciseList;
    }

    public static class ExerciseViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;

        public ExerciseViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.exerciseImageView);
            textView = itemView.findViewById(R.id.exerciseTextView);
        }
    }

    @NonNull
    @Override
    public ExerciseViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.search_results, viewGroup, false);
        return new ExerciseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExerciseViewHolder viewHolder, int i) {
        if (exerciseList.get(i) != null) {
            final Exercise exercise = exerciseList.get(i);
            StorageReference storageReference = FirebaseStorage.getInstance().getReference();
            StorageReference exerciseReference = storageReference.child(exercise.getName() + ".jpg");

            Glide.with(context).using(new FirebaseImageLoader()).load(exerciseReference).into(viewHolder.imageView);
            viewHolder.textView.setText(exercise.getName());

            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Bundle exercise data
                    Bundle args = new Bundle();
                    args.putString("name", exercise.getName());
                    args.putByteArray("image", exercise.getImage());
                    args.putString("category", exercise.getCategory());
                    args.putStringArrayList("targets", (ArrayList<String>) exercise.getTargets());
                    // Pass data to new fragment
                    Fragment fragment = new LogWorkout();
                    fragment.setArguments(args);
                    ((FragmentActivity) context).getSupportFragmentManager().beginTransaction().replace(R.id.fragment, fragment).commit();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return exerciseList.size();
    }
}
