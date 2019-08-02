package com.zagori.loada.models;

import android.content.Context;
import android.graphics.Bitmap;
import org.json.JSONArray;
import io.reactivex.Observable;

/*
* This handles all data sources... So far, Only 2 data sources are used: Memory and Network
* */
public class DataSource {

    private MemoryDataSource memoryDataSource;
    private NetworkDataSource networkDataSource;

    public DataSource(MemoryDataSource memoryDataSource, NetworkDataSource networkDataSource) {
        this.memoryDataSource = memoryDataSource;
        this.networkDataSource = networkDataSource;
    }

    public Observable<JSONArray> getJsonArrayFromMemory(String url) {
        return memoryDataSource.getJsonArray(url);
    }

    public Observable<JSONArray> getJsonArrayFromNetwork(String url, Context context){
        return networkDataSource.getJsonArray(url, context).doOnNext(jsonArray -> {
            memoryDataSource.cacheInMemory(url, jsonArray);
        });
    }

    public Observable<Bitmap> getBitmapFromMemory(String url) {
        return memoryDataSource.getBitmap(url);
    }

    public Observable<Bitmap> getBitmapFromNetwork(String url, Context context){
        return networkDataSource.getBitmap(url, context).doOnNext(bitmap -> {
            memoryDataSource.cacheInMemory(url, bitmap);
        });
    }

    public Observable<byte[]> getBytesFromMemory(String url) {
        return memoryDataSource.getBytes(url);
    }

    public Observable<byte[]> getBytesFromNetwork(String url, Context context){
        return networkDataSource.getBytes(url, context).doOnNext(bytes -> {
            memoryDataSource.cacheInMemory(url, bytes);
        });
    }


}
