package com.example.laura.project;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.laura.project.Activities.LocActivity;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    PlaceAutocompleteFragment placeAutoComplete;
    int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;
    private static final String TAG="MapsActivity2";
    private String placeDetailsStr;
    private String namePlace;
    LatLng coord;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        btn = (Button)findViewById(R.id.addLocMapBTN);
        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete);

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                Log.i(TAG, "Place: " + place.getName());

                namePlace = place.getName().toString();
                placeDetailsStr = place.getName() + " -> "
                        + place.getAddress();

                coord=place.getLatLng();
                mMap.addMarker(new MarkerOptions().position(coord).title(namePlace));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(coord, (float) 15));

                btn.setText(placeDetailsStr);
            }

            @Override
            public void onError(Status status) {
                Log.i(TAG, "An error occurred: " + status);
            }
        });
        //btn.setText(coord.toString());
        Toast.makeText(this,btn.getText().toString(),Toast.LENGTH_SHORT).show();
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        // LatLng sydney = new LatLng(-34, 151);
        //mMap.addMarker(new MarkerOptions().position(coord).title("Marker"));
        // mMap.moveCamera(CameraUpdateFactory.newLatLng(coord));
    }

    public void addLocation(View view) {
        //Toast.makeText(this,btn.getText().toString(),Toast.LENGTH_SHORT).show();
        String x = btn.getText().toString();
        //Toast.makeText(this,x,Toast.LENGTH_SHORT).show();
       Intent intent = new Intent(this, LocActivity.class);
       intent.putExtra("locMap", x);
        setResult(RESULT_OK, intent);
        finish();
       Toast.makeText(this,"Maps closed",Toast.LENGTH_SHORT);


        setResult(RESULT_OK, intent);
        finish();
    }
}
