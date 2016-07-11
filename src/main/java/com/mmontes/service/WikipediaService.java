package com.mmontes.service;

import com.amazonaws.util.json.JSONArray;
import com.mmontes.rest.response.WikipediaResult;
import com.mmontes.util.Constants;
import com.mmontes.util.JSONParser;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@Service("WikipediaService")
public class WikipediaService {

    public WikipediaService() {
    }

    public List<WikipediaResult> search(String language, String keywords) throws Exception {
        String encodedName;
        try {
            encodedName = URLEncoder.encode(keywords, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return null;
        }
        String url = "https://" + language + Constants.WIKIPEDIA_SEARCH_URL + encodedName
                + "&limit=10&namespace=0&format=json";
        URL obj = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("User-Agent", "Mozilla/5.0");

        int responseCode = connection.getResponseCode();
        if (responseCode >= 400) {
            throw new Exception("Request to Wikipedia failed");
        }

        JSONArray jsonArray = JSONParser.parseJSONArray(connection.getInputStream());
        JSONArray names = jsonArray.getJSONArray(1);
        JSONArray urls = jsonArray.getJSONArray(3);

        List<WikipediaResult> results = new ArrayList<>();

        for (int i = 0; i < names.length(); i++) {
            WikipediaResult result = new WikipediaResult();
            result.setTitle(names.getString(i));
            result.setUrl(urls.getString(i));
            results.add(result);
        }
        return results;
    }
}
