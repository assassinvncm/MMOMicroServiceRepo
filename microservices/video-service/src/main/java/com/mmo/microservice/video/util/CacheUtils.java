package com.mmo.microservice.video.util;

import java.util.HashMap;
import java.util.Map;

public class CacheUtils {

    public static Map<Long, String> pageTokenMap = new HashMap<>();

    public static String getToken(Long id) {
        String r = pageTokenMap.get(id);
        return r;
    }

    public static void putToken(Long id, String token) {
        pageTokenMap.put(id, token);
    }
}
