package com.eugene.contractorsearch.map;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import com.eugene.contractorsearch.ContractorSearchAdapter;
import com.eugene.contractorsearch.R;
import com.eugene.contractorsearch.contractor_info.ContractorInfoActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class MapActivity extends FragmentActivity implements OnMapReadyCallback {

    private Double lat;
    private Double lng;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_fragment);
        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            lat = intent.getExtras().getDouble(ContractorInfoActivity.LAT);
            lng = intent.getExtras().getDouble(ContractorInfoActivity.LNG);
        }
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        if (lat != null && lng != null) {
            LatLng address = new LatLng(lat, lng);
            googleMap.addMarker(new MarkerOptions().position(address));
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(address));
        }
    }
}
