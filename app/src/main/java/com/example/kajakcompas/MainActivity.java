package com.example.kajakcompas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Route> routes = new ArrayList<>();
    private ArrayAdapter<Route> adapter;
    private ListView routeView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        routeView = (ListView) findViewById(R.id.main_listview);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, routes);

        routeView.setAdapter(adapter);
        }

    @Override
    protected void onResume() {
        super.onResume();
        populateListView();
    }

    private void goToCompass(View view){
        Intent intent = new Intent(this, CompassActivity.class);
        startActivity(intent);
    }

    private void goToRoute(View view) {
        Intent intent = new Intent(this, RouteActivity.class);
        startActivity(intent);
    }

    private void populateListView() {
        RouteDB routeDB = RouteDB.getInstance(this);
        routes = (ArrayList<Route>) routeDB.routeDao().getRoutes();
        adapter.notifyDataSetChanged();
    }
}
