package com.example.kajakcompas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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
    private Button btn_delete;

    private int selectedIndex;
    private int oldIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_Compass = findViewById(R.id.btn_main_go_to_compass);
        btn_Route = findViewById(R.id.btn_main_create_route);
        btn_Start = findViewById(R.id.btn_main_start_route);
        btn_delete = findViewById(R.id.btn_main_delete_route);

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
        btn_Start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startRoute();
            }
        });
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteRoute();
            }
        });

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, routes);

        routeView.setAdapter(adapter);
        routeView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getChildAt(oldIndex) != null) {
                    parent.getChildAt(oldIndex).setBackgroundColor(Color.WHITE);
                }

                selectedIndex = position;
                parent.getChildAt(selectedIndex).setBackgroundColor(Color.parseColor("#919294"));
                oldIndex = selectedIndex;
            }
        });
    }

    private void startRoute() {
        Route selectedRoute = (Route) routeView.getItemAtPosition(selectedIndex);
        if (selectedRoute != null) {
            Intent intent = new Intent(this, CompassActivity.class);
            intent.putExtra("route", selectedRoute);
            startActivity(intent);
        }
    }

    private void goToCompass() {
        Intent intent = new Intent(this, CompassActivity.class);
        startActivity(intent);
    }

    private void goToRoute() {
        Intent intent = new Intent(this, RouteActivity.class);
        startActivity(intent);
    }

    private void populateListView() {
        final RouteDB routeDB = RouteDB.getInstance(this);
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                routes.clear();
                routes.addAll(routeDB.routeDao().getRoutes());
            }
        });
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        adapter.notifyDataSetChanged();
    }

    private void deleteRoute() {
        final RouteDB routeDB = RouteDB.getInstance(this);
        final Route routeToBeDeleted = (Route) routeView.getItemAtPosition(selectedIndex);
        if(routeToBeDeleted != null) {
            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    routeDB.routeDao().deleteRoute(routeToBeDeleted);
                }
            });
        }
        populateListView();
    }
}
