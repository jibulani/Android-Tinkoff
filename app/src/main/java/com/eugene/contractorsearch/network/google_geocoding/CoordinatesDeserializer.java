package com.eugene.contractorsearch.network.google_geocoding;


import com.eugene.contractorsearch.model.Coordinates;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

public class CoordinatesDeserializer implements JsonDeserializer<Coordinates> {
    @Override
    public Coordinates deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Coordinates coordinates = new Coordinates();
        JsonArray results = json.getAsJsonObject().get("results").getAsJsonArray();
        if (json.getAsJsonObject().get("status").getAsString().equals("OK")
                && results != null && results.size() > 0) {
            JsonObject location = results.get(0).getAsJsonObject()
                    .get("geometry").getAsJsonObject()
                    .get("location").getAsJsonObject();
            coordinates.setLat(location.get("lat").getAsDouble());
            coordinates.setLng(location.get("lng").getAsDouble());
        }
        return coordinates;
    }
}
