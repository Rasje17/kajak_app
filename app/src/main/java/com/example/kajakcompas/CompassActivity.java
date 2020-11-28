package com.example.kajakcompas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CompassActivity extends AppCompatActivity implements SensorEventListener, LocationListener {
    private SensorManager sensorManager;
    private LocationManager locationManager;
    private final float[] accelerometerReading = new float[3];
    private final float[] magnetometerReading = new float[3];

    private final float[] rotationMatrix = new float[9];
    private final float[] orientationAngles = new float[3];

    int curentRoute_subgoal_index = 0;
    int distance_to_curent_goal;

    boolean route_enabled;

    private long timestamp = 0;

    ImageView compasrose;
    ImageView direction;
    TextView distance_text;

    long gpsMinTimeMS = 1000;
    float gpsMinDistanceM = 10;

    float direction_angle = 0;

    Route currentRoute;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compass);

        //getting the compas image vev on the activity
        compasrose = findViewById(R.id.compas_iw);
        direction = findViewById(R.id.direction);
        distance_text = findViewById(R.id.distance_to_goal);
        if(getIntent().getExtras() != null) {
            currentRoute = (Route) getIntent().getExtras().getSerializable("route");
            Log.d("COMPASS", currentRoute.getName());
        }


        // check if we need location functionality
        if (currentRoute != null && currentRoute.getCoordinates().size() < 0 ) {
            //setup route handling
            route_enabled = true;



            // setup location handling
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, gpsMinTimeMS, gpsMinDistanceM, this);




        } else {
            route_enabled = false;
            distance_text.setText("Compass");
        }

        //getting access to sensor manager
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);



    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Do something here if sensor accuracy changes.
        // You must implement this callback in your code
    }
    @Override
    protected void onResume() {
        super.onResume();

        // Get updates from the accelerometer and magnetometer at a constant rate.
        // To make batch operations more efficient and reduce power consumption,
        // provide support for delaying updates to the application.
        //
        // In this example, the sensor reporting delay is small enough such that
        // the application receives an update before the system checks the sensor
        // readings again.
        Sensor accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if (accelerometer != null) {
            sensorManager.registerListener(this, accelerometer,
                    SensorManager.SENSOR_DELAY_NORMAL, SensorManager.SENSOR_DELAY_UI);
        }
        Sensor magneticField = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        if (magneticField != null) {
            sensorManager.registerListener(this, magneticField,
                    SensorManager.SENSOR_DELAY_NORMAL, SensorManager.SENSOR_DELAY_UI);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        // Don't receive any more updates from either sensor.
        sensorManager.unregisterListener(this);
    }

    // Get readings from accelerometer and magnetometer. To simplify calculations,
    // consider storing these readings as unit vectors.
    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            System.arraycopy(event.values, 0, accelerometerReading,
                    0, accelerometerReading.length);
        } else if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            System.arraycopy(event.values, 0, magnetometerReading,
                    0, magnetometerReading.length);
        }
        // use timestamp verifacatin to reduce amount of calculations
        long check_time = System.currentTimeMillis();
        if (timestamp < check_time -1000) {
            updateOrientationAngles();
            timestamp = check_time;
        }
    }

    // Compute the three orientation angles based on the most recent readings from
    // the device's accelerometer and magnetometer.
    public void updateOrientationAngles() {
        // Update rotation matrix, which is needed to update orientation angles.
        SensorManager.getRotationMatrix(rotationMatrix, null,
                accelerometerReading, magnetometerReading);

        // "rotationMatrix" now has up-to-date information.

        SensorManager.getOrientation(rotationMatrix, orientationAngles);
        // "orientationAngles" now has up-to-date information.


        update_compas_and_direction_orientation();
    }




    @Override
    public void onLocationChanged(Location location) {
        check_for_goal(location);
        update_direction_angle(location);
        update_finalgoal_distance(location);

        update_compas_and_direction_orientation();

    }



    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }



    private void update_compas_and_direction_orientation() {
        // set the correct orientation of compas image
        compasrose.setRotation(convertToDegrees(orientationAngles[0]));
        Log.d("azimut", String.valueOf(convertToDegrees(orientationAngles[0])));

        //change the direction arrow to allign with new compas alignment
        direction.setRotation(convertToDegrees(orientationAngles[0]) + direction_angle);
    }

    private void update_finalgoal_distance(Location currentLocation) {
        int distance = distance_to_curent_goal;

        //distance between all Coordinate remaining in Route
        for (int i=curentRoute_subgoal_index; i<currentRoute.getCoordinates().size();i++){

            Location firstLocation = new Location("");
            firstLocation.setLatitude(currentRoute.getCoordinates().get(i).getLatitude());
            firstLocation.setLongitude(currentRoute.getCoordinates().get(i).getLongitude());

            Location secondLocation = new Location("");
            secondLocation.setLatitude(currentRoute.getCoordinates().get(i+1).getLatitude());
            secondLocation.setLongitude(currentRoute.getCoordinates().get(i+1).getLongitude());

            distance += firstLocation.distanceTo(secondLocation);
        }


        // vrite to screen
       distance_text.setText(distance +" m");
    }

    private void update_direction_angle(Location currentLocation) {

    Location curent_goal_loc = new Location("");
    curent_goal_loc.setLatitude(currentRoute.getCoordinates().get(curentRoute_subgoal_index).getLatitude());
    curent_goal_loc.setLongitude(currentRoute.getCoordinates().get(curentRoute_subgoal_index).getLongitude());
    direction_angle = currentLocation.bearingTo(curent_goal_loc);
    }

    private void check_for_goal(Location currentLocation) {

        //distance from curent pos to current goal
        Location curent_goal_loc = new Location("");
        curent_goal_loc.setLatitude(currentRoute.getCoordinates().get(curentRoute_subgoal_index).getLatitude());
        curent_goal_loc.setLongitude(currentRoute.getCoordinates().get(curentRoute_subgoal_index).getLongitude());

        distance_to_curent_goal = (int) currentLocation.distanceTo(curent_goal_loc);

       if(distance_to_curent_goal<25){
           if(curentRoute_subgoal_index == currentRoute.getCoordinates().size()-1){
              distance_text.setText("Goal Reached");
              distance_text.setTextColor(Color.GREEN);
           }else{
               curentRoute_subgoal_index++;

               curent_goal_loc = new Location("");
               curent_goal_loc.setLatitude(currentRoute.getCoordinates().get(curentRoute_subgoal_index).getLatitude());
               curent_goal_loc.setLongitude(currentRoute.getCoordinates().get(curentRoute_subgoal_index).getLongitude());

               distance_to_curent_goal = (int) currentLocation.distanceTo(curent_goal_loc);

           }

       }
    }

    private float convertToDegrees(float radiansAngle){
        float degreeangle = (float) (radiansAngle*(180/Math.PI));
        return degreeangle;
    }
}