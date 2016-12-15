package com.example.osheadouglas.app;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

public class InspireOptions extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener, locationCallBack<String> {


    private static final String TAG = MainActivity.class.getSimpleName();
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 1000;
    private GoogleApiClient mGoogleApiClient = null;
    private Location mLastLocation;
    private boolean mRequestLocationUpdates = false;
    private LocationRequest mLocationRequest;
    private static int UPDATE_INTERVAL = 10000;
    private static int FATEST_INTERVAL = 5000;
    private static int DISPLACEMENT = 10;
    public Button showLoc;
    private String longitudeOut = "";
    private String latitudeOut = "";
    private String vicinity;
    private boolean moodBand;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspire_options);

        if (checkPlayServices()) {
            buildGoogleApiClient(); //  Calls method in this class
            createLocationRequest(); //  Calls method in this class
        }

        showLoc = (Button) findViewById(R.id.testbtn);
    }

    protected void onStart() {
        super.onStart();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }


    }

    public void fmlClick(View view){
        //LocationASync l = new LocationASync(this,longitudeOut, latitudeOut);
       // l.execute();
        goToFML();
    }

    public void displayLocation() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLastLocation != null) {
            double latitude = mLastLocation.getLatitude();
            double longitude = mLastLocation.getLongitude();
            longitudeOut = (String.valueOf(longitude));
            latitudeOut = (String.valueOf(latitude));
            Log.i("LONG : ",longitudeOut);
        } else {
            longitudeOut = String.valueOf("No Location");
            latitudeOut = String.valueOf("No Location");
        }
    }
    public void toggleUpdates(View view) {
        if (!mRequestLocationUpdates) {
            //btnShowLocUp.setText("Stop Location Updates");
            mRequestLocationUpdates = true;
            startLocationUpdates();
        } else {
            //btnShowLocUp.setText("Start Location Updates");
            mRequestLocationUpdates = false;
            stopLocationUpdates();
        }
    }
    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }
    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(UPDATE_INTERVAL);
        mLocationRequest.setFastestInterval(FATEST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setSmallestDisplacement(DISPLACEMENT);
    }
    // Checking I guess
    private boolean checkPlayServices() {
        GoogleApiAvailability googleAPI = GoogleApiAvailability.getInstance();
        int result = googleAPI.isGooglePlayServicesAvailable(this);
        if (result != ConnectionResult.SUCCESS) {
            if (googleAPI.isUserResolvableError(result)) {
                googleAPI.getErrorDialog(this, result,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            }
            return false;
        }
        return true;
    }
    protected void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
    }
    protected void stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
    }
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        displayLocation();
        LocationASync l = new LocationASync(this,longitudeOut, latitudeOut);
        l.execute();

        if (mRequestLocationUpdates) {
            startLocationUpdates();
        }
    }
    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();
    }
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        // Failed Connection
        Log.i(TAG, "Connection failed: " + connectionResult.getErrorCode());
    }
    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;

        Toast.makeText(getApplicationContext(), "Location Changed!", Toast.LENGTH_SHORT).show();

        displayLocation();
    }

    @Override
    public void onTaskCompleteLocation(String result) {
        vicinity = result;
        //Log.i("Vicinity : ",vicinity);
         //goToFML(); // Launch next activity
    }

    public void happyClick(View view){
        Intent intent = new Intent(this,InspireActivity.class);
        intent.putExtra("urlParameter","&trackarousal=500000&trackvalence=900000");
        intent.putExtra("SP","mood");
        intent.putExtra("location",vicinity);
        moodBand = true;
        intent.putExtra("moodBand",moodBand);
        startActivity(intent);
    }

    public void sadClick(View view){
        Intent intent = new Intent(this,InspireActivity.class);
        intent.putExtra("urlParameter","&trackarousal=1&trackvalence=1");
        intent.putExtra("SP","mood");
        intent.putExtra("location",vicinity);
        moodBand = true;
        intent.putExtra("moodBand",moodBand);
        startActivity(intent);
    }

    public void calmClick(View view){
        Intent intent = new Intent(this,InspireActivity.class);
        intent.putExtra("urlParameter","&trackarousal=100000&trackvalence=700000");
        intent.putExtra("SP","mood");
        intent.putExtra("location",vicinity);
        moodBand = true;
        intent.putExtra("moodBand",moodBand);
        startActivity(intent);
    }

    public void anxClick(View view){
        Intent intent = new Intent(this,InspireActivity.class);
        intent.putExtra("urlParameter","&trackarousal=900000&trackvalence=300000");
        intent.putExtra("SP","mood");
        intent.putExtra("location",vicinity);
        moodBand = true;
        intent.putExtra("moodBand",moodBand);
        startActivity(intent);
    }

    public void alertClick(View view){
        Intent intent = new Intent(this,InspireActivity.class);
        intent.putExtra("urlParameter","&trackarousal=900000&trackvalence=600000");
        intent.putExtra("SP","mood");
        intent.putExtra("location",vicinity);
        moodBand = true;
        intent.putExtra("moodBand",moodBand);
        startActivity(intent);
    }

    public void tiredClick(View view){
        Intent intent = new Intent(this,InspireActivity.class);
        intent.putExtra("urlParameter","&trackarousal=100000&trackvalence=400000");
        intent.putExtra("SP","mood");
        intent.putExtra("location",vicinity);
        moodBand = true;
        intent.putExtra("moodBand",moodBand);
        startActivity(intent);
    }

    public void goToFML(){
        Intent intent = new Intent(this,InspireActivity.class);
        intent.putExtra("urlParameter",vicinity);
        intent.putExtra("SP","loc");
        intent.putExtra("location",vicinity);
        moodBand = false;
        intent.putExtra("moodBand",moodBand);
        startActivity(intent);
    }

    public void ukClick(View view){
        Intent intent = new Intent(this,InspireActivity.class);
        intent.putExtra("urlParameter","uk");
        intent.putExtra("SP","loc");
        //Log.i("LOC TAG Before ",vicinity);
        intent.putExtra ("location",vicinity);
        moodBand = false;
        intent.putExtra("moodBand",moodBand);
        startActivity(intent);
    }

    public void usaClick(View view){
        Intent intent = new Intent(this,InspireActivity.class);
        intent.putExtra("urlParameter","usa");
        intent.putExtra("SP","loc");
        intent.putExtra("location",vicinity);
        moodBand = false;
        intent.putExtra("moodBand",moodBand);
        startActivity(intent);
    }

    public void spClick(View view){
        Intent intent = new Intent(this,InspireActivity.class);
        intent.putExtra("urlParameter","spain");
        intent.putExtra("SP","loc");
        intent.putExtra("location",vicinity);
        moodBand = false;
        intent.putExtra("moodBand",moodBand);
        startActivity(intent);
    }

    public void frClick(View view){
        Intent intent = new Intent(this,InspireActivity.class);
        intent.putExtra("urlParameter","france");
        intent.putExtra("SP","loc");
        intent.putExtra("location",vicinity);
        moodBand = false;
        intent.putExtra("moodBand",moodBand);
        startActivity(intent);
    }

    public void gerClick(View view){
        Intent intent = new Intent(this,InspireActivity.class);
        intent.putExtra("urlParameter","germany");
        intent.putExtra("SP","loc");
        intent.putExtra("location",vicinity);
        moodBand = false;
        intent.putExtra("moodBand",moodBand);
        startActivity(intent);
    }












}





















