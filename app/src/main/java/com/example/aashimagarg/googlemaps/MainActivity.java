package com.example.aashimagarg.googlemaps;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;

import java.io.IOException;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.GeocodingResult;

public class MainActivity extends AppCompatActivity {

    EditText addressInput;
    MapView mapView;
    GoogleMap map;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addressInput = (EditText) findViewById(R.id.etAddress);

        // map view set up
        mapView = (MapView) findViewById(R.id.mapView);

        GeoApiContext context = new GeoApiContext.Builder()
                .apiKey("AIzaSyBKhOHktTe5r3zH0uQeTbNrgfHJbeEqoCw")
                .build();
        GeocodingResult[] results = new GeocodingResult[0];
        try {
            results = GeocodingApi.geocode(context,
                    "2100 San Antonio St. Austin TX 75287").await();
        } catch (ApiException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        final Gson gson = new GsonBuilder().setPrettyPrinting().create();
        System.out.println(gson.toJson(results[0].geometry.location.lat));
        System.out.println(gson.toJson(results[0].geometry.location.lng));
        final double lat = results[0].geometry.location.lat;
        final double lng = results[0].geometry.location.lng;
        final LatLng ll = new LatLng(lat, lng);
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                map = googleMap;
                map.moveCamera(CameraUpdateFactory.newLatLng(ll));
            }
        });


//        addressInput.setOnKeyListener(new View.OnKeyListener() {
//            @Override
//            public boolean onKey(View view, int i, KeyEvent keyEvent) {
//                if (keyEvent.getAction() == KeyEvent.ACTION_DOWN &&
//                        i == KeyEvent.KEYCODE_ENTER) {
//                    address = addressInput.getText().toString();
//                    mapView.onCreate(savedInstanceState);
//                    mapView.getMapAsync(new OnMapReadyCallback() {
//                        @Override
//                        public void onMapReady(GoogleMap googleMap) {
//                            // address.replaceAll(" ", "+");
//                            // String response = getLatLongByURL("https://maps.googleapis.com/maps/api/geocode/json?address=" + address + "&key=AIzaSyBKhOHktTe5r3zH0uQeTbNrgfHJbeEqoCw\n");
//
//                        }
//                    });
//                    return true;
//                }
//                return false;
//            }
//        });
    }
}
