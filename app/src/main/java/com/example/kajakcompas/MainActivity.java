package com.example.kajakcompas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Route> routes = new ArrayList<>();
    private ArrayAdapter<Route> adapter;
    private ListView routeView;
    private ImageView refresh;
    private Button btn_Compass;
    private Button btn_Route;
    private Button btn_Start;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_Compass = findViewById(R.id.btn_main_go_to_compass);
        btn_Route = findViewById(R.id.btn_main_create_route);
        btn_Start = findViewById(R.id.btn_main_start_route);
        routeView = (ListView) findViewById(R.id.main_listview);
        refresh = findViewById(R.id.main_list_imageview);

        btn_Route.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToRoute();
            }
        });
        btn_Compass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToCompass();
            }
        });
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                populateListView();
            }
        });

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, routes);

        routeView.setAdapter(adapter);
        }

    private void goToCompass(){
        Intent intent = new Intent(this, CompassActivity.class);
        startActivity(intent);
    }

    private void goToRoute() {
        Intent intent = new Intent(this, RouteActivity.class);
        startActivity(intent);
    }

    private void populateListView() {

        RouteDB routeDB = RouteDB.getInstance(this);
        routes = (ArrayList<Route>) routeDB.routeDao().getRoutes();
        adapter.notifyDataSetChanged();
    }
}
