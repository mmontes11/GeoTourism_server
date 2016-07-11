package com.mmontes.util;


import com.mmontes.model.entity.TIP.TIP;
import com.mmontes.util.exception.InvalidTIPUrlException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class URLvalidator {

    private static final int SECONDS_TIMEOUT = 5;

    public static boolean isValidURL(String urlStr){
        if (urlStr == null || urlStr.isEmpty()){
            return true;
        }
        try {
            URL url = new URL(urlStr);
            URLConnection conn = url.openConnection();
            conn.setConnectTimeout(SECONDS_TIMEOUT * 1000);
            conn.connect();
            return true;
        } catch (MalformedURLException e) {
            return false;
        } catch (IOException e) {
            return false;
        }
    }

    public static void checkURLs(TIP tip) throws InvalidTIPUrlException {
        // FIXME: Es necesario lanzar excepciones? Así no se crea el tip. Con poner el valor a null pienso que valdría.
        if (tip.getPhotoUrl() != null && !URLvalidator.isValidURL(tip.getPhotoUrl())){
            throw new InvalidTIPUrlException("photo",tip.getPhotoUrl());
        }
        if (tip.getInfoUrl() != null && !URLvalidator.isValidURL(tip.getInfoUrl())){
            throw new InvalidTIPUrlException("info",tip.getInfoUrl());
        }
        if (tip.getGoogleMapsUrl() != null && !URLvalidator.isValidURL(tip.getGoogleMapsUrl())){
            throw new InvalidTIPUrlException("Google Maps",tip.getGoogleMapsUrl());
        }
    }
}
