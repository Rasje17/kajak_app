package com.example.kajakcompas;

import androidx.appcompat.app.AppCompatActivity;

import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class RouteActivity extends AppCompatActivity {
    ArrayList<Location> currentRoute;
    ListView listView;
    EditText nameField;
    EditText latField;
    EditText longField;
    Button btn_addPoint;
    Button btn_undo;
    Button btn_clearRoute;
    Button btn_saveRoute;
    ArrayAdapter<String> arrayAdapter;
    MainActivity main;
    ArrayList<String> stringRoute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);

        currentRoute = new ArrayList<>();
        listView = findViewById(R.id.route_listview);
        nameField = findViewById(R.id.route_name_edittext);
        latField = findViewById(R.id.route_north_edittext);
        longField = findViewById(R.id.route_east_edittext);
        btn_addPoint = findViewById(R.id.btn_route_addpoint);
        btn_undo = findViewById(R.id.btn_route_undo);
        btn_clearRoute = findViewById(R.id.btn_route_clearroute);
        btn_saveRoute = findViewById(R.id.btn_route_saveroute);
        stringRoute = new ArrayList<String>();
        main = new MainActivity();

        nameField.setHint("Name");
        latField.setHint("Latitude");
        longField.setHint("Longitude");

        arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                stringRoute);

        listView.setAdapter(arrayAdapter);

        btn_undo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeLastPoint();
            }
        });

        btn_saveRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveRoute();
            }
        });

        btn_clearRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearRoute();
            }
        });

        btn_addPoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPoint();
            }
        });
    }


    private void addPoint() {
        float lat = Float.valueOf(latField.getText().toString());
        float _long = Float.valueOf(longField.getText().toString());

        //TODO: Create location object
        Location location = new Location("");
        location.setLatitude(lat);
        location.setLongitude(_long);
        currentRoute.add(location);

        stringRoute.add(location.getLatitude() + " " + location.getLongitude());
        arrayAdapter.notifyDataSetChanged();
        latField.getText().clear();
        longField.getText().clear();

    }

    private void removeLastPoint() {
        if(!currentRoute.isEmpty()) {
            currentRoute.remove(currentRoute.size() -1);
            stringRoute.remove(stringRoute.size() -1);
            arrayAdapter.notifyDataSetChanged();
        }

    }

    private void clearRoute() {
        currentRoute.clear();
        stringRoute.clear();
        arrayAdapter.notifyDataSetChanged();
    }

    private void saveRoute() {
        Route route = new Route(nameField.getText().toString(), currentRoute);
        main.getRoutes().add(route);
        main.getAdapter().notifyDataSetChanged();

    }


}