package com.zagori.loada.interfaces;

import android.graphics.Bitmap;

import com.zagori.loada.models.ResponseError;

public interface ImageListener {
    void onSuccess(Bitmap bitmap);
    void onError(ResponseError error);
}
