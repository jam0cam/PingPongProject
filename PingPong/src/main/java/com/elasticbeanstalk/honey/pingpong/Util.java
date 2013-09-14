package com.elasticbeanstalk.honey.pingpong;

import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by jitse on 9/11/13.
 */
public class Util {
    public static <T> T fromJSON(Object object, Class<T> clazz) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            Object o = mapper.readValue(object.toString(), clazz);
            return (T)o;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static JSONObject toJsonObject(Object o) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            String s = mapper.writeValueAsString(o);
            return new JSONObject(s);
        } catch (Exception e) {
            return null;
        }
    }
}
