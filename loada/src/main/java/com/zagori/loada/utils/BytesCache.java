package com.zagori.loada.utils;

import android.util.LruCache;

public class BytesCache extends LruCache<String, byte[]> {

    private static final String TAG = BytesCache.class.getSimpleName();
    private static BytesCache instance;

    public static BytesCache get(){
        if (instance == null)
            instance = new BytesCache(Utilities.getDefaultCacheSize());

        return instance;
    }

    /*
     * Initiate cache with an allocated max memory
     * */
    private BytesCache(int maxSize) {
        super(maxSize);
    }

    /*
     * Initiate cache with a default max memory
     * */
    public BytesCache(){
        this(Utilities.getDefaultCacheSize());
    }

    /*
     * Measure item size in kilobytes rather than units
     * */
    @Override
    protected int sizeOf(String key, byte[] value) {
        int bytesSize = value.length;
        return bytesSize == 0 ? 1: bytesSize;
    }
}
