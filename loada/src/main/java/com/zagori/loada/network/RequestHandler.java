package com.zagori.loada.network;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.zagori.loada.interfaces.BytesListener;
import com.zagori.loada.interfaces.ImageListener;
import com.zagori.loada.interfaces.JsonArrayListener;
import com.zagori.loada.utils.Utilities;

import org.json.JSONArray;

/*
 * This class encapsulates the RequestQueue (and the other Volley functionality)
 *
 * */
public class RequestHandler {

    private static final String TAG = RequestHandler.class.getSimpleName();

    private static RequestHandler instance;
    private Context context;
    private RequestQueue requestQueue;


    public RequestHandler(Context context) {
        this.context = context;
    }

    public static synchronized RequestHandler getInstance(Context context) {
        if (instance == null) {
            instance = new RequestHandler(context);
        }
        return instance;
    }

    private <T> void addToRequestQueue(Request<T> request) {
        getRequestQueue().add(request);
    }

    /*
    * Initialise the RequestQueue
    * */
    private RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return requestQueue;
    }

    /*
     * This method makes a GET request to download image.
     * returns responseError or a success load in form of bitmap
     *
     * @param url, the URL to fetch the image.
     * @param callback, to receive the response.
     * */
    public void requestBitmap(String url, final ImageListener callback){
        ImageRequest imageRequest = new ImageRequest(url,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        callback.onSuccess(response);
                    }
                },0,0,//width and height
                ImageView.ScaleType.CENTER_INSIDE, // CENTER_CROP
                Bitmap.Config.RGB_565,// Image decode config
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callback.onError(Utilities.getError(error));
                    }
                });

        // Disable cache for volley
        imageRequest.setShouldCache(false);

        // Add the request to the RequestQueue.
        addToRequestQueue(imageRequest);
    }

    /*
     * This method makes GET request to download file of any data type.
     * returns responseError or a success load in form of byte[] which can
     * be parsed to any other data type
     *
     * @param url, the URL to fetch the data.
     * @param callback, to receive the response.
     * */
    public void requestBytes(String url, BytesListener callback){
        ByteRequest byteRequest = new ByteRequest(url,
                new Response.Listener<byte[]>() {
                    @Override
                    public void onResponse(byte[] response) {
                        callback.onSuccess(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callback.onError(Utilities.getError(error));
                    }
                });

        // Disable cache for volley
        byteRequest.setShouldCache(false);

        // Add the request to the RequestQueue.
        addToRequestQueue(byteRequest);
    }

    /*
     * Make basic Json Array request
     *this uses GET method by default
     *
     * @param url to fetch the json from.
     * @param callback, to receive the response.
     * */
    public void requestJsonArray(String url, final JsonArrayListener callback) {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        callback.onSuccess(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callback.onError(Utilities.getError(error));
                    }
                });

        // Disable cache for volley
        jsonArrayRequest.setShouldCache(false);

        // clearing cache
        //requestQueue.getCache().clear();

        // Add the request to the RequestQueue.
        addToRequestQueue(jsonArrayRequest);
    }

    /*
     * Make advanced Json Array request
     *
     * @param params, to post with this request. Null can be passed if there is no parameters
     *   indicates no parameters will be posted along with request.
     * @param headers, (carries e.g. apiKey...) to post with the request.
     * @param method, the HTTP method.
     * @param url to fetch the json from.
     * @param callback, to receive the response.
     * */
    /*public void requestJsonArray(@Nullable JSONArray params, @Nullable final Map<String, String> headers, int method,
                                     String url, final JsonArrayListener callback) {

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                method, url, params,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        callback.onSuccess(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callback.onError(error);
                    }
                }) {

            // passing some request headers if exist
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Log.d(TAG, "Original headers: " + super.getHeaders());
                if (headers != null) super.getHeaders().putAll(headers);
                Log.d(TAG, "Merged headers: " + super.getHeaders());

                return super.getHeaders();
            }
        };

        // Add the request to the RequestQueue.
        addToRequestQueue(jsonArrayRequest);
    }*/
}
