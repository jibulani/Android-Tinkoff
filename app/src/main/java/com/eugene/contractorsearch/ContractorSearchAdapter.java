package com.eugene.contractorsearch;


import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.eugene.contractorsearch.contractor_info.ContractorInfoActivity;
import com.eugene.contractorsearch.db.AppDatabase;
import com.eugene.contractorsearch.db.ContractorShortInfo;
import com.eugene.contractorsearch.model.Contractor;
import com.eugene.contractorsearch.model.Coordinates;
import com.eugene.contractorsearch.network.dadata.ApiDadataServer;
import com.eugene.contractorsearch.network.dadata.RequestObject;
import com.eugene.contractorsearch.network.google_geocoding.GoogleGeocodingServer;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ContractorSearchAdapter extends ArrayAdapter<Contractor> implements Filterable {

    public static final String CONTRACTOR_ID = "contractor_id";

    private LayoutInflater layoutInflater;
    private ApiDadataServer apiDadataServer;
    private List<Contractor> contractorList;
    private AppDatabase appDatabase;
    private GoogleGeocodingServer googleGeocodingServer;

    public ContractorSearchAdapter(final Context context) {
        super(context, -1);
        layoutInflater = LayoutInflater.from(context);
        apiDadataServer = new ApiDadataServer();
        appDatabase = App.getInstance().getAppDatabase();
        googleGeocodingServer = new GoogleGeocodingServer();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final TextView textView;
        if (convertView != null) {
            textView = (TextView) convertView;
        } else {
            textView = (TextView) layoutInflater.inflate(android.R.layout.simple_dropdown_item_1line, parent, false);
        }
        Contractor contractor = getItem(position);
        textView.setText(contractor.getValue());
        textView.setOnClickListener(v -> {
            String key = v.getContext().getResources().getString(R.string.google_maps_key);
            googleGeocodingServer.getCoordinates(contractor.getData().getAddress().getValue(), key)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(coordinates -> {
                        Single.fromCallable(() -> saveContractor(contractor, coordinates))
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(
                                        hid -> {
                                            Bundle bundle = new Bundle();
                                            bundle.putString(CONTRACTOR_ID, hid);
                                            bundle.putBoolean(ContractorInfoActivity.IS_NEED_TO_REFRESH, false);
                                            Intent intent = new Intent(v.getContext(), ContractorInfoActivity.class);
                                            intent.putExtras(bundle);
                                            v.getContext().startActivity(intent);
                                        });
                    });


        });
        return textView;
    }

    private String saveContractor(Contractor contractor, Coordinates coordinates) {
        ContractorShortInfo oldContractor = appDatabase.contractorDao()
                .getContractorById(contractor.getData().getHid());
        boolean isFavourite = false;
        if (oldContractor != null) {
            isFavourite = oldContractor.isFavourite();
            appDatabase.contractorDao().deleteContractor(oldContractor);
        }
        ContractorShortInfo contractorShortInfo = new ContractorShortInfo(contractor, isFavourite);
        contractorShortInfo.setCoordinates(coordinates);
        appDatabase.contractorDao().insert(contractorShortInfo);
        return contractor.getData().getHid();
    }

    @NonNull
    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                if (constraint != null) {
                    RequestObject requestObject = new RequestObject();
                    requestObject.setQuery(constraint.toString());
                    apiDadataServer.getApi().getContractors(requestObject)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    suggestions -> {
                                        suggestions.getContractors()
                                                .forEach(contractor -> System.out.println(contractor.getValue()));
                                        contractorList = suggestions.getContractors();
                                    },
                                    throwable -> System.out.println(String.format("Throwable:%s", throwable.getMessage()))
                            );
                }
                if (contractorList == null) {
                    contractorList = new ArrayList<>();
                }
                final FilterResults filterResults = new FilterResults();
                filterResults.values = contractorList;
                filterResults.count = contractorList.size();

                return filterResults;
            }

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                clear();
                ((List<Contractor>)results.values).forEach(contractor -> add(contractor));
                if (results.count > 0) {
                    notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }
            }

            @Override
            public CharSequence convertResultToString(Object resultValue) {
                return resultValue == null ? "" : ((Contractor) resultValue).getValue();
            }
        };
        return filter;
    }
}
