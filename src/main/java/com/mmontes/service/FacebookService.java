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
        this.urlParams = "?access_token=" + this.accessToken + "&format=json&redirect=false";
    }

    private HttpURLConnection getConnection(String requestUrl) throws IOException, FacebookServiceException {
        URL url = new URL(requestUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        int responseCode = connection.getResponseCode();
        if (responseCode >= 400) {
            throw new FacebookServiceException();
        }
        return connection;
    }

    public String getUser() throws FacebookServiceException, IOException, JSONException {
        String requestUrl = this.rootUrl + "/" + this.userID + this.urlParams;
        HttpURLConnection connection = getConnection(requestUrl);
        JSONObject obj = JSONParser.parseJSON(connection.getInputStream());
        return obj.getString("name");
    }

    public String getUserProfilePhoto() throws IOException, FacebookServiceException, JSONException {
        String requestUrl = this.rootUrl + "/" + this.userID + "/picture" + this.urlParams;
        HttpURLConnection connection = getConnection(requestUrl);
        JSONObject obj = JSONParser.parseJSON(connection.getInputStream());
        return obj.getJSONObject("data").getString("url");
    }

}
