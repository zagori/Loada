package com.zagori.loada.network;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.zagori.loada.interfaces.RequestMethod;

import java.util.Map;

public class ByteRequest extends Request<byte[]> {

    private Listener<byte[]> listener;

    public ByteRequest(int method, String url, Listener<byte[]> listener ,
                       Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        this.listener = listener;

        // disable volley cache
        setShouldCache(false);
    }

    /*
    * default request method is GET
    * */
    public ByteRequest(String url, Listener<byte[]> listener ,
                       Response.ErrorListener errorListener) {
        super(RequestMethod.Get, url, errorListener);
        this.listener = listener;

        // disable volley cache
        setShouldCache(false);
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return super.getHeaders();
    }

    @Override
    public void deliverError(VolleyError error) {
        super.deliverError(error);
    }

    @Override
    protected void deliverResponse(byte[] response) {

    }

    @Override
    protected Response<byte[]> parseNetworkResponse(NetworkResponse response) {

        return Response.success(response.data, HttpHeaderParser.parseCacheHeaders(response));
        //return null;
    }
}
