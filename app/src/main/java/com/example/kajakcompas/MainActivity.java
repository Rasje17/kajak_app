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
        if (getIntent().getExtras() != null) {
            Route route = (Route) getIntent().getSerializableExtra("Route");
            routes.add(route);
            adapter.notifyDataSetChanged();
        } else {
            Log.d("TAG", "Main_OnCreate_Else");
        }
        }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.d("INTENT", "onNewIntent()");
        Route route = (Route) intent.getSerializableExtra("Route");
        routes.add(route);
        adapter.notifyDataSetChanged();
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
