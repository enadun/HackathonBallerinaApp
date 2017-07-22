package com.fs.ballerinachatapp;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Dinithi on 7/22/2017.
 */

public class Util {

    public static String createResponseString(String username) {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("message-type","signin");
            jsonObject.put("username", username);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }
}
