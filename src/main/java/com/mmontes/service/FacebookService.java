package com.mmontes.service;

import com.amazonaws.util.json.JSONException;
import com.amazonaws.util.json.JSONObject;
import com.mmontes.util.Constants;
import com.mmontes.util.JSONParser;
import com.mmontes.util.exception.FacebookServiceException;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;

public class FacebookService {

    private String accessToken;
    private Long userID;
    private String appSecretProof;
    private String rootUrl;
    private String urlParams;

    public FacebookService(String accessToken, Long userID) {
        this.accessToken = accessToken;
        this.userID = userID;
        this.rootUrl = Constants.FB_ROOT_URL;
        this.urlParams = "?access_token=" + this.accessToken + "&format=json";
    }

    public HashMap<String, String> getUser() throws FacebookServiceException, IOException, JSONException {
        HashMap<String, String> user = new HashMap<>();

        String requestUrl = this.rootUrl + "/" + this.userID + this.urlParams;
        URL url = new URL(requestUrl);
        HttpURLConnection connnection = (HttpURLConnection) url.openConnection();
        connnection.setRequestMethod("GET");
        int responseCode = connnection.getResponseCode();
        if (responseCode >= 400) {
            throw new FacebookServiceException();
        }

        JSONObject obj = JSONParser.parseJSON(connnection.getInputStream());
        user.put("name", obj.getString("name"));

        requestUrl = this.rootUrl + "/" + this.userID + "/picture" + this.urlParams;
        url = new URL(requestUrl);
        connnection = (HttpURLConnection) url.openConnection();
        connnection.setRequestMethod("GET");
        responseCode = connnection.getResponseCode();
        if (responseCode >= 400) {
            throw new FacebookServiceException();
        }

        obj = JSONParser.parseJSON(connnection.getInputStream());
        user.put("facebookProfilePhotoUrl", obj.getString("url"));

        return user;
    }

}
