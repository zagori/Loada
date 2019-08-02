package com.zagori.loada.utils;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.zagori.loada.interfaces.ErrorCode;
import com.zagori.loada.models.ResponseError;

public class Utilities {

    private static final String TAG = Utilities.class.getSimpleName();

    public static ResponseError getError(VolleyError error){

        ResponseError responseError = new ResponseError();

        if (error instanceof TimeoutError) {
            //This indicates that the request has time out
            responseError.setCode(ErrorCode.TimeoutError);
            if (error.networkResponse != null)
                responseError.setData(error.networkResponse.toString());

        } else if (error instanceof NoConnectionError) {
            //This indicates that there is no connection
            responseError.setCode(ErrorCode.NoConnectionError);
            if (error.networkResponse != null)
                responseError.setData(error.networkResponse.toString());

        } else if (error instanceof AuthFailureError) {
            //Error indicating that there was an Authentication Failure while performing the request
            responseError.setCode(ErrorCode.AuthFailureError);
            if (error.networkResponse != null)
                responseError.setData(error.networkResponse.toString());

        } else if (error instanceof ServerError) {
            //Indicates that the server responded with a error response
            responseError.setCode(ErrorCode.ServerError);
            if (error.networkResponse != null && error.networkResponse.data != null)
                responseError.setData(new String(error.networkResponse.data));

        } else if (error instanceof NetworkError) {
            //Indicates that there was network error while performing the request
            responseError.setCode(ErrorCode.NetworkError);
            if (error.networkResponse != null)
                responseError.setData(error.networkResponse.toString());

        } else if (error instanceof ParseError) {
            // Indicates that the server response could not be parsed
            responseError.setCode(ErrorCode.ParseError);
            if (error.networkResponse != null)
                responseError.setData(error.networkResponse.toString());
        }

        return responseError;
    }

    public static int getDefaultCacheSize(){

        // Get the maximum amount of memory that the virtual machine will attempt to use,
        // exceeding this amount will throw an OutOfMemory exception.
        // Stored in kilobytes as LruCache takes an int in its constructor.
        int defaultMaxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);

        Log.d(TAG, "max memory: " + defaultMaxMemory);
        // return 1/8th of the available memory
        // Return 1/8th of the available memory as a max memory for the cache
        return defaultMaxMemory / 8; // return cacheSize
    }


}
