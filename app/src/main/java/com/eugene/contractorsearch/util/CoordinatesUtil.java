package com.eugene.contractorsearch.util;


import com.eugene.contractorsearch.db.Coordinates;
import com.eugene.contractorsearch.model.AddressData;
import com.eugene.contractorsearch.model.Contractor;

public class CoordinatesUtil {

    public static Coordinates getCoordinates(Contractor contractor) {
        Coordinates coordinates = new Coordinates();
        try {
            Double lat = Double.parseDouble(contractor.getData().getAddress()
                    .getAddressData().getGeoLat());
            Double lng = Double.parseDouble(contractor.getData().getAddress()
                    .getAddressData().getGeoLon());
            coordinates.setLat(lat);
            coordinates.setLng(lng);
            return coordinates;
        } catch (Exception e) {
            throw e;
        }
    }

    public static boolean isCoordinatesSet(Contractor contractor) {
        AddressData addressData = contractor.getData().getAddress().getAddressData();
        if (addressData != null && addressData.getGeoLat() != null &&
                addressData.getGeoLon() != null) {
            try {
                getCoordinates(contractor);
                return true;
            } catch (Exception e) {
                return false;
            }
        }
        return false;
    }
}
