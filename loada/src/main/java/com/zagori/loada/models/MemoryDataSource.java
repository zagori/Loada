package com.zagori.loada.models;

import android.graphics.Bitmap;

import com.zagori.loada.utils.BytesCache;
import com.zagori.loada.utils.ImageCache;
import com.zagori.loada.utils.JsonArrayCache;

import org.json.JSONArray;

import io.reactivex.Observable;

/*
 * This produces in-memory data source
 * */
public class MemoryDataSource {

    private JsonArrayCache jsonArrayCache;
    private ImageCache imageCache;
    private BytesCache bytesCache;

    public MemoryDataSource(){
        this.jsonArrayCache = JsonArrayCache.get();
        this.imageCache = ImageCache.get();
        this.bytesCache = BytesCache.get();
    }

    public Observable<JSONArray> getJsonArray(String url) {
        return Observable.create(emitter -> {

            // get from memory
            JSONArray jsonArray = jsonArrayCache.get(url);

            if (jsonArray != null){
                System.out.println("Response of ["+url+"] was loaded from Memory.");
                emitter.onNext(jsonArray);
            }
            emitter.onComplete();
        });
    }

    public void cacheInMemory(String url, JSONArray jsonArray) {
        jsonArrayCache.put(url, jsonArray);
    }

    public Observable<Bitmap> getBitmap(String url) {
        return Observable.create(emitter -> {

            // get from memory
            Bitmap bitmap = imageCache.get(url);

            if (bitmap != null){
                System.out.println("Response of ["+url+"] was loaded from Memory.");
                emitter.onNext(bitmap);
            }
            emitter.onComplete();
        });
    }

    public void cacheInMemory(String url, Bitmap bitmap) {
        imageCache.put(url, bitmap);
    }


    public Observable<byte[]> getBytes(String url) {
        return Observable.create(emitter -> {

            // get from memory
            byte[] bytes = bytesCache.get(url);

            if (bytes != null){
                System.out.println("Response of ["+url+"] was loaded from Memory.");
                emitter.onNext(bytes);
            }
            emitter.onComplete();
        });
    }

    public void cacheInMemory(String url, byte[] bytes) {
        bytesCache.put(url, bytes);
    }
}
