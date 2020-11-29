package com.example.kajakcompas;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class RouteActivity extends AppCompatActivity {
    ArrayList<Coordinate> currentRoute;
    ListView listView;
    EditText nameField;
    EditText latField;
    EditText longField;
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
        latField = findViewById(R.id.route_north_edittext);
        longField = findViewById(R.id.route_east_edittext);
        btn_addPoint = findViewById(R.id.btn_route_addpoint);
        btn_undo = findViewById(R.id.btn_route_undo);
        btn_clearRoute = findViewById(R.id.btn_route_clearroute);
        btn_saveRoute = findViewById(R.id.btn_route_saveroute);

        nameField.setHint("Name");
        latField.setHint("Latitude");
        longField.setHint("Longitude");

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
        float lat = Float.valueOf(latField.getText().toString());
        float _long = Float.valueOf(longField.getText().toString());

        Coordinate coordinate = new Coordinate(lat, _long);
        currentRoute.add(coordinate);
        arrayAdapter.notifyDataSetChanged();
        latField.getText().clear();
        longField.getText().clear();
    }

    private void removeLastPoint() {
        if(!currentRoute.isEmpty()) {
            currentRoute.remove(currentRoute.size() -1);
            arrayAdapter.notifyDataSetChanged();
        }
    }

    private void clearRoute() {
        currentRoute.clear();
        arrayAdapter.notifyDataSetChanged();
    }

    private void saveRoute() {
        if(currentRoute.size() > 0 ) {
            final Route route = new Route(nameField.getText().toString(), currentRoute);
            final RouteDB routeDB = RouteDB.getInstance(this);

            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    routeDB.routeDao().insertRoute(route);
                }
            });
            latField.getText().clear();
            longField.getText().clear();
            nameField.getText().clear();
        }
    }
}