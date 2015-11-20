package com.mmontes.service;

import com.mmontes.rest.response.WikipediaResult;
import com.mmontes.util.XMLParser;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

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
        String url = "https://" + language + ".wikipedia.org/w/api.php?action=query&generator=search&gsrsearch="
                + encodedName + "&format=xml&gsrprop=snippet&prop=info&inprop=url";
        URL obj = new URL(url);
        HttpURLConnection connnection = (HttpURLConnection) obj.openConnection();
        connnection.setRequestMethod("GET");
        connnection.setRequestProperty("User-Agent", "Mozilla/5.0");

        int responseCode = connnection.getResponseCode();
        if (responseCode >= 400) {
            throw new Exception("Request to Wikipedia failed");
        }

        Document document = XMLParser.parseXml(connnection.getInputStream());
        NodeList nodes = document.getElementsByTagName("page");
        List<WikipediaResult> results = new ArrayList<>();

        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);
            WikipediaResult result = new WikipediaResult();
            result.setTitle(node.getAttributes().getNamedItem("title").getNodeValue());
            result.setUrl(node.getAttributes().getNamedItem("fullurl").getNodeValue());
            results.add(result);
        }
        return results;
    }
}
