package com.pushnotificationfcm.ws.helper;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.webkit.MimeTypeMap;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.pushnotificationfcm.ws.WebConstants;
import com.pushnotificationfcm.ws.models.Attribute;
import com.pushnotificationfcm.ws.models.CommonViewFileObject;
import com.pushnotificationfcm.ws.models.CommonViewInfo;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * WebserviceWrapperVolley class to make calls for all the api. IF too much long operation like download large file then better to use DownloadManager
 */
public class WebserviceWrapper {
    private RequestQueue mRequestQueue;
    private Context mContext;
    private WebserviceResponse mWebserviceResponse;
    private static final Lock lock = new ReentrantLock();
    private static ObjectMapper mapper = null;
    private ArrayList<RequestQueue> arrayRequestsAll = new ArrayList<>();

    public enum WebserviceCallType {
        QUEUE,
        PARALLEL
    }


    public WebserviceWrapper(Context context, WebserviceResponse webserviceResponse) {
        mContext = context;
        this.mWebserviceResponse = webserviceResponse;
    }


    /***
     *
     * @param tagRequest - Tag associated with given request - so we can use it to cancel request if needed
     */
    public void cancelRequestQueue(String tagRequest) {
        getRequestQueue().cancelAll(tagRequest);
        for (int i = 0; i < arrayRequestsAll.size(); i++) {
            arrayRequestsAll.get(i).cancelAll(tagRequest);
        }
    }


    private RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            mRequestQueue = Volley.newRequestQueue(mContext.getApplicationContext());
        }
        return mRequestQueue;
    }


    /***
     *     * Method used to call other API
     * @param url - API code
     * @param requestMethod - Request.METHOD.GET  / Request.METHOD.POST
     * @param requestObject - request object
     * @param responseType - Response type class name
     * @param tagRequest - Tag associated with given request - so later we can use it to cancel request if needed
     * @param arrayHeader - list of header
     * @param webserviceCallType - QUEUE OR PARALLEL
     */
    public void addOrCallRequest(@NonNull final String url, int requestMethod, Attribute requestObject,
                                 final Object responseType, String tagRequest, final Map<String, String> arrayHeader,
                                 @NonNull WebserviceCallType webserviceCallType) {
        ObjectWriter writer = getMapper().writer();
        String jsonObject = "";
        try {
            if (requestObject != null) {
                jsonObject = writer.writeValueAsString(requestObject);
                Log.i("request object1",jsonObject);
            }

            final JsonObjectRequest requestJson = new JsonObjectRequest(requestMethod, WebConstants.SERVER_MAIN_URL + url,
                    jsonObject.length() > 0 ? new JSONObject(jsonObject) : null,
                    new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            Log.i(" onResponse1",""+response);
                            try {
                                if (responseType == JSONObject.class) {
                                    mWebserviceResponse.onResponse(url, response);
                                } else {
                                    mWebserviceResponse.onResponse(url, getMapper().readValue(response.toString(),
                                            (Class<Object>) responseType));
                                }
                            } catch (IOException error) {
                                error.printStackTrace();
                                mWebserviceResponse.onErrorResponse(url, error, "Error in parsing response");
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            String message = null;
                            if (error instanceof NetworkError) {
                                message = "Cannot connect to Internet...Please check your connection!";
                            } else if (error instanceof ServerError) {
                                message = "The server could not be found. Please try again after some time!!";
                            } else if (error instanceof AuthFailureError) {
                                message = "Cannot connect to Internet...Please check your connection!";
                            } else if (error instanceof ParseError) {
                                message = "Parsing error! Please try again after some time!!";
                            } else if (error instanceof NoConnectionError) {
                                message = "Cannot connect to Internet...Please check your connection!";
                            } else if (error instanceof TimeoutError) {
                                message = "Connection TimeOut! Please check your internet connection.";
                            }
                            mWebserviceResponse.onErrorResponse(url, error, message);
                        }
                    }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    if (arrayHeader == null)
                        return super.getHeaders();
                    else {
                        return arrayHeader;
                    }
                }
            };
            requestJson.setTag(tagRequest);
            requestJson.setRetryPolicy(new DefaultRetryPolicy(
                    2000 * 60,//2 min time out
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            /*
             * We are adding request to queue or parallel calling based on request type
             */
            if (webserviceCallType == WebserviceCallType.QUEUE) {
                getRequestQueue().add(requestJson);
            } else {
                RequestQueue requestQueue = Volley.newRequestQueue(mContext);
                arrayRequestsAll.add(requestQueue);
                requestQueue.add(requestJson);
            }
        } catch (JsonProcessingException | JSONException error) {
            error.printStackTrace();
            mWebserviceResponse.onErrorResponse(url, error, "Please try again");
        }
    }

    /****
     * Method used to send multipart data
     * @param url  - API code
     * @param requestObject - request object
     * @param responseType - Response type class name
     * @param tagRequest - Tag associated with given request - so later we can use it to cancel request if needed
     * @param arrayHeader - list of header
     * @param webserviceCallType - QUEUE OR PARALLEL
     * @param webserviceProgressListener - implement this listener in caller activity if need progress of image attachment
     */
