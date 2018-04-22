package com.eugene.contractorsearch.settings;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Switch;

import com.eugene.contractorsearch.R;
import com.eugene.contractorsearch.SettingTypes;
import com.eugene.contractorsearch.latest_contractors.LatestContractorsActivity;

public class SettingsActivity extends AppCompatActivity {

    private Switch bottomSwitch;
    private Switch drawerSwitch;
    private BottomNavigationView bottomNavigationView;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        bottomSwitch = findViewById(R.id.bottom_switch);
        drawerSwitch = findViewById(R.id.drawer_switch);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        if (SettingTypes.bottomMenu) {
            bottomSwitch.setChecked(true);
        } else if (SettingTypes.drawerMenu) {
            drawerSwitch.setChecked(true);
        }
        bottomSwitch.setOnClickListener(v -> {
            if (bottomSwitch.isChecked()) {
                drawerSwitch.setChecked(false);
                SettingTypes.bottomMenu = true;
                SettingTypes.drawerMenu = false;
            } else {
                SettingTypes.bottomMenu = false;
            }
            setListeners();
        });
        drawerSwitch.setOnClickListener(v -> {
            if (drawerSwitch.isChecked()) {
                bottomSwitch.setChecked(false);
                SettingTypes.bottomMenu = false;
                SettingTypes.drawerMenu = true;
            } else {
                SettingTypes.drawerMenu = false;
            }
            setListeners();
        });
        bottomNavigationView = findViewById(R.id.navigation);
        bottomNavigationView.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setListeners();
    }

    @Override
    protected void onPause() {
        super.onPause();
        removeListeners();
    }

    private void setListeners() {
        if (SettingTypes.bottomMenu) {
            bottomNavigationView.setOnNavigationItemSelectedListener
                    (item -> {
                        switch (item.getItemId()) {
                            case R.id.action_home:
                                finish();
                                break;
                            case R.id.action_latest:
                                Intent intent = new Intent(SettingsActivity.this, LatestContractorsActivity.class);
                                SettingsActivity.this.startActivity(intent);
                                finish();
                                break;
                        }
                        return true;
                    });
            bottomNavigationView.setSelectedItemId(R.id.action_settings);
            bottomNavigationView.setVisibility(View.VISIBLE);
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        } else if (SettingTypes.drawerMenu) {
            drawerLayout.closeDrawers();
            navigationView.getMenu().findItem(R.id.action_settings).setChecked(true);
            navigationView.setNavigationItemSelectedListener
                    (item -> {
                        switch (item.getItemId()) {
                            case R.id.action_home:
                                finish();
                                break;
                            case R.id.action_latest:
                                Intent intent = new Intent(SettingsActivity.this, LatestContractorsActivity.class);
                                SettingsActivity.this.startActivity(intent);
                                finish();
                                break;
                        }
                        return true;
                    });
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
            bottomNavigationView.setVisibility(View.INVISIBLE);
        } else {
            bottomNavigationView.setVisibility(View.INVISIBLE);
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        }
    }

    private void removeListeners() {
        bottomNavigationView.setOnNavigationItemSelectedListener(null);
        navigationView.setNavigationItemSelectedListener(null);
    }
}
