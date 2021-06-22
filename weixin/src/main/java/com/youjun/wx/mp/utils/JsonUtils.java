package com.youjun.wx.mp.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @author Binary Wang(https://github.com/binarywang)
 */
public class JsonUtils {
    public static Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .disableHtmlEscaping()
            .create();
    public static String toJson(Object obj) {
        return gson.toJson(obj);
    }

    public static void main(String[] args) {
        String jsonStr="{" +
                "    \"sites\": [" +
                "    { \"name\":\"菜鸟教程\" , \"url\":\"www.runoob.com\" }, " +
                "    { \"name\":\"google\" , \"url\":\"www.google.com\" }, " +
                "    { \"name\":\"微博\" , \"url\":\"www.weibo.com\" }" +
                "    ]" +
                "}";
//        List<String> fruitList = gson.fromJson(jsonStr, new TypeToken<List<String>>(){}.getType());
//        User user = gson.fromJson(jsonStr, User.class);
    }
}
