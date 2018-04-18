package com.eugene.contractorsearch.util;


import com.eugene.contractorsearch.db.ContractorShortInfo;
import com.google.common.collect.Collections2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ContractorsSortUtil {

    public static List<ContractorShortInfo> sortContractors(List<ContractorShortInfo> contractorList) {
        List<ContractorShortInfo> favoriteContractors = new ArrayList<>(Collections2
                .filter(contractorList, contractorShortInfo -> contractorShortInfo != null &&
                        contractorShortInfo.isFavourite()));
        List<ContractorShortInfo> notFavoriteContractors = new ArrayList<>(Collections2
                .filter(contractorList, contractorShortInfo -> contractorShortInfo != null &&
                        !contractorShortInfo.isFavourite()));
        Collections.sort(favoriteContractors, (contractor1, contractor2) ->
                contractor2.getLastRequestDate().compareTo(contractor1.getLastRequestDate()));
        Collections.sort(notFavoriteContractors, (contractor1, contractor2) ->
                contractor2.getLastRequestDate().compareTo(contractor1.getLastRequestDate()));
        favoriteContractors.addAll(notFavoriteContractors);
        return favoriteContractors;
    }
}
