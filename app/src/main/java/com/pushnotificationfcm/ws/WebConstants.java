package com.pushnotificationfcm.ws;

/**
 * common class for all the api urls and its constants.
 */

public class WebConstants {


    private static final String HOST_URL  = "";
    public static final String SERVER_MAIN_URL = HOST_URL + "";
    public static final String SERVICE_LOGIN="Login";
    public static final String SERVICE_REGISTRATION="RegisterUserDetails";

    public enum ResponseStatus {
        SUCCESS("success"),
        FAILED("failed");

        private final String status;

        ResponseStatus(final String status) {
            this.status = status;
        }

        public String getStatus() {
            return status;
        }

    }


}
