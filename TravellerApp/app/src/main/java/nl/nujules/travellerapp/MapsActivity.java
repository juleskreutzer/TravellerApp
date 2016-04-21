package nl.nujules.travellerapp;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.LocationManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.Layout;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Map;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private boolean markerClicked = false;
    private Marker clickedMarker = null;

    private LinearLayout mapLayout;
    private LinearLayout infoLayout;
    private TextView infoLayoutMarkerName;
    private TextView infoLayoutMarkerSnippet;
    private TextView infoLayoutMarkerKeywords;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mapLayout = (LinearLayout)findViewById(R.id.mapLayout);
        infoLayout = (LinearLayout)findViewById(R.id.infoLayout);
        infoLayoutMarkerName = (TextView)findViewById(R.id.title);
        infoLayoutMarkerSnippet = (TextView)findViewById(R.id.snippet);
        infoLayoutMarkerKeywords = (TextView)findViewById(R.id.keywords);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        setUpMap();
        Marker.setUpMarkers();
        for (Marker marker : Marker.markers) {
            addMarker(marker.name, marker.snippet, marker.latitude, marker.longitude);
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void setUpMap() throws SecurityException {

        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {

            // No explanation needed, we can request the permission.
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_CONTACTS},0);
        }


        if (this.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        }

        // Get LocationManager object from System Service LOCATION_SERVICE
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        // Create a criteria object to retrieve provider
        Criteria criteria = new Criteria();

        // Get the name of the best provider
        String provider = locationManager.getBestProvider(criteria, false);



        //Location l = locationManager.getLastKnownLocation(provider);
        LatLng myCoordinates = new LatLng(51.4516028,5.4818477); // location of fontys R1
        CameraUpdate yourLocation = CameraUpdateFactory.newLatLngZoom(myCoordinates, 12);
        mMap.animateCamera(yourLocation);
    }

    private void addMarker(String title, String snippet, double lat, double lng) {
        LatLng latLng = new LatLng(lat, lng);
        com.google.android.gms.maps.model.Marker m = mMap.addMarker(new MarkerOptions().position(latLng).title(title).snippet(snippet));
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(com.google.android.gms.maps.model.Marker mapsMarker) {
                forloop:
                for (Marker m : Marker.markers) {
                    if (mapsMarker.getPosition().latitude == m.latitude
                            && mapsMarker.getPosition().longitude == m.longitude) {
                        markerClicked(m);
                        break forloop;
                    }
                }

                return false;
            }
        });
    }

    private void markerClicked(Marker m){
        markerClicked = true;
        clickedMarker = m;
        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                android.widget.LinearLayout.LayoutParams.MATCH_PARENT,
                android.widget.LinearLayout.LayoutParams.MATCH_PARENT, 0.7f);
        mapLayout.setLayoutParams(param);
        param = new LinearLayout.LayoutParams(
                android.widget.LinearLayout.LayoutParams.MATCH_PARENT,
                android.widget.LinearLayout.LayoutParams.MATCH_PARENT, 0.35f);
        infoLayout.setLayoutParams(param);
        infoLayoutMarkerName.setText(m.name);
        infoLayoutMarkerSnippet.setText("\n" + m.snippet);
        String keywords = " \n Top Keywords:";
        for (Map.Entry<String, Integer> e : m.keywords.entrySet()) {
            keywords = keywords + "\n" + e.getKey() + ": " + e.getValue() + "x";
        }
        infoLayoutMarkerKeywords.setText(keywords);
    }


}