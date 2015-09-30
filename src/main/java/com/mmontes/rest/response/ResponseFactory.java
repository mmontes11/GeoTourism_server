package com.mmontes.rest.response;

import org.json.simple.JSONObject;

public class ResponseFactory {

    public static JSONObject geCreatedResponse(Long id){
        JSONObject json = new JSONObject();
        json.put("ID",id);
        return json;
    }

    public static JSONObject getErrorResponse(String errorMessage){
        JSONObject json = new JSONObject();
        json.put("error",errorMessage);
        return json;
    }
}
