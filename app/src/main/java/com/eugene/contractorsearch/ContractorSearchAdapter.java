package com.eugene.contractorsearch;


import android.content.Context;
import android.os.Build;
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

import com.eugene.contractorsearch.model.Contractor;
import com.eugene.contractorsearch.network.ApiDadataServer;
import com.eugene.contractorsearch.network.RequestObject;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ContractorSearchAdapter extends ArrayAdapter<Contractor> implements Filterable {

    private LayoutInflater layoutInflater;
    private ApiDadataServer apiDadataServer;
    private List<Contractor> contractorList;

    public ContractorSearchAdapter(final Context context) {
        super(context, -1);
        layoutInflater = LayoutInflater.from(context);
        apiDadataServer = new ApiDadataServer();
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
        textView.setText(getItem(position).getValue());
        textView.setOnClickListener(v -> System.out.println("clicked"));
        return textView;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                //final List<Contractor>[] contractorList = new List[]{null};
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
