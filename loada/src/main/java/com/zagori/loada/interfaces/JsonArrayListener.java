package com.zagori.loada.interfaces;

import com.zagori.loada.models.ResponseError;

import org.json.JSONArray;

public interface JsonArrayListener {
    void onSuccess(JSONArray result);
    void onError(ResponseError error);
}
