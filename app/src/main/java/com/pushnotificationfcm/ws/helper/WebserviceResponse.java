package com.pushnotificationfcm.ws.helper;

public interface WebserviceResponse {

    void onResponse(String url, Object object);
    void onErrorResponse(String url, Exception error, String errorMessage);
}
