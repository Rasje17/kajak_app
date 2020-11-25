package com.example.kajakcompas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Route> routes;
    private ArrayAdapter<Route> adapter;
    private ListView routeView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        routeView = (ListView) findViewById(R.id.main_listview);
        routes = new ArrayList<>();
        adapter = new ArrayAdapter<Route>(this, android.R.layout.simple_list_item_1, routes);

        routeView.setAdapter(adapter);
    }

    public void goToCompass(View view){
        Intent intent = new Intent(this, CompassActivity.class);
        startActivity(intent);
    }

    public void goToRoute(View view) {
        Intent intent = new Intent(this, RouteActivity.class);
        startActivity(intent);
    }

    public ArrayList<Route> getRoutes() {
        return routes;
    }

    public void setRoutes(ArrayList<Route> routes) {
        this.routes = routes;
    }

    public ArrayAdapter<Route> getAdapter() {
        return adapter;
    }
}
