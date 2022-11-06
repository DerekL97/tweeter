package edu.byu.cs.tweeter.client.model.net;

import com.google.gson.Gson;

import edu.byu.cs.tweeter.model.domain.User;

public class JsonSerializer {

    public static String serialize(Object requestInfo) {
        return (new Gson()).toJson(requestInfo);
    }

    public static <T> T deserialize(String value, Class<T> returnType) {
        return (new Gson()).fromJson(value, returnType);
    }
}
