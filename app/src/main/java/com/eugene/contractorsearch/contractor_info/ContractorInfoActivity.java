package com.eugene.contractorsearch.contractor_info;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import com.eugene.contractorsearch.App;
import com.eugene.contractorsearch.ContractorSearchAdapter;
import com.eugene.contractorsearch.R;
import com.eugene.contractorsearch.db.AppDatabase;
import com.eugene.contractorsearch.db.ContractorShortInfo;
import com.eugene.contractorsearch.map.MapActivity;
import com.eugene.contractorsearch.model.Contractor;
import com.eugene.contractorsearch.model.Suggestions;
import com.eugene.contractorsearch.network.dadata.ApiDadataServer;
import com.eugene.contractorsearch.network.dadata.RequestObject;
import com.eugene.contractorsearch.network.google_geocoding.GoogleGeocodingServer;

import java.util.Optional;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ContractorInfoActivity extends AppCompatActivity {

    public static final String IS_NEED_TO_REFRESH = "is_need_to_refresh";
    public static final String LAT = "lat";
    public static final String LNG = "lng";

    private String contractorId;
    private AppDatabase appDatabase;
    private TextView contractorInfo;
    private Button mapButton;
    private ContractorShortInfo contractorShortInfo;
    private ApiDadataServer apiDadataServer;
    private GoogleGeocodingServer googleGeocodingServer;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contractor_info);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void init() {
        mapButton = findViewById(R.id.map_button);
        appDatabase = App.getInstance().getAppDatabase();
        contractorInfo = findViewById(R.id.contractor_info);
        apiDadataServer = new ApiDadataServer();
        googleGeocodingServer = new GoogleGeocodingServer();
        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            contractorId = intent.getExtras().getString(ContractorSearchAdapter.CONTRACTOR_ID);
            System.out.println(contractorId);
            Single.fromCallable(() -> getContractor(contractorId))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(loadedContractorShortInfo -> {
                        contractorInfo.setText(loadedContractorShortInfo.toString());
                        contractorShortInfo = loadedContractorShortInfo;
                        if (intent.getExtras().getBoolean(IS_NEED_TO_REFRESH)) {
                            makeContractorRefresh();
                        } else {
                            setListeners();
                        }
                    });
        }
    }

    private void makeContractorRefresh() {
        RequestObject requestObject = new RequestObject();
        requestObject.setQuery(contractorShortInfo.getValue());
        apiDadataServer.getApi().getContractors(requestObject)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        this::updateContractorFromSuggestions,
                        throwable -> System.out.println(String.format("Throwable:%s", throwable.getMessage()))
                );
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void updateContractorFromSuggestions(Suggestions suggestions) {
        Optional<Contractor> refreshedContractor = suggestions
                .getContractors().stream().filter(contractor ->
                        contractor.getData().getHid()
                                .equals(contractorShortInfo.getHid()))
                .findAny();
        if (refreshedContractor.isPresent()) {
            ContractorShortInfo newContractorShortInfo = new ContractorShortInfo(
                    refreshedContractor.get(), contractorShortInfo.isFavourite());
            contractorInfo.setText(newContractorShortInfo.toString());
            updateWithCoordinates(newContractorShortInfo);
        }
    }

    private void updateWithCoordinates(ContractorShortInfo newContractorShortInfo) {
        String key = getResources().getString(R.string.google_maps_key);
        googleGeocodingServer.getCoordinates(newContractorShortInfo.getAddress(), key)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(coordinates -> {
                    newContractorShortInfo.setCoordinates(coordinates);
                    contractorShortInfo = newContractorShortInfo;
                    updateDb(newContractorShortInfo);
                    setListeners();
                });
    }

    private void updateDb(ContractorShortInfo newContractorShortInfo) {
        Single.fromCallable(() -> appDatabase.contractorDao().updateContractor(newContractorShortInfo))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

    private void setListeners() {
        mapButton.setOnClickListener(v -> {
            if (contractorShortInfo != null && contractorShortInfo.getCoordinates() != null) {
                Bundle bundle = new Bundle();
                bundle.putDouble(LAT, contractorShortInfo.getCoordinates().getLat());
                bundle.putDouble(LNG, contractorShortInfo.getCoordinates().getLng());
                Intent newIntent = new Intent(v.getContext(), MapActivity.class);
                newIntent.putExtras(bundle);
                startActivity(newIntent);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        removeListeners();
    }

    private void removeListeners() {
        mapButton.setOnClickListener(null);
    }

    private ContractorShortInfo getContractor(String contractorId) {
        return appDatabase.contractorDao().getContractorById(contractorId);
    }
}
