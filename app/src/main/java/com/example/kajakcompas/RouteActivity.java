package com.example.kajakcompas;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
    ArrayAdapter<Coordinate> arrayAdapter;

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

        nameField.setHint("Name");
        northField.setHint("North");
        eastField.setHint("East");

        arrayAdapter = new ArrayAdapter<Coordinate>(
                this,
                android.R.layout.simple_list_item_1,
                currentRoute);

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
        float north = Float.valueOf(northField.getText().toString());
        float east = Float.valueOf(eastField.getText().toString());
        Coordinate coord = new Coordinate(north, east);
        currentRoute.add(coord);
        Log.d("TAG", currentRoute.toString());
        arrayAdapter.notifyDataSetChanged();
        northField.getText().clear();
        eastField.getText().clear();

    }

    private void removeLastPoint() {
        if(currentRoute.size() -1 != -1) {
            currentRoute.remove(currentRoute.size() -1);
            arrayAdapter.notifyDataSetChanged();
        }


    }

    private void clearRoute() {
        currentRoute.clear();
        arrayAdapter.notifyDataSetChanged();
    }

    private void saveRoute() {
        Route route = new Route(nameField.getText().toString(), currentRoute);
    }


}