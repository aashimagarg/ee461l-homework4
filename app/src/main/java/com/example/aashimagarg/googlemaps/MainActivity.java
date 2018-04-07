package com.example.aashimagarg.googlemaps;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

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
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;


/**
 * An activity that displays a Google map with a marker (pin) to indicate a particular location.
 */
public class MainActivity extends AppCompatActivity
        implements OnMapReadyCallback {

    PlaceAutocompleteFragment placeAutoComplete;
    SupportMapFragment mapFragment;
    String address;
    LatLng loc;
    TextView formatAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Retrieve the content view that renders the map.
        setContentView(R.layout.activity_main);
        placeAutoComplete = (PlaceAutocompleteFragment) getFragmentManager().findFragmentById(R.id.place_autocomplete);
        formatAddress = findViewById(R.id.formatAddress);
        final OnMapReadyCallback thisObj = this;
        /*addressInput.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (keyEvent.getAction() == KeyEvent.ACTION_DOWN &&
                        i == KeyEvent.KEYCODE_ENTER) {
                    address = addressInput.getText().toString();
                    GeoApiContext context = new GeoApiContext.Builder()
                            .apiKey("AIzaSyBKhOHktTe5r3zH0uQeTbNrgfHJbeEqoCw")
                            .build();
                    GeocodingResult[] results = new GeocodingResult[0];
                    try {
                        results = GeocodingApi.geocode(context,
                                address).await();
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                    loc = new LatLng(results[0].geometry.location.lat, results[0].geometry.location.lng);
                    String formattedAddress = results[0].formattedAddress;
                    formatAddress.setText(formattedAddress);
                    // Get the SupportMapFragment and request notification
                    // when the map is ready to be used.
                    mapFragment = (SupportMapFragment) getSupportFragmentManager()
                            .findFragmentById(R.id.mapView);
                    mapFragment.getMapAsync(thisObj);
                    return true;
                }
                return false;
            }
        }); */
        placeAutoComplete.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                address = place.getName().toString();
                GeoApiContext context = new GeoApiContext.Builder()
                        .apiKey("AIzaSyBKhOHktTe5r3zH0uQeTbNrgfHJbeEqoCw")
                        .build();
                GeocodingResult[] results = new GeocodingResult[0];
                try {
                    results = GeocodingApi.geocode(context,
                            address).await();
                } catch (Exception e){
                    e.printStackTrace();
                }
                loc = new LatLng(results[0].geometry.location.lat, results[0].geometry.location.lng);
                String formattedAddress = results[0].formattedAddress;
                formatAddress.setText(formattedAddress);
                // Get the SupportMapFragment and request notification
                // when the map is ready to be used.
                mapFragment = (SupportMapFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.mapView);
                mapFragment.getMapAsync(thisObj);
            }

            @Override
            public void onError(Status status) {

            }
        });

    }

    /**
     * Manipulates the map when it's available.
     * The API invokes this callback when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user receives a prompt to install
     * Play services inside the SupportMapFragment. The API invokes this method after the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        // Add a marker in Sydney, Australia,
        // and move the map's camera to the same location.
        googleMap.addMarker(new MarkerOptions().position(loc)
                .title("New Location"));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(loc,15));
    }
}
