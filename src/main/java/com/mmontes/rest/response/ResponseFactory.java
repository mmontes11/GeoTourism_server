package com.mmontes.rest.response;

import org.json.simple.JSONObject;

public class ResponseFactory {

    public static JSONObject getCustomJSON(String field,String value){
        JSONObject json = new JSONObject();
        json.put(field,value);
        return json;
    }
}
