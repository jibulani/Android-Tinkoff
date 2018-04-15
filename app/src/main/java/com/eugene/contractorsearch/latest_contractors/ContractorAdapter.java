package com.eugene.contractorsearch.latest_contractors;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.eugene.contractorsearch.App;
import com.eugene.contractorsearch.R;
import com.eugene.contractorsearch.contractor_info.ContractorInfoActivity;
import com.eugene.contractorsearch.db.AppDatabase;
import com.eugene.contractorsearch.db.ContractorShortInfo;
import com.eugene.contractorsearch.model.Contractor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ContractorAdapter extends RecyclerView.Adapter<BaseViewHolder<ContractorShortInfo>>
        implements Filterable {

    private List<ContractorShortInfo> contractorList;
    private List<ContractorShortInfo> contractorListFiltered;
    private AppDatabase appDatabase;

    public ContractorAdapter() {
        contractorList = new ArrayList<>();
        contractorListFiltered = new ArrayList<>();
        appDatabase = App.getInstance().getAppDatabase();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void setDataFromDb() {
        contractorList.clear();
        contractorListFiltered.clear();
        Single.fromCallable(this::loadContractors)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(contractorShortInfoList -> {
                    contractorList.addAll(contractorShortInfoList);
                    contractorList = sortContractors(contractorList);
                    contractorListFiltered.addAll(contractorList);
                    notifyDataSetChanged();
                });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private List<ContractorShortInfo> sortContractors(List<ContractorShortInfo> contractorList) {
        List<ContractorShortInfo> favoriteContractors = contractorList.stream()
                .filter(ContractorShortInfo::isFavourite).collect(Collectors.toList());
        List<ContractorShortInfo> notFavoriteContractors = contractorList.stream()
                .filter(contractorShortInfo -> !contractorShortInfo.isFavourite())
                .collect(Collectors.toList());
        favoriteContractors = favoriteContractors.stream().sorted((contractor1, contractor2) ->
                contractor2.getLastRequestDate().compareTo(contractor1.getLastRequestDate())).collect(Collectors.toList());
        notFavoriteContractors = notFavoriteContractors.stream().sorted((contractor1, contractor2) ->
                contractor2.getLastRequestDate().compareTo(contractor1.getLastRequestDate())).collect(Collectors.toList());
        List<ContractorShortInfo> sortedContractors = favoriteContractors;
        sortedContractors.addAll(notFavoriteContractors);
        return sortedContractors;
    }

    private List<ContractorShortInfo> loadContractors() {
        return appDatabase.contractorDao().getAll();
    }

    @Override
    public BaseViewHolder<ContractorShortInfo> onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.contractor_item, parent, false);
        return new VerticalItemHolder(view);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder<ContractorShortInfo> holder, int position) {
        ContractorShortInfo contractor = contractorListFiltered.get(position);
        holder.bind(contractor);
        holder.itemView.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString(ContractorInfoActivity.CONTRACTOR_ID, contractor.getHid());
            bundle.putBoolean(ContractorInfoActivity.IS_NEED_TO_REFRESH, true);
            Intent intent = new Intent(v.getContext(), ContractorInfoActivity.class);
            intent.putExtras(bundle);
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return contractorListFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString();
                if (charString.isEmpty()) {
                    contractorListFiltered = contractorList;
                } else {
                    List<ContractorShortInfo> filteredList = new ArrayList<>();
                    contractorList.stream()
                            .filter(contractorShortInfo ->
                                    contractorShortInfo.getValue().toLowerCase()
                                            .contains(charString.toLowerCase()))
                            .forEach(filteredList::add);
                    contractorListFiltered = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = contractorListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                contractorListFiltered = (ArrayList<ContractorShortInfo>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    static class VerticalItemHolder extends BaseViewHolder<ContractorShortInfo> {

        private TextView contractorValue;
        private TextView contractorDate;
        private ImageView star;

        public VerticalItemHolder(View itemView) {
            super(itemView);
            contractorValue = itemView.findViewById(R.id.contractor_value);
            contractorDate = itemView.findViewById(R.id.contractor_date);
            star = itemView.findViewById(R.id.star);
        }

        public void bind(ContractorShortInfo contractor) {
            super.bind(contractor);
            contractorValue.setText(contractor.getValue());
            contractorDate.setText(contractor.getLastRequestDate().toString());
            if (contractor.isFavourite()) {
                star.setImageResource(android.R.drawable.star_big_on);
            } else {
                star.setImageResource(android.R.drawable.star_big_off);
            }
        }
    }
}
