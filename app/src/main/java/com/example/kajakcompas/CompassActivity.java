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
import android.widget.ImageView;
import android.widget.TextView;

public class CompassActivity extends AppCompatActivity implements SensorEventListener, LocationListener {
    private SensorManager sensorManager;
    private LocationManager locationManager;

    private final float[] accelerometerReading = new float[3];
    private final float[] magnetometerReading = new float[3];
    private final float[] rotationMatrix = new float[9];
    private final float[] orientationAngles = new float[3];

    int currentRoute_subGoal_index = 0;
    int distance_to_current_goal;

    boolean route_enabled;

    private long timestamp = 0;
    private Coordinate ref;

    ImageView compassRose;
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

        compassRose = findViewById(R.id.compas_iw);
        direction = findViewById(R.id.direction);
        distance_text = findViewById(R.id.distance_to_goal);

        if(getIntent().getExtras() != null) {
            currentRoute = (Route) getIntent().getExtras().getSerializable("route");
        }

        if (currentRoute != null && currentRoute.getCoordinates().size() > 0 ) {
            route_enabled = true;

            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            if(ActivityCompat.checkSelfPermission(CompassActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, gpsMinTimeMS, gpsMinDistanceM, this);
            } else {
                ActivityCompat.requestPermissions(CompassActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
            }
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, gpsMinTimeMS, gpsMinDistanceM, this);
        } else {
            route_enabled = false;
            distance_text.setText("Compass");
        }
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {}

    @Override
    protected void onResume() {
        super.onResume();

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

        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            System.arraycopy(event.values, 0, accelerometerReading,
                    0, accelerometerReading.length);
        } else if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            System.arraycopy(event.values, 0, magnetometerReading,
                    0, magnetometerReading.length);
        }

        long check_time = System.currentTimeMillis();
        if (timestamp < check_time -1000) {
            updateOrientationAngles();
            timestamp = check_time;
        }
    }

    public void updateOrientationAngles() {
        SensorManager.getRotationMatrix(rotationMatrix, null,
                accelerometerReading, magnetometerReading);

        SensorManager.getOrientation(rotationMatrix, orientationAngles);

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
    public void onStatusChanged(String provider, int status, Bundle extras) {}

    @Override
    public void onProviderEnabled(String provider) {}

    @Override
    public void onProviderDisabled(String provider) {}

    private void update_compas_and_direction_orientation() {
        compassRose.setRotation(convertToDegrees(orientationAngles[0]));

        direction.setRotation(convertToDegrees(orientationAngles[0]) + direction_angle);
    }

    private void update_finalgoal_distance(Location currentLocation) {
        int distance = distance_to_current_goal;

        for (int i = currentRoute_subGoal_index; i<currentRoute.getCoordinates().size()-1; i++){

            Location firstLocation = new Location("");
            firstLocation.setLatitude(currentRoute.getCoordinates().get(i).getLatitude());
            firstLocation.setLongitude(currentRoute.getCoordinates().get(i).getLongitude());

            Location secondLocation = new Location("");
            secondLocation.setLatitude(currentRoute.getCoordinates().get(i+1).getLatitude());
            secondLocation.setLongitude(currentRoute.getCoordinates().get(i+1).getLongitude());

            distance += firstLocation.distanceTo(secondLocation);
        }

       distance_text.setText(distance +" m");
    }

    private void update_direction_angle(Location currentLocation) {
    ref = currentRoute.getCoordinates().get(currentRoute_subGoal_index);
    Location curent_goal_loc = new Location("");
    curent_goal_loc.setLatitude(ref.getLatitude());
    curent_goal_loc.setLongitude(ref.getLongitude());
    direction_angle = currentLocation.bearingTo(curent_goal_loc);
    }

    private void check_for_goal(Location currentLocation) {
        ref = currentRoute.getCoordinates().get(currentRoute_subGoal_index);

        Location curent_goal_loc = new Location("");
        curent_goal_loc.setLatitude(ref.getLatitude());
        curent_goal_loc.setLongitude(ref.getLongitude());

        distance_to_current_goal = (int) currentLocation.distanceTo(curent_goal_loc);

       if(distance_to_current_goal <25){
           if(currentRoute_subGoal_index == currentRoute.getCoordinates().size()-1){
              distance_text.setText("Goal Reached");
              distance_text.setTextColor(Color.GREEN);
           }else{
               currentRoute_subGoal_index++;
               ref = currentRoute.getCoordinates().get(currentRoute_subGoal_index);

               curent_goal_loc = new Location("");
               curent_goal_loc.setLatitude(ref.getLatitude());
               curent_goal_loc.setLongitude(ref.getLongitude());

               distance_to_current_goal = (int) currentLocation.distanceTo(curent_goal_loc);
           }
       }
    }

    private float convertToDegrees(float radiansAngle){
        float degreeangle = (float) (radiansAngle*(180/Math.PI));
        return degreeangle;
    }
}