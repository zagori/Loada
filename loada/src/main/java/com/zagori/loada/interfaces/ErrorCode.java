package com.zagori.loada.interfaces;

public interface ErrorCode {
    int NoConnectionError = 0;
    int TimeoutError = 1;
    int NetworkError = 2;
    int AuthFailureError = 3;
    int ServerError = 4;
    int ParseError = 5;
}
