package com.eugene.contractorsearch.contractor_info;


import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.eugene.contractorsearch.App;
import com.eugene.contractorsearch.R;
import com.eugene.contractorsearch.db.AppDatabase;
import com.eugene.contractorsearch.db.ContractorShortInfo;
import com.eugene.contractorsearch.db.Coordinates;
import com.eugene.contractorsearch.map.MapActivity;
import com.eugene.contractorsearch.model.Contractor;
import com.eugene.contractorsearch.model.Suggestions;
import com.eugene.contractorsearch.network.dadata.ApiDadataServer;
import com.eugene.contractorsearch.network.dadata.RequestObject;
import com.eugene.contractorsearch.network.google_geocoding.GoogleGeocodingServer;
import com.google.common.collect.Collections2;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ContractorInfoActivity extends AppCompatActivity {

    public static final String CONTRACTOR_ID = "contractor_id";
    public static final String IS_NEED_TO_REFRESH = "is_need_to_refresh";
    public static final String LAT = "lat";
    public static final String LNG = "lng";

    private String contractorId;
    private AppDatabase appDatabase;
    private TextView contractorInfo;
    private FloatingActionButton mapButton;
    private ContractorShortInfo contractorShortInfo;
    private ApiDadataServer apiDadataServer;
    private GoogleGeocodingServer googleGeocodingServer;
    private FloatingActionButton favouriteButton;
    private FloatingActionButton deleteButton;
    private FloatingActionButton shareButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contractor_info);
        init();
    }

    private void init() {
        mapButton = (FloatingActionButton) findViewById(R.id.map_button);
        appDatabase = App.getInstance().getAppDatabase();
        contractorInfo = findViewById(R.id.contractor_info);
        favouriteButton = (FloatingActionButton) findViewById(R.id.favourite_button);
        deleteButton = (FloatingActionButton) findViewById(R.id.delete_button);
        shareButton = (FloatingActionButton) findViewById(R.id.share_button);
        apiDadataServer = new ApiDadataServer();
        googleGeocodingServer = new GoogleGeocodingServer();
        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            contractorId = intent.getExtras().getString(CONTRACTOR_ID);
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

    private void updateContractorFromSuggestions(Suggestions suggestions) {
        List<Contractor> refreshedContractors = new ArrayList<>(Collections2.filter(
                suggestions.getContractors(),
                input -> input != null && input.getData().getHid().equals(contractorShortInfo.getHid())));
        if (!refreshedContractors.isEmpty()) {
            ContractorShortInfo newContractorShortInfo = new ContractorShortInfo(
                    refreshedContractors.get(0), contractorShortInfo.isFavourite());
            Coordinates coordinates = new Coordinates();
            coordinates.setLat(Double.parseDouble(refreshedContractors.get(0).getData()
                    .getAddress().getAddressData().getGeoLat()));
            coordinates.setLng(Double.parseDouble(refreshedContractors.get(0).getData()
                    .getAddress().getAddressData().getGeoLon()));
            newContractorShortInfo.setCoordinates(coordinates);
            contractorInfo.setText(newContractorShortInfo.toString());
            if (newContractorShortInfo.getCoordinates() != null) {
                updateDb(newContractorShortInfo);
                contractorShortInfo = newContractorShortInfo;
                setListeners();
            } else {
                updateWithCoordinates(newContractorShortInfo);
            }
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

    private void setImageResourceForFavouriteButton() {
        if (contractorShortInfo.isFavourite()) {
            favouriteButton.setImageResource(android.R.drawable.star_big_on);
        } else {
            favouriteButton.setImageResource(android.R.drawable.star_big_off);
        }
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
        setImageResourceForFavouriteButton();
        favouriteButton.setOnClickListener(v -> {
            changeFavouriteStatus();
            setImageResourceForFavouriteButton();
        });
        deleteButton.setOnClickListener(v -> deleteContractor());
        shareButton.setOnClickListener(v -> {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, contractorShortInfo.toString());
            sendIntent.setType("text/plain");
            startActivity(sendIntent);
        });
    }

    private void deleteContractor() {
        Single.fromCallable(() -> appDatabase.contractorDao().deleteContractor(contractorShortInfo))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

    private void changeFavouriteStatus() {
        contractorShortInfo.setFavourite(!contractorShortInfo.isFavourite());
        Single.fromCallable(() -> appDatabase.contractorDao().updateContractor(contractorShortInfo))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

    private ContractorShortInfo getContractor(String contractorId) {
        return appDatabase.contractorDao().getContractorById(contractorId);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }
}
