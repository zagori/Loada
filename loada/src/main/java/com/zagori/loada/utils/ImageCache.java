package com.zagori.loada.utils;

import android.graphics.Bitmap;
import android.util.LruCache;

public class ImageCache extends LruCache<String, Bitmap> {

    private static final String TAG = ImageCache.class.getSimpleName();
    private static ImageCache instance;

    public static ImageCache get(){
        if (instance == null)
            instance = new ImageCache(Utilities.getDefaultCacheSize());

        return instance;
    }

    /*
    * Initiate cache with an allocated max memory
    * */
    private ImageCache(int maxSize) {
        super(maxSize);
    }

    /*
    * Initiate cache with a default max memory
    * */
    public ImageCache(){
        this(Utilities.getDefaultCacheSize());
    }


    /*
    * Measure item size in kilobytes rather than units,
    * which is more practical for a bitmap cache
    * */
    @Override
    protected int sizeOf(String key, Bitmap value) {
        int bitmapSize = value.getByteCount() / 1024;
        return bitmapSize == 0 ? 1: bitmapSize;
    }

    /*@Override
    protected int sizeOf(String key, Bitmap value) {
        return value.getRowBytes() * value.getHeight() / 1024;
    }*/

}
