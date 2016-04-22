package nl.nujules.travellerapp;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.LocationManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.Layout;
import android.view.View;
import android.widget.Button;
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

import nl.nujules.travellerapp.util.ActivityType;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private boolean markerClicked = false;
    private Marker clickedMarker = null;

    private LinearLayout mapLayout;
    private LinearLayout infoLayout;
    private TextView infoLayoutMarkerName;
    private TextView infoLayoutMarkerSnippet;
    private TextView infoLayoutMarkerKeywords;
    private Button btnMoreInfo;
    private Button btnAddReview;

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
        infoLayoutMarkerKeywords = (TextView)findViewById(R.id.keywords);Button menuButton = (Button) findViewById(R.id.btnMenu);
        btnMoreInfo = (Button)findViewById(R.id.btnMoreInfo);
        btnAddReview = (Button)findViewById(R.id.btnAddReview);


        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Replace "Placeholder.this" with the class name you implement this is.
                Intent intent = new Intent(MapsActivity.this, Menu.class);
                intent.putExtra("activity", ActivityType.MAPS_ACTIVITY);

                startActivity(intent);
            }
        });

        btnMoreInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MapsActivity.this, NearbyResultSpecific.class);
                startActivity(intent);
            }
        });

        btnAddReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MapsActivity.this, AddReview.class);
                startActivity(intent);
            }
        });
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


        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String provider = locationManager.getBestProvider(criteria, false);



        //Location l = locationManager.getLastKnownLocation(provider); DOESNT WORK
        LatLng myCoordinates = new LatLng(51.4516028,5.4818477); // location of fontys R1
        CameraUpdate yourLocation = CameraUpdateFactory.newLatLngZoom(myCoordinates, 12);
        mMap.animateCamera(yourLocation);
    }

    private void addMarker(String title, String snippet, double lat, double lng) {
        LatLng latLng = new LatLng(lat, lng);
        com.google.android.gms.maps.model.Marker m = mMap.addMarker(new MarkerOptions().position(latLng).title(title));
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

        LatLng markerLatLng = new LatLng(m.latitude,m.longitude);
        CameraUpdate markerLocation = CameraUpdateFactory.newLatLngZoom(markerLatLng, 12);
        mMap.animateCamera(markerLocation);

        infoLayoutMarkerName.setText(m.name);
        infoLayoutMarkerSnippet.setText("\n" + m.snippet);
        String keywords = " \n Top Keywords:";
        for (Map.Entry<String, Integer> e : m.keywords.entrySet()) {
            keywords = keywords + "\n" + e.getKey() + ": " + e.getValue() + "x";
        }
        infoLayoutMarkerKeywords.setText(keywords);
    }

    @Override
    public void onBackPressed() {
        markerClicked = false;
        clickedMarker = null;
        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                android.widget.LinearLayout.LayoutParams.MATCH_PARENT,
                android.widget.LinearLayout.LayoutParams.MATCH_PARENT, 0.1f);
        mapLayout.setLayoutParams(param);
        param = new LinearLayout.LayoutParams(
                android.widget.LinearLayout.LayoutParams.MATCH_PARENT,
                android.widget.LinearLayout.LayoutParams.MATCH_PARENT, 0.95f);
        infoLayout.setLayoutParams(param);
    }


}
