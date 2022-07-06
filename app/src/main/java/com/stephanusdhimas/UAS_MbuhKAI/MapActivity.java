package com.stephanusdhimas.UAS_MbuhKAI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.Icon;
import com.mapbox.mapboxsdk.annotations.IconFactory;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;

import im.delight.android.location.SimpleLocation;

public class MapActivity extends AppCompatActivity {
    private MapView mapView;
    double latitude;
    double longitude;
    private SimpleLocation location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mapbox.getInstance(this, "pk.eyJ1IjoibWFzc2FrdGkiLCJhIjoiY2t1cWkxb3VwMG5yZjJvczd2YTBtMGZqYiJ9.8sPb1M0LhaioxVJjd_mJbg");
        setContentView(R.layout.activity_map);

        location = new SimpleLocation(this);
        if (!location.hasLocationEnabled()) {
            // ask the user to enable location access
            SimpleLocation.openSettings(this);
        }

        latitude = location.getLatitude();
        longitude = location.getLongitude();

        IconFactory icon = IconFactory.getInstance(MapActivity.this);
        Icon ic = icon.fromResource(R.drawable.marker);
        mapView = (MapView) findViewById(R.id.map_layout);
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull MapboxMap mapboxMap) {
                mapboxMap.setStyle(new Style.Builder().fromUri("mapbox://styles/mapbox/outdoors-v11"));
                MarkerOptions options = new MarkerOptions();
                MarkerOptions options1 = new MarkerOptions();

                options1.title("Lokasi Saat Ini");
                options1.icon(ic);
                options1.setPosition(new LatLng(latitude, longitude));
                mapboxMap.addMarker(options1);

                options.title("Jarak ke Stasiun Jakarta " + String.valueOf(getJarak(latitude, longitude, -6.137569, 106.814632)));
                options.setPosition(new LatLng(-6.137569, 106.814632));
                mapboxMap.addMarker(options);

                options.title("Jarak ke Stasiun Surabaya " + String.valueOf(getJarak(latitude, longitude, -7.243018, 112.741214)));
                options.setPosition(new LatLng(-7.243018, 112.741214));
                mapboxMap.addMarker(options);

                options.title("Jarak ke Stasiun Yogyakarta "  + String.valueOf(getJarak(latitude, longitude, -7.789213, 110.363495)));
                options.setPosition(new LatLng(-7.789213, 110.363495));
                mapboxMap.addMarker(options);

                options.title("Jarak ke Stasiun Purwokerto " + String.valueOf(getJarak(latitude, longitude, -7.419194, 109.222008)));
                options.setPosition(new LatLng(-7.419194, 109.222008));
                mapboxMap.addMarker(options);

                options.title("Jarak ke Stasiun Bandung " + String.valueOf(getJarak(latitude, longitude, -6.913772, 107.602506)));
                options.setPosition(new LatLng(-6.913772, 107.602506));
                mapboxMap.addMarker(options);

                options.title("Jarak ke Stasiun Malang " + String.valueOf(getJarak(latitude, longitude, -7.977140092066347, 112.637030196675)));
                options.setPosition(new LatLng(-7.977140092066347, 112.637030196675));
                mapboxMap.addMarker(options);
            }
        });

        Button btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public static double getJarak(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371; // Radious of the earth
        Double latDistance = toRad(lat2 - lat1);
        Double lonDistance = toRad(lon2 - lon1);
        Double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) +
                Math.cos(toRad(lat1)) * Math.cos(toRad(lat2)) *
                        Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        Double distance = R * c;
        int angkaSignifikan = 2;
        double temp = Math.pow(10, angkaSignifikan);
        double y = (double) Math.round(distance * temp) / temp;
        return y;
    }

    private static Double toRad(Double value) {
        return value * Math.PI / 180;
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    protected void onPause() {
        location.endUpdates();
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        location.beginUpdates();
        mapView.onResume();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

}