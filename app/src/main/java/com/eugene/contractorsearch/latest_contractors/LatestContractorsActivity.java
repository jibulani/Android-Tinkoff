package com.eugene.contractorsearch.latest_contractors;


import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.View;

import com.eugene.contractorsearch.R;
import com.eugene.contractorsearch.SettingTypes;
import com.eugene.contractorsearch.settings.SettingsActivity;

public class LatestContractorsActivity extends AppCompatActivity {

    private static final String CONTRACTOR_FRAGMENT_TAG = "contractor_fragment";
    private ContractorFragment contractorFragment;
    private SearchView searchView;
    private BottomNavigationView bottomNavigationView;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.latest_contractors);
        if (savedInstanceState == null) {
            contractorFragment = ContractorFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.contractors_container, contractorFragment, CONTRACTOR_FRAGMENT_TAG)
                    .commit();
        } else {
            contractorFragment = (ContractorFragment) getSupportFragmentManager()
                    .findFragmentByTag(CONTRACTOR_FRAGMENT_TAG);
        }
        bottomNavigationView = findViewById(R.id.navigation);
        bottomNavigationView.setVisibility(View.INVISIBLE);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setListeners();
        contractorFragment.updateAdapterData();
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
                            case R.id.action_settings:
                                Intent intent = new Intent(LatestContractorsActivity.this, SettingsActivity.class);
                                LatestContractorsActivity.this.startActivity(intent);
                                finish();
                                break;
                        }
                        return true;
                    });
            bottomNavigationView.setSelectedItemId(R.id.action_latest);
            bottomNavigationView.setVisibility(View.VISIBLE);
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        } else if (SettingTypes.drawerMenu) {
            drawerLayout.closeDrawers();
            navigationView.getMenu().findItem(R.id.action_latest).setChecked(true);
            navigationView.setNavigationItemSelectedListener
                    (item -> {
                        switch (item.getItemId()) {
                            case R.id.action_home:
                                finish();
                                break;
                            case R.id.action_settings:
                                Intent intent = new Intent(LatestContractorsActivity.this, SettingsActivity.class);
                                LatestContractorsActivity.this.startActivity(intent);
                                finish();
                                break;
                        }
                        return true;
                    });
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
            bottomNavigationView.setVisibility(View.INVISIBLE);
        } else {
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            bottomNavigationView.setVisibility(View.INVISIBLE);
        }
    }

    private void removeListeners() {
        bottomNavigationView.setOnNavigationItemSelectedListener(null);
        navigationView.setNavigationItemSelectedListener(null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_panel, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                contractorFragment.getContractorAdapter().getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                contractorFragment.getContractorAdapter().getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }
}
