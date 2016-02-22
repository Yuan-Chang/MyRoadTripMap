package com.me.myroadtripmap;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by developer3 on 1/9/16.
 */
public class TripInfoJsonDeserializer implements JsonDeserializer<TripInfo[]>{

    @Override
    public TripInfo[] deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        // Get the "content" element from the parsed JSON
        JsonElement content = json.getAsJsonObject().get("results").getAsJsonArray();

        // Deserialize it. You use a new instance of Gson to avoid infinite recursion
        // to this deserializer
        //Type listType = new TypeToken<List<TripInfo>>(){}.getType();
        return new Gson().fromJson(content, TripInfo[].class);
    }
}
