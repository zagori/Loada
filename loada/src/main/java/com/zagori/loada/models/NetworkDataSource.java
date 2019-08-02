package com.zagori.loada.models;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import com.zagori.loada.interfaces.BytesListener;
import com.zagori.loada.interfaces.ImageListener;
import com.zagori.loada.interfaces.JsonArrayListener;
import com.zagori.loada.network.RequestHandler;

import org.json.JSONArray;

import io.reactivex.Observable;

/*
* This class produces the network data source
* */
public class NetworkDataSource {

    private static final String TAG = NetworkDataSource.class.getSimpleName();

    public Observable<byte[]> getBytes(String url, Context context) {
        return Observable.create(emitter -> {

            RequestHandler.getInstance(context).requestBytes(url,
                    new BytesListener(){
                        @Override
                        public void onSuccess(byte[] result) {
                            Log.d(TAG, "Response of ["+url+"] was loaded from Network.");
                            emitter.onNext(result);
                            emitter.onComplete();
                        }

                        @Override
                        public void onError(ResponseError error) {
                            Log.e(TAG, "onError: " + error);
                            emitter.onComplete();
                        }
                    });
        });
    }

    public Observable<JSONArray> getJsonArray(String url, Context context) {
        return Observable.create(emitter -> {

            RequestHandler.getInstance(context).requestJsonArray(url,
                    new JsonArrayListener() {
                        @Override
                        public void onSuccess(JSONArray result) {
                            Log.d(TAG, "Response of ["+url+"] was loaded from Network.");
                            emitter.onNext(result);
                            emitter.onComplete();
                        }

                        @Override
                        public void onError(ResponseError error) {
                            Log.e(TAG, "onError: " + error);
                            emitter.onComplete();
                        }
                    });
        });
    }

    public Observable<Bitmap> getBitmap(String url, Context context) {
        return Observable.create(emitter -> {

            RequestHandler.getInstance(context).requestBitmap(url,
                    new ImageListener() {
                        @Override
                        public void onSuccess(Bitmap bitmap) {
                            Log.d(TAG, "Response of ["+url+"] was loaded from Network.");
                            emitter.onNext(bitmap);
                            emitter.onComplete();
                        }

                        @Override
                        public void onError(ResponseError error) {
                            Log.e(TAG, "onError: " + error);
                            emitter.onComplete();
                        }
                    });
        });
    }
}
