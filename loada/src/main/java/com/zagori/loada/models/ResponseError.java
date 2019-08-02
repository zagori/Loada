package com.zagori.loada.models;

public class ResponseError {

    private int code;
    private String data;

    public ResponseError() {
    }

    public ResponseError(int code, String data) {
        this.code = code;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
