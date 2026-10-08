package com.example.congressionalapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import java.util.List;

public class InteractiveMapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private FusedLocationProviderClient fusedLocationClient;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 102;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        findViewById(R.id.btnBack).setOnClickListener(v -> finish());
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setMapToolbarEnabled(false);
        mMap.getUiSettings().setZoomControlsEnabled(false);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);

        BeachLocation targetBeach = (BeachLocation) getIntent().getSerializableExtra("TARGET_BEACH");
        com.google.android.gms.maps.model.Marker targetMarker = null;

        List<BeachLocation> beaches = BeachRepository.getBeaches();
        for (BeachLocation beach : beaches) {
            LatLng pos = new LatLng(beach.getLatitude(), beach.getLongitude());
            com.google.android.gms.maps.model.Marker marker = mMap.addMarker(new MarkerOptions()
                    .position(pos)
                    .title(beach.getName()));

            if (targetBeach != null && beach.getId().equals(targetBeach.getId())) {
                targetMarker = marker;
            }
        }

        if (targetBeach != null) {
            LatLng targetPos = new LatLng(targetBeach.getLatitude(), targetBeach.getLongitude());
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(targetPos, 14f));
            if (targetMarker != null) {
                targetMarker.showInfoWindow();
            }
            enableUserLocationOnMap(false);
        } else {
            enableUserLocationOnMap(true);
        }

        mMap.setOnInfoWindowClickListener(marker -> {
            for (BeachLocation beach : beaches) {
                if (beach.getName().equals(marker.getTitle())) {
                    Intent intent = new Intent(InteractiveMapActivity.this, BeachInfoDisplayActivity.class);
                    intent.putExtra("BEACH", beach);
                    startActivity(intent);
                    break;
                }
            }
        });
    }

    private void enableUserLocationOnMap(boolean centerOnUser) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (mMap != null) {
                mMap.setMyLocationEnabled(true);
                if (centerOnUser) {
                    fusedLocationClient.getLastLocation().addOnSuccessListener(this, location -> {
                        if (location != null) {
                            LatLng userLatLng = new LatLng(location.getLatitude(), location.getLongitude());
                            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(userLatLng, 12));
                        } else {
                            LatLng oahu = new LatLng(21.4389, -158.0001);
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(oahu, 10));
                        }
                    });
                }
            }
        } else {
            if (centerOnUser) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
                if (mMap != null) {
                    LatLng oahu = new LatLng(21.4389, -158.0001);
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(oahu, 10));
                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                enableUserLocationOnMap(true);
                Toast.makeText(this, "Location tracking enabled on map", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Location permission denied. Showing default Oʻahu view.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
