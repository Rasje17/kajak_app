package com.example.kajakcompas;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class RouteActivity extends AppCompatActivity {
    ArrayList<Coordinate> currentRoute;
    ListView listView;
    EditText nameField;
    EditText northField;
    EditText eastField;
    Button btn_addPoint;
    Button btn_undo;
    Button btn_clearRoute;
    Button btn_saveRoute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);

        currentRoute = new ArrayList<>();
        listView = findViewById(R.id.route_listview);
        nameField = findViewById(R.id.route_name_edittext);
        northField = findViewById(R.id.route_north_edittext);
        eastField = findViewById(R.id.route_east_edittext);
        btn_addPoint = findViewById(R.id.btn_route_addpoint);
        btn_undo = findViewById(R.id.btn_route_undo);
        btn_clearRoute = findViewById(R.id.btn_route_clearroute);
        btn_saveRoute = findViewById(R.id.btn_route_saveroute);

        for(int i = 0; i < 10; i++) {
            currentRoute.add(new Coordinate((float)2.1, (float)1.2));
        }

        ArrayList<String> stringRoute = new ArrayList<>();

        for (Coordinate coord : currentRoute) {
            stringRoute.add(coord.getLatitude() + " " + coord.getLongitude());
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                stringRoute);

        listView.setAdapter(arrayAdapter);
    }

    private void addPoint() {
        float north = Float.valueOf(northField.getText().toString());
        float east = Float.valueOf(eastField.getText().toString());
        Coordinate coord = new Coordinate(north, east);
        currentRoute.add(coord);
    }

    private void removeLastPoint() {
        currentRoute.remove(currentRoute.size() -1);
    }

    private void clearRoute() {
        currentRoute.clear();
    }

    private void saveRoute() {
        Route route = new Route(nameField.getText().toString(), currentRoute);
    }


}