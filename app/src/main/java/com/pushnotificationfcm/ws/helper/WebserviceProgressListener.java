package com.pushnotificationfcm.ws.helper;

public interface WebserviceProgressListener {

    void onProgressResponse(long fileId, long transferredBytes, long totalBytes);

}
