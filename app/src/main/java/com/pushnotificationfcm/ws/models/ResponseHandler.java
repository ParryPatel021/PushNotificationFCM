package com.pushnotificationfcm.ws.models;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.pushnotificationfcm.ws.WebConstants;


/**
 * Common Class to handle response of all the api.
 * Add classes in model according to response.
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseHandler {

    private String message;
    private String status;

    @JsonProperty("data")
    private DataObject dataObject;

    @JsonProperty("message")
    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @JsonProperty("status")
    public WebConstants.ResponseStatus getStatus() {
        return status.equalsIgnoreCase(WebConstants.ResponseStatus.SUCCESS.getStatus()) ?
                WebConstants.ResponseStatus.SUCCESS :
                WebConstants.ResponseStatus.FAILED;
    }
    public void setStatus(String status) {
        this.status = status;
    }


    public DataObject getDataObject() {
        return dataObject;
    }

    public void setDataObject(DataObject dataObject) {
        this.dataObject = dataObject;
    }
}

