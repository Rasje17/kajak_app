package com.example.kajakcompas;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class CompassActivity extends AppCompatActivity {
    ImageView compasrose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compass);

        compasrose = findViewById(R.id.compas_iw);
        compasrose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                compasrose.setRotation(90);
            }
        });

    }

}