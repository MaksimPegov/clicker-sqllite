package com.example.myapplication;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private DBHandler dbHandler;

    private Button likeButton;
    private Button dislikeButton;

    private TextView likes;
    private TextView dislikes;

    private ImageView image;

    private int imageId = 0;

    private int[] dataObjects = new int[]{
        R.drawable.img1,
        R.drawable.img2,
        R.drawable.img3};

    Handler handler = new Handler();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHandler = new DBHandler(MainActivity.this);

        likeButton = findViewById(R.id.like_button);
        dislikeButton = findViewById(R.id.dislike_button);
        image = findViewById(R.id.image);

        likes = findViewById(R.id.likes);
        dislikes = findViewById(R.id.dislikes);

        likes.setText(String.valueOf(dbHandler.getLikes(imageId)));
        dislikes.setText(String.valueOf(dbHandler.getDislikes(imageId)));


        likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLike();
            }
        });

        dislikeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDislike();
            }
        });
    }

    private void onLike() {
        int actualImage = dataObjects[imageId];
        dbHandler.addNewRecord(imageId, 1, 0);
        enableButtons(false);
        updateStats();

        handler.postDelayed(() -> {
            setImage();
            enableButtons(true);
        }, 1000);
    }

    private void onDislike() {
        dbHandler.addNewRecord(imageId, 0, 1);
        enableButtons(false);
        updateStats();

        handler.postDelayed(() -> {
            setImage();
            enableButtons(true);
        }, 1000);
    }

    private void setImage() {
        if (imageId >= dataObjects.length - 1) {
            imageId = 0;
        } else {
            imageId++;
        }
        image.setImageResource(dataObjects[imageId]);
        updateStats();
    }

    private void enableButtons(boolean enable) {
        likeButton.setEnabled(enable);
        dislikeButton.setEnabled(enable);
    }

    private void updateStats() {
        likes.setText(String.valueOf(dbHandler.getLikes(imageId)));
        dislikes.setText(String.valueOf(dbHandler.getDislikes(imageId)));
    }
}
