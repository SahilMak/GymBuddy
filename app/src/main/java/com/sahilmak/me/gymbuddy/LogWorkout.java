package com.sahilmak.me.gymbuddy;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class LogWorkout extends Fragment {
    ImageView logImage;
    TextView logName;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.log_workout, container, false);

        logImage = view.findViewById(R.id.logImage);
        logName = view.findViewById(R.id.logName);
        // Populate with exercise info
        Bundle args = getArguments();
        byte[] byteImage = args.getByteArray("image");
        Bitmap bitmapImage = BitmapFactory.decodeByteArray(byteImage, 0, byteImage.length);
        logImage.setImageBitmap(bitmapImage);
        logName.setText(args.getString("name"));

        // Inflate the layout for this fragment
        return view;
    }
}
