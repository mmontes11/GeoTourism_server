package com.mmontes.util;


import com.amazonaws.util.json.JSONException;
import com.amazonaws.util.json.JSONObject;
import com.amazonaws.util.json.JSONArray;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class JSONParser {

    public static JSONObject parseJSON(InputStream is) throws IOException, JSONException {
        return new JSONObject(getStringFromInputStream(is));
    }

    public static JSONArray parseJSONArray(InputStream is) throws IOException, JSONException {
        return new JSONArray(getStringFromInputStream(is));
    }

    private static String getStringFromInputStream(InputStream is) throws IOException {
        BufferedReader streamReader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
        StringBuilder responseStrBuilder = new StringBuilder();

        String inputStr;
        while ((inputStr = streamReader.readLine()) != null)
            responseStrBuilder.append(inputStr);

        return responseStrBuilder.toString();
    }
}
