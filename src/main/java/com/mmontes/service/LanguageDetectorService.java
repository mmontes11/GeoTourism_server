package com.mmontes.service;


import com.amazonaws.util.json.JSONObject;
import com.mmontes.util.Constants;
import com.mmontes.util.JSONParser;
import org.springframework.stereotype.Service;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

@Service("LanguageDetectorService")
public class LanguageDetectorService {

    public LanguageDetectorService() {
    }

    public String getLanguage(String keywords) throws Exception {
        String encodedKeywords = URLEncoder.encode(keywords, "UTF-8");
        String url = Constants.LANGUAGE_DETECTION_BASE_URL + "&q=" + encodedKeywords;
        URL obj = new URL(url);
        HttpURLConnection connnection = (HttpURLConnection) obj.openConnection();
        connnection.setRequestMethod("GET");
        connnection.setRequestProperty("User-Agent", "Mozilla/5.0");
        JSONObject jsonObject = JSONParser.parseJSON(connnection.getInputStream());

        return jsonObject.getJSONObject("data").getJSONArray("detections").getJSONObject(0).getString("language");
    }
}
