package com.zagori.loada;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ImageView;

import com.zagori.loada.interfaces.BytesListener;
import com.zagori.loada.interfaces.ImageErrorListener;
import com.zagori.loada.interfaces.ImageListener;
import com.zagori.loada.interfaces.JsonArrayListener;
import com.zagori.loada.models.DataSource;
import com.zagori.loada.models.MemoryDataSource;
import com.zagori.loada.models.NetworkDataSource;
import com.zagori.loada.network.RequestHandler;

import org.json.JSONArray;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

public class Loada {

    private static final String TAG = Loada.class.getSimpleName();

    private Context context;
    private DataSource dataSource;

    public static Loada get(Context context) {
        return new Loada(context);
    }

    private Loada(Context context) {
        this.context = context;
        this.dataSource = new DataSource(new MemoryDataSource(), new NetworkDataSource());
    }

    /*
    * This method allows to cancel a pending request
    *
    * @param url is the URL of the data
    * */
    public void cancelDownloadRequest(String url){
        RequestHandler.getInstance(context).cancelRequest(url);
    }

    /*
     * This method send request to download raw data, it returns an error
     * or a success load which has a byte[]
     *
     * @param url is the URL of the data
     * @param callback is the response listener which delivers error or success
     * */
    public void loadRawData(String url, BytesListener callback){
        Observable<byte[]> memory = dataSource.getBytesFromMemory(url);
        Observable<byte[]> network = dataSource.getBytesFromNetwork(url, context);

        Observable.concat(memory, network)
                .firstElement()
                .subscribeOn(AndroidSchedulers.mainThread())
                .toObservable()
                .subscribe(new Observer<byte[]>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        //Log.d(TAG, " onSubscribe : " + d.isDisposed());
                    }

                    @Override
                    public void onNext(byte[] bytes) {
                        //Log.d(TAG, " onNext : " + bytes);
                        callback.onSuccess(bytes);
                    }

                    @Override
                    public void onError(Throwable e) {
                        //Log.e(TAG, " onError : " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        //Log.d(TAG, " onComplete");
                    }
                });
    }

    /*
     * This method send request to download jasonArray, it returns an error
     * or a success load which has a bitmap
     *
     * @param url is the URL of the image
     * @param callback is the response listener which delivers error or success
     * */
    public void loadJsonArray(String url, JsonArrayListener callback) {

        Observable<JSONArray> memory = dataSource.getJsonArrayFromMemory(url);
        Observable<JSONArray> network = dataSource.getJsonArrayFromNetwork(url, context);

        Observable.concat(memory, network)
                .firstElement()
                //.subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .toObservable()
                .subscribe(new Observer<JSONArray>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        //Log.d(TAG, " onSubscribe : " + d.isDisposed());
                    }

                    @Override
                    public void onNext(JSONArray jsonArray) {
                        //Log.d(TAG, " onNext : " + jsonArray);
                        callback.onSuccess(jsonArray);
                    }

                    @Override
                    public void onError(Throwable e) {
                        //Log.e(TAG, " onError : " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        //Log.d(TAG, " onComplete");
                    }
                });
    }

    /*
     * This method send request to download images, it returns an error
     * or a success load which has a bitmap
     *
     * @param url is the URL of the image
     * @param callback is the response listener which delivers error or success
     * */
    public void loadImage(String url, ImageListener callback) {

        Observable<Bitmap> memory = dataSource.getBitmapFromMemory(url);
        Observable<Bitmap> network = dataSource.getBitmapFromNetwork(url, context);

        Observable.concat(memory, network)
                .firstElement()
                .subscribeOn(AndroidSchedulers.mainThread())
                //.subscribeOn(Schedulers.io())
                .toObservable()
                .subscribe(new Observer<Bitmap>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, " onSubscribe : " + d.isDisposed());
                    }

                    @Override
                    public void onNext(Bitmap bitmap) {
                        Log.d(TAG, " onNext : " + bitmap);
                        callback.onSuccess(bitmap);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, " onError : " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, " onComplete");
                    }
                });
    }


    /*
     * This method send request to download images,
     * it set the bitmap to the passed imageView or returns an error
     *
     * @param url is the URL of the image
     * @param imageView is view where the image is to be displayed
     * @param callback is the response listener which delivers error or success
     * */
    public void loadImage(String url, ImageView imageView, ImageErrorListener callback) {

        Observable<Bitmap> memory = dataSource.getBitmapFromMemory(url);
        Observable<Bitmap> network = dataSource.getBitmapFromNetwork(url, context);

        Observable.concat(memory, network)
                .firstElement()
                .subscribeOn(AndroidSchedulers.mainThread())
                .toObservable()
                .subscribe(new Observer<Bitmap>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, " onSubscribe : " + d.isDisposed());
                    }

                    @Override
                    public void onNext(Bitmap bitmap) {
                        //Log.d(TAG, " onNext : " + bitmap);
                        imageView.setImageBitmap(bitmap);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, " onError : " + e.getMessage());
                        callback.onError(e);
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, " onComplete");
                    }
                });
    }

}
