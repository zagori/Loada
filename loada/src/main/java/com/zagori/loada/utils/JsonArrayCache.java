package com.zagori.loada.utils;

import android.util.LruCache;

import org.json.JSONArray;

public class JsonArrayCache extends LruCache<String, JSONArray>{

    private static final String TAG = ImageCache.class.getSimpleName();
    private static JsonArrayCache instance;

    public static JsonArrayCache get(){
        if (instance == null)
            instance = new JsonArrayCache(Utilities.getDefaultCacheSize());

        return instance;
    }

    /*
     * Initiate cache with an allocated max memory
     * */
    private JsonArrayCache(int maxSize) {
        super(maxSize);
    }

    /*
     * Initiate cache with a default max memory
     * */
    public JsonArrayCache(){
        this(Utilities.getDefaultCacheSize());
    }

    /*
     * Measure item size in kilobytes rather than units
     * */
    @Override
    protected int sizeOf(String key, JSONArray value) {
        int jsonArraySize = value.toString().getBytes().length;
        return jsonArraySize == 0 ? 1: jsonArraySize;
    }
}