//    public void addOrCallMultiPartRequest(@NonNull final String url, final Attribute requestObject,
//                                          final Object responseType, String tagRequest, final Map<String, String> arrayHeader,
//                                          @NonNull WebserviceCallType webserviceCallType,
//                                          final WebserviceProgressListener webserviceProgressListener) {
//        MultipartRequest multipartRequest = new MultipartRequest(WebConstants.SERVER_MAIN_URL + url,
//                arrayHeader,
//                new Response.Listener<NetworkResponse>() {
//                    @Override
//                    public void onResponse(NetworkResponse response) {
//                        String resultResponse = new String(response.data);
//                        try {
//                            if (responseType == JSONObject.class) {
//                                mWebserviceResponse.onResponse(url, new JSONObject(resultResponse));
//                            } else {
//                                mWebserviceResponse.onResponse(url, getMapper().readValue(resultResponse, (Class<Object>) responseType));
//                            }
//                        } catch (Exception error) {
//                            mWebserviceResponse.onErrorResponse(url, error, "Error in parsing response");
//                        }
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        String message = null;
//                        if (error instanceof NetworkError) {
//                            message = "Cannot connect to Internet...Please check your connection!";
//                        } else if (error instanceof ServerError) {
//                            message = "The server could not be found. Please try again after some time!!";
//                        } else if (error instanceof AuthFailureError) {
//                            message = "Cannot connect to Internet...Please check your connection!";
//                        } else if (error instanceof ParseError) {
//                            message = "Parsing error! Please try again after some time!!";
//                        } else if (error instanceof NoConnectionError) {
//                            message = "Cannot connect to Internet...Please check your connection!";
//                        } else if (error instanceof TimeoutError) {
//                            message = "Connection TimeOut! Please check your internet connection.";
//                        }
//                        mWebserviceResponse.onErrorResponse(url, error, message);
//
//                    }
//                },
//                new WebserviceProgressListener() {
//                    @Override
//                    public void onProgressResponse(long fileId, long transferredBytes, long totalBytes) {
//                        if (webserviceProgressListener != null)
//                            webserviceProgressListener.onProgressResponse(fileId, totalBytes, transferredBytes);
//                    }
//                }) {
//            @Override
//            protected Map<String, ArrayList<DataPart>> getByteData() throws AuthFailureError {
//                if (requestObject.getArrayFiles() != null && requestObject.getArrayFiles().size() > 0) {
//                    Map<String, ArrayList<DataPart>> dataPart = new HashMap<>();
//                    for (int i = 0; i < requestObject.getArrayFiles().size(); i++) {
//                        if (requestObject.getArrayFiles().get(i).getItem_val() instanceof CommonViewFileObject) {
//                            CommonViewFileObject commonViewFileObject = (CommonViewFileObject) requestObject.getArrayFiles().get(i).getItem_val();
//                            File file = new File(commonViewFileObject.getFile_path());
//                            int size = (int) file.length();
//                            byte[] bytes = new byte[size];
//                            try {
//                                BufferedInputStream buf = new BufferedInputStream(new FileInputStream(file));
//                                buf.read(bytes, 0, bytes.length);
//                                buf.close();
//                                ArrayList<DataPart> arrayFiles = new ArrayList<>();
//                                DataPart fileData = new DataPart(file.getName(),
//                                        bytes,
//                                        getMimeType(commonViewFileObject.getFile_path()),
//                                        commonViewFileObject.getFile_id());
//                                arrayFiles.add(fileData);
//                                dataPart.put(requestObject.getArrayFiles().get(i).getItem_head(), arrayFiles);
//                            } catch (Exception error) {
//                                mWebserviceResponse.onErrorResponse(url, error, "Error in uploading data");
//                            }
//                        } else if (requestObject.getArrayFiles().get(i).getItem_val() instanceof ArrayList) {
//                            ArrayList<DataPart> arrayFiles = new ArrayList<>();
//                            ArrayList<CommonViewFileObject> arrayFilesInfo = (ArrayList<CommonViewFileObject>) requestObject.getArrayFiles().get(i).getItem_array();
//                            for (int j = 0; j < arrayFilesInfo.size(); j++) {
//                                CommonViewFileObject commonViewFileObject = (CommonViewFileObject) arrayFilesInfo.get(j);
//                                File file = new File(commonViewFileObject.getFile_path());
//                                int size = (int) file.length();
//                                byte[] bytes = new byte[size];
//                                try {
//                                    BufferedInputStream buf = new BufferedInputStream(new FileInputStream(file));
//                                    buf.read(bytes, 0, bytes.length);
//                                    buf.close();
//                                    DataPart fileData = new DataPart(file.getName(),
//                                            bytes,
//                                            getMimeType(commonViewFileObject.getFile_path()),
//                                            commonViewFileObject.getFile_id());
//                                    arrayFiles.add(fileData);
//                                } catch (Exception error) {
//                                    mWebserviceResponse.onErrorResponse(url, error, "Error in uploading data");
//                                }
//                            }
//                            dataPart.put(requestObject.getArrayFiles().get(i).getItem_head(), arrayFiles);
//                        }
//                    }
//                    return dataPart;
//                } else {
//                    return super.getByteData();
//                }
//            }
//
//
//            @Override
//            protected ArrayList<CommonViewInfo> getMultiPartPayloadData() throws AuthFailureError {
//                if (requestObject.getArrayParams() != null && requestObject.getArrayParams().size()
//                        > 0) {
//                    return requestObject.getArrayParams();
//
//                } else {
//                    return super.getMultiPartPayloadData();
//                }
//            }
//
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                return super.getParams();
//            }
//        };
//        /*
//         * We are adding request to queue or parallel calling based on request type
//         */
//        multipartRequest.setTag(tagRequest);
//
//        if (webserviceCallType == WebserviceCallType.QUEUE) {
//            getRequestQueue().add(multipartRequest);
//        } else {
//            RequestQueue requestQueue = Volley.newRequestQueue(mContext);
//            arrayRequestsAll.add(requestQueue);
//            requestQueue.add(multipartRequest);
//        }
//    }
//

    private synchronized ObjectMapper getMapper() {
        if (mapper != null) {
            return mapper;
        }
        lock.lock();
        if (mapper == null) {
            mapper = new ObjectMapper();
            mapper.configure(SerializationFeature.WRITE_NULL_MAP_VALUES,
                    false);
        }
        lock.unlock();
        return mapper;
    }


    /***
     * @param url - url of which mime type required
     * @return - mime type
     */
    private String getMimeType(String url) {
        String type = null;
        String extension = MimeTypeMap.getFileExtensionFromUrl(url);
        if (extension != null) {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        }
        return type;
    }
}
