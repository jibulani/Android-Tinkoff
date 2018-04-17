package com.eugene.contractorsearch.latest_contractors;


import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;

import com.eugene.contractorsearch.R;

public class LatestContractorsActivity extends AppCompatActivity {

    private static final String CONTRACTOR_FRAGMENT_TAG = "contractor_fragment";
    private ContractorFragment contractorFragment;
    private SearchView searchView;

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
    }

    @Override
    protected void onResume() {
        super.onResume();
        contractorFragment.updateAdapterData();
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
