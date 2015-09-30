package com.mmontes.model.service.external;

import com.mmontes.util.XMLParser;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class WikipediaService {

    public static String getWikipediaUrl(String domain, String name) throws Exception {
        String encodedName;
        try {
            encodedName = URLEncoder.encode(name, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return null;
        }
        String url = "https://" + domain + ".wikipedia.org/w/api.php?action=query&generator=search&gsrsearch=" + encodedName + "&format=xml&gsrprop=snippet&prop=info&inprop=url";
        URL obj = new URL(url);
        HttpURLConnection connnection = (HttpURLConnection) obj.openConnection();
        connnection.setRequestMethod("GET");
        connnection.setRequestProperty("User-Agent", "Mozilla/5.0");

        int responseCode = connnection.getResponseCode();
        if (responseCode >= 400) {
            throw new Exception("Request Error");
        }

        Document document = XMLParser.parseXml(connnection.getInputStream());
        Element node = (Element) document.getElementsByTagName("page").item(0);
        return node.getAttribute("fullurl");
    }
}
