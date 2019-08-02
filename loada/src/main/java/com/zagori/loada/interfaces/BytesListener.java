package com.zagori.loada.interfaces;

import com.zagori.loada.models.ResponseError;

public interface BytesListener {

    void onSuccess(byte[] result);
    void onError(ResponseError error);
}
